package messaggiServer;

import enums.ServerEventType;

public class WaitMessage implements ServerEvent {
	private ServerEventType id = ServerEventType.WAIT;
	
	public WaitMessage() {
		
	}

	@Override
	public String getId() {
		return ServerEvent.enumToString(id);
	}
	
	@Override
	public String toString() {
		return ServerEvent.enumToString(id);
	}
}
