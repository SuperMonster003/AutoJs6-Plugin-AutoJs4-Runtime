AutoJs4 Runtime содержит движок [AutoJs4](https://github.com/SuperMonster003/AutoJs4), клона Auto.js 4.1.1 Alpha2, который служит эталоном поведения AutoJs6. После установки пункт "Запустить в AutoJs4" в файловом менеджере AutoJs6 выполняет файлы `.js`, записи `.auto` и папки проектов оригинальным движком Rhino 1.7.7.2 в собственном процессе плагина, а вывод консоли возвращается в AutoJs6. Так на одном устройстве можно проверить, ведет ли себя скрипт по-разному в AutoJs4 и AutoJs6, не устанавливая сам AutoJs4.

Этот выпуск содержит AutoJs4 4.4.0 (commit `3fb08269198ebd49a880ab3ca2621383eaa10443`) с Rhino 1.7.7.2 и OpenCV 3.4.3 и выполняет каждый скрипт в изолированном процессе `:autojs4_runtime`. Запуск, строки консоли и завершение возвращаются в AutoJs6 через контракт host; задачу можно остановить из списка задач AutoJs6.

### Быстрый старт

```javascript
console.log(context.getPackageName() + " / Rhino " + org.mozilla.javascript.Context.getCurrentContext().getImplementationVersion());
console.log("device: " + device.brand + " " + device.model + " (API " + device.sdkInt + ")");
toast("Hello from AutoJs4");
```

Консоль AutoJs6 выводит строки с префиксом `[AutoJs4]`; первая строка показывает имя пакета AutoJs4 и версию Rhino, выполнившие скрипт.

### Совместимость и область

- Движок: AutoJs4 4.4.0 (клон Auto.js 4.1.1 Alpha2), Git commit [`3fb08269198ebd49a880ab3ca2621383eaa10443`](https://github.com/SuperMonster003/AutoJs4/commit/3fb08269198ebd49a880ab3ca2621383eaa10443), Rhino 1.7.7.2, OpenCV 3.4.3. Исходный код AutoJs4 подключен как Git submodule и компилируется без изменений.
- Платформа: Android 7.0 (API 24) или новее с поддержкой 32-битных приложений. APK для одной ABI покрывают `armeabi-v7a, x86`; APK `universal` содержит обе. Устройства только с 64-битной поддержкой не могут установить плагин, как и AutoJs4.
- Скрипты: файлы `.js`, root-записи `.auto` и папки проектов с `project.json` (учитываются `main` и `features`). Скрипты `"ui";` открывают собственную Activity AutoJs4 внутри процесса плагина.
- Разрешения: доступ к хранилищу (устаревшее внешнее хранилище, `targetSdk 28`), показ поверх других приложений и служба специальных возможностей AutoJs4 предоставляются плагину отдельно от AutoJs6. Root-функции используют `su` устройства, как в AutoJs4.
- Параллельность: одновременно выполняется до 16 скриптов AutoJs4; остановка задачи в AutoJs6 останавливает движок AutoJs4, а сбой процесса плагина сообщается как неудачная задача.

Выбор ABI, разрешения, точный контракт и область этой версии описаны в [README проекта](https://github.com/SuperMonster003/AutoJs6-Plugin-AutoJs4-Runtime).
