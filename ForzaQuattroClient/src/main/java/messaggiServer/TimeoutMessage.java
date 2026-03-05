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
	public void action() {
		//kevin è buono e finalmente mi ha detto cosa fa timeout
		System.out.println("Tempo scaduto per la selezione della mossa, ora verrai obliterato.");
	}
}
 