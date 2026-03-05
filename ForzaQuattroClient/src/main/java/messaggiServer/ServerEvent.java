package messaggiServer;
 
import enums.ServerEventType;
 
public interface ServerEvent {
 
	public String getId();
	
	public static String enumToString(ServerEventType name) {
		//questo è qui per semplificare la gestione dei messaggi nel client
		switch(name){
			case WELCOME:
				return "WELCOME";
			case WAIT:
				return "WAIT";
			case START:
				return "START";
			case TURN:
				return "TURN";
			case VALID:
				return "VALID";
			case ERROR:
				return "ERROR";
			case WIN:
				return "WIN";
			case LOSE:
				return "LOSE";
			case DRAW:
				return "DRAW";
			case TIMEOUT:
				return "TIMEOUT";
			case DISCONNECT:
				return "DISCONNECT";
			default:
				System.out.println("Errore: Messaggio non riconosciuto.");
				return null;
		}
		
		
	}
 
	public void action();//ogni messaggio farà override di questo metodo con quello che fanno all'interno
	
}