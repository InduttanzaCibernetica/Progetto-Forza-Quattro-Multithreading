package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import data.Player;
import data.PlayerSession;
import enums.Token;
import protocol.Command;
import protocol.MessageFormatter;
import protocol.MessageParser;

public class ClientHandler extends Thread {

    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;

    private String nickname;
    private Token token;
    private PlayerSession session;
    private GameRoom room;

    private final MessageParser parser = new MessageParser();
    private final MessageFormatter formatter = new MessageFormatter();

    // Campo mancante nello skeleton originale
    private GameManager gameManager;

    public ClientHandler(Socket socket, GameManager gameManager) {
        super("ClientHandler");
        this.socket = socket;
        this.gameManager = gameManager;
    }

    @Override
    public void run() {
        try (Socket s = this.socket;
             BufferedReader bin = new BufferedReader(new InputStreamReader(s.getInputStream()));
             PrintWriter pout = new PrintWriter(s.getOutputStream(), true)) {

            // collega gli stream ai campi, come da design
            this.in = bin;
            this.out = pout;

            // ciclo principale: leggi → parse → delega al GameManager
            String raw;
            while ((raw = in.readLine()) != null) {
                Command cmd = parser.parse(raw);

                // Assunzione (coerente con architettura): il GameManager gestisce il comando
                // e può:
                //  - restituire una risposta testuale (sincrona) da inviare
                //  - inviare lui stesso messaggi asincroni usando out.println(...) if needed
                String response = gameManager.handleCommand(this, cmd);

                if (response != null && !response.isEmpty()) {
                    out.println(response);
                }
            }

        } catch (IOException e) {
            System.out.println("[ClientHandler] IO error: " + e.getMessage());
        } finally {
            // Notifica al GameManager la disconnessione del client (previsto dal design server)
            try {
                gameManager.onClientDisconnected(this);
            } catch (Exception ignored) {}
        }
    }
}