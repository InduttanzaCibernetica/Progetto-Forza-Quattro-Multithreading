package messaggiServer;
 
import enums.ServerEventType;
 
public class DrawMessage implements ServerEvent {
	private ServerEventType id = ServerEventType.DRAW;
	
	public DrawMessage() {
		
	}
	
	public String getId() {
		return ServerEvent.enumToString(this.id);
	}
	
	@Override
	public String toString() {
		return ServerEvent.enumToString(id);
	}
}
 