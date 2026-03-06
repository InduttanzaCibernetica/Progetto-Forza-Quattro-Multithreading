
package data;

public class PlayerQueue {

    private final PlayerSession[] buffer;
    private final int capacita;
    private int numeroElementi;
    private int head;
    private int tail;

    public PlayerQueue(int capacita) {
        this.capacita = capacita;
        this.buffer = new PlayerSession[capacita];
        this.numeroElementi = 0;
        this.head = 0;
        this.tail = 0;
    }

    public synchronized void enqueue(PlayerSession session) throws InterruptedException {
        while (numeroElementi == capacita) wait();
        buffer[tail] = session;
        tail = (tail + 1) % capacita;
        numeroElementi++;
        notifyAll();
    }

    public synchronized PlayerSession dequeue() throws InterruptedException {
        while (numeroElementi == 0) wait();
        PlayerSession session = buffer[head]; // fix: rimossi i __
        head = (head + 1) % capacita;
        numeroElementi--;
        notifyAll();
        return session;
    }

    public synchronized boolean isEmpty() {
        return numeroElementi == 0;
    }
}
