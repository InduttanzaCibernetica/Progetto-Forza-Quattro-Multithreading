package messaggiServer;

import enums.ServerEventType;

public class WinMessage implements ServerEvent {
	private ServerEventType id = ServerEventType.WIN;
	private String reason;
	
	public WinMessage(String reason) {
		this.reason = reason;
	}
	
	@Override
	public String getId() {
		return ServerEvent.enumToString(id);
	}

	@Override
	public String toString() {
		return ServerEvent.enumToString(id) + this.reason;
	}
}
