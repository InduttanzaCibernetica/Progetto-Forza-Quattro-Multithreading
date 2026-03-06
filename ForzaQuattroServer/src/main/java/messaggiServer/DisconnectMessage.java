package messaggiServer;
 
import enums.ServerEventType;
 
public class DisconnectMessage implements ServerEvent {
	private ServerEventType id = ServerEventType.DISCONNECT;
 
	public DisconnectMessage() {
		
	}
 
	public String getId() {
		return ServerEvent.enumToString(this.id);
	}
	
	@Override
	public String toString() {
		return ServerEvent.enumToString(id);
	}
}
 