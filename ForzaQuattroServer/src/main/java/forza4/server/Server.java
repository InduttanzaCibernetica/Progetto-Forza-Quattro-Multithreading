
package forza4.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    private int port;
    private ServerSocket serverSocket;
    private GameManager gameManager = new GameManager(100);

    public Server(int port, ServerSocket serverSocket) {
        this.port = port;
        this.serverSocket = serverSocket;
    }

    public void start() {
        System.out.println("Server avviato. In attesa di connessioni...");
        System.out.println("Server in ascolto su " + serverSocket.getLocalSocketAddress());

        while (true) {
            try {
                Socket socket = serverSocket.accept();
                System.out.println("Client connesso");
                ClientHandler handler = new ClientHandler(socket, gameManager);
                new Thread(handler, "ClientHandler").start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        int port = 5678;
        try {
            ServerSocket socket = new ServerSocket(port);
            Server s = new Server(port, socket);
            s.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
