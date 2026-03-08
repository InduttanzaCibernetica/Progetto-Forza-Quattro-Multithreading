package forza4.server;

import messaggiServer.*;
import messaggiServer.ServerEvent;

public class MessageFormatter {
	
	public MessageFormatter() {
		
	}

	public String format(ServerEvent cmd) {
		String msg = cmd.toString();
		return msg;
	}
}