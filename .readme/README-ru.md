<!--suppress HtmlDeprecatedAttribute, HttpUrlsUsage -->

<div align="center">
  <p>
    <picture>
      <source media="(prefers-color-scheme: dark)" srcset="https://raw.githubusercontent.com/SuperMonster003/AutoJs6-Plugin-AutoJs4-Runtime/master/app/src/main/res/mipmap-night/ic_launcher.png" />
      <img src="https://raw.githubusercontent.com/SuperMonster003/AutoJs6-Plugin-AutoJs4-Runtime/master/app/src/main/res/mipmap/ic_launcher.png" alt="AutoJs4 Runtime" border="0" width="128" />
    </picture>
  </p>

  <p>Запускает скрипты встроенным движком Auto.js 4.1.1 Alpha2 для сравнения результатов с AutoJs6</p>

  <p>
    <a href="https://github.com/SuperMonster003/AutoJs6-Plugin-AutoJs4-Runtime/releases"><img alt="GitHub release" src="https://img.shields.io/github/v/release/SuperMonster003/AutoJs6-Plugin-AutoJs4-Runtime?label=Release"/></a>
    <a href="https://github.com/SuperMonster003/AutoJs6-Plugin-AutoJs4-Runtime/issues"><img alt="GitHub issues" src="https://img.shields.io/github/issues/SuperMonster003/AutoJs6-Plugin-AutoJs4-Runtime?color=A24232&label=Issues"/></a>
    <a href="https://github.com/SuperMonster003/AutoJs6-Plugin-AutoJs4-Runtime/blob/master/LICENSE"><img alt="GitHub license" src="https://img.shields.io/github/license/SuperMonster003/AutoJs6-Plugin-AutoJs4-Runtime?color=534BAE&label=License"/></a>
  </p>
</div>

******

### Языки

******

Текущий README.md доступен на следующих языках:

- [English [en]](https://github.com/SuperMonster003/AutoJs6-Plugin-AutoJs4-Runtime/blob/master/.readme/README-en.md)
- [العربية [ar]](https://github.com/SuperMonster003/AutoJs6-Plugin-AutoJs4-Runtime/blob/master/.readme/README-ar.md)
- [Español [es]](https://github.com/SuperMonster003/AutoJs6-Plugin-AutoJs4-Runtime/blob/master/.readme/README-es.md)
- [Français [fr]](https://github.com/SuperMonster003/AutoJs6-Plugin-AutoJs4-Runtime/blob/master/.readme/README-fr.md)
- [日本語 [ja]](https://github.com/SuperMonster003/AutoJs6-Plugin-AutoJs4-Runtime/blob/master/.readme/README-ja.md)
- [한국어 [ko]](https://github.com/SuperMonster003/AutoJs6-Plugin-AutoJs4-Runtime/blob/master/.readme/README-ko.md)
- Русский [ru] # текущий
- [简体中文 [zh-Hans]](https://github.com/SuperMonster003/AutoJs6-Plugin-AutoJs4-Runtime/blob/master/.readme/README-zh-Hans.md)
- [繁體中文 香港 [zh-Hant-HK]](https://github.com/SuperMonster003/AutoJs6-Plugin-AutoJs4-Runtime/blob/master/.readme/README-zh-Hant-HK.md)
- [繁體中文 台灣 [zh-Hant-TW]](https://github.com/SuperMonster003/AutoJs6-Plugin-AutoJs4-Runtime/blob/master/.readme/README-zh-Hant-TW.md)

******

### Введение

******

AutoJs4 Runtime содержит движок [AutoJs4](https://github.com/SuperMonster003/AutoJs4), клона Auto.js 4.1.1 Alpha2, который служит эталоном поведения AutoJs6. После установки пункт "Запустить в AutoJs4" в файловом менеджере AutoJs6 выполняет файлы `.js`, записи `.auto` и папки проектов оригинальным движком Rhino 1.7.7.2 в собственном процессе плагина, а вывод консоли возвращается в AutoJs6. Так на одном устройстве можно проверить, ведет ли себя скрипт по-разному в AutoJs4 и AutoJs6, не устанавливая сам AutoJs4.

******

### Установка и использование

******

1. Используйте AutoJs6 build 5334 (6.8.0) или новее на Android 7.0 (API 24) или новее. Устройство должно уметь запускать 32-битные приложения, как и для AutoJs4.
2. Установите release APK для ABI устройства (`armeabi-v7a` для ARM, `x86` для эмуляторов) или APK `universal`.
3. Откройте центр плагинов AutoJs6 и включите AutoJs4 Runtime. Если Android оставил новый пакет остановленным, используйте действие `Активировать` в host.
4. Откройте настройки плагина из центра плагинов и предоставьте доступ к хранилищу, показ поверх других приложений и службу специальных возможностей по мере необходимости скриптов. Эти разрешения принадлежат плагину, а не AutoJs6.
5. В файловом менеджере AutoJs6 откройте меню файла `.js`, файла `.auto` или папки проекта и выберите `Запустить в AutoJs4`. Задача появится в списке задач AutoJs6, где ее можно остановить.

> Плагин не изменяет AutoJs4: тот же Rhino 1.7.7.2, та же нативная библиотека OpenCV 3.4.3, `targetSdk 28` и тот же набор 32-битных ABI. Поэтому различия между двумя движками являются различиями между AutoJs4 и AutoJs6 и могут быть сообщены в трекер issue AutoJs6.

******

### Быстрый старт

******

Сохраните этот файл в рабочей области AutoJs6 и запустите через `Запустить в AutoJs4`:

```javascript
console.log("engine package: " + context.getPackageName());
console.log("device: " + device.brand + " " + device.model + " (API " + device.sdkInt + ")");
toast("Hello from AutoJs4");
```

Консоль AutoJs6 выводит строки с префиксом `[AutoJs4]`; первая строка показывает имя пакета плагина, выполнившего скрипт, что подтверждает запуск движком AutoJs4.

******

### Совместимость и область

******

- Движок: AutoJs4 4.4.0 (клон Auto.js 4.1.1 Alpha2), Git commit [`3fb08269198ebd49a880ab3ca2621383eaa10443`](https://github.com/SuperMonster003/AutoJs4/commit/3fb08269198ebd49a880ab3ca2621383eaa10443), Rhino 1.7.7.2, OpenCV 3.4.3. Исходный код AutoJs4 подключен как Git submodule и компилируется без изменений.
- Платформа: Android 7.0 (API 24) или новее с поддержкой 32-битных приложений. APK для одной ABI покрывают `armeabi-v7a, x86`; APK `universal` содержит обе. Устройства только с 64-битной поддержкой не могут установить плагин, как и AutoJs4.
- Скрипты: файлы `.js`, root-записи `.auto` и папки проектов с `project.json` (учитываются `main` и `features`). Скрипты `"ui";` открывают собственную Activity AutoJs4 внутри процесса плагина.
- Разрешения: доступ к хранилищу (устаревшее внешнее хранилище, `targetSdk 28`), показ поверх других приложений и служба специальных возможностей AutoJs4 предоставляются плагину отдельно от AutoJs6. Root-функции используют `su` устройства, как в AutoJs4.
- Параллельность: одновременно выполняется до 16 скриптов AutoJs4; остановка задачи в AutoJs6 останавливает движок AutoJs4, а сбой процесса плагина сообщается как неудачная задача.

******

### Разрешения и целостность

******

- Wake Activity и обе экспортированные службы требуют `org.autojs.permission.PLUGIN`; скрипты выполняются под UID плагина в процессе `:autojs4_runtime`, никогда внутри AutoJs6.
- Скрипты адресуются абсолютными путями. Плагин читает и пишет файлы только с разрешением на хранилище, предоставленным ему пользователем, и отклоняет запросы с неабсолютными путями или нечитаемыми файлами.
- Вывод консоли передается в AutoJs6 через ограниченный oneway callback частями не более 64 KiB; завершающее событие содержит только статус, сообщение об ошибке и краткий стек.
- Собственные внешние точки входа AutoJs4 (intent запуска, ярлыки, Tasker) не экспортируются. Только host AutoJs6 может запускать скрипты через защищенную службу Binder.

******

### Интерфейс плагина

******

Стабильные идентификаторы и ограничения для разработчиков host и плагина AutoJs6:

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
supported abis: armeabi-v7a, x86
max concurrent executions: 16
max log chunk: 64 KiB
```

`AutoJs4RuntimeService` обнаруживается по action `org.autojs.plugin.autojs4.RUNTIME` и category `autojs4-runtime`. `runScript` проверяет Bundle запроса (путь скрипта, рабочий каталог, необязательную папку проекта, аргументы, задержку, интервал и число повторов), запускает выполнение AutoJs4 и немедленно возвращает Bundle принятия; затем события `started`, `log` и `finished` приходят через callback. `cancelScript` останавливает выполняющийся скрипт или запоминает отмену для еще не запущенного выполнения. Полный контракт описан в репозитории AutoJs6 ([protocol V1](https://github.com/SuperMonster003/AutoJs6/blob/master/docs/dev/autojs4-runtime-protocol-v1.md)).

Размер страницы 16 KB: плагин содержит только 32-битные нативные библиотеки, которые поставляет AutoJs4. 64-битного нативного кода нет, поэтому проверке выравнивания нечего проверять, а APK нельзя установить на устройства только с 64-битной поддержкой. Добавление сборки `arm64-v8a` с пересобранным OpenCV 3.4.x отслеживается как открытый вопрос в [roadmap](https://github.com/SuperMonster003/AutoJs6/blob/master/docs/dev/autojs4-runtime-plugin-roadmap.md).

******

### История выпусков

******

#### v1.0.0

_2026/10/12_

- `Подсказка` Первый выпуск точно повторяет AutoJs4, включая его только 32-битные нативные библиотеки, поэтому плагин нельзя установить на устройства только с 64-битной поддержкой
- `Функция` Запуск файлов `.js`, записей `.auto` и папок проектов из файлового менеджера AutoJs6 встроенным движком AutoJs4 4.4.0 (Rhino 1.7.7.2, OpenCV 3.4.3) через пункт меню `Запустить в AutoJs4`
- `Функция` Защищенная служба выполнения с версионированным AIDL-контрактом `autojs4-runtime-api`: запросы по пути файла, потоковые строки консоли, события запуска и завершения, отмена и параллельные выполнения
- `Функция` Страница настроек плагина со службой специальных возможностей, показом поверх других приложений, доступом к хранилищу, оптимизацией батареи и состоянием root, а также переключатель `Показывать окно консоли AutoJs4`
- `Функция` Поставка APK `armeabi-v7a`, `x86` и `universal`, Wake-активация, полные метаданные PluginInfo, инструкции центра плагинов, README и changelog на 10 языках
- `Зависимость` Встроен AutoJs4 4.4.0 как Git submodule на commit [`3fb08269198ebd49a880ab3ca2621383eaa10443`](https://github.com/SuperMonster003/AutoJs4/commit/3fb08269198ebd49a880ab3ca2621383eaa10443)

##### Дополнительная история выпусков

- [CHANGELOG.md](https://github.com/SuperMonster003/AutoJs6-Plugin-AutoJs4-Runtime/blob/master/app/src/main/assets/doc/CHANGELOG-ru.md)

******

### Сборка и проверка

******

Для сборки нужны JDK 17 или новее, Python 3 и Android SDK 37 (модули движка AutoJs4 компилируются против API 30, который Android SDK загружает по запросу). Клонируйте с `--recurse-submodules`, чтобы заполнить `autojs4/`. Стандартные проверки включают сгенерированную документацию, JVM-тесты, debug APK, пакет instrumentation и Android Lint.

```powershell
git clone --recurse-submodules https://github.com/SuperMonster003/AutoJs6-Plugin-AutoJs4-Runtime.git
py .python\generate_markdown.py --check
py .python\generate_provisional_icon.py --check
.\gradlew.bat :app:testDebugUnitTest :app:assembleDebug :app:assembleDebugAndroidTest :app:lintDebug
```

******

### Локализация и создание документов

******

Отредактируйте JSON и Markdown-шаблоны, затем запустите `py .python/generate_markdown.py`. Сгенерированные README, changelog и файлы описания плагина нельзя редактировать вручную. Режим `--check` проверяет структуру языков, согласованность версий, локализованные ресурсы, лишние файлы и расхождение сгенерированных файлов без записи.

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

Значок запуска является временным глифом, который рисует `.python/generate_provisional_icon.py`, пока нет графики от мейнтейнера; проверьте его командой `py .python/generate_provisional_icon.py --check`.

******

### Лицензия

******

Код плагина распространяется по [Mozilla Public License 2.0](https://github.com/SuperMonster003/AutoJs6-Plugin-AutoJs4-Runtime/blob/master/LICENSE). Встроенный исходный код AutoJs4 лицензирован по MPL 2.0 с некоммерческим условием репозитория AutoJs4; Rhino, OpenCV, терминальные библиотеки jackpal и другие сторонние компоненты сохраняют собственные лицензии. См. [сторонние уведомления](https://github.com/SuperMonster003/AutoJs6-Plugin-AutoJs4-Runtime/blob/master/THIRD_PARTY_NOTICES.md).

******

### Ссылки

******

- Проект AutoJs6: https://github.com/SuperMonster003/AutoJs6
- Проект AutoJs4: https://github.com/SuperMonster003/AutoJs4
- Документация AutoJs4: https://github.com/SuperMonster003/AutoJs4-Documentation
- AutoJs4 Runtime protocol V1: https://github.com/SuperMonster003/AutoJs6/blob/master/docs/dev/autojs4-runtime-protocol-v1.md
- AutoJs4 Runtime roadmap: https://github.com/SuperMonster003/AutoJs6/blob/master/docs/dev/autojs4-runtime-plugin-roadmap.md
- Сторонние уведомления: https://github.com/SuperMonster003/AutoJs6-Plugin-AutoJs4-Runtime/blob/master/THIRD_PARTY_NOTICES.md
