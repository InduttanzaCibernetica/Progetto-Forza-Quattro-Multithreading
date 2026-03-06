package messaggiServer;
 
import enums.ServerEventType;
 
public class ErrorMessage implements ServerEvent {
	private ServerEventType id = ServerEventType.ERROR;
	private String descrizione;
	
	public ErrorMessage(String descrizione) {
		this.descrizione = descrizione;
	}
 
	public String getId() {
		return ServerEvent.enumToString(this.id);
	}
	
	@Override
	public String toString() {
		return ServerEvent.enumToString(id) + this.descrizione;
	}
}
 