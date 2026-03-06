package messaggiServer;

import enums.ServerEventType;

public class TurnMessage implements ServerEvent {
	private ServerEventType id = ServerEventType.TURN;
	
	public TurnMessage() {
		
	}

	@Override
	public String getId() {
		// TODO Auto-generated method stub
		return null;
	}
	
	
}
