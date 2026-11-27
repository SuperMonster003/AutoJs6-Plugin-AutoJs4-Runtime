******

### Historique des versions

******

# v1.1.2

###### 2026/11/27

* `Amélioration` Le résumé et les instructions du plugin indiquent désormais à quoi il sert: comparer les résultats avec AutoJs6 lors de la migration d'anciens scripts Auto.js 4.x; la plupart des utilisateurs, qui écrivent seulement de nouveaux scripts dans AutoJs6, n'ont pas besoin de l'installer

# v1.1.1

###### 2026/11/20

* `Amélioration` La page d'historique des versions se développe ou se réduit désormais par version: une version réduite affiche sa date et le nombre d'entrées par catégorie, une version développée ne nomme chaque catégorie qu'une fois; toutes les versions peuvent être développées ou réduites d'un coup, la plus récente s'ouvre développée et le sous-titre affiche le nom du plugin
* `Amélioration` Réécriture des instructions affichées dans le Centre des plugins en cinq parties: prise en main, fonctionnalités principales, utilisation dans les scripts, prérequis et questions fréquentes
* `Dépendance` Ajout de Jetpack Compose BOM 2026.09.00 (page Historique des versions)
* `Dépendance` Mise à niveau d'AndroidX Core vers 1.16.0 et d'AndroidX Lifecycle vers 2.9.4 (requis par Jetpack Compose; AppCompat reste à la version 1.0.2 d'AutoJs4)
* `Dépendance` Mise à niveau des plugins de compilation partagés version 1.9.0 -> 1.9.7

# v1.1.0

###### 2026/11/08

* `Correction` Icône de l'application difficile à distinguer sur les fonds sombres de certains lanceurs et listes d'applications système
* `Amélioration` Dans le menu de fichier d'AutoJs6 6.8.1 et versions ultérieures, "Exécuter avec AutoJs4" et "Exécuter avec AutoJs4 et comparer" sont désormais fournis par le plugin, dans le menu de premier niveau "AutoJs4" (inchangé dans les versions antérieures d'AutoJs6)
* `Amélioration` Les pages de paramètres et d'historique des versions suivent la langue, le mode sombre et la couleur du thème AutoJs6, avec repli sur l'apparence du système si les paramètres de l'hôte sont indisponibles

# v1.0.1

###### 2026/10/12

* `Amélioration` Lorsqu'un script a besoin du service d'accessibilité alors que le service du plugin n'est pas activé, un message toast explique pourquoi les paramètres d'accessibilité du système s'ouvrent

# v1.0.0

###### 2026/10/12

* `Conseil` armeabi-v7a utilise la bibliothèque native OpenCV 3.4.3 d'origine d'AutoJs4, les appareils 64 bits utilisent la même version recompilée avec le NDK 28.2 ; la build x86 n'a pas d'OpenCV, comme AutoJs4
* `Fonctionnalité` Exécution des fichiers `.js`, des enregistrements `.auto` et des dossiers de projet depuis le gestionnaire de fichiers d'AutoJs6 avec le moteur AutoJs4 4.4.0 embarqué (Rhino 1.7.7.2, OpenCV 3.4.3) via l'entrée de menu `Exécuter avec AutoJs4`
* `Fonctionnalité` Service d'exécution protégé avec le contrat AIDL versionné `autojs4-runtime-api` : requêtes par chemin de fichier, lignes de console diffusées, événements de démarrage et de fin, annulation et exécutions concurrentes
* `Fonctionnalité` Page de paramètres du plugin avec service d'accessibilité, superposition aux autres applications, accès au stockage, optimisation de la batterie et état root, plus l'interrupteur `Afficher la fenêtre de console AutoJs4`
* `Fonctionnalité` Livraison des APK `arm64-v8a`, `armeabi-v7a`, `x86_64`, `x86` et `universal`, activation Wake, métadonnées PluginInfo complètes, instructions du centre de plugins, README et changelog en 10 langues
* `Amélioration` Compilation des bibliothèques OpenCV 3.4.3 et de terminal jackpal pour arm64-v8a et x86_64 avec alignement sur des pages de 16 KB, et vérification à la compilation de l'alignement ELF et de la couverture complète des symboles JNI de la bibliothèque d'origine
* `Dépendance` Intégration d'AutoJs4 4.4.0 comme submodule Git au commit [`3fb08269198ebd49a880ab3ca2621383eaa10443`](https://github.com/SuperMonster003/AutoJs4/commit/3fb08269198ebd49a880ab3ca2621383eaa10443)
