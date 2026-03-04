package messaggiServer;

import enums.ServerEventType;

public class LoseMessage implements ServerEvent {
	private ServerEventType id = ServerEventType.LOSE;
	
	public LoseMessage() {
		
	}

	@Override
	public void action() {
		// TODO Auto-generated method stub
		
	}
}
