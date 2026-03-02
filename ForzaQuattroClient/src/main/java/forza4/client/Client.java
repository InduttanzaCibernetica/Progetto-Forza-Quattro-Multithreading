package forza4.client;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;
import java.io.PrintWriter;

import messaggiClient.*;

public class Client {
	
	private String username;
	private int age;
	
	private MessageFormatter formatter;
	
	private Socket socket;
	private ServerListener listener;
	private PrintWriter out;
	
	public Client(String username, int age) {
		this.username = username;
		this.age = age;
		this.formatter = new MessageFormatter();
	}
	
	public void sendCommand(String message) {
		out.println(message);
	}
	
	public void start(String serverAddress, int port) {
		
		try {
			this.socket = new Socket(serverAddress, port);
			this.listener = new ServerListener(socket);
			Thread tr = new Thread(listener);
			tr.start();
			this.out = new PrintWriter(socket.getOutputStream(), true); //true serve ad attivare l'auto flush, quindi i messaggi vengono inviati subito e tolti dal buffer
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		play();
	}
	
	public void play() {
		
	}
	
	public static void main (String[] args) {
		String serverAddress = "127.0.0.1";
		int port = 5678;
		Client c = new Client("ciao", 99);
		c.start(serverAddress, port);
	}
}
