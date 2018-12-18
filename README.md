
Projet  IFI M2 eServices : Pokemon
---


**Cahier de charges :**  
Réaliser une application JEE de Pokemon en suivant une architecture micro-service

**Structure du projet :**  

Le projet est structuré en 3 micro-services qui communiquent entre eux. *pokemon-service* permet de récupérer des données relatives aux pokemon disponibles sur une api externe, *trainer-service* permet de récupérer des données relatives aux dresseurs de pokemon. Ces données sont hard-codées dans la fonction principale du projet (04-jpa). *fight-service*, permet de simuler des combats entre dresseurs.

L'application possède une interface graphique (GUI). Mais les notifications de combat sont affichées sur la console.

**Lancement du projet :**

Il faut exécuter à la fois les projets mvn *06-async*, *04-jpa*, *fight-service* et *05-gui* afin d'obtenir l'application complète.

Le site est accessible à l'url : http://localhost:9000/
