package data;

import server.GameRoom;
import java.util.HashSet;
import java.util.Set; //serve ad non avere duplicati, quindi stanze duplicate
					// la stessa gameroom non può essere registrata più volte

public class GameQueue { // gestione dei gameroom attivi
	private final HashSet <GameRoom> games=new HashSet<GameRoom>();
	public synchronized void addGame(GameRoom game) {	//synchronized per garantire una sicurezza quando più thread
		if(game!=null) {
			games.add(game);	//aggiunge la stanza se non nulla
		}
	}
	
	public synchronized void removeGame(GameRoom game) {
		if(game!=null) {
			games.remove(game);	//stessa cosa di addGame ma rimuove
		}
	}
}
