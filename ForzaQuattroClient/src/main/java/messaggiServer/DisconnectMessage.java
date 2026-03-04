package messaggiServer;

import enums.ServerEventType;

public class DisconnectMessage implements ServerEvent {
	private ServerEventType id = ServerEventType.DISCONNECT;

	public DisconnectMessage() {
		
	}

	@Override
	public void action() {
		// TODO Auto-generated method stub
		
	}
}
