
package forza4.server;

import data.GameQueue;
import data.PlayerQueue;
import data.PlayerSession;
import enums.Token;
import java.util.Random;

public class GameManager {

    private final PlayerQueue waitingQueue; // coda giocatori in attesa
    private final GameQueue activeGames = new GameQueue(); // partite attive

    public GameManager(int maxWaiting) {
        this.waitingQueue = new PlayerQueue(maxWaiting); // crea la coda con la capacità specificata
    }

    public synchronized void assignPlayer(PlayerSession session) throws InterruptedException {
        waitingQueue.enqueue(session);          // inserisce in coda
        if (waitingQueue.getSize() >= 2) {
            PlayerSession p1 = waitingQueue.dequeue(); // primo giocatore
            PlayerSession p2 = waitingQueue.dequeue(); // secondo giocatore
            createGame(p1, p2);
        }
    }

    private void createGame(PlayerSession p1, PlayerSession p2) {
        // decide chi inizia: il più giovane, in caso di parità casuale
        PlayerSession first = chooseFirst(p1, p2);
        PlayerSession second = (first == p1) ? p2 : p1;
        first.getPlayer().setToken(Token.X);   // chi inizia prende X
        second.getPlayer().setToken(Token.O);  // l'altro prende O
        GameRoom room = new GameRoom(first, second, activeGames);
        activeGames.addGame(room);
        p1.getHandler().setRoom(room);
        p2.getHandler().setRoom(room);
        room.start();                          // avvia la partita
    }

    private PlayerSession chooseFirst(PlayerSession p1, PlayerSession p2) {
        int a1 = p1.getPlayer().getAge();
        int a2 = p2.getPlayer().getAge();
        if (a1 < a2) return p1;               // vince il più giovane
        if (a2 < a1) return p2;
        return new Random().nextBoolean() ? p1 : p2; // parità: casuale
    }
}
