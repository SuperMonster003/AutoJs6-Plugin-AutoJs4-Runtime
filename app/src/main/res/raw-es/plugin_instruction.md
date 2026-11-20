AutoJs4 Runtime incorpora el motor sin modificar de AutoJs4 (un clon de Auto.js 4.1.1 Alpha2), de modo que AutoJs6 puede ejecutar scripts con él sin instalar AutoJs4 por separado. La salida de la consola se devuelve a AutoJs6, lo que facilita comparar cómo se comporta un script en AutoJs4 y en AutoJs6 en el mismo dispositivo.

### Primeros pasos

1. Compruebe que este plugin está activado en el centro de plugins de AutoJs6.
2. Abra los ajustes de este plugin desde el centro de plugins y, en "Permisos y servicios", conceda "Acceso al almacenamiento", "Mostrar sobre otras aplicaciones" o "Servicio de accesibilidad" según lo necesiten sus scripts.
3. En el gestor de archivos de AutoJs6, abra el menú de un archivo `.js`, un archivo `.auto` o una carpeta de proyecto y elija "Ejecutar con AutoJs4" (en el submenú "AutoJs4" a partir de AutoJs6 6.8.1).
4. Los registros aparecen en la consola de AutoJs6 con el prefijo `[AutoJs4]`; la tarea aparece en la lista de tareas de AutoJs6, donde puede detenerla.

### Funciones principales

- **Ejecutar con AutoJs4**: ejecuta archivos `.js`, grabaciones `.auto` y carpetas de proyecto con un `project.json` en el motor de AutoJs4; la interfaz de los scripts `"ui";` se muestra en este plugin.
- **Ejecutar con AutoJs4 y comparar**: ejecuta el mismo script en AutoJs6 y en AutoJs4 y muestra en "Comparación con AutoJs4" si las dos salidas de consola coinciden y cuánto tardó cada ejecución.
- **Mostrar la ventana de consola de AutoJs4**: un interruptor del grupo "Ejecución" de los ajustes; cuando está activado, `console.show()` muestra la consola flotante de AutoJs4. Los registros siempre vuelven a AutoJs6.
- **Permisos y servicios**: los ajustes muestran el estado del acceso al almacenamiento, la superposición sobre otras aplicaciones, el servicio de accesibilidad, la optimización de batería y el acceso root; toque una fila para concederlo o comprobarlo.

### Uso en scripts

Los scripts usan la API de AutoJs4 (la misma que Auto.js 4.1.1 Alpha2). Guarde el código siguiente como archivo `.js` y elija "Ejecutar con AutoJs4": la primera línea de la consola muestra el nombre de paquete de este plugin, lo que indica que AutoJs4 ejecutó el script:

```js
console.log("engine package: " + context.getPackageName());
console.log("device: " + device.brand + " " + device.model + " (API " + device.sdkInt + ")");
toast("Hello from AutoJs4");
```

- Los módulos y la sintaxis exclusivos de AutoJs6 no están disponibles en AutoJs4; si los dos motores dan resultados distintos, compárelos con "Ejecutar con AutoJs4 y comparar".
- Los scripts se ejecutan como este plugin: el acceso a archivos depende del permiso de almacenamiento de este plugin y las funciones root usan `su` del dispositivo.
- Pueden ejecutarse hasta 16 scripts de AutoJs4 a la vez.

### Requisitos

- Android 7.0 o posterior y AutoJs6 6.8.0 o posterior.
- El acceso al almacenamiento permite a los scripts leer y escribir sus archivos.
- La superposición sobre otras aplicaciones se usa para la ventana de consola y las interfaces flotantes de AutoJs4, y el servicio de accesibilidad para buscar controles y tocar automáticamente; concédalos solo cuando un script los necesite.
- Igual que en el AutoJs4 original, la versión x86 no incluye OpenCV, así que las funciones de imagen que dependen de él (como buscar imágenes) no están disponibles en dispositivos x86.

### Preguntas frecuentes

**Cuando se indica que falta un permiso**

Los permisos de este plugin son independientes de los de AutoJs6. Abra los ajustes de este plugin desde el centro de plugins, conceda el permiso indicado en "Permisos y servicios" y vuelva a ejecutar el script.

**Cuando los dos motores dan resultados distintos**

Este plugin usa el motor de AutoJs4 sin modificar, con las mismas versiones de Rhino y OpenCV que AutoJs4, así que una diferencia en los resultados es una diferencia entre AutoJs4 y AutoJs6. Si afecta a la migración de sus scripts, puede comunicarla en el [gestor de issues de AutoJs6](https://github.com/SuperMonster003/AutoJs6/issues).

**Cuando el sistema detiene scripts de larga duración**

En "Permisos y servicios" de los ajustes de este plugin, toque "Ignorar la optimización de batería" para que la optimización de batería no limite este plugin.

Para la API de scripts de AutoJs4, consulte la [documentación de AutoJs4](https://github.com/SuperMonster003/AutoJs4-Documentation); para el código fuente y más detalles, consulte la [página del proyecto](https://github.com/SuperMonster003/AutoJs6-Plugin-AutoJs4-Runtime).
