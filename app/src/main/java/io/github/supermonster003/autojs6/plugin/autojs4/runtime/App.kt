package io.github.supermonster003.autojs6.plugin.autojs4.runtime

import android.app.Application
import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.net.Uri
import android.view.View
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.transition.Transition
import com.stardust.app.GlobalAppContext
import com.stardust.autojs.core.ui.inflater.ImageLoader
import com.stardust.autojs.core.ui.inflater.util.Drawables

/**
 * Process entry. Every component of this package runs in the `:autojs4_runtime` process (see
 * AndroidManifest.xml), so the AutoJs4 engine singletons created here are visible to the Binder
 * service, the accessibility service, floating windows and UI-mode script activities alike.
 *
 * zh-CN: 进程入口. 本包全部组件都运行在 `:autojs4_runtime` 进程 (见 AndroidManifest.xml), 因此这里创建的
 * AutoJs4 引擎单例对 Binder 服务, 无障碍服务, 浮动窗口与 UI 模式脚本的 Activity 都可见.
 */
class App : Application() {

    override fun onCreate() {
        super.onCreate()
        GlobalAppContext.set(this)
        PluginPreferences.init(this)
        AutoJs4Runtime.initInstance(this)
        Drawables.setDefaultImageLoader(GlideImageLoader(this))
    }

    /** Same Glide-backed loader the AutoJs4 shells install for `ui` scripts. */
    private class GlideImageLoader(private val context: Context) : ImageLoader {

        override fun loadInto(imageView: ImageView, uri: Uri) {
            Glide.with(context).load(uri).into(imageView)
        }

        override fun loadIntoBackground(view: View, uri: Uri) {
            Glide.with(context).load(uri).into(object : SimpleTarget<Drawable>() {
                override fun onResourceReady(resource: Drawable, transition: Transition<in Drawable>?) {
                    view.background = resource
                }
            })
        }

        override fun load(view: View, uri: Uri): Drawable {
            throw UnsupportedOperationException()
        }

        override fun load(view: View, uri: Uri, drawableCallback: ImageLoader.DrawableCallback) {
            Glide.with(context).load(uri).into(object : SimpleTarget<Drawable>() {
                override fun onResourceReady(resource: Drawable, transition: Transition<in Drawable>?) {
                    drawableCallback.onLoaded(resource)
                }
            })
        }

        override fun load(view: View, uri: Uri, bitmapCallback: ImageLoader.BitmapCallback) {
            Glide.with(context).asBitmap().load(uri).into(object : SimpleTarget<Bitmap>() {
                override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                    bitmapCallback.onLoaded(resource)
                }
            })
        }
    }
}
