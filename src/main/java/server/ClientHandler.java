package server;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.net.Socket;
import protocol.MessageParser;
import protocol.MessageFormatter;
import data.PlayerSession;
import enums.Token;


public class ClientHandler extends Thread {
	private Socket socket;
	private BufferedReader in;
	private PrintWriter out;
	private String nickname;
	private Token token;
	private PlayerSession session;
	private GameRoom room;
	private MessageParser parser;
	private MessageFormatter formatter;
	
	public ClientHandler(Socket socket, GameManager gameManager) {
		super("ClientHandler");
		this.socket=socket;
		
	}
	
}
