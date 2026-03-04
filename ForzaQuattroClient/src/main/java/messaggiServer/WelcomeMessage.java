package messaggiServer;

import enums.ServerEventType;

public class WelcomeMessage implements ServerEvent {
	private ServerEventType id = ServerEventType.WELCOME;
	
	public WelcomeMessage() {
		
	}

	@Override
	public void action() {
		// TODO Auto-generated method stub
		
	}
}
