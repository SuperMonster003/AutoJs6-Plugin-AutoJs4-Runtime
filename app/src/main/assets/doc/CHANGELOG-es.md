******

### Historial de versiones

******

# v1.1.2

###### 2026/11/27

* `Mejora` El resumen y las instrucciones del plugin indican ahora para qué sirve: comparar los resultados con AutoJs6 al migrar scripts antiguos de Auto.js 4.x; la mayoría de los usuarios, que solo escriben scripts nuevos en AutoJs6, no necesitan instalarlo

# v1.1.1

###### 2026/11/20

* `Mejora` La página del historial de versiones ahora se expande o contrae por versión: una versión contraída muestra su fecha y el número de entradas por categoría, una expandida nombra cada categoría una sola vez; todas las versiones se pueden expandir o contraer a la vez, la más reciente se abre expandida y el subtítulo muestra el nombre del plugin
* `Mejora` Se reescribieron las instrucciones del Centro de plugins en cinco partes: primeros pasos, funciones principales, uso en scripts, requisitos y preguntas frecuentes
* `Dependencia` Se añadió Jetpack Compose BOM 2026.09.00 (página Historial de versiones)
* `Dependencia` Se actualizaron AndroidX Core a 1.16.0 y AndroidX Lifecycle a 2.9.4 (los requiere Jetpack Compose; AppCompat sigue en la versión 1.0.2 de AutoJs4)
* `Dependencia` Se actualizaron los plugins de compilación compartidos versión 1.9.0 -> 1.9.7

# v1.1.0

###### 2026/11/08

* `Corrección` El icono de la aplicación era difícil de distinguir sobre fondos oscuros en algunos lanzadores y listas de aplicaciones del sistema
* `Mejora` En el menú de archivos de AutoJs6 6.8.1 y posteriores, "Ejecutar con AutoJs4" y "Ejecutar con AutoJs4 y comparar" los proporciona ahora el complemento, dentro del menú de primer nivel "AutoJs4" (sin cambios en versiones anteriores de AutoJs6)
* `Mejora` Las páginas de ajustes e historial de versiones siguen el idioma, modo oscuro y color del tema de AutoJs6, y usan la apariencia del sistema si los ajustes del anfitrión no están disponibles

# v1.0.1

###### 2026/10/12

* `Mejora` Cuando un script necesita el servicio de accesibilidad y el servicio del complemento no está activado, un aviso emergente explica por qué se abren los ajustes de accesibilidad del sistema

# v1.0.0

###### 2026/10/12

* `Aviso` armeabi-v7a usa la biblioteca nativa OpenCV 3.4.3 original de AutoJs4, los dispositivos de 64 bits usan la misma versión recompilada con el NDK 28.2; la build x86 no tiene OpenCV, igual que AutoJs4
* `Función` Ejecución de archivos `.js`, grabaciones `.auto` y carpetas de proyecto desde el gestor de archivos de AutoJs6 con el motor AutoJs4 4.4.0 integrado (Rhino 1.7.7.2, OpenCV 3.4.3) mediante la entrada de menú `Ejecutar con AutoJs4`
* `Función` Servicio de ejecución protegido con el contrato AIDL versionado `autojs4-runtime-api`: solicitudes por ruta de archivo, líneas de consola transmitidas, eventos de inicio y finalización, cancelación y ejecuciones concurrentes
* `Función` Página de ajustes del complemento con servicio de accesibilidad, superposición sobre otras aplicaciones, acceso al almacenamiento, optimización de batería y estado root, además del interruptor `Mostrar la ventana de consola de AutoJs4`
* `Función` Distribución de los APK `arm64-v8a`, `armeabi-v7a`, `x86_64`, `x86` y `universal`, activación Wake, metadatos PluginInfo completos, instrucciones del centro de complementos, README y changelog en 10 idiomas
* `Mejora` Compilación de las bibliotecas OpenCV 3.4.3 y de terminal jackpal de arm64-v8a y x86_64 con alineación de páginas de 16 KB, y verificación al compilar de la alineación ELF y de la cobertura completa de los símbolos JNI de la biblioteca original
* `Dependencia` Integración de AutoJs4 4.4.0 como submodule de Git en el commit [`3fb08269198ebd49a880ab3ca2621383eaa10443`](https://github.com/SuperMonster003/AutoJs4/commit/3fb08269198ebd49a880ab3ca2621383eaa10443)
