package messaggiServer;

import enums.ServerEventType;

public class DrawMessage implements ServerEvent {
	private ServerEventType id = ServerEventType.DRAW;
	
	public DrawMessage() {
		
	}
	
	@Override
	public void action() {
		System.out.println("Pareggio! La partita è finita.");
		//da inserire probabilmente la disconnessione del client
	}
}
