package forza4.server;

import data.Board;
import data.GameQueue;
import data.PlayerSession;
import messaggiServer.*;
import enums.GameState;
import enums.Token;

public class GameRoom {

    private final PlayerSession player1;
    private final PlayerSession player2;
    private final Board board = new Board();
    private final GameQueue activeGames;
    private final MessageFormatter formatter = new MessageFormatter();
    private final TurnTimer timer = new TurnTimer();

    private Token currentTurn;
    private GameState state = GameState.WAITING;
    private ServerEvent currentEvent;

    private static final long TIMEOUT_MILLIS = 60_000; // 60 secondi per turno

    public GameRoom(PlayerSession player1, PlayerSession player2, GameQueue activeGames) {
        this.player1 = player1;
        this.player2 = player2;
        this.activeGames = activeGames;
    }

    public void start() {
        state = GameState.ACTIVE;
        currentTurn = player1.getPlayer().getToken(); // player1 ha sempre X e inizia
        
        // manda START a entrambi
        currentEvent = new StartMessage(player1.getPlayer().getToken(), player2.getPlayer().getName());
        player1.getHandler().sendMessage(formatter.format(currentEvent));
        
        currentEvent = new StartMessage(player2.getPlayer().getToken(), player1.getPlayer().getName());
        player2.getHandler().sendMessage(formatter.format(currentEvent));

        // manda la griglia vuota
        currentEvent = new ValidMessage(board.toLinearState());
        broadcast(formatter.format(currentEvent));

        // notifica chi deve muovere
        currentEvent = new TurnMessage();
        getSessionByToken(currentTurn).getHandler().sendMessage(formatter.format(currentEvent));

        // avvia il timer
        timer.start(this, TIMEOUT_MILLIS);
    }

    public synchronized boolean handleMove(PlayerSession session, int col) { 
    	
        if (state != GameState.ACTIVE) return false;
        if (session.getPlayer().getToken() != currentTurn) {
        	currentEvent = new ErrorMessage("Non è il tuo turno");
            session.getHandler().sendMessage(formatter.format(currentEvent));
            return false;
        }
        if (!board.isColumnAvailable(col)) {
        	currentEvent = new ErrorMessage("Colonna non disponibile");
            session.getHandler().sendMessage(formatter.format(currentEvent));
            return false;
        }

        timer.cancel();
        board.applyMove(currentTurn, col);
        currentEvent = new ValidMessage(board.toLinearState());
        broadcast(formatter.format(currentEvent));

        if (board.checkVictory(currentTurn)) {
        	currentEvent = new WinMessage("Hai allineato 4 dischi");
            session.getHandler().sendMessage(formatter.format(currentEvent));
            currentEvent = new LoseMessage("Il tuo avversario ha allineato 4 dischi");
            getOpponent(session).getHandler().sendMessage(formatter.format(currentEvent));
            endGame();
            return true;
        }
        if (board.isFull()) {
        	currentEvent = new DrawMessage();
            broadcast(formatter.format(currentEvent));
            endGame();
            return true;
        }

        // passa il turno
        currentTurn = (currentTurn == Token.X) ? Token.O : Token.X;
        currentEvent = new TurnMessage();
        getSessionByToken(currentTurn).getHandler().sendMessage(formatter.format(currentEvent));
        timer.start(this, TIMEOUT_MILLIS);
        return true;
    }

    public synchronized void onTimeout() {
        if (state != GameState.ACTIVE) return;
        	currentEvent = new TimeoutMessage();
        	
        	getSessionByToken(currentTurn).getHandler().sendMessage(formatter.format(currentEvent));

        	
	        //broadcast(formatter.format(currentEvent));
	        
	        currentEvent = new WinMessage("Avversario in timeout");
	        getOpponent(getSessionByToken(currentTurn)).getHandler().sendMessage(formatter.format(currentEvent));
	        //getSessionByToken(currentTurn).getHandler().sendMessage(formatter.formatLose("Tempo scaduto"));
	        endGame();
    }

    public synchronized void onDisconnect(PlayerSession session) {
        if (state != GameState.ACTIVE) return;
        currentEvent = new DisconnectMessage();
        getSessionByToken(currentTurn).getHandler().sendMessage(formatter.format(currentEvent));
        
        currentEvent = new WinMessage("Avversario disconnesso");
        getOpponent(session).getHandler().sendMessage(formatter.format(currentEvent));
        endGame();
    }

    private void endGame() {
        state = GameState.FINISHED;
        timer.cancel();
        activeGames.removeGame(this);
    }

    private void broadcast(String msg) {
        player1.getHandler().sendMessage(msg);
        player2.getHandler().sendMessage(msg);
    }

    private PlayerSession getSessionByToken(Token token) {
        return player1.getPlayer().getToken() == token ? player1 : player2;
    }

    private PlayerSession getOpponent(PlayerSession session) {
        return session == player1 ? player2 : player1;
    }
}
