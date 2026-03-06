package forza4.server;
import messaggiClient.*;

public class MessageParser {

	public static ClientCommand parse(String msg) {
		String[] strarr = msg.split(";");
		ClientCommand cmd = null;
		
		switch(strarr[0]) {
			case "CONNECT":
				cmd = new ConnectMessage(strarr[1], Integer.parseInt(strarr[2]));
				break;
			case "MOVE":
				cmd = new MoveMessage(Integer.parseInt(strarr[1]));
				break;
			case "QUIT":
				cmd = new QuitMessage();
				break;
			default:
				System.out.println("Messaggio non riconosciuto");
		}
		return cmd;
	}
}
