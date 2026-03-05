package data;

import server.ClientHandler;

public class PlayerSession {
	private final Player player;
	private final ClientHandler handler;
	
	public PlayerSession(Player player, ClientHandler handler) {
		this.player=player;
		this.handler=handler;
	}
	
	public Player getPlayer() {
		return player;
	}
	public ClientHandler getHandler() {
		return handler;
	}
}
