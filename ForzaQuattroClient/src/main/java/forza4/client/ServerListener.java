package forza4.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class ServerListener implements Runnable {

	private boolean running;
	private BufferedReader reader;
	private Socket socket;
	private CodaCircolare coda;
	
	public ServerListener(Socket socket) {
		
		try {
			this.running = true;
			this.socket = socket;
			this.coda = new CodaCircolare(10);
			this.reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		} catch (IOException e) {
			e.printStackTrace();
			stop();
		}
		
	}
	
	public void stop() {
		running = false;
		try {
			if(this.reader != null) {
				this.reader.close();
			}
			if(this.socket != null) {
				socket.close();
			}
			synchronized(coda) {
				coda.notifyAll();
			}
			//coda.inserisci("DISCONNECT_INTERNAL");
		} catch (IOException e){
			e.printStackTrace();
		}
		coda.stop(); //questo per svegliare preleva() e la fa uscire
		
	}
	
	public String getMessage () throws InterruptedException {
		return coda.preleva();
	}

	@Override
	public void run() {
		while(running) {
			try {
				String message = reader.readLine();
				
				if(message == null) {
	                stop();
	                break;
	            }
				
				coda.inserisci(message);
			} catch(IOException | InterruptedException e) {
				if (running) {
					e.printStackTrace();
				}
				stop();
			}
		}
		System.out.println("Listener chiuso");
	}

}
