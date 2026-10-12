******

### Historique des versions

******

# v1.0.0

###### 2026/10/12

* `Conseil` La première version reproduit exactement AutoJs4, y compris ses bibliothèques natives uniquement 32 bits ; le plugin ne peut donc pas être installé sur les appareils uniquement 64 bits
* `Fonctionnalité` Exécution des fichiers `.js`, des enregistrements `.auto` et des dossiers de projet depuis le gestionnaire de fichiers d'AutoJs6 avec le moteur AutoJs4 4.4.0 embarqué (Rhino 1.7.7.2, OpenCV 3.4.3) via l'entrée de menu `Exécuter avec AutoJs4`
* `Fonctionnalité` Service d'exécution protégé avec le contrat AIDL versionné `autojs4-runtime-api` : requêtes par chemin de fichier, lignes de console diffusées, événements de démarrage et de fin, annulation et exécutions concurrentes
* `Fonctionnalité` Page de paramètres du plugin avec service d'accessibilité, superposition aux autres applications, accès au stockage, optimisation de la batterie et état root, plus l'interrupteur `Afficher la fenêtre de console AutoJs4`
* `Fonctionnalité` Livraison des APK `armeabi-v7a`, `x86` et `universal`, activation Wake, métadonnées PluginInfo complètes, instructions du centre de plugins, README et changelog en 10 langues
* `Dépendance` Intégration d'AutoJs4 4.4.0 comme submodule Git au commit [`3fb08269198ebd49a880ab3ca2621383eaa10443`](https://github.com/SuperMonster003/AutoJs4/commit/3fb08269198ebd49a880ab3ca2621383eaa10443)
