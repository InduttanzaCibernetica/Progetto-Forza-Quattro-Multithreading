package messaggiServer;

import enums.ServerEventType;

public class ErrorMessage implements ServerEvent {
	private ServerEventType id = ServerEventType.ERROR;
	
	public ErrorMessage() {
		
	}

	@Override
	public void action() {
		// TODO Auto-generated method stub
		
	}
}
