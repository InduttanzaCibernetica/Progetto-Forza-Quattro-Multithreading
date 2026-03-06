package server;

import data.Player;
import data.PlayerSession;
import protocol.Command;
import protocol.MessageFormatter;
import protocol.MessageParser;
import java.io.*;
import java.net.Socket;

public class ClientHandler implements Runnable {

    private final Socket socket;
    private final BufferedReader in;
    private final PrintWriter out;
    private final GameManager gameManager;
    private final MessageParser parser;
    private final MessageFormatter formatter;
    private PlayerSession session;
    private GameRoom room;

    public ClientHandler(Socket socket, GameManager gameManager) throws IOException {
        this.socket = socket;
        this.gameManager = gameManager;
        this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        this.out = new PrintWriter(socket.getOutputStream(), true);
        this.parser = new MessageParser();
        this.formatter = new MessageFormatter();
    }

    @Override
    public void run() {
        try {
            String raw;
            while ((raw = in.readLine()) != null) {
                handleMessage(raw);
            }
        } catch (IOException e) {
            System.out.println("Client disconnesso: " + socket.getInetAddress());
        } finally {
            onDisconnect();
        }
    }

    public void handleMessage(String raw) {
        Command cmd = parser.parse(raw);
        if (cmd == null) {
            sendMessage(formatter.formatError("Comando non riconosciuto"));
            return;
        }
        switch (cmd.getType()) {
            case CONNECT: {
                handleConnect(cmd);
                break;
            }
            case MOVE: {
                handleMove(cmd);
                break;
            }
            case QUIT: {
                handleQuit();
                break;
            }
            case NEWGAME: {
                handleNewGame();
                break;
            }
        }
    }

    private void handleConnect(Command cmd) {
        String name = cmd.getParameters()[0];
        int age = Integer.parseInt(cmd.getParameters()[1]);
        Player player = new Player(0, name, age);
        session = new PlayerSession(player, this);
        sendMessage(formatter.formatWait());
        try {
            gameManager.assignPlayer(session);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private void handleMove(Command cmd) {
        if (room == null) {
            sendMessage(formatter.formatError("Partita non iniziata"));
            return;
        }
        int col = Integer.parseInt(cmd.getParameters()[0]);
        room.handleMove(session, col);
    }

    private void handleQuit() {
        if (room != null) room.onDisconnect(session);
        close();
    }

    private void handleNewGame() {
        room = null;
        sendMessage(formatter.formatWait());
        try {
            gameManager.assignPlayer(session);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private void onDisconnect() {
        if (room != null) room.onDisconnect(session);
        close();
    }

    public void sendMessage(String msg) {
        out.println(msg);
    }

    public void setRoom(GameRoom room) {
        this.room = room;
    }

    private void close() {
        try {
            socket.close();
        } catch (IOException e) {
            System.out.println("Errore chiusura socket");
        }
    }
}