package forza4.client;
 
import messaggiServer.*;
 
public class MessageParser {
	
	public MessageParser() {
		
	}
	
	public ServerEvent parse(String s) {
		String[] strarr = s.split(";");
		
		ServerEvent msg = null;
		
		switch(strarr[0]) {
		case "DISCONNECT":
			msg = new DisconnectMessage();
			break;
		case "DRAW":
			msg = new DrawMessage();
			break;
		case "ERROR":
			msg = new ErrorMessage(strarr[1]);
			break;
		case "LOSE":
			msg = new LoseMessage(strarr[1]);
			break;
		case "START":
			msg = new StartMessage(strarr[1]);
			break;
		case "TIMEOUT":
			msg = new TimeoutMessage();
			break;
		case "TURN":
			msg = new TurnMessage();
			break;
		case "VALID":
			msg = new ValidMessage(strarr[1]);
			break;
		case "WAIT":
			msg = new WaitMessage();
			break;
		case "WELCOME":
			msg = new WelcomeMessage(strarr[1]);
			break;
		case "WIN":
			msg = new WinMessage(strarr[1]);
			break;
		default:
			System.out.println("Errore: messaggio non riconosciuto.");
			break;
		}
		return msg;
	}
}
