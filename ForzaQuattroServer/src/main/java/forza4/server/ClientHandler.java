//DA RIVEDERE
package forza4.server;

import data.Player;
import data.PlayerSession;
import messaggiClient.*;
import messaggiServer.*;
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
    private ServerEvent currentEvent;

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
        ClientCommand cmd;
        cmd = parser.parse(raw);
        if (cmd == null) {
        	currentEvent = new ErrorMessage("Comando non riconosciuto");
            sendMessage(formatter.format(currentEvent));
            return;
        }
        switch (cmd.getId()) {
            case "CONNECT": {
                handleConnect(cmd);
                break;
            }
            case "MOVE": {
                handleMove(cmd);
                break;
            }
            case "QUIT": {
                onDisconnect();
                break;
            }
        }
    }

    private void handleConnect(ClientCommand cmd) {
        String name = ((ConnectMessage)cmd).getName();
        int age = ((ConnectMessage)cmd).getAge();
        Player player = new Player(0, name, age);
        session = new PlayerSession(player, this);
        currentEvent = new WaitMessage();
        sendMessage(formatter.format(currentEvent));
        try {
            gameManager.assignPlayer(session);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private void handleMove(ClientCommand cmd) {
        if (room == null) {
        	currentEvent = new ErrorMessage("Partita non iniziata.");
            sendMessage(formatter.format(currentEvent));
            return;
        }
        int col = ((MoveMessage)cmd).getColumn();
        room.handleMove(session, col);
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
