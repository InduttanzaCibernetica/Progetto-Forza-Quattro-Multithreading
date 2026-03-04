package messaggiServer;

import enums.ServerEventType;

public class ValidMessage implements ServerEvent {
	private ServerEventType id = ServerEventType.VALID;
	
	public ValidMessage() {
		
	}

	@Override
	public void action() {
		// TODO Auto-generated method stub
		
	}
}
