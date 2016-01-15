# TCPServer
### Licence profesionnelle Systèmes Informatiques et Logiciels
#### Programmation 2 | Programmation concurrente
##### Énoncé :
Développer un serveur TCP pour le protocole echo (https://tools.ietf.org/html/rfc862), capable de supporter plusieurs connexions simultanées. Si un client reste inactif pendant une durée supérieure à un paramàtre fixé, cette connexion est fermée par le serveur. Deux implémentations sont à fournir : l'une utilisant l'API de bas niveau, l'autre utilisant l'API de haut niveau.

Au démarrage, le serveur lira un fichier de configuration pour déterminer les valeurs des paramétres suivants :
- Implémentation à utiliser
- Nombre maximal de connexions simultanées
- Durée maximale d’inactivité d'une connexion
- Port d'écoute du serveur.

##### Consignes :

- Travail à rendre avant le ```15 Janvier 2016```
- Travail à réaliser en binome
- sujet du mail : ```[lp-prog2] tp echo```
- contenu du mail : Prénoms et noms des membres du binome
- pièce-jointe : Archive ```ZIP``` contenant les codes sources commentés et un script ant de construction de l’application.

#### Usage

- mvn clean package (Génère les jars dans le dossier target)
- java -jar server-jar-with-dependencies.jar (Démarre le serveur)
- java -jar client-jar-with-dependencies.jar (Démarre un client)
