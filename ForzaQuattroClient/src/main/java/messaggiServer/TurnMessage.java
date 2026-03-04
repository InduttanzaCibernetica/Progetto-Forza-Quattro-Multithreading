package messaggiServer;

import enums.ServerEventType;

public class TurnMessage implements ServerEvent {
	private ServerEventType id = ServerEventType.TURN;
	
	public TurnMessage() {
		
	}

	@Override
	public void action() {
		// TODO Auto-generated method stub
		
	}
}
