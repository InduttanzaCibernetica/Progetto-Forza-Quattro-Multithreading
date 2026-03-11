package messaggiServer;

import enums.ServerEventType;
import enums.Token;

public class StartMessage implements ServerEvent {
	private ServerEventType id = ServerEventType.START;
	private String token;
	private String enemyName;
	
	public StartMessage(Token token, String enemyName) {
		this.token = token.name();
		this.enemyName = enemyName;
	}

	
	@Override
	public String getId() {
		return ServerEvent.enumToString(id);
	}
	
	@Override
	public String toString() {
		return ServerEvent.enumToString(id) + ";" + this.token + ";" + this.enemyName;
	}

}
