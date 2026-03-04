package messaggiServer;

import enums.ServerEventType;

public class TimeoutMessage implements ServerEvent{
	private ServerEventType id = ServerEventType.TIMEOUT;
	
	public TimeoutMessage() {
		
	}

	@Override
	public void action() {
		//kevin è cattivo e non mi vuole dire cosa fanno i messaggi, quindi credo faccia questo
		System.out.println("Tempo scaduto per la selezione della mossa, ora verrai obliterato.");
		//da inserire SICURAMENTE la disconnessione del client
	}
}
