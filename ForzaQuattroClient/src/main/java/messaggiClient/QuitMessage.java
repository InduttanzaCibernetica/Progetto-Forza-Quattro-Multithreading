package messaggiClient;
import enums.ClientCommandType;

public class QuitMessage implements ClientCommand {

	private ClientCommandType id = ClientCommandType.QUIT;
	
	public QuitMessage() {
		
	}
	
	public String getId() {
		return ClientCommand.enumToString(id);
	}
	
	@Override
	public String toString() {
		return this.getId();
	}
}