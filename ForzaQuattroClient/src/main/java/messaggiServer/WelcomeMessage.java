package messaggiServer;

import enums.ServerEventType;

public class WelcomeMessage implements ServerEvent {
	private ServerEventType id = ServerEventType.WELCOME;
	private String PlayerId;
	
	public WelcomeMessage(String PlayerId) {
		this.PlayerId = PlayerId;
	}

	public String getId() {
		// TODO Auto-generated method stub
		return ServerEvent.enumToString(id);
	}
	
	@Override
	public void action() {
		System.out.println("Benvenuto! il tuo id è: " + this.PlayerId);
		
	}

}
