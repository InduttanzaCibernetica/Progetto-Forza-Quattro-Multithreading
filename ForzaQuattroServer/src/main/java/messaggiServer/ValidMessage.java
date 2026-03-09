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
		for(int i = 1; i<board.length(); i++) {
			System.out.print(board.charAt(i));
			
			if (i % COLUMNS == 0) {
				System.out.print('\n');
			}
		}
	}
	
	public String getId() {
		return ServerEvent.enumToString(this.id);
	}
	
	
	@Override
	public String toString() {
		return ServerEvent.enumToString(id) + ";" + this.board;
	}
}
 