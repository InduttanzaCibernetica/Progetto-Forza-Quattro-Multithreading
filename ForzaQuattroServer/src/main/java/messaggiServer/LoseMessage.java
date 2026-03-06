package messaggiServer;
 
import enums.ServerEventType;
 
public class LoseMessage implements ServerEvent {
	private ServerEventType id = ServerEventType.LOSE;
	private String reason;
	
	public LoseMessage(String reason) {
		this.reason = reason;
	}
	
	public String getId() {
		return ServerEvent.enumToString(this.id);
	}
 
	@Override
	public String toString() {
		return ServerEvent.enumToString(id) + this.reason;
	}
}
 