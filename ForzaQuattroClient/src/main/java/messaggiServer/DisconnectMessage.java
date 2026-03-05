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
	public void action() {
		// TODO Auto-generated method stub
		System.out.println("Hai deciso di abbandonare la sfida, codardo!");
	}
}
 