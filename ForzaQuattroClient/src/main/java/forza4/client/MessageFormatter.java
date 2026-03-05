package forza4.client;

import messaggiClient.*;
import messaggiClient.ClientCommand;

public class MessageFormatter {
	
	public MessageFormatter() {
		
	}

	public static String format(ClientCommand cmd) {
		String msg = cmd.toString();
		return msg;
	}
}