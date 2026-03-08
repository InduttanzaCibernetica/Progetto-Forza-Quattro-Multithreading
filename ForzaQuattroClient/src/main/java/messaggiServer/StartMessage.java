package messaggiServer;

import enums.ServerEventType;

public class StartMessage implements ServerEvent {
	private ServerEventType id = ServerEventType.START;
	private String token;
	
	//comunica il token iniziale al giocatore
	//il server già lo memorizza, quindi non c'è bisogno che lo memorizzi il client
	public StartMessage(String token) {
		this.token = token;
	}

	
	@Override
	public String getId() {
		return ServerEvent.enumToString(id);
	}
	
	@Override
	public void action() {
		System.out.println("Inizia la partita!");
		System.out.println("Il tuo token: " + this.token);
	}

}
