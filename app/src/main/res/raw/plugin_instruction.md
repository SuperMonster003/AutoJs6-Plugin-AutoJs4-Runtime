AutoJs4 Runtime embeds the unmodified engine of AutoJs4 (a clone of Auto.js 4.1.1 Alpha2), so AutoJs6 can run scripts with it without installing AutoJs4 separately. Console output is sent back to AutoJs6, which makes it easy to compare how a script behaves on AutoJs4 and on AutoJs6 on the same device.

### Getting started

1. Make sure this plugin is enabled in the AutoJs6 Plugin Center.
2. Open this plugin's settings from the Plugin Center, and in "Permissions and services" grant "Storage access", "Display over other apps" or "Accessibility service" as your scripts need.
3. In the AutoJs6 file manager, open the menu of a `.js` file, an `.auto` file or a project folder and choose "Run with AutoJs4" (in the "AutoJs4" submenu from AutoJs6 6.8.1).
4. Logs appear in the AutoJs6 console with the `[AutoJs4]` prefix; the task appears in the AutoJs6 task list, where you can stop it.

### Main features

- **Run with AutoJs4**: runs `.js` files, `.auto` recordings and project folders with a `project.json` on the AutoJs4 engine; the UI of `"ui";` scripts shows in this plugin.
- **Run with AutoJs4 and compare**: runs the same script on AutoJs6 and on AutoJs4, then shows in "AutoJs4 comparison" whether the two console outputs match and how long each run took.
- **Show AutoJs4 console window**: a switch in the "Running" group of the settings; when it is on, `console.show()` shows the AutoJs4 floating console. Logs always go back to AutoJs6.
- **Permissions and services**: the settings show the state of storage access, display over other apps, the accessibility service, battery optimizations and root access; tap a row to grant or check it.

### Using it in scripts

Scripts use the AutoJs4 API (the same as Auto.js 4.1.1 Alpha2). Save the code below as a `.js` file and choose "Run with AutoJs4": the first console line shows this plugin's package name, which means AutoJs4 ran the script:

```js
console.log("engine package: " + context.getPackageName());
console.log("device: " + device.brand + " " + device.model + " (API " + device.sdkInt + ")");
toast("Hello from AutoJs4");
```

- Modules and syntax that only AutoJs6 has are not available in AutoJs4; when the two engines give different results, compare them with "Run with AutoJs4 and compare".
- Scripts run as this plugin: file access relies on this plugin's storage permission, and root features use `su` on the device.
- Up to 16 AutoJs4 scripts can run at the same time.

### Requirements

- Android 7.0 or later and AutoJs6 6.8.0 or later.
- Storage access lets scripts read and write their files.
- Display over other apps is used by the AutoJs4 console window and floating UIs, and the accessibility service by finding widgets and automatic taps; grant them only when a script needs them.
- Like the original AutoJs4, the x86 build has no OpenCV, so image features that depend on it (such as finding images) are not available on x86 devices.

### FAQ

**When a permission is reported missing**

This plugin's permissions are separate from those of AutoJs6. Open this plugin's settings from the Plugin Center, grant the reported permission in "Permissions and services", then run the script again.

**When the two engines give different results**

This plugin uses the unmodified AutoJs4 engine with the same Rhino and OpenCV versions as AutoJs4, so a difference in results is a difference between AutoJs4 and AutoJs6. If it gets in the way of migrating your scripts, you can report it on the [AutoJs6 issue tracker](https://github.com/SuperMonster003/AutoJs6/issues).

**When the system stops long-running scripts**

In "Permissions and services" in this plugin's settings, tap "Ignore battery optimizations" to exempt this plugin from battery optimizations.

For the AutoJs4 script API, see the [AutoJs4 documentation](https://github.com/SuperMonster003/AutoJs4-Documentation); for the source code and more details, see the [project page](https://github.com/SuperMonster003/AutoJs6-Plugin-AutoJs4-Runtime).
