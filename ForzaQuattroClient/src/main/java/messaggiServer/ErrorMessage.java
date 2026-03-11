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
	
	public String getDescrizione() {
		return this.descrizione;
	}
	@Override
	public void action() {
		System.out.println("Errore: " + this.descrizione);
		
	}
}
 