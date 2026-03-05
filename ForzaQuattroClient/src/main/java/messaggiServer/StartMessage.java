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

	public void printBoard() {
		for(int i = 1; i<board.length(); i++) {
			System.out.print(board.charAt(i));
			
			if (i % COLUMNS == 0) {
				System.out.print('\n');
			}
		}
	}
	
	@Override
	public String getId() {
		return ServerEvent.enumToString(id);
	}
	
	@Override
	public void action() {
		System.out.println("Inizia la partita!");
		printBoard();		
	}

}
