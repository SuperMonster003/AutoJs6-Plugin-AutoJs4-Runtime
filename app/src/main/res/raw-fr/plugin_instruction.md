AutoJs4 Runtime intègre le moteur non modifié d'AutoJs4 (un clone de Auto.js 4.1.1 Alpha2), ce qui permet à AutoJs6 d'exécuter des scripts avec ce moteur sans installer AutoJs4 séparément. La sortie de la console est renvoyée à AutoJs6, ce qui facilite la comparaison du comportement d'un script sous AutoJs4 et sous AutoJs6 sur le même appareil.

### Prise en main

1. Vérifiez que ce plugin est activé dans le centre des plugins d'AutoJs6.
2. Ouvrez les paramètres de ce plugin depuis le centre des plugins, puis, dans "Permissions et services", accordez "Accès au stockage", "Superposition aux autres applications" ou "Service d'accessibilité" selon les besoins de vos scripts.
3. Dans le gestionnaire de fichiers d'AutoJs6, ouvrez le menu d'un fichier `.js`, d'un fichier `.auto` ou d'un dossier de projet et choisissez "Exécuter avec AutoJs4" (dans le sous-menu "AutoJs4" à partir d'AutoJs6 6.8.1).
4. Les journaux s'affichent dans la console d'AutoJs6 avec le préfixe `[AutoJs4]`; la tâche apparaît dans la liste des tâches d'AutoJs6, où vous pouvez l'arrêter.

### Fonctionnalités principales

- **Exécuter avec AutoJs4**: exécute les fichiers `.js`, les enregistrements `.auto` et les dossiers de projet contenant un `project.json` avec le moteur AutoJs4; l'interface des scripts `"ui";` s'affiche dans ce plugin.
- **Exécuter avec AutoJs4 et comparer**: exécute le même script avec AutoJs6 puis avec AutoJs4 et indique dans "Comparaison avec AutoJs4" si les deux sorties de console sont identiques, ainsi que la durée de chaque exécution.
- **Afficher la fenêtre de console AutoJs4**: un interrupteur du groupe "Exécution" des paramètres; une fois activé, `console.show()` affiche la console flottante d'AutoJs4. Les journaux sont toujours renvoyés à AutoJs6.
- **Permissions et services**: les paramètres indiquent l'état de l'accès au stockage, de la superposition aux autres applications, du service d'accessibilité, de l'optimisation de la batterie et de l'accès root; touchez une ligne pour l'accorder ou le vérifier.

### Utilisation dans les scripts

Les scripts utilisent l'API d'AutoJs4 (identique à celle de Auto.js 4.1.1 Alpha2). Enregistrez le code ci-dessous dans un fichier `.js` et choisissez "Exécuter avec AutoJs4": la première ligne de la console affiche le nom de paquet de ce plugin, ce qui signifie que le script a été exécuté par AutoJs4:

```js
console.log("engine package: " + context.getPackageName());
console.log("device: " + device.brand + " " + device.model + " (API " + device.sdkInt + ")");
toast("Hello from AutoJs4");
```

- Les modules et la syntaxe propres à AutoJs6 ne sont pas disponibles dans AutoJs4; si les deux moteurs donnent des résultats différents, comparez-les avec "Exécuter avec AutoJs4 et comparer".
- Les scripts s'exécutent en tant que ce plugin: l'accès aux fichiers dépend de la permission de stockage de ce plugin, et les fonctions root utilisent `su` sur l'appareil.
- Jusqu'à 16 scripts AutoJs4 peuvent s'exécuter en même temps.

### Prérequis

- Android 7.0 ou version ultérieure et AutoJs6 6.8.0 ou version ultérieure.
- L'accès au stockage permet aux scripts de lire et d'écrire leurs fichiers.
- La superposition aux autres applications sert à la fenêtre de console et aux interfaces flottantes d'AutoJs4, le service d'accessibilité à la recherche de widgets et aux touchers automatiques; accordez-les seulement quand un script en a besoin.
- Comme dans l'AutoJs4 d'origine, la version x86 n'inclut pas OpenCV: les fonctions d'image qui en dépendent (par exemple la recherche d'image) ne sont pas disponibles sur les appareils x86.

### Questions fréquentes

**Lorsqu'une permission est signalée comme manquante**

Les permissions de ce plugin sont distinctes de celles d'AutoJs6. Ouvrez les paramètres de ce plugin depuis le centre des plugins, accordez la permission signalée dans "Permissions et services", puis relancez le script.

**Lorsque les deux moteurs donnent des résultats différents**

Ce plugin utilise le moteur AutoJs4 non modifié, avec les mêmes versions de Rhino et d'OpenCV qu'AutoJs4: une différence de résultat est donc une différence entre AutoJs4 et AutoJs6. Si elle gêne la migration de vos scripts, vous pouvez la signaler sur le [suivi des issues d'AutoJs6](https://github.com/SuperMonster003/AutoJs6/issues).

**Lorsque le système interrompt les scripts de longue durée**

Dans "Permissions et services" des paramètres de ce plugin, touchez "Ignorer l'optimisation de la batterie" pour exempter ce plugin de l'optimisation de la batterie.

Pour l'API de script d'AutoJs4, consultez la [documentation d'AutoJs4](https://github.com/SuperMonster003/AutoJs4-Documentation); pour le code source et plus de détails, consultez la [page du projet](https://github.com/SuperMonster003/AutoJs6-Plugin-AutoJs4-Runtime).
