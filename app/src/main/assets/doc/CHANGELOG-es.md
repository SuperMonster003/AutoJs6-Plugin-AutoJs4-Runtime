******

### Historial de versiones

******

# v1.0.0

###### 2026/10/12

* `Aviso` armeabi-v7a usa la biblioteca nativa OpenCV 3.4.3 original de AutoJs4, los dispositivos de 64 bits usan la misma versión recompilada con el NDK 28.2; la build x86 no tiene OpenCV, igual que AutoJs4
* `Función` Ejecución de archivos `.js`, grabaciones `.auto` y carpetas de proyecto desde el gestor de archivos de AutoJs6 con el motor AutoJs4 4.4.0 integrado (Rhino 1.7.7.2, OpenCV 3.4.3) mediante la entrada de menú `Ejecutar con AutoJs4`
* `Función` Servicio de ejecución protegido con el contrato AIDL versionado `autojs4-runtime-api`: solicitudes por ruta de archivo, líneas de consola transmitidas, eventos de inicio y finalización, cancelación y ejecuciones concurrentes
* `Función` Página de ajustes del complemento con servicio de accesibilidad, superposición sobre otras aplicaciones, acceso al almacenamiento, optimización de batería y estado root, además del interruptor `Mostrar la ventana de consola de AutoJs4`
* `Función` Distribución de los APK `arm64-v8a`, `armeabi-v7a`, `x86_64`, `x86` y `universal`, activación Wake, metadatos PluginInfo completos, instrucciones del centro de complementos, README y changelog en 10 idiomas
* `Mejora` Compilación de las bibliotecas OpenCV 3.4.3 y de terminal jackpal de arm64-v8a y x86_64 con alineación de páginas de 16 KB, y verificación al compilar de la alineación ELF y de la cobertura completa de los símbolos JNI de la biblioteca original
* `Dependencia` Integración de AutoJs4 4.4.0 como submodule de Git en el commit [`3fb08269198ebd49a880ab3ca2621383eaa10443`](https://github.com/SuperMonster003/AutoJs4/commit/3fb08269198ebd49a880ab3ca2621383eaa10443)
