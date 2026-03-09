package messaggiServer;

import enums.ServerEventType;

public class TurnMessage implements ServerEvent {
	private ServerEventType id = ServerEventType.TURN;
	
	public TurnMessage() {
		
	}

	@Override
	public String getId() {
		// TODO Auto-generated method stub
		return ServerEvent.enumToString(id);
	}
	
	@Override
	public void action() {
		System.out.println("Hey, è il tuo turno!");
	}

}
