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
			msg = new ErrorMessage();
			break;
		case "LOSE":
			msg = new LoseMessage();
			break;
		case "START":
			msg = new StartMessage();
			break;
		case "TIMEOUT":
			msg = new TimeoutMessage();
			break;
		case "TURN":
			msg = new TurnMessage();
			break;
		case "VALID":
			msg = new ValidMessage();
			break;
		case "WAIT":
			msg = new WaitMessage();
			break;
		case "WELCOME":
			msg = new WelcomeMessage();
			break;
		case "WIN":
			msg = new WinMessage();
			break;
		default:
			System.out.println("Errore: messaggio non riconosciuto.");
			break;
		}
		return msg;
	}
}
