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
	public void action() {
		System.out.println("Hai perso! " + this.reason);
		
	}
}
 