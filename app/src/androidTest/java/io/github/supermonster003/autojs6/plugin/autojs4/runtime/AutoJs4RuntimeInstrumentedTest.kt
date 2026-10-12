package io.github.supermonster003.autojs6.plugin.autojs4.runtime

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.os.IBinder
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import org.autojs.plugin.autojs4.runtime.api.AutoJs4PluginActions
import org.autojs.plugin.autojs4.runtime.api.AutoJs4PluginCapabilityKeys
import org.autojs.plugin.autojs4.runtime.api.AutoJs4PluginIds
import org.autojs.plugin.autojs4.runtime.api.AutoJs4RuntimeContract
import org.autojs.plugin.autojs4.runtime.api.IAutoJs4RuntimeCallback
import org.autojs.plugin.autojs4.runtime.api.IAutoJs4RuntimePlugin
import org.autojs.plugin.common.api.PluginCapabilityKeys
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.File
import java.util.UUID
import java.util.concurrent.CountDownLatch
import java.util.concurrent.CopyOnWriteArrayList
import java.util.concurrent.TimeUnit
import java.util.concurrent.atomic.AtomicReference

@RunWith(AndroidJUnit4::class)
class AutoJs4RuntimeInstrumentedTest {
    private val context: Context = InstrumentationRegistry.getInstrumentation().targetContext

    @Before
    fun assertRequestedApiLevel() {
        InstrumentationRegistry.getArguments()
            .getString(REQUIRED_API_LEVEL_ARGUMENT)
            ?.toIntOrNull()
            ?.let { expected ->
                assertEquals(
                    "Instrumentation ran on API ${Build.VERSION.SDK_INT}, but API $expected was required",
                    expected,
                    Build.VERSION.SDK_INT,
                )
            }
    }

    @Test
    fun manifestContractsAreDiscoverable() {
        val packageManager = context.packageManager
        val runtimeServices = packageManager.queryIntentServices(
            Intent(AutoJs4PluginActions.RUNTIME).addCategory(AutoJs4PluginActions.CATEGORY).setPackage(context.packageName),
            PackageManager.GET_META_DATA,
        )
        assertEquals(1, runtimeServices.size)
        val runtimeService = runtimeServices.single().serviceInfo
        assertEquals(AutoJs4RuntimeService::class.java.name, runtimeService.name)
        assertTrue(runtimeService.exported)
        assertEquals(AutoJs4RuntimeContract.PLUGIN_PERMISSION, runtimeService.permission)
        assertEquals("${context.packageName}:autojs4_runtime", runtimeService.processName)

        val infoServices = packageManager.queryIntentServices(
            Intent(AutoJs4PluginActions.INFO).addCategory(AutoJs4PluginActions.CATEGORY).setPackage(context.packageName),
            0,
        )
        assertEquals(1, infoServices.size)
        assertEquals(AutoJs4PluginInfoService::class.java.name, infoServices.single().serviceInfo.name)

        val applicationInfo = packageManager.getApplicationInfo(context.packageName, PackageManager.GET_META_DATA)
        val wakeName = applicationInfo.metaData.getString("org.autojs.plugin.WAKE_ACTIVITY")
        assertEquals(".WakeActivity", wakeName)
        val wakeActivity = packageManager.getActivityInfo(ComponentName(context, WakeActivity::class.java), 0)
        assertTrue(wakeActivity.exported)
        assertEquals(AutoJs4RuntimeContract.PLUGIN_PERMISSION, wakeActivity.permission)
        assertNotNull(packageManager.getLaunchIntentForPackage(context.packageName))
    }

    @Test
    fun infoRuntimeInfoAndScriptRoundTrip() {
        withBoundRuntime { runtime ->
            val info = runtime.info
            assertEquals(context.getString(R.string.plugin_name), info.name)
            assertEquals(context.getString(R.string.plugin_description), info.description)
            assertEquals(AutoJs4PluginIds.ID, info.id)
            assertEquals(AutoJs4PluginIds.ENGINE, info.engine)
            assertEquals(AutoJs4PluginIds.VARIANT_DEFAULT, info.variant)
            assertEquals("@raw/plugin_instruction", info.instruction)
            val packagedAbis = context.packagedRuntimeAbis().toSet()
            assertTrue(packagedAbis.isNotEmpty())
            assertEquals(packagedAbis, info.supportedAbis.orEmpty().toSet())
            val capabilities = requireNotNull(info.capabilities)
            assertEquals(AutoJs4RuntimeContract.REQUIRES_HOST_VERSION, capabilities.getLong(PluginCapabilityKeys.REQUIRES_HOST_VERSION))
            assertEquals(AutoJs4RuntimeContract.PROTOCOL_VERSION, capabilities.getInt(AutoJs4PluginCapabilityKeys.CONTRACT_VERSION))
            assertTrue(capabilities.getBoolean(AutoJs4PluginCapabilityKeys.SUPPORTS_UI_MODE))
            assertTrue(capabilities.getBoolean(AutoJs4PluginCapabilityKeys.SUPPORTS_CANCELLATION))

            val runtimeInfo = runtime.runtimeInfo
            assertTrue(runtimeInfo.getBoolean(AutoJs4RuntimeContract.KEY_RUNTIME_READY))
            assertEquals("${context.packageName}:autojs4_runtime", runtimeInfo.getString(AutoJs4RuntimeContract.KEY_PROCESS_NAME))
            assertEquals(AutoJs4RuntimeContract.RHINO_VERSION, runtimeInfo.getString(AutoJs4RuntimeContract.KEY_RHINO_VERSION))

            val marker = "autojs4-marker-${UUID.randomUUID()}"
            val script = writeScript(
                "round-trip.js",
                """
                    console.log("$marker");
                    var sum = 0;
                    for (var i = 1; i <= 10; i++) sum += i;
                    console.warn("sum=" + sum);
                    sum;
                """.trimIndent(),
            )
            val run = runScript(runtime, script)
            assertTrue(run.acceptance.getBoolean(AutoJs4RuntimeContract.KEY_ACCEPTED))
            assertFalse(run.acceptance.getBoolean(AutoJs4RuntimeContract.KEY_UI_MODE))
            val terminal = run.awaitTerminal()
            assertTrue(run.describe(), terminal.getBoolean(AutoJs4RuntimeContract.KEY_SUCCEEDED))
            assertFalse(terminal.getBoolean(AutoJs4RuntimeContract.KEY_CANCELLED))
            assertTrue(run.describe(), run.logs.any { it.contains(marker) })
            assertTrue(run.describe(), run.logs.any { it.contains("sum=55") })
            assertTrue(run.started.count == 0L)

            val failing = runScript(runtime, writeScript("failing.js", "throw new Error('expected-failure');"))
            assertTrue(failing.acceptance.getBoolean(AutoJs4RuntimeContract.KEY_ACCEPTED))
            val failure = failing.awaitTerminal()
            assertFalse(failure.getBoolean(AutoJs4RuntimeContract.KEY_SUCCEEDED))
            assertEquals(AutoJs4RuntimeContract.ERROR_SCRIPT_EXCEPTION, failure.getString(AutoJs4RuntimeContract.KEY_ERROR_CODE))
            assertTrue(failure.getString(AutoJs4RuntimeContract.KEY_ERROR_MESSAGE).orEmpty().contains("expected-failure"))
        }
    }

    @Test
    fun cancellationAndRequestValidation() {
        withBoundRuntime { runtime ->
            val invalid = runtime.runScript(Bundle().apply { putInt(AutoJs4RuntimeContract.KEY_PROTOCOL_VERSION, 99) }, NoopCallback())
            assertFalse(invalid.getBoolean(AutoJs4RuntimeContract.KEY_ACCEPTED))
            assertEquals(AutoJs4RuntimeContract.ERROR_INVALID_REQUEST, invalid.getString(AutoJs4RuntimeContract.KEY_ERROR_CODE))

            val missing = runScript(runtime, File(context.cacheDir, "does-not-exist.js"))
            assertFalse(missing.acceptance.getBoolean(AutoJs4RuntimeContract.KEY_ACCEPTED))
            assertEquals(AutoJs4RuntimeContract.ERROR_FILE_NOT_FOUND, missing.acceptance.getString(AutoJs4RuntimeContract.KEY_ERROR_CODE))

            val preCancelledId = "cancel-before-${UUID.randomUUID()}"
            assertTrue(runtime.cancelScript(preCancelledId))
            val preCancelled = runScript(runtime, writeScript("never.js", "throw new Error('must-not-run');"), executionId = preCancelledId)
            assertFalse(preCancelled.acceptance.getBoolean(AutoJs4RuntimeContract.KEY_ACCEPTED))
            assertEquals(AutoJs4RuntimeContract.ERROR_CANCELLED, preCancelled.acceptance.getString(AutoJs4RuntimeContract.KEY_ERROR_CODE))

            val loop = runScript(runtime, writeScript("loop.js", "while (true) { sleep(50); }"))
            assertTrue(loop.acceptance.getBoolean(AutoJs4RuntimeContract.KEY_ACCEPTED))
            assertTrue("AutoJs4 execution did not start", loop.started.await(20, TimeUnit.SECONDS))
            assertTrue(runtime.cancelScript(loop.executionId))
            val cancelled = loop.awaitTerminal()
            assertFalse(cancelled.getBoolean(AutoJs4RuntimeContract.KEY_SUCCEEDED))
            assertTrue(loop.describe(), cancelled.getBoolean(AutoJs4RuntimeContract.KEY_CANCELLED))
            assertEquals(AutoJs4RuntimeContract.ERROR_CANCELLED, cancelled.getString(AutoJs4RuntimeContract.KEY_ERROR_CODE))
        }
    }

    private fun writeScript(name: String, source: String): File {
        val directory = File(context.cacheDir, "autojs4-instrumented").apply { mkdirs() }
        return File(directory, name).apply { writeText(source, Charsets.UTF_8) }
    }

    private class CapturedRun(val executionId: String, val acceptance: Bundle, private val callback: RecordingCallback) {
        val logs: List<String> get() = callback.logs
        val started: CountDownLatch get() = callback.started

        fun awaitTerminal(): Bundle {
            assertTrue("AutoJs4 execution $executionId did not finish", callback.finished.await(60, TimeUnit.SECONDS))
            return requireNotNull(callback.terminal.get())
        }

        fun describe(): String = "execution=$executionId logs=${callback.logs} terminal=${callback.terminal.get()}"
    }

    private fun runScript(runtime: IAutoJs4RuntimePlugin, script: File, executionId: String = "test-${UUID.randomUUID()}"): CapturedRun {
        val callback = RecordingCallback(executionId)
        val request = Bundle().apply {
            putInt(AutoJs4RuntimeContract.KEY_PROTOCOL_VERSION, AutoJs4RuntimeContract.PROTOCOL_VERSION)
            putString(AutoJs4RuntimeContract.KEY_EXECUTION_ID, executionId)
            putString(AutoJs4RuntimeContract.KEY_SOURCE_TYPE, AutoJs4RuntimeContract.SOURCE_TYPE_JAVASCRIPT)
            putString(AutoJs4RuntimeContract.KEY_SCRIPT_PATH, script.absolutePath)
            putString(AutoJs4RuntimeContract.KEY_WORKING_DIRECTORY, script.absoluteFile.parent)
        }
        val acceptance = runtime.runScript(request, callback)
        assertEquals(executionId, acceptance.getString(AutoJs4RuntimeContract.KEY_EXECUTION_ID))
        return CapturedRun(executionId, acceptance, callback)
    }

    private class RecordingCallback(private val executionId: String) : IAutoJs4RuntimeCallback.Stub() {
        val logs = CopyOnWriteArrayList<String>()
        val started = CountDownLatch(1)
        val finished = CountDownLatch(1)
        val terminal = AtomicReference<Bundle?>()
        private var lastSequence = -1L

        override fun onEvent(event: Bundle?) {
            requireNotNull(event)
            assertEquals(AutoJs4RuntimeContract.PROTOCOL_VERSION, event.getInt(AutoJs4RuntimeContract.KEY_PROTOCOL_VERSION))
            assertEquals(executionId, event.getString(AutoJs4RuntimeContract.KEY_EXECUTION_ID))
            val sequence = event.getLong(AutoJs4RuntimeContract.KEY_SEQUENCE, -1L)
            assertTrue(sequence > lastSequence)
            lastSequence = sequence
            when (event.getString(AutoJs4RuntimeContract.KEY_EVENT_TYPE)) {
                AutoJs4RuntimeContract.EVENT_STARTED -> started.countDown()
                AutoJs4RuntimeContract.EVENT_LOG -> logs.add(event.getString(AutoJs4RuntimeContract.KEY_TEXT).orEmpty())
                AutoJs4RuntimeContract.EVENT_FINISHED -> {
                    terminal.set(Bundle(event))
                    finished.countDown()
                }
            }
        }
    }

    private class NoopCallback : IAutoJs4RuntimeCallback.Stub() {
        override fun onEvent(event: Bundle?) = Unit
    }

    private fun withBoundRuntime(block: (IAutoJs4RuntimePlugin) -> Unit) {
        val bound = CountDownLatch(1)
        val binder = AtomicReference<IBinder?>()
        val connection = object : ServiceConnection {
            override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
                binder.set(service)
                bound.countDown()
            }

            override fun onServiceDisconnected(name: ComponentName?) = Unit
        }
        val intent = Intent().setComponent(ComponentName(context, AutoJs4RuntimeService::class.java))
        assertTrue(context.bindService(intent, connection, Context.BIND_AUTO_CREATE))
        try {
            assertTrue("AutoJs4 runtime service did not bind", bound.await(30, TimeUnit.SECONDS))
            val service = requireNotNull(binder.get())
            assertEquals(AutoJs4RuntimeContract.BINDER_DESCRIPTOR, service.interfaceDescriptor)
            block(IAutoJs4RuntimePlugin.Stub.asInterface(service))
        } finally {
            context.unbindService(connection)
        }
    }

    private companion object {
        const val REQUIRED_API_LEVEL_ARGUMENT = "requiredApiLevel"
    }
}
