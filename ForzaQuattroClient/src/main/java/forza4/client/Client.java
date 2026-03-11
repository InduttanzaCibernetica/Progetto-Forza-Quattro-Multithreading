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
	private volatile boolean waitingMove = false;
	
	private final Set<String> DisconnectValues = Set.of("DISCONNECT", "DRAW", "LOSE", "TIMEOUT", "WIN"); //Set dei messaggi che fanno disconnettere il client
												//ho tolto ERRORE dopo draw  ^
	private Scanner scanner;					//							 |
	private ClientCommand currentmsg;
	private MessageParser parser;
	
	private Socket socket;
	private ServerListener listener;
	private Thread listenerThread;
	private PrintWriter out;
	
	
	private Thread moveThread;
	
	
	public Client(String username, int age) {
		this.username = username;
		this.age = age;
		this.scanner = new Scanner(System.in);
		this.parser = new MessageParser();
	}
	
	//METODI GESTIONE PARTITA
	
	public void handleEvent(ServerEvent msg) {
		
		msg.action();
		
		if (DisconnectValues.contains(msg.getId())) {
			disconnect();
			return;
		}
		else if (msg.getId().equals("TURN")){
			if (!waitingMove) {
				waitingMove = true;
				
				moveThread = new Thread(() -> {
				//new Thread(() -> {
					askMove();
				}); //.start(); // in teoria questo aggiusta i problemi di disconnessione
				moveThread.start();
			}
		}
		
		
		else if (msg.getId().equals("ERROR")) {
		    waitingMove = false;
		    waitingMove = true;
		    moveThread = new Thread(() -> {
		        askMove();
		    });
		    moveThread.start();
		}
	}
	
	public void askMove() {
		String move; //dichiarata qui perchè sennò non la trova il ciclo do while
		
		try {
			
			do {
				if (!playing) return;
				System.out.println("Inserisci la tua mossa... (una colonna da 1 a 7, o forfeit per abbandonare.)");
				move = scanner.nextLine();
			}while(!checkMove(move));
			
		} catch (Exception e) {
			return;
		}
		
		if (!playing) return;
		if (move.equals("forfeit")) {
			currentmsg = new QuitMessage();
			sendCommand(MessageFormatter.format(currentmsg));
		} else {
			currentmsg = new MoveMessage(Integer.parseInt(move));
			sendCommand(MessageFormatter.format(currentmsg));
		}
		
		waitingMove = false; //aggiunto per risolvere problema deadlock e resettare flag dopo aver inviato mossa
	}
	
	public boolean checkMove(String move) {
		
		if(move.equals("forfeit")) {
			return true;
		} else if (isNumeric(move)) {
			if (Integer.parseInt(move) >= 1 && Integer.parseInt(move) <= 7) {
				return true;
			}
		}
		System.out.println("Mossa invalida.");
		return false;
	}
	
	public boolean isNumeric(String move) {
		try {
			Integer.parseInt(move);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}
	
	//METODI GESTIONE COMUNICAZIONE
	public void sendCommand(String message) {
		out.println(message);
	}
	
	public void disconnect() {
		System.out.println("Disconnessione in corso...");
		this.playing = false;
		waitingMove = false;
		
		if (this.scanner != null) {
			scanner.close();
		}
		
		
		
		if (this.listener != null) {
		listenerThread.interrupt();
		listener.stop();
		}
		
		
		if (moveThread != null) { //a regola client si disconnette
			moveThread.interrupt();
		}
		
		
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
		System.out.println("Disconnessione effettuata.");
	}
	
	//METODI INIZIO PARTITA
	public void start(String serverAddress, int port) {
		
		try {
			this.socket = new Socket(serverAddress, port);
			this.listener = new ServerListener(socket);
			listenerThread = new Thread(listener);
			listenerThread.start();
			this.out = new PrintWriter(socket.getOutputStream(), true); //true serve ad attivare l'auto flush, quindi i messaggi vengono inviati subito e tolti dal buffer
			play();
			
		} catch (IOException e) {
			e.printStackTrace();
			disconnect();
		}
		
	}
	
	public void play() {
		this.playing = true;
		this.currentmsg = new ConnectMessage(username, age);
		sendCommand(MessageFormatter.format(currentmsg));
		String servermsg;
		
		while(playing) {
			try {
				System.out.println("Aspetto messaggio...");
				servermsg = listener.getMessage();
				System.out.println("Ricevuto: " + servermsg);
				
				ServerEvent msg = parser.parse(servermsg);
				handleEvent(msg);
			} catch (InterruptedException e) {
				//e.printStackTrace();
				break;
			}
		}
	}
	
	public static void main (String[] args) {
		Scanner s = new Scanner(System.in);
		String serverAddress = "127.0.0.1";
		int port = 5678;
		
		System.out.println("Inserisci il tuo nome: ");
		String name = s.nextLine();
		
		System.out.println("Inserisci la tua età: ");
		int age = s.nextInt();
		Client c = new Client(name, age);
		c.start(serverAddress, port);
		s.close();
	}
}
 