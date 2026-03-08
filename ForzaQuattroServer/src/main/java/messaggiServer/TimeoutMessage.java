package messaggiServer;
 
import enums.ServerEventType;
 
public class TimeoutMessage implements ServerEvent{
	private ServerEventType id = ServerEventType.TIMEOUT;
	
	public TimeoutMessage() {
		
	}
 
	public String getId() {
		return ServerEvent.enumToString(this.id);
	}
	
	@Override
	public String toString() {
		return ServerEvent.enumToString(id);
	}
}
 