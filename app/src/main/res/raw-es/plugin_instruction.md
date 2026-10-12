AutoJs4 Runtime integra el motor de [AutoJs4](https://github.com/SuperMonster003/AutoJs4), el clon de Auto.js 4.1.1 Alpha2 que sirve como referencia de comportamiento de AutoJs6. Tras instalarlo, la entrada "Ejecutar con AutoJs4" del gestor de archivos de AutoJs6 ejecuta archivos `.js`, grabaciones `.auto` y carpetas de proyecto con el motor Rhino 1.7.7.2 original dentro del propio proceso del complemento, mientras la salida de la consola vuelve a AutoJs6. Así se puede comprobar en un solo dispositivo si un script se comporta de forma distinta en AutoJs4 y en AutoJs6 sin instalar AutoJs4.

Esta versión integra AutoJs4 4.4.0 (commit `3fb08269198ebd49a880ab3ca2621383eaa10443`) con Rhino 1.7.7.2 y OpenCV 3.4.3 y ejecuta cada script en el proceso aislado `:autojs4_runtime`. El inicio, las líneas de consola y la finalización vuelven a AutoJs6 mediante el contrato del host; la tarea puede detenerse desde la lista de tareas de AutoJs6.

### Inicio rápido

```javascript
console.log("engine package: " + context.getPackageName());
console.log("device: " + device.brand + " " + device.model + " (API " + device.sdkInt + ")");
toast("Hello from AutoJs4");
```

La consola de AutoJs6 muestra las líneas con el prefijo `[AutoJs4]`; la primera línea indica el nombre del paquete del complemento que ejecutó el script, lo que confirma que lo ejecutó el motor AutoJs4.

### Compatibilidad y alcance

- Motor: AutoJs4 4.4.0 (clon de Auto.js 4.1.1 Alpha2), commit de Git [`3fb08269198ebd49a880ab3ca2621383eaa10443`](https://github.com/SuperMonster003/AutoJs4/commit/3fb08269198ebd49a880ab3ca2621383eaa10443), Rhino 1.7.7.2, OpenCV 3.4.3. Las fuentes de AutoJs4 se incluyen como submodule de Git y se compilan sin modificaciones.
- Plataforma: Android 7.0 (API 24) o posterior con soporte de aplicaciones de 32 bits. Los APK de una sola ABI cubren `armeabi-v7a, x86`; el APK `universal` contiene ambas. Los dispositivos exclusivamente de 64 bits no pueden instalar el complemento, igual que no pueden instalar AutoJs4.
- Scripts: archivos `.js`, grabaciones root `.auto` y carpetas de proyecto con `project.json` (se respetan el script `main` y las `features`). Los scripts `"ui";` abren la propia actividad de script de AutoJs4 dentro del proceso del complemento.
- Permisos: el acceso al almacenamiento (almacenamiento externo heredado, `targetSdk 28`), la superposición sobre otras aplicaciones y el servicio de accesibilidad de AutoJs4 se conceden al complemento de forma independiente de AutoJs6. Las funciones root usan el `su` del dispositivo como AutoJs4.
- Concurrencia: hasta 16 scripts de AutoJs4 se ejecutan a la vez; detener una tarea en AutoJs6 detiene el motor AutoJs4, y un fallo del proceso del complemento se notifica como tarea fallida.

Consulta el [README del proyecto](https://github.com/SuperMonster003/AutoJs6-Plugin-AutoJs4-Runtime) para la selección de ABI, los permisos, el contrato exacto y el alcance de esta versión.
