package messaggiServer;
 
import enums.ServerEventType;
 
public class DrawMessage implements ServerEvent {
	private ServerEventType id = ServerEventType.DRAW;
	
	public DrawMessage() {
		
	}
	
	public String getId() {
		return ServerEvent.enumToString(this.id);
	}
	
	
}
 