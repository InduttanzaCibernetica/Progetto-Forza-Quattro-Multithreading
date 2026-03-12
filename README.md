# Progetto Forza Quattro Sincronizzata
Applicazione del classico gioco Forza Quattro in java


## Requisiti
* **JDK 11+**
* **Maven 3.8 o superiore**


## Come iniziare:
Assicurati di aver clonato il progetto (da effettuare una volta)
 `git clone https://github.com/InduttanzaCibernetica/Progetto-Forza-Quattro-Multithreading.git`
altrimenti scarica lo zip e vai al percorso del progetto

## Esecuzione:
# Server
* Accedere alla cartella dove si trova il server `C:\ProgramFiles\Progetto-Forza-Quattro-Multithreading\ForzaQuattroServer`
* Eseguire il comando &rarr; `mvn -q exec:java -Dexec.mainClass=forza4.server.Server`

# Client
* Da terminale accedere alla cartella dove si trova il server &rarr; `C:\ProgramFiles\Progetto-Forza-Quattro-Multithreading\ForzaQuattroClient`
* Eseguire il comando &rarr; `mvn -q exec:java -Dexec.mainClass=forza4.client.Client`
* Inserisci l'IP del server per potersi connettere
