package messaggiServer;

import enums.ServerEventType;
import enums.Token;

public class StartMessage implements ServerEvent {
	private ServerEventType id = ServerEventType.START;
	private String token;

	public StartMessage(Token token) {
		this.token = token.name();
	}

	
	@Override
	public String getId() {
		return ServerEvent.enumToString(id);
	}
	
	@Override
	public String toString() {
		return ServerEvent.enumToString(id) + this.board;
	}

}
