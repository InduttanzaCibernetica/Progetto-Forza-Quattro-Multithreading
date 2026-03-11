package messaggiServer;

import enums.ServerEventType;

public class StartMessage implements ServerEvent {
	private ServerEventType id = ServerEventType.START;
	private String token;
	private String enemyName;
	//comunica il token iniziale al giocatore
	//il server già lo memorizza, quindi non c'è bisogno che lo memorizzi il client
	public StartMessage(String token, String enemyName) {
		this.token = token;
		this.enemyName = enemyName;
	}

	
	@Override
	public String getId() {
		return ServerEvent.enumToString(id);
	}
	
	@Override
	public void action() {
		System.out.println("Inizia la partita!");
		System.out.println("Il tuo token: " + this.token);
		System.out.println("Stai giocando contro: " + this.enemyName);
	}

}
