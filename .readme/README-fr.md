<!--suppress HtmlDeprecatedAttribute, HttpUrlsUsage -->

<div align="center">
  <p>
    <picture>
      <source media="(prefers-color-scheme: dark)" srcset="https://raw.githubusercontent.com/SuperMonster003/AutoJs6-Plugin-AutoJs4-Runtime/master/app/src/main/res/mipmap-night/ic_launcher.png" />
      <img src="https://raw.githubusercontent.com/SuperMonster003/AutoJs6-Plugin-AutoJs4-Runtime/master/app/src/main/res/mipmap/ic_launcher.png" alt="AutoJs4 Runtime" border="0" width="128" />
    </picture>
  </p>

  <p>Exécute des scripts avec le moteur Auto.js 4.1.1 Alpha2 embarqué pour comparer les résultats avec AutoJs6</p>

  <p>
    <a href="https://github.com/SuperMonster003/AutoJs6-Plugin-AutoJs4-Runtime/releases"><img alt="GitHub release" src="https://img.shields.io/github/v/release/SuperMonster003/AutoJs6-Plugin-AutoJs4-Runtime?label=Release"/></a>
    <a href="https://github.com/SuperMonster003/AutoJs6-Plugin-AutoJs4-Runtime/issues"><img alt="GitHub issues" src="https://img.shields.io/github/issues/SuperMonster003/AutoJs6-Plugin-AutoJs4-Runtime?color=A24232&label=Issues"/></a>
    <a href="https://github.com/SuperMonster003/AutoJs6-Plugin-AutoJs4-Runtime/blob/master/LICENSE"><img alt="GitHub license" src="https://img.shields.io/github/license/SuperMonster003/AutoJs6-Plugin-AutoJs4-Runtime?color=534BAE&label=License"/></a>
  </p>
</div>

******

### Langues

******

Le README.md actuel prend en charge les langues suivantes:

- [English [en]](https://github.com/SuperMonster003/AutoJs6-Plugin-AutoJs4-Runtime/blob/master/.readme/README-en.md)
- [العربية [ar]](https://github.com/SuperMonster003/AutoJs6-Plugin-AutoJs4-Runtime/blob/master/.readme/README-ar.md)
- [Español [es]](https://github.com/SuperMonster003/AutoJs6-Plugin-AutoJs4-Runtime/blob/master/.readme/README-es.md)
- Français [fr] # actuel
- [日本語 [ja]](https://github.com/SuperMonster003/AutoJs6-Plugin-AutoJs4-Runtime/blob/master/.readme/README-ja.md)
- [한국어 [ko]](https://github.com/SuperMonster003/AutoJs6-Plugin-AutoJs4-Runtime/blob/master/.readme/README-ko.md)
- [Русский [ru]](https://github.com/SuperMonster003/AutoJs6-Plugin-AutoJs4-Runtime/blob/master/.readme/README-ru.md)
- [简体中文 [zh-Hans]](https://github.com/SuperMonster003/AutoJs6-Plugin-AutoJs4-Runtime/blob/master/.readme/README-zh-Hans.md)
- [繁體中文 香港 [zh-Hant-HK]](https://github.com/SuperMonster003/AutoJs6-Plugin-AutoJs4-Runtime/blob/master/.readme/README-zh-Hant-HK.md)
- [繁體中文 台灣 [zh-Hant-TW]](https://github.com/SuperMonster003/AutoJs6-Plugin-AutoJs4-Runtime/blob/master/.readme/README-zh-Hant-TW.md)

******

### Introduction

******

AutoJs4 Runtime embarque le moteur d'[AutoJs4](https://github.com/SuperMonster003/AutoJs4), le clone de Auto.js 4.1.1 Alpha2 qui sert de référence comportementale à AutoJs6. Une fois installé, l'entrée "Exécuter avec AutoJs4" du gestionnaire de fichiers d'AutoJs6 exécute les fichiers `.js`, les enregistrements `.auto` et les dossiers de projet avec le moteur Rhino 1.7.7.2 d'origine dans le propre processus du plugin, tandis que la sortie console revient dans AutoJs6. On peut ainsi vérifier sur un seul appareil si un script se comporte différemment sur AutoJs4 et sur AutoJs6, sans installer AutoJs4.

******

### Installation et utilisation

******

1. Utilisez AutoJs6 build 5334 (6.8.0) ou ultérieur sur Android 7.0 (API 24) ou ultérieur. L'appareil doit pouvoir exécuter des applications 32 bits, exactement comme pour AutoJs4.
2. Installez l'APK de release correspondant à l'ABI de l'appareil (`armeabi-v7a` pour ARM, `x86` pour les émulateurs), ou l'APK `universal`.
3. Ouvrez le centre de plugins d'AutoJs6 et activez AutoJs4 Runtime. Si Android a laissé le nouveau paquet arrêté, utilisez l'action `Activer` de l'hôte.
4. Ouvrez les paramètres du plugin depuis le centre de plugins et accordez l'accès au stockage, la superposition aux autres applications et le service d'accessibilité selon les besoins des scripts ; ces permissions appartiennent au plugin, pas à AutoJs6.
5. Dans le gestionnaire de fichiers d'AutoJs6, ouvrez le menu d'un fichier `.js`, d'un fichier `.auto` ou d'un dossier de projet et choisissez `Exécuter avec AutoJs4`. La tâche apparaît dans la liste des tâches d'AutoJs6 et peut y être arrêtée.

> Le plugin ne modifie pas AutoJs4 : même Rhino 1.7.7.2, même bibliothèque native OpenCV 3.4.3, `targetSdk 28` et même couverture d'ABI 32 bits. Les différences observées entre les deux moteurs sont donc des différences entre AutoJs4 et AutoJs6 et peuvent être signalées dans le suivi des issues d'AutoJs6.

******

### Démarrage rapide

******

Enregistrez ce fichier dans l'espace de travail AutoJs6 et exécutez-le via `Exécuter avec AutoJs4`:

```javascript
console.log(context.getPackageName() + " / Rhino " + org.mozilla.javascript.Context.getCurrentContext().getImplementationVersion());
console.log("device: " + device.brand + " " + device.model + " (API " + device.sdkInt + ")");
toast("Hello from AutoJs4");
```

La console d'AutoJs6 affiche les lignes préfixées par `[AutoJs4]` ; la première ligne indique le nom du paquet AutoJs4 et la version de Rhino qui ont exécuté le script.

******

### Compatibilité et portée

******

- Moteur : AutoJs4 4.4.0 (clone de Auto.js 4.1.1 Alpha2), commit Git [`3fb08269198ebd49a880ab3ca2621383eaa10443`](https://github.com/SuperMonster003/AutoJs4/commit/3fb08269198ebd49a880ab3ca2621383eaa10443), Rhino 1.7.7.2, OpenCV 3.4.3. Les sources d'AutoJs4 sont incluses comme submodule Git et compilées sans modification.
- Plateforme : Android 7.0 (API 24) ou ultérieur avec prise en charge des applications 32 bits. Les APK mono-ABI couvrent `armeabi-v7a, x86` ; l'APK `universal` contient les deux. Les appareils uniquement 64 bits ne peuvent pas installer le plugin, pas plus qu'AutoJs4.
- Scripts : fichiers `.js`, enregistrements root `.auto` et dossiers de projet avec `project.json` (le script `main` et les `features` sont respectés). Les scripts `"ui";` ouvrent l'activité de script d'AutoJs4 dans le processus du plugin.
- Permissions : l'accès au stockage (stockage externe hérité, `targetSdk 28`), la superposition aux autres applications et le service d'accessibilité d'AutoJs4 sont accordés au plugin indépendamment d'AutoJs6. Les fonctions root utilisent le `su` de l'appareil comme AutoJs4.
- Concurrence : jusqu'à 16 scripts AutoJs4 s'exécutent en même temps ; arrêter une tâche dans AutoJs6 arrête le moteur AutoJs4, et un plantage du processus du plugin est signalé comme tâche échouée.

******

### Permissions et intégrité

******

- L'activité Wake et les deux services exportés exigent `org.autojs.permission.PLUGIN` ; les scripts s'exécutent sous l'UID du plugin dans le processus `:autojs4_runtime`, jamais dans AutoJs6.
- Les scripts sont désignés par chemin absolu. Le plugin ne lit et n'écrit des fichiers qu'avec la permission de stockage accordée par l'utilisateur et rejette les requêtes dont le chemin n'est pas absolu ou dont le fichier est illisible.
- La sortie console est diffusée vers AutoJs6 par un callback oneway borné en blocs de 64 KiB au plus ; l'événement terminal ne porte que l'état, le message d'erreur et un résumé de pile.
- Les points d'entrée externes d'AutoJs4 (intents d'exécution, raccourcis, Tasker) ne sont pas exportés. Seul l'hôte AutoJs6 peut lancer des scripts via le service Binder protégé.

******

### Interface du plugin

******

Identifiants et limites stables pour les développeurs de l'hôte et du plugin AutoJs6:

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

`AutoJs4RuntimeService` est découvert par l'action `org.autojs.plugin.autojs4.RUNTIME` et la catégorie `autojs4-runtime`. `runScript` valide le Bundle de requête (chemin du script, répertoire de travail, dossier de projet facultatif, arguments, délai, intervalle et nombre de boucles), démarre l'exécution AutoJs4 et renvoie immédiatement un Bundle d'acceptation ; les événements `started`, `log` et `finished` arrivent ensuite par le callback. `cancelScript` arrête une exécution en cours ou mémorise une annulation pour une exécution pas encore lancée. Le contrat complet est documenté dans le dépôt AutoJs6 ([protocole V1](https://github.com/SuperMonster003/AutoJs6/blob/master/docs/dev/autojs4-runtime-protocol-v1.md)).

Pages de 16 KB : le plugin ne contient que les bibliothèques natives 32 bits livrées par AutoJs4. Sans code natif 64 bits, le contrôle d'alignement n'a rien à vérifier et l'APK ne peut pas être installé sur les appareils uniquement 64 bits. L'ajout d'une build `arm64-v8a` avec un OpenCV 3.4.x recompilé est suivi comme question ouverte dans la [roadmap](https://github.com/SuperMonster003/AutoJs6/blob/master/docs/dev/autojs4-runtime-plugin-roadmap.md).

******

### Historique des versions

******

#### v1.0.0

_2026/10/12_

- `Conseil` La première version reproduit exactement AutoJs4, y compris ses bibliothèques natives uniquement 32 bits ; le plugin ne peut donc pas être installé sur les appareils uniquement 64 bits
- `Fonctionnalité` Exécution des fichiers `.js`, des enregistrements `.auto` et des dossiers de projet depuis le gestionnaire de fichiers d'AutoJs6 avec le moteur AutoJs4 4.4.0 embarqué (Rhino 1.7.7.2, OpenCV 3.4.3) via l'entrée de menu `Exécuter avec AutoJs4`
- `Fonctionnalité` Service d'exécution protégé avec le contrat AIDL versionné `autojs4-runtime-api` : requêtes par chemin de fichier, lignes de console diffusées, événements de démarrage et de fin, annulation et exécutions concurrentes
- `Fonctionnalité` Page de paramètres du plugin avec service d'accessibilité, superposition aux autres applications, accès au stockage, optimisation de la batterie et état root, plus l'interrupteur `Afficher la fenêtre de console AutoJs4`
- `Fonctionnalité` Livraison des APK `armeabi-v7a`, `x86` et `universal`, activation Wake, métadonnées PluginInfo complètes, instructions du centre de plugins, README et changelog en 10 langues
- `Dépendance` Intégration d'AutoJs4 4.4.0 comme submodule Git au commit [`3fb08269198ebd49a880ab3ca2621383eaa10443`](https://github.com/SuperMonster003/AutoJs4/commit/3fb08269198ebd49a880ab3ca2621383eaa10443)

##### Historique supplémentaire

- [CHANGELOG.md](https://github.com/SuperMonster003/AutoJs6-Plugin-AutoJs4-Runtime/blob/master/app/src/main/assets/doc/CHANGELOG-fr.md)

******

### Compilation et vérification

******

La compilation nécessite JDK 17 ou ultérieur, Python 3 et Android SDK 37 (les modules du moteur AutoJs4 compilent contre l'API 30, téléchargée à la demande par le SDK Android). Clonez avec `--recurse-submodules` pour remplir `autojs4/`. Les vérifications standard valident la documentation générée, les tests JVM, les APK debug, le paquet d'instrumentation et Android Lint.

```powershell
git clone --recurse-submodules https://github.com/SuperMonster003/AutoJs6-Plugin-AutoJs4-Runtime.git
py .python\generate_markdown.py --check
py .python\generate_provisional_icon.py --check
.\gradlew.bat :app:testDebugUnitTest :app:assembleDebug :app:assembleDebugAndroidTest :app:lintDebug
```

******

### Localisation et génération des documents

******

Modifiez les modèles JSON et Markdown, puis exécutez `py .python/generate_markdown.py`. Les fichiers README, changelog et d'instructions du plugin générés ne doivent pas être modifiés à la main. Le mode `--check` valide la structure des langues, l'alignement des versions, les ressources localisées, les fichiers orphelins et la dérive des fichiers générés sans écrire.

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

L'icône de lancement est un glyphe provisoire rendu par `.python/generate_provisional_icon.py` en attendant l'illustration du mainteneur ; vérifiez-la avec `py .python/generate_provisional_icon.py --check`.

******

### Licence

******

Le code du plugin est sous [Mozilla Public License 2.0](https://github.com/SuperMonster003/AutoJs6-Plugin-AutoJs4-Runtime/blob/master/LICENSE). Les sources AutoJs4 embarquées sont sous MPL 2.0 avec la clause non commerciale du dépôt AutoJs4 ; Rhino, OpenCV, les bibliothèques de terminal jackpal et les autres composants tiers conservent leurs propres licences. Voir les [avis tiers](https://github.com/SuperMonster003/AutoJs6-Plugin-AutoJs4-Runtime/blob/master/THIRD_PARTY_NOTICES.md).

******

### Liens

******

- Projet AutoJs6: https://github.com/SuperMonster003/AutoJs6
- Projet AutoJs4: https://github.com/SuperMonster003/AutoJs4
- Documentation AutoJs4: https://github.com/SuperMonster003/AutoJs4-Documentation
- Protocole V1 d'AutoJs4 Runtime: https://github.com/SuperMonster003/AutoJs6/blob/master/docs/dev/autojs4-runtime-protocol-v1.md
- Roadmap d'AutoJs4 Runtime: https://github.com/SuperMonster003/AutoJs6/blob/master/docs/dev/autojs4-runtime-plugin-roadmap.md
- Avis tiers: https://github.com/SuperMonster003/AutoJs6-Plugin-AutoJs4-Runtime/blob/master/THIRD_PARTY_NOTICES.md
