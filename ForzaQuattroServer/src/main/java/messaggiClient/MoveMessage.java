package messaggiClient;
import enums.ClientCommandType;

public class MoveMessage implements ClientCommand {

	private ClientCommandType id = ClientCommandType.MOVE;
	private int column;
	
	public MoveMessage(int column) {
		this.column = column;
	}
	
	@Override
	public String getId() {
		return ClientCommand.enumToString(id);
	}
	
	public int getColumn() {
		return this.column;
	}
	
	@Override
	public String toString() {
		return this.getId() + ";" + this.column;
	}
}