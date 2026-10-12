<!--suppress HtmlDeprecatedAttribute, HttpUrlsUsage -->

<div align="center">
  <p>
    <picture>
      <source media="(prefers-color-scheme: dark)" srcset="https://raw.githubusercontent.com/SuperMonster003/AutoJs6-Plugin-AutoJs4-Runtime/master/app/src/main/res/mipmap-night/ic_launcher.png" />
      <img src="https://raw.githubusercontent.com/SuperMonster003/AutoJs6-Plugin-AutoJs4-Runtime/master/app/src/main/res/mipmap/ic_launcher.png" alt="AutoJs4 Runtime" border="0" width="128" />
    </picture>
  </p>

  <p>Ejecuta scripts con el motor Auto.js 4.1.1 Alpha2 integrado para comparar los resultados con AutoJs6</p>

  <p>
    <a href="https://github.com/SuperMonster003/AutoJs6-Plugin-AutoJs4-Runtime/releases"><img alt="GitHub release" src="https://img.shields.io/github/v/release/SuperMonster003/AutoJs6-Plugin-AutoJs4-Runtime?label=Release"/></a>
    <a href="https://github.com/SuperMonster003/AutoJs6-Plugin-AutoJs4-Runtime/issues"><img alt="GitHub issues" src="https://img.shields.io/github/issues/SuperMonster003/AutoJs6-Plugin-AutoJs4-Runtime?color=A24232&label=Issues"/></a>
    <a href="https://github.com/SuperMonster003/AutoJs6-Plugin-AutoJs4-Runtime/blob/master/LICENSE"><img alt="GitHub license" src="https://img.shields.io/github/license/SuperMonster003/AutoJs6-Plugin-AutoJs4-Runtime?color=534BAE&label=License"/></a>
  </p>
</div>

******

### Idiomas

******

El README.md actual admite los siguientes idiomas:

- [English [en]](https://github.com/SuperMonster003/AutoJs6-Plugin-AutoJs4-Runtime/blob/master/.readme/README-en.md)
- [العربية [ar]](https://github.com/SuperMonster003/AutoJs6-Plugin-AutoJs4-Runtime/blob/master/.readme/README-ar.md)
- Español [es] # actual
- [Français [fr]](https://github.com/SuperMonster003/AutoJs6-Plugin-AutoJs4-Runtime/blob/master/.readme/README-fr.md)
- [日本語 [ja]](https://github.com/SuperMonster003/AutoJs6-Plugin-AutoJs4-Runtime/blob/master/.readme/README-ja.md)
- [한국어 [ko]](https://github.com/SuperMonster003/AutoJs6-Plugin-AutoJs4-Runtime/blob/master/.readme/README-ko.md)
- [Русский [ru]](https://github.com/SuperMonster003/AutoJs6-Plugin-AutoJs4-Runtime/blob/master/.readme/README-ru.md)
- [简体中文 [zh-Hans]](https://github.com/SuperMonster003/AutoJs6-Plugin-AutoJs4-Runtime/blob/master/.readme/README-zh-Hans.md)
- [繁體中文 香港 [zh-Hant-HK]](https://github.com/SuperMonster003/AutoJs6-Plugin-AutoJs4-Runtime/blob/master/.readme/README-zh-Hant-HK.md)
- [繁體中文 台灣 [zh-Hant-TW]](https://github.com/SuperMonster003/AutoJs6-Plugin-AutoJs4-Runtime/blob/master/.readme/README-zh-Hant-TW.md)

******

### Introducción

******

AutoJs4 Runtime integra el motor de [AutoJs4](https://github.com/SuperMonster003/AutoJs4), el clon de Auto.js 4.1.1 Alpha2 que sirve como referencia de comportamiento de AutoJs6. Tras instalarlo, la entrada "Ejecutar con AutoJs4" del gestor de archivos de AutoJs6 ejecuta archivos `.js`, grabaciones `.auto` y carpetas de proyecto con el motor Rhino 1.7.7.2 original dentro del propio proceso del complemento, mientras la salida de la consola vuelve a AutoJs6. Así se puede comprobar en un solo dispositivo si un script se comporta de forma distinta en AutoJs4 y en AutoJs6 sin instalar AutoJs4.

******

### Instalación y uso

******

1. Usa AutoJs6 build 5334 (6.8.0) o posterior en Android 7.0 (API 24) o posterior.
2. Instala el APK de release que corresponda a la ABI del dispositivo (`arm64-v8a` para la mayoría de los teléfonos actuales, `armeabi-v7a` para dispositivos de 32 bits, `x86_64` o `x86` para emuladores) o el APK `universal`, más grande.
3. Abre el centro de complementos de AutoJs6 y activa AutoJs4 Runtime. Si Android dejó el paquete recién instalado detenido, usa la acción `Activar` del host.
4. Abre los ajustes del complemento desde el centro de complementos y concede el acceso al almacenamiento, la superposición sobre otras aplicaciones y el servicio de accesibilidad según lo necesiten los scripts; estos permisos pertenecen al complemento, no a AutoJs6.
5. En el gestor de archivos de AutoJs6 abre el menú de un archivo `.js`, un archivo `.auto` o una carpeta de proyecto y elige `Ejecutar con AutoJs4`. La tarea aparece en la lista de tareas de AutoJs6 y puede detenerse allí.

> El complemento no modifica AutoJs4: el mismo Rhino 1.7.7.2, la biblioteca OpenCV 3.4.3 original de AutoJs4 en armeabi-v7a y la misma versión de OpenCV recompilada para dispositivos de 64 bits, `targetSdk 28` y los mismos módulos del motor. Por tanto, las diferencias observadas entre ambos motores son diferencias entre AutoJs4 y AutoJs6 y pueden notificarse en el seguimiento de issues de AutoJs6.

******

### Inicio rápido

******

Guarda este archivo en el espacio de trabajo de AutoJs6 y ejecútalo mediante `Ejecutar con AutoJs4`:

```javascript
console.log("engine package: " + context.getPackageName());
console.log("device: " + device.brand + " " + device.model + " (API " + device.sdkInt + ")");
toast("Hello from AutoJs4");
```

La consola de AutoJs6 muestra las líneas con el prefijo `[AutoJs4]`; la primera línea indica el nombre del paquete del complemento que ejecutó el script, lo que confirma que lo ejecutó el motor AutoJs4.

******

### Compatibilidad y alcance

******

- Motor: AutoJs4 4.4.0 (clon de Auto.js 4.1.1 Alpha2), commit de Git [`3fb08269198ebd49a880ab3ca2621383eaa10443`](https://github.com/SuperMonster003/AutoJs4/commit/3fb08269198ebd49a880ab3ca2621383eaa10443), Rhino 1.7.7.2, OpenCV 3.4.3. Las fuentes de AutoJs4 se incluyen como submodule de Git y se compilan sin modificaciones; las bibliotecas OpenCV de arm64-v8a y x86_64 se recompilan a partir de la misma versión con el NDK 28.2.13676358 y se verifica que exportan todos los símbolos JNI de la biblioteca original.
- Plataforma: Android 7.0 (API 24) o posterior. Los APK de una sola ABI cubren `arm64-v8a, armeabi-v7a, x86_64, x86`; el APK `universal` contiene las cuatro. Igual que AutoJs4, la build `x86` no tiene OpenCV.
- Scripts: archivos `.js`, grabaciones root `.auto` y carpetas de proyecto con `project.json` (se respetan el script `main` y las `features`). Los scripts `"ui";` abren la propia actividad de script de AutoJs4 dentro del proceso del complemento.
- Permisos: el acceso al almacenamiento (almacenamiento externo heredado, `targetSdk 28`), la superposición sobre otras aplicaciones y el servicio de accesibilidad de AutoJs4 se conceden al complemento de forma independiente de AutoJs6. Las funciones root usan el `su` del dispositivo como AutoJs4.
- Concurrencia: hasta 16 scripts de AutoJs4 se ejecutan a la vez; detener una tarea en AutoJs6 detiene el motor AutoJs4, y un fallo del proceso del complemento se notifica como tarea fallida.

******

### Permisos e integridad

******

- La actividad Wake y los dos servicios exportados requieren `org.autojs.permission.PLUGIN`; los scripts se ejecutan con el UID del complemento en el proceso `:autojs4_runtime`, nunca dentro de AutoJs6.
- Los scripts se identifican por ruta absoluta. El complemento lee y escribe archivos solo con el permiso de almacenamiento que el usuario le concedió y rechaza las solicitudes cuya ruta no sea absoluta o cuyo archivo no pueda leerse.
- La salida de la consola se transmite a AutoJs6 mediante un callback oneway acotado en fragmentos de como máximo 64 KiB; el evento terminal solo lleva el estado, el mensaje de error y un resumen de la pila.
- Los puntos de entrada externos propios de AutoJs4 (intents de ejecución, accesos directos, Tasker) no se exportan. Solo el host AutoJs6 puede iniciar scripts a través del servicio Binder protegido.

******

### Interfaz del complemento

******

Identificadores y límites estables para desarrolladores del host y del complemento AutoJs6:

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

`AutoJs4RuntimeService` se descubre mediante la action `org.autojs.plugin.autojs4.RUNTIME` y la category `autojs4-runtime`. `runScript` valida el Bundle de solicitud (ruta del script, directorio de trabajo, carpeta de proyecto opcional, argumentos, retraso, intervalo y número de repeticiones), inicia la ejecución de AutoJs4 y devuelve de inmediato un Bundle de aceptación; después llegan los eventos `started`, `log` y `finished` por el callback. `cancelScript` detiene una ejecución en curso o recuerda una cancelación para una ejecución aún no despachada. El contrato completo está documentado en el repositorio de AutoJs6 ([protocolo V1](https://github.com/SuperMonster003/AutoJs6/blob/master/docs/dev/autojs4-runtime-protocol-v1.md)).

Tamaño de página de 16 KB: las bibliotecas nativas de arm64-v8a y x86_64 (OpenCV 3.4.3 recompilado con el NDK 28.2.13676358 y las bibliotecas de terminal jackpal recompiladas por el host AutoJs6) se enlazan con un tamaño de página máximo de 16 KB, y la comprobación de alineación compartida verifica cada segmento ELF PT_LOAD al compilar. Todavía no se ha ejecutado en un dispositivo con páginas de 16 KB.

******

### Historial de versiones

******

#### v1.0.1

_2026/10/12_

- `Mejora` Cuando un script necesita el servicio de accesibilidad y el servicio del complemento no está activado, un aviso emergente explica por qué se abren los ajustes de accesibilidad del sistema

#### v1.0.0

_2026/10/12_

- `Aviso` armeabi-v7a usa la biblioteca nativa OpenCV 3.4.3 original de AutoJs4, los dispositivos de 64 bits usan la misma versión recompilada con el NDK 28.2; la build x86 no tiene OpenCV, igual que AutoJs4
- `Función` Ejecución de archivos `.js`, grabaciones `.auto` y carpetas de proyecto desde el gestor de archivos de AutoJs6 con el motor AutoJs4 4.4.0 integrado (Rhino 1.7.7.2, OpenCV 3.4.3) mediante la entrada de menú `Ejecutar con AutoJs4`
- `Función` Servicio de ejecución protegido con el contrato AIDL versionado `autojs4-runtime-api`: solicitudes por ruta de archivo, líneas de consola transmitidas, eventos de inicio y finalización, cancelación y ejecuciones concurrentes
- `Función` Página de ajustes del complemento con servicio de accesibilidad, superposición sobre otras aplicaciones, acceso al almacenamiento, optimización de batería y estado root, además del interruptor `Mostrar la ventana de consola de AutoJs4`
- `Función` Distribución de los APK `arm64-v8a`, `armeabi-v7a`, `x86_64`, `x86` y `universal`, activación Wake, metadatos PluginInfo completos, instrucciones del centro de complementos, README y changelog en 10 idiomas
- `Mejora` Compilación de las bibliotecas OpenCV 3.4.3 y de terminal jackpal de arm64-v8a y x86_64 con alineación de páginas de 16 KB, y verificación al compilar de la alineación ELF y de la cobertura completa de los símbolos JNI de la biblioteca original
- `Dependencia` Integración de AutoJs4 4.4.0 como submodule de Git en el commit [`3fb08269198ebd49a880ab3ca2621383eaa10443`](https://github.com/SuperMonster003/AutoJs4/commit/3fb08269198ebd49a880ab3ca2621383eaa10443)

##### Más historial de versiones

- [CHANGELOG.md](https://github.com/SuperMonster003/AutoJs6-Plugin-AutoJs4-Runtime/blob/master/app/src/main/assets/doc/CHANGELOG-es.md)

******

### Compilación y verificación

******

La compilación requiere JDK 17 o posterior, Python 3 y Android SDK 37 (los módulos del motor AutoJs4 compilan contra la API 30, que el SDK de Android descarga a demanda). Clona con `--recurse-submodules` para rellenar `autojs4/`. Las comprobaciones estándar validan la documentación generada, las pruebas JVM, los APK debug, el paquete de instrumentación y Android Lint.

```powershell
git clone --recurse-submodules https://github.com/SuperMonster003/AutoJs6-Plugin-AutoJs4-Runtime.git
py .python\generate_markdown.py --check
py .python\generate_provisional_icon.py --check
.\gradlew.bat :app:testDebugUnitTest :app:assembleDebug :app:assembleDebugAndroidTest :app:lintDebug
```

******

### Localización y generación de documentos

******

Edita las plantillas JSON y Markdown y ejecuta `py .python/generate_markdown.py`. Los archivos README, changelog e instrucciones del complemento generados no deben editarse a mano. El modo `--check` valida la estructura de idiomas, la alineación de versiones, los recursos localizados, los archivos huérfanos y la desviación de los archivos generados sin escribir.

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

El icono conserva el [PNG proporcionado por el mantenedor](https://github.com/SuperMonster003/AutoJs6-Plugin-AutoJs4-Runtime/tree/master/.python/icons) (una variante para el tema claro y otra para el oscuro) y su composición original. Ajústelo con Icon Studio y compruebe la receta y los recursos generados con `python .python/generate_icon_studio.py --check`.

******

### Licencia

******

El código del complemento se publica bajo la [Mozilla Public License 2.0](https://github.com/SuperMonster003/AutoJs6-Plugin-AutoJs4-Runtime/blob/master/LICENSE). Las fuentes de AutoJs4 integradas tienen licencia MPL 2.0 con la cláusula no comercial del repositorio de AutoJs4; Rhino, OpenCV, las bibliotecas de terminal jackpal y los demás componentes de terceros conservan sus propias licencias. Consulta los [avisos de terceros](https://github.com/SuperMonster003/AutoJs6-Plugin-AutoJs4-Runtime/blob/master/THIRD_PARTY_NOTICES.md).

******

### Enlaces

******

- Proyecto AutoJs6: https://github.com/SuperMonster003/AutoJs6
- Proyecto AutoJs4: https://github.com/SuperMonster003/AutoJs4
- Documentación de AutoJs4: https://github.com/SuperMonster003/AutoJs4-Documentation
- Protocolo V1 de AutoJs4 Runtime: https://github.com/SuperMonster003/AutoJs6/blob/master/docs/dev/autojs4-runtime-protocol-v1.md
- Roadmap de AutoJs4 Runtime: https://github.com/SuperMonster003/AutoJs6/blob/master/docs/dev/autojs4-runtime-plugin-roadmap.md
- Avisos de terceros: https://github.com/SuperMonster003/AutoJs6-Plugin-AutoJs4-Runtime/blob/master/THIRD_PARTY_NOTICES.md
