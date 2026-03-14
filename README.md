# Progetto Forza Quattro Sincronizzata
Applicazione del classico gioco Forza Quattro in java


## Requisiti
* **JDK 11+**
* **Maven 3.8 o superiore**

## Installazione:
Clona la Repository: Usa il seguente comando per clonare la repository del progetto:
```
 git clone https://github.com/InduttanzaCibernetica/Progetto-Forza-Quattro-Multithreading.git
```

## Esecuzione:
# Server
* Da terminale accedere alla cartella dove si trova il server &rarr; `C:\Program Files\Progetto-Forza-Quattro-Multithreading\ForzaQuattroServer`
* Eseguire il comando:
```
mvn -q exec:java -Dexec.mainClass=forza4.server.Server
```

# Client
* Da terminale accedere alla cartella dove si trova il client &rarr; `C:\Program Files\Progetto-Forza-Quattro-Multithreading\ForzaQuattroClient`
* Eseguire il comando:
```mvn -q exec:java -Dexec.mainClass=forza4.client.Client
```
* Inserisci l'IP del server per potersi connettere
