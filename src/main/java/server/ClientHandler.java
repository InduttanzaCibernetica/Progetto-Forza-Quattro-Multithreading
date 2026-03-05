package server;

import data.Player;
import data.PlayerSession;
import enums.Token;
import protocol.Command;
import protocol.MessageFormatter;
import protocol.MessageParser;
import java.io.*;
import java.net.Socket;

public class ClientHandler implements Runnable {
    private final Socket socket;           //connessione col client
    private final BufferedReader in;       //legge messaggi dal client
    private final PrintWriter out;	//invia messaggi al client
    private final GameManager gameManager; //riferimento al game manager
    private final MessageParser parser;	//parser dei messaggi in arrivo
    private final MessageFormatter formatter; //parser dei messaggi in uscita
    private PlayerSession session;	//sessione del giocatore
    private GameRoom room;	//stanza partita corrente

    public ClientHandler(Socket socket, GameManager gameManager) throws IOException {
        this.socket = socket;
        this.gameManager = gameManager;
        this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        this.out = new PrintWriter(socket.getOutputStream(), true); // flush automatico
        this.parser = new MessageParser();
        this.formatter = new MessageFormatter();
    }

    @Override
    public void run() {
        try {
            String raw;
            while ((raw = in.readLine()) != null) { // legge messaggi in arrivo finché il client è connesso
                handleMessage(raw);
            }
        } catch (IOException e) {
            System.out.println("Client disconnesso: " + socket.getInetAddress());
        } finally {
            onDisconnect(); // gestisce la disconnessione
        }
    }

    // smista il messaggio in base al tipo di comando
    public void handleMessage(String raw) {
        Command cmd = parser.parse(raw);
        if (cmd == null) {
            sendMessage(formatter.formatError("Comando non riconosciuto"));
            return;
        }
        switch (cmd.getType()) {
            case CONNECT  -> handleConnect(cmd);
            case MOVE     -> handleMove(cmd);
            case QUIT     -> handleQuit();
            case NEWGAME  -> handleNewGame();
        }
    }

    // gestisce la connessione iniziale del client
    private void handleConnect(Command cmd) {
        String name = cmd.getParameters()[0];       // es. "Alberto"
        int age = Integer.parseInt(cmd.getParameters()[1]); // es. 18
        Player player = new Player(0, name, age);   // id assegnato dal server in seguito
        session = new PlayerSession(player, this);
        sendMessage(formatter.formatWait());         // avvisa il client che è in attesa
        try {
            gameManager.assignPlayer(session);       // inserisce il giocatore nella coda
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    // gestisce una mossa del giocatore
    private void handleMove(Command cmd) {
        if (room == null) {
            sendMessage(formatter.formatError("Partita non iniziata"));
            return;
        }
        int col = Integer.parseInt(cmd.getParameters()[0]); // colonna scelta dal giocatore
        room.handleMove(session, col);                      // delega alla GameRoom
    }

    // gestisce l'abbandono della partita
    private void handleQuit() {
        if (room != null) room.onDisconnect(session); // la GameRoom assegna la vittoria all'avversario
        close();
    }

    // gestisce la richiesta di una nuova partita
    private void handleNewGame() {
        room = null; // resetta la stanza corrente
        sendMessage(formatter.formatWait());
        try {
            gameManager.assignPlayer(session); // reinserisce il giocatore in coda
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    // chiamato in caso di disconnessione improvvisa
    private void onDisconnect() {
        if (room != null) room.onDisconnect(session); // notifica la GameRoom
        close();
    }

    // invia un messaggio al client
    public void sendMessage(String msg) {
        out.println(msg);
    }

    // imposta la GameRoom quando la partita inizia
    public void setRoom(GameRoom room) {
        this.room = room;
    }

    // chiude la connessione
    private void close() {
        try {
            socket.close();
        } catch (IOException e) {
            System.out.println("Errore chiusura socket");
        }
    }
}