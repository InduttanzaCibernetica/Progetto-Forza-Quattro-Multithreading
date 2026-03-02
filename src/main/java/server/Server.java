package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
	private int port;
	private ServerSocket serverSocket;
	private GameManager gameManager;
	
	public Server(int port) {
		this.port=port;
		this.gameManager=new GameManager();
	}
	
	public void start() throws IOException {
		this.serverSocket=new ServerSocket(port);
		this.serverSocket.setReuseAddress(true);
		acceptClients();
	}
	
	public void acceptClients() throws IOException {
		while(!serverSocket.isClosed()) {
			Socket clientSocket = serverSocket.accept();
			ClientHandler handler = new ClientHandler(clientSocket, gameManager);
			handler.start();
		}
	}

}
