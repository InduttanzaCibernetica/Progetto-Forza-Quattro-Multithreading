package messaggiServer;
 
import enums.ServerEventType;
 
public class ValidMessage implements ServerEvent {
	private ServerEventType id = ServerEventType.VALID;
	private final int COLUMNS = 7;
	
	private String board;
	public ValidMessage(String board) {
		this.board = board;
	}
 
	public void printBoard() {
		for(int i = 0; i<board.length(); i++) {
			System.out.print(board.charAt(i));
			
			if ( (i+1) % COLUMNS == 0) {
				System.out.print('\n');
			}
		}
	}
	
	public String getId() {
		return ServerEvent.enumToString(this.id);
	}
	
	@Override
	public void action() {
		System.out.println("Tabella:");
		printBoard();
	}
	
}
 