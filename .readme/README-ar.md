<!--suppress HtmlDeprecatedAttribute, HttpUrlsUsage -->

<div align="center">
  <p>
    <picture>
      <source media="(prefers-color-scheme: dark)" srcset="https://raw.githubusercontent.com/SuperMonster003/AutoJs6-Plugin-AutoJs4-Runtime/master/app/src/main/res/mipmap-night/ic_launcher.png" />
      <img src="https://raw.githubusercontent.com/SuperMonster003/AutoJs6-Plugin-AutoJs4-Runtime/master/app/src/main/res/mipmap/ic_launcher.png" alt="AutoJs4 Runtime" border="0" width="128" />
    </picture>
  </p>

  <p>يشغّل السكربتات بمحرك Auto.js 4.1.1 Alpha2 المضمّن لمقارنة النتائج مع AutoJs6</p>

  <p>
    <a href="https://github.com/SuperMonster003/AutoJs6-Plugin-AutoJs4-Runtime/releases"><img alt="GitHub release" src="https://img.shields.io/github/v/release/SuperMonster003/AutoJs6-Plugin-AutoJs4-Runtime?label=Release"/></a>
    <a href="https://github.com/SuperMonster003/AutoJs6-Plugin-AutoJs4-Runtime/issues"><img alt="GitHub issues" src="https://img.shields.io/github/issues/SuperMonster003/AutoJs6-Plugin-AutoJs4-Runtime?color=A24232&label=Issues"/></a>
    <a href="https://github.com/SuperMonster003/AutoJs6-Plugin-AutoJs4-Runtime/blob/master/LICENSE"><img alt="GitHub license" src="https://img.shields.io/github/license/SuperMonster003/AutoJs6-Plugin-AutoJs4-Runtime?color=534BAE&label=License"/></a>
  </p>
</div>

******

### اللغات

******

يدعم README.md الحالي اللغات التالية:

- [English [en]](https://github.com/SuperMonster003/AutoJs6-Plugin-AutoJs4-Runtime/blob/master/.readme/README-en.md)
- العربية [ar] # الحالية
- [Español [es]](https://github.com/SuperMonster003/AutoJs6-Plugin-AutoJs4-Runtime/blob/master/.readme/README-es.md)
- [Français [fr]](https://github.com/SuperMonster003/AutoJs6-Plugin-AutoJs4-Runtime/blob/master/.readme/README-fr.md)
- [日本語 [ja]](https://github.com/SuperMonster003/AutoJs6-Plugin-AutoJs4-Runtime/blob/master/.readme/README-ja.md)
- [한국어 [ko]](https://github.com/SuperMonster003/AutoJs6-Plugin-AutoJs4-Runtime/blob/master/.readme/README-ko.md)
- [Русский [ru]](https://github.com/SuperMonster003/AutoJs6-Plugin-AutoJs4-Runtime/blob/master/.readme/README-ru.md)
- [简体中文 [zh-Hans]](https://github.com/SuperMonster003/AutoJs6-Plugin-AutoJs4-Runtime/blob/master/.readme/README-zh-Hans.md)
- [繁體中文 香港 [zh-Hant-HK]](https://github.com/SuperMonster003/AutoJs6-Plugin-AutoJs4-Runtime/blob/master/.readme/README-zh-Hant-HK.md)
- [繁體中文 台灣 [zh-Hant-TW]](https://github.com/SuperMonster003/AutoJs6-Plugin-AutoJs4-Runtime/blob/master/.readme/README-zh-Hant-TW.md)

******

### مقدمة

******

تضمّن إضافة AutoJs4 Runtime محرك [AutoJs4](https://github.com/SuperMonster003/AutoJs4), وهو نسخة مستنسخة من Auto.js 4.1.1 Alpha2 تُستخدم كمرجع سلوكي لـ AutoJs6. بعد التثبيت, يشغّل الخيار "تشغيل باستخدام AutoJs4" في مدير ملفات AutoJs6 ملفات `.js` وتسجيلات `.auto` ومجلدات المشاريع بمحرك Rhino 1.7.7.2 الأصلي داخل عملية الإضافة الخاصة, بينما تعود مخرجات وحدة التحكم إلى AutoJs6. بذلك يمكن التحقق على جهاز واحد مما إذا كان سلوك السكربت يختلف بين AutoJs4 و AutoJs6 دون تثبيت AutoJs4 نفسه.

******

### التثبيت والاستخدام

******

1. استخدم AutoJs6 build 5334 (6.8.0) أو أحدث على Android 7.0 (API 24) أو أحدث.
2. ثبّت ملف APK الإصدار المطابق لمعمارية الجهاز (`arm64-v8a` لمعظم الهواتف الحديثة, و `armeabi-v7a` لأجهزة 32 بت, و `x86_64` أو `x86` للمحاكيات), أو ملف APK `universal` الأكبر حجما.
3. افتح مركز الإضافات في AutoJs6 وفعّل AutoJs4 Runtime. إذا أبقى Android الحزمة المثبتة حديثا في حالة متوقفة, فاستخدم إجراء `تنشيط` في host.
4. افتح إعدادات الإضافة من مركز الإضافات وامنح الوصول إلى وحدة التخزين والعرض فوق التطبيقات الأخرى وخدمة إمكانية الوصول حسب حاجة السكربتات; هذه الأذونات تخص الإضافة وليس AutoJs6.
5. في مدير ملفات AutoJs6 افتح قائمة ملف `.js` أو ملف `.auto` أو مجلد مشروع واختر `تشغيل باستخدام AutoJs4`. تظهر المهمة في قائمة مهام AutoJs6 ويمكن إيقافها من هناك.

> لا تعدّل الإضافة AutoJs4: نفس Rhino 1.7.7.2, ومكتبة OpenCV 3.4.3 الأصلية من AutoJs4 على armeabi-v7a ونفس إصدار OpenCV المُعاد بناؤه لأجهزة 64 بت, و `targetSdk 28`, ونفس وحدات المحرك. لذلك فإن الفروق الملاحظة بين المحركين هي فروق بين AutoJs4 و AutoJs6 ويمكن الإبلاغ عنها في متتبع issue الخاص بـ AutoJs6.

******

### بدء سريع

******

احفظ هذا الملف في مساحة عمل AutoJs6 ثم شغّله عبر `تشغيل باستخدام AutoJs4`:

```javascript
console.log("engine package: " + context.getPackageName());
console.log("device: " + device.brand + " " + device.model + " (API " + device.sdkInt + ")");
toast("Hello from AutoJs4");
```

تطبع وحدة تحكم AutoJs6 الأسطر مسبوقة بـ `[AutoJs4]`; يعرض السطر الأول اسم حزمة الإضافة التي نفّذت السكربت, مما يؤكد أن محرك AutoJs4 هو من شغّله.

******

### التوافق والنطاق

******

- المحرك: AutoJs4 4.4.0 (نسخة من Auto.js 4.1.1 Alpha2), Git commit [`3fb08269198ebd49a880ab3ca2621383eaa10443`](https://github.com/SuperMonster003/AutoJs4/commit/3fb08269198ebd49a880ab3ca2621383eaa10443), Rhino 1.7.7.2, OpenCV 3.4.3. تُضمّن مصادر AutoJs4 كـ Git submodule وتُترجم دون تعديل; وتُعاد ترجمة مكتبات OpenCV لمعماريتي arm64-v8a و x86_64 من الإصدار نفسه بـ NDK 28.2.13676358 مع التحقق من أنها تصدّر جميع رموز JNI للمكتبة الأصلية.
- المنصة: Android 7.0 (API 24) أو أحدث. تغطي ملفات APK أحادية المعمارية `arm64-v8a, armeabi-v7a, x86_64, x86`; ويحتوي APK `universal` على المعماريات الأربع. مثل AutoJs4, لا يحتوي بناء `x86` على OpenCV.
- السكربتات: ملفات `.js`, وتسجيلات root بصيغة `.auto`, ومجلدات المشاريع التي تحوي `project.json` (يُحترم السكربت `main` و `features`). تفتح سكربتات `"ui";` نشاط السكربت الخاص بـ AutoJs4 داخل عملية الإضافة.
- الأذونات: الوصول إلى وحدة التخزين (التخزين الخارجي القديم, `targetSdk 28`), والعرض فوق التطبيقات الأخرى, وخدمة إمكانية الوصول في AutoJs4 تُمنح للإضافة بشكل مستقل عن AutoJs6. تستخدم ميزات root الأمر `su` في الجهاز مثل AutoJs4.
- التزامن: يعمل حتى 16 سكربت AutoJs4 في الوقت نفسه; إيقاف المهمة في AutoJs6 يوقف محرك AutoJs4, ويُبلّغ عن انهيار عملية الإضافة كمهمة فاشلة.

******

### الأذونات والتكامل

******

- يتطلب نشاط Wake والخدمتان المصدّرتان الإذن `org.autojs.permission.PLUGIN`; تعمل السكربتات بمعرّف UID الخاص بالإضافة في العملية `:autojs4_runtime` ولا تدخل أبدا عملية AutoJs6.
- تُحدَّد السكربتات بمسارات مطلقة. تقرأ الإضافة الملفات وتكتبها فقط بإذن التخزين الذي منحه المستخدم لها, وترفض الطلبات ذات المسارات غير المطلقة أو الملفات غير القابلة للقراءة.
- تُبث مخرجات وحدة التحكم إلى AutoJs6 عبر callback أحادي الاتجاه محدود في أجزاء لا تتجاوز 64 KiB; ولا يحمل حدث الإنهاء سوى الحالة ورسالة الخطأ وملخص تتبع المكدس.
- لا تُصدَّر نقاط الدخول الخارجية الخاصة بـ AutoJs4 (intents التشغيل والاختصارات و Tasker). وحده host الخاص بـ AutoJs6 يمكنه بدء السكربتات عبر خدمة Binder المحمية.

******

### واجهة الإضافة

******

معرفات وحدود ثابتة لمطوري host وإضافة AutoJs6:

```text
application id: io.github.supermonster003.autojs6.plugin.autojs4.runtime
plugin id: autojs4-runtime
engine: autojs4
variant: default
service action: org.autojs.plugin.autojs4.RUNTIME
service category: autojs4-runtime
aidl interface: org.autojs.plugin.autojs4.runtime.api.IAutoJs4RuntimePlugin
contract version: 1
aidl methods: getInfo(), getRuntimeInfo(), runScript(request, callback), cancelScript(executionId)
minimum host build: 5334 (6.8.0)
embedded AutoJs4: 4.4.0 (3fb08269198ebd49a880ab3ca2621383eaa10443)
rhino: 1.7.7.2
supported abis: arm64-v8a, armeabi-v7a, x86_64, x86
max concurrent executions: 16
max log chunk: 64 KiB
```

تُكتشف `AutoJs4RuntimeService` عبر action `org.autojs.plugin.autojs4.RUNTIME` و category `autojs4-runtime`. تتحقق `runScript` من Bundle الطلب (مسار السكربت, ودليل العمل, ومجلد المشروع الاختياري, والوسائط, والتأخير, والفاصل الزمني, وعدد التكرارات), ثم تبدأ تنفيذ AutoJs4 وتعيد Bundle القبول فورا; بعدها تصل أحداث `started` و `log` و `finished` عبر callback. توقف `cancelScript` تنفيذا جاريا أو تحفظ إلغاء لتنفيذ لم يُرسل بعد. العقد الكامل موثق في مستودع AutoJs6 ([protocol V1](https://github.com/SuperMonster003/AutoJs6/blob/master/docs/dev/autojs4-runtime-protocol-v1.md)).

حجم الصفحة 16 KB: المكتبات الأصلية لمعماريتي arm64-v8a و x86_64 (OpenCV 3.4.3 المُعاد بناؤه بـ NDK 28.2.13676358 ومكتبات طرفية jackpal المُعاد بناؤها بواسطة host الخاص بـ AutoJs6) مربوطة بحجم صفحة أقصى 16 KB, وتتحقق بوابة المحاذاة المشتركة من كل مقطع ELF PT_LOAD أثناء البناء. لم يُنفّذ التشغيل على جهاز بصفحات 16 KB بعد.

******

### سجل الإصدارات

******

#### v1.0.0

_2026/10/12_

- `تنبيه` تستخدم armeabi-v7a مكتبة OpenCV 3.4.3 الأصلية من AutoJs4, وتستخدم أجهزة 64 بت الإصدار نفسه المُعاد بناؤه بـ NDK 28.2; ولا يحتوي بناء x86 على OpenCV مثل AutoJs4
- `ميزة` تشغيل ملفات `.js` وتسجيلات `.auto` ومجلدات المشاريع من مدير ملفات AutoJs6 بمحرك AutoJs4 4.4.0 المضمّن (Rhino 1.7.7.2, OpenCV 3.4.3) عبر عنصر القائمة `تشغيل باستخدام AutoJs4`
- `ميزة` خدمة تشغيل محمية بعقد AIDL ذي إصدار `autojs4-runtime-api`: طلبات بمسار الملف, وأسطر وحدة تحكم مُبثّة, وأحداث البدء والإنهاء, والإلغاء, والتنفيذ المتزامن
- `ميزة` صفحة إعدادات للإضافة تعرض خدمة إمكانية الوصول والعرض فوق التطبيقات الأخرى والوصول إلى وحدة التخزين وتحسين البطارية وحالة root, إضافة إلى مفتاح `إظهار نافذة وحدة تحكم AutoJs4`
- `ميزة` توفير ملفات APK بمعماريات `arm64-v8a` و `armeabi-v7a` و `x86_64` و `x86` و `universal`, وتنشيط Wake, وبيانات PluginInfo الوصفية الكاملة, وتعليمات مركز الإضافات و README و changelog بعشر لغات
- `تحسين` بناء مكتبات OpenCV 3.4.3 وطرفية jackpal لمعماريتي arm64-v8a و x86_64 بمحاذاة صفحات 16 KB, والتحقق أثناء البناء من محاذاة ELF ومن التغطية الكاملة لرموز JNI للمكتبة الأصلية
- `اعتماد` تضمين AutoJs4 4.4.0 كـ Git submodule عند commit [`3fb08269198ebd49a880ab3ca2621383eaa10443`](https://github.com/SuperMonster003/AutoJs4/commit/3fb08269198ebd49a880ab3ca2621383eaa10443)

##### لمزيد من سجل الإصدارات

- [CHANGELOG.md](https://github.com/SuperMonster003/AutoJs6-Plugin-AutoJs4-Runtime/blob/master/app/src/main/assets/doc/CHANGELOG-ar.md)

******

### البناء والتحقق

******

يتطلب البناء JDK 17 أو أحدث و Python 3 و Android SDK 37 (تُترجم وحدات محرك AutoJs4 مقابل API 30 الذي يحمّله Android SDK عند الحاجة). استنسخ المستودع بـ `--recurse-submodules` لتعبئة `autojs4/`. تتحقق الفحوصات القياسية من الوثائق المولّدة واختبارات JVM وملفات APK للتصحيح وحزمة instrumentation و Android Lint.

```powershell
git clone --recurse-submodules https://github.com/SuperMonster003/AutoJs6-Plugin-AutoJs4-Runtime.git
py .python\generate_markdown.py --check
py .python\generate_provisional_icon.py --check
.\gradlew.bat :app:testDebugUnitTest :app:assembleDebug :app:assembleDebugAndroidTest :app:lintDebug
```

******

### الترجمة وتوليد الوثائق

******

حرّر قوالب JSON و Markdown ثم شغّل `py .python/generate_markdown.py`. لا تحرر يدويا ملفات README و changelog وتعليمات الإضافة المولّدة. يتحقق وضع `--check` من بنية اللغات وتوافق الإصدارات والموارد المترجمة والملفات اليتيمة وانحراف الملفات المولّدة دون كتابة.

```text
.readme/common.json
.readme/lang_*.json
.readme/template_readme.md
.readme/template_plugin_instruction.md
.changelog/lang_*.json
.changelog/template_changelog.md
.python/generate_markdown.py
app/src/main/assets/doc/CHANGELOG-*.md
app/src/main/res/values*/strings.xml
app/src/main/res/raw*/plugin_instruction.md
```

أيقونة المشغّل رمز مؤقت يرسمه `.python/generate_provisional_icon.py` حتى يتوفر عمل المشرف الفني; تحقق منها بالأمر `py .python/generate_provisional_icon.py --check`.

******

### الترخيص

******

كود الإضافة مرخّص بموجب [Mozilla Public License 2.0](https://github.com/SuperMonster003/AutoJs6-Plugin-AutoJs4-Runtime/blob/master/LICENSE). مصادر AutoJs4 المضمّنة مرخّصة بموجب MPL 2.0 مع الشرط غير التجاري لمستودع AutoJs4; وتحتفظ Rhino و OpenCV ومكتبات طرفية jackpal وبقية مكونات الطرف الثالث بتراخيصها. راجع [إشعارات الطرف الثالث](https://github.com/SuperMonster003/AutoJs6-Plugin-AutoJs4-Runtime/blob/master/THIRD_PARTY_NOTICES.md).

******

### الروابط

******

- مشروع AutoJs6: https://github.com/SuperMonster003/AutoJs6
- مشروع AutoJs4: https://github.com/SuperMonster003/AutoJs4
- وثائق AutoJs4: https://github.com/SuperMonster003/AutoJs4-Documentation
- AutoJs4 Runtime protocol V1: https://github.com/SuperMonster003/AutoJs6/blob/master/docs/dev/autojs4-runtime-protocol-v1.md
- AutoJs4 Runtime roadmap: https://github.com/SuperMonster003/AutoJs6/blob/master/docs/dev/autojs4-runtime-plugin-roadmap.md
- إشعارات الطرف الثالث: https://github.com/SuperMonster003/AutoJs6-Plugin-AutoJs4-Runtime/blob/master/THIRD_PARTY_NOTICES.md
