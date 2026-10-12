AutoJs4 Runtime embarque le moteur d'[AutoJs4](https://github.com/SuperMonster003/AutoJs4), le clone de Auto.js 4.1.1 Alpha2 qui sert de référence comportementale à AutoJs6. Une fois installé, l'entrée "Exécuter avec AutoJs4" du gestionnaire de fichiers d'AutoJs6 exécute les fichiers `.js`, les enregistrements `.auto` et les dossiers de projet avec le moteur Rhino 1.7.7.2 d'origine dans le propre processus du plugin, tandis que la sortie console revient dans AutoJs6. On peut ainsi vérifier sur un seul appareil si un script se comporte différemment sur AutoJs4 et sur AutoJs6, sans installer AutoJs4.

Cette version embarque AutoJs4 4.4.0 (commit `3fb08269198ebd49a880ab3ca2621383eaa10443`) avec Rhino 1.7.7.2 et OpenCV 3.4.3 et exécute chaque script dans le processus isolé `:autojs4_runtime`. Le démarrage, les lignes de console et la fin reviennent à AutoJs6 par le contrat de l'hôte ; la tâche peut être arrêtée depuis la liste des tâches d'AutoJs6.

### Démarrage rapide

```javascript
console.log("engine package: " + context.getPackageName());
console.log("device: " + device.brand + " " + device.model + " (API " + device.sdkInt + ")");
toast("Hello from AutoJs4");
```

La console d'AutoJs6 affiche les lignes préfixées par `[AutoJs4]` ; la première ligne indique le nom du paquet du plugin qui a exécuté le script, ce qui confirme l'exécution par le moteur AutoJs4.

### Compatibilité et portée

- Moteur : AutoJs4 4.4.0 (clone de Auto.js 4.1.1 Alpha2), commit Git [`3fb08269198ebd49a880ab3ca2621383eaa10443`](https://github.com/SuperMonster003/AutoJs4/commit/3fb08269198ebd49a880ab3ca2621383eaa10443), Rhino 1.7.7.2, OpenCV 3.4.3. Les sources d'AutoJs4 sont incluses comme submodule Git et compilées sans modification ; les bibliothèques OpenCV arm64-v8a et x86_64 sont recompilées à partir de la même version avec le NDK 28.2.13676358 et vérifiées pour exporter tous les symboles JNI de la bibliothèque d'origine.
- Plateforme : Android 7.0 (API 24) ou ultérieur. Les APK mono-ABI couvrent `arm64-v8a, armeabi-v7a, x86_64, x86` ; l'APK `universal` contient les quatre. Comme AutoJs4, la build `x86` n'a pas d'OpenCV.
- Scripts : fichiers `.js`, enregistrements root `.auto` et dossiers de projet avec `project.json` (le script `main` et les `features` sont respectés). Les scripts `"ui";` ouvrent l'activité de script d'AutoJs4 dans le processus du plugin.
- Permissions : l'accès au stockage (stockage externe hérité, `targetSdk 28`), la superposition aux autres applications et le service d'accessibilité d'AutoJs4 sont accordés au plugin indépendamment d'AutoJs6. Les fonctions root utilisent le `su` de l'appareil comme AutoJs4.
- Concurrence : jusqu'à 16 scripts AutoJs4 s'exécutent en même temps ; arrêter une tâche dans AutoJs6 arrête le moteur AutoJs4, et un plantage du processus du plugin est signalé comme tâche échouée.

Consultez le [README du projet](https://github.com/SuperMonster003/AutoJs6-Plugin-AutoJs4-Runtime) pour le choix de l'ABI, les permissions, le contrat exact et le périmètre de cette version.
