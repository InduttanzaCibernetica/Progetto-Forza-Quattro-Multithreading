package forza4.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;


public class Server {
	private int port;
	private ServerSocket serverSocket;
	
	public Server(int port, ServerSocket serverSocket) {
		this.port = port;
		this.serverSocket = serverSocket;
	}
	
	public void start() {
		System.out.println("Server avviato. In attesa di connessioni...");
		System.out.println("Server in ascolto su "+serverSocket.getLocalSocketAddress());
		
		
	}
	
	public static void main(String[] args) {
		int port = 5678;
		
		try {
			ServerSocket socket = new ServerSocket(port);
			Server s = new Server(port, socket);
			s.start();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
