package messaggiServer;

import enums.ServerEventType;

public class StartMessage implements ServerEvent {
	private ServerEventType id = ServerEventType.START;
	
	public StartMessage() {
		
	}

	@Override
	public void action() {
		// TODO Auto-generated method stub
		
	}
}
