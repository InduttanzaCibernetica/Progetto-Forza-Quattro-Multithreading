package forza4.client;
 
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;
import java.util.Set;
import java.io.PrintWriter;
 
import messaggiClient.*;
import messaggiServer.*;
 
public class Client {
	
	private String username;
	private int age;
	private boolean playing;
	
	private Scanner scanner;
	private ClientCommand currentmsg;
	private MessageParser parser;
	
	private Socket socket;
	private ServerListener listener;
	private PrintWriter out;
	
	public Client(String username, int age) {
		this.username = username;
		this.age = age;
		this.scanner = new Scanner(System.in);
		this.parser = new MessageParser();
	}
	
	//METODI GESTIONE PARTITA
	
	public void handleEvent(ServerEvent msg) {
		
		Set<String> DisconnectValues = Set.of("DISCONNECT", "DRAW", "ERROR", "LOSE", "TIMEOUT", "WIN"); //Set dei messaggi che fanno disconnettere il client
		
		if (DisconnectValues.contains(msg.getId())) {
			msg.action();
			this.disconnect();
		}
		else if (msg.getId().equals("TURN")){
			msg.action();
			String move;
			
			do {
				System.out.println("Inserisci la tua mossa... (una colonna da 1 a 7, o forfeit per abbandonare.)");
				move = scanner.nextLine();
			}while(!checkMove(move));
			
			if (move.equals("forfeit")) {
				currentmsg = new QuitMessage();
				sendCommand(MessageFormatter.format(currentmsg));
			} else {
				currentmsg = new MoveMessage(Integer.parseInt(move));
				sendCommand(MessageFormatter.format(currentmsg));
			}
			
		} else {
			msg.action();
		}
	}
	
	public boolean checkMove(String move) {
		
		if(move.equals("forfeit")) {
			return true;
		} else if (Integer.parseInt(move) >= 1 && Integer.parseInt(move) <= 7) {
			return true;
		}
		System.out.println("Mossa invalida.");
		return false;
	}
	
	//METODI GESTIONE COMUNICAZIONE
	public void sendCommand(String message) {
		out.println(message);
	}
	
	public void disconnect() {
		System.out.println("Disconnessione in corso...");
		listener.stop();
		
		try {
			if (this.socket != null) {
				socket.close();
			}
			
			if (this.out != null) {
				out.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.playing = false;
		System.out.println("Disconnessione effettuata.");
	}
	
	//METODI INIZIO PARTITA
	public void start(String serverAddress, int port) {
		
		try {
			this.socket = new Socket(serverAddress, port);
			this.listener = new ServerListener(socket);
			Thread tr = new Thread(listener);
			tr.start();
			this.out = new PrintWriter(socket.getOutputStream(), true); //true serve ad attivare l'auto flush, quindi i messaggi vengono inviati subito e tolti dal buffer
			play();
			
		} catch (IOException e) {
			e.printStackTrace();
			disconnect();
		}
		
	}
	
	public void play() { //è un mondo succoso
		this.playing = true;
		this.currentmsg = new ConnectMessage(username, age);
		sendCommand(MessageFormatter.format(currentmsg));
		String servermsg;
		
		while(playing) {
			try {
				//da rivedere la ricezione di messaggi
				servermsg = listener.getMessage();
				ServerEvent msg = parser.parse(servermsg);
				handleEvent(msg);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void main (String[] args) {
		String serverAddress = "127.0.0.1";
		int port = 5678;
		Client c = new Client("ciao", 99);
		c.start(serverAddress, port);
	}
}
 