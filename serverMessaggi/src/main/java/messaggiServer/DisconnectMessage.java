package messaggiServer;

import enums.ServerEventType;

public class DisconnectMessage implements ServerEvent {
	private ServerEventType id = ServerEventType.DISCONNECT;
}
