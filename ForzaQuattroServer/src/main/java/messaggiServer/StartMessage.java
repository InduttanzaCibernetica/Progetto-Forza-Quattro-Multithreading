package messaggiServer;

import enums.ServerEventType;

public class StartMessage implements ServerEvent {
	private ServerEventType id = ServerEventType.START;
	private final int COLUMNS = 7;
	private String board;
	//PENSO che stampi la matrice vuota iniziale
	public StartMessage(String board) {
		this.board = board;
	}

	
	@Override
	public String getId() {
		return ServerEvent.enumToString(id);
	}
	
	@Override
	public String toString() {
		return ServerEvent.enumToString(id) + this.board;
	}

}
