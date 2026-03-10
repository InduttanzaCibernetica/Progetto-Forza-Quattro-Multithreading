package forza4.client;

public class CodaCircolare {
	
	private final String[] buffer;
	private int head = 0;
	private int tail = 0;
	private int count = 0;
	
	public CodaCircolare(int size) {
	    buffer = new String[size];
	}
	
	
	public synchronized void inserisci(String messaggio) throws InterruptedException {
	    while (count == buffer.length) { //guardia
	    	if (Thread.currentThread().isInterrupted()) {
	            throw new InterruptedException();
	        }
	        wait();
	    }
	    buffer[tail] = messaggio;
	    tail = (tail + 1) % buffer.length;
	    count++;
	    notifyAll();
	}
	
	
	public synchronized String preleva() throws InterruptedException {
	    while (count == 0) { //guardia
	    	if (Thread.currentThread().isInterrupted()) {
	            throw new InterruptedException();
	        }
	        wait();
	    }
	    String messaggio = buffer[head];
	    head = (head + 1) % buffer.length;
	    count--;
	    notifyAll();
	    return messaggio;
	}
}