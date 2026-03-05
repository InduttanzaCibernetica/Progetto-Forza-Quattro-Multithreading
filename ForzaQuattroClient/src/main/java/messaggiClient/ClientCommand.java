package messaggiClient;

import enums.ClientCommandType;

public interface ClientCommand {

	public static String enumToString(ClientCommandType name) {
		
		switch(name){
		case CONNECT:
			return "CONNECT";
		case MOVE:
			return "MOVE";
		case QUIT:
			return "QUIT";
		default:
			System.out.println("Errore: Messaggio non riconosciuto.");
			return null;
		}
	}
}