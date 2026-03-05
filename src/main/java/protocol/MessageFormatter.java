package protocol;

import enums.Token;

public class MessageFormatter {
	public String formatWelcome(int playerId) {
		return "WELCOME;" + playerId;
	}
	public String formatWait() {
		return "WAIT";
	}
	public String formatStart(Token token) {
		return "START;" + token.name();	//es: START;X oppure START;O
	}
	public String formatBoard(String state) {
		return "VALID;" + state;	//es: VALID;.......X...
	}
	public String formatError(String msg) {
		return "ERROR;" + msg; //es: ERROR;Colonna è piena
	}
	public String formatWin(String reason) {
		return "WIN;" + reason;	//es: WIN;hai vinto
	}
	public String formatLose(String reason) {
		return "LOSE;" + reason;	//es: LOSE;avversario ha vinto
	}
	public String formatDraw() {
		return "DRAW";
	}
	public String formatTurn() {
		return "TURN";
	}
	public String formatTimeout() {
		return "TIMEOUT";
	}
	public String formatDisconnect() {
		return "DISCONNECT";
	}
}
	