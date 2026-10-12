تضمّن إضافة AutoJs4 Runtime محرك [AutoJs4](https://github.com/SuperMonster003/AutoJs4), وهو نسخة مستنسخة من Auto.js 4.1.1 Alpha2 تُستخدم كمرجع سلوكي لـ AutoJs6. بعد التثبيت, يشغّل الخيار "تشغيل باستخدام AutoJs4" في مدير ملفات AutoJs6 ملفات `.js` وتسجيلات `.auto` ومجلدات المشاريع بمحرك Rhino 1.7.7.2 الأصلي داخل عملية الإضافة الخاصة, بينما تعود مخرجات وحدة التحكم إلى AutoJs6. بذلك يمكن التحقق على جهاز واحد مما إذا كان سلوك السكربت يختلف بين AutoJs4 و AutoJs6 دون تثبيت AutoJs4 نفسه.

يضمّن هذا الإصدار AutoJs4 4.4.0 (commit `3fb08269198ebd49a880ab3ca2621383eaa10443`) مع Rhino 1.7.7.2 و OpenCV 3.4.3 ويشغّل كل سكربت في العملية المعزولة `:autojs4_runtime`. تعود البداية وأسطر وحدة التحكم والإنهاء إلى AutoJs6 عبر عقد host; ويمكن إيقاف المهمة من قائمة مهام AutoJs6.

### بدء سريع

```javascript
console.log(context.getPackageName() + " / Rhino " + org.mozilla.javascript.Context.getCurrentContext().getImplementationVersion());
console.log("device: " + device.brand + " " + device.model + " (API " + device.sdkInt + ")");
toast("Hello from AutoJs4");
```

تطبع وحدة تحكم AutoJs6 الأسطر مسبوقة بـ `[AutoJs4]`; يعرض السطر الأول اسم حزمة AutoJs4 وإصدار Rhino الذي نفّذ السكربت.

### التوافق والنطاق

- المحرك: AutoJs4 4.4.0 (نسخة من Auto.js 4.1.1 Alpha2), Git commit [`3fb08269198ebd49a880ab3ca2621383eaa10443`](https://github.com/SuperMonster003/AutoJs4/commit/3fb08269198ebd49a880ab3ca2621383eaa10443), Rhino 1.7.7.2, OpenCV 3.4.3. تُضمّن مصادر AutoJs4 كـ Git submodule وتُترجم دون تعديل.
- المنصة: Android 7.0 (API 24) أو أحدث مع دعم تطبيقات 32 بت. تغطي ملفات APK أحادية المعمارية `armeabi-v7a, x86`; ويحتوي APK `universal` على كليهما. لا يمكن تثبيت الإضافة على الأجهزة التي تدعم 64 بت فقط, تماما كما لا يمكن تثبيت AutoJs4 عليها.
- السكربتات: ملفات `.js`, وتسجيلات root بصيغة `.auto`, ومجلدات المشاريع التي تحوي `project.json` (يُحترم السكربت `main` و `features`). تفتح سكربتات `"ui";` نشاط السكربت الخاص بـ AutoJs4 داخل عملية الإضافة.
- الأذونات: الوصول إلى وحدة التخزين (التخزين الخارجي القديم, `targetSdk 28`), والعرض فوق التطبيقات الأخرى, وخدمة إمكانية الوصول في AutoJs4 تُمنح للإضافة بشكل مستقل عن AutoJs6. تستخدم ميزات root الأمر `su` في الجهاز مثل AutoJs4.
- التزامن: يعمل حتى 16 سكربت AutoJs4 في الوقت نفسه; إيقاف المهمة في AutoJs6 يوقف محرك AutoJs4, ويُبلّغ عن انهيار عملية الإضافة كمهمة فاشلة.

راجع [README المشروع](https://github.com/SuperMonster003/AutoJs6-Plugin-AutoJs4-Runtime) لاختيار المعمارية والأذونات والعقد الدقيق ونطاق هذا الإصدار.
