******

### Historial de versiones

******

# v1.0.0

###### 2026/10/12

* `Aviso` La primera versión replica exactamente AutoJs4, incluidas sus bibliotecas nativas solo de 32 bits, por lo que el complemento no puede instalarse en dispositivos exclusivamente de 64 bits
* `Función` Ejecución de archivos `.js`, grabaciones `.auto` y carpetas de proyecto desde el gestor de archivos de AutoJs6 con el motor AutoJs4 4.4.0 integrado (Rhino 1.7.7.2, OpenCV 3.4.3) mediante la entrada de menú `Ejecutar con AutoJs4`
* `Función` Servicio de ejecución protegido con el contrato AIDL versionado `autojs4-runtime-api`: solicitudes por ruta de archivo, líneas de consola transmitidas, eventos de inicio y finalización, cancelación y ejecuciones concurrentes
* `Función` Página de ajustes del complemento con servicio de accesibilidad, superposición sobre otras aplicaciones, acceso al almacenamiento, optimización de batería y estado root, además del interruptor `Mostrar la ventana de consola de AutoJs4`
* `Función` Distribución de los APK `armeabi-v7a`, `x86` y `universal`, activación Wake, metadatos PluginInfo completos, instrucciones del centro de complementos, README y changelog en 10 idiomas
* `Dependencia` Integración de AutoJs4 4.4.0 como submodule de Git en el commit [`3fb08269198ebd49a880ab3ca2621383eaa10443`](https://github.com/SuperMonster003/AutoJs4/commit/3fb08269198ebd49a880ab3ca2621383eaa10443)
