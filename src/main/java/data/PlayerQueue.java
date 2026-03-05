package data;

public class PlayerQueue {

    private final PlayerSession[] buffer;
    private final int capacita;           //capacità massima della coda
    private int numeroElementi;           //numero di giocatori attualmente in attesa
    private int head;                     //testa
    private int tail;                     //coda

    public PlayerQueue(int capacita) {
        this.capacita = capacita;
        this.buffer = new PlayerSession[capacita];
        this.numeroElementi = 0;
        this.head = 0;
        this.tail = 0;
    }

    public synchronized void enqueue(PlayerSession session) throws InterruptedException {
        while (numeroElementi == capacita) wait(); //aspetta se la coda è piena
        buffer[tail] = session;
        tail = (tail + 1) % capacita; //avanza tail circolarmente
        numeroElementi++;
        notifyAll();
    }

    public synchronized PlayerSession dequeue() throws InterruptedException {
        while (numeroElementi == 0) wait(); // aspetta se la coda è vuota
        PlayerSession session = buffer[head];
        head = (head + 1) % capacita; // avanza head circolarmente
        numeroElementi--;
        notifyAll();
        return session;
    }

    public synchronized boolean isEmpty() {
        return numeroElementi == 0; // true se non ci sono giocatori in attesa
    }
}



