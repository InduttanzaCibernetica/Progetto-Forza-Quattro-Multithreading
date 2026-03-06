//DA RIVEDERE
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

    private static final long TIMEOUT_MILLIS = 15_000; // 15 secondi per turno

    public GameRoom(PlayerSession player1, PlayerSession player2, GameQueue activeGames) {
        this.player1 = player1;
        this.player2 = player2;
        this.activeGames = activeGames;
    }

    public void start() {
        state = GameState.ACTIVE;
        currentTurn = player1.getPlayer().getToken(); // player1 ha sempre X e inizia

        // manda START a entrambi
        player1.getHandler().sendMessage(formatter.formatStart(player1.getPlayer().getToken()));
        player2.getHandler().sendMessage(formatter.formatStart(player1.getPlayer().getToken()));

        // manda la griglia vuota
        broadcast(formatter.formatBoard(board.toLinearState()));

        // notifica chi deve muovere
        getSessionByToken(currentTurn).getHandler().sendMessage(formatter.formatTurn());

        // avvia il timer
        timer.start(this, TIMEOUT_MILLIS);
    }

    public synchronized boolean handleMove(PlayerSession session, int col) {
        if (state != GameState.ACTIVE) return false;
        if (session.getPlayer().getToken() != currentTurn) {
            session.getHandler().sendMessage(formatter.formatError("Non è il tuo turno"));
            return false;
        }
        if (!board.isColumnAvailable(col)) {
            session.getHandler().sendMessage(formatter.formatError("Colonna non disponibile"));
            return false;
        }

        timer.cancel();
        board.applyMove(currentTurn, col);
        broadcast(formatter.formatBoard(board.toLinearState()));

        if (board.checkVictory(currentTurn)) {
            session.getHandler().sendMessage(formatter.formatWin("Hai allineato 4 dischi"));
            getOpponent(session).getHandler().sendMessage(formatter.formatLose("Hai perso"));
            endGame();
            return true;
        }
        if (board.isFull()) {
            broadcast(formatter.formatDraw());
            endGame();
            return true;
        }

        // passa il turno
        currentTurn = (currentTurn == Token.X) ? Token.O : Token.X;
        getSessionByToken(currentTurn).getHandler().sendMessage(formatter.formatTurn());
        timer.start(this, TIMEOUT_MILLIS);
        return true;
    }

    public void onTimeout() {
        if (state != GameState.ACTIVE) return;
        broadcast(formatter.formatTimeout());
        getOpponent(getSessionByToken(currentTurn)).getHandler().sendMessage(formatter.formatWin("Avversario in timeout"));
        getSessionByToken(currentTurn).getHandler().sendMessage(formatter.formatLose("Tempo scaduto"));
        endGame();
    }

    public void onDisconnect(PlayerSession session) {
        if (state != GameState.ACTIVE) return;
        getOpponent(session).getHandler().sendMessage(formatter.formatWin("Avversario disconnesso"));
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
