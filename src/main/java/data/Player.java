package data;

import enums.Token;

public class Player {
	private final int id;
	private final String name;
	private final int age;
	private Token token; // X, O
	
	public Player(int id, String name, int age) {
		this.id=id;
		this.name=name;
		this.age=age;
		this.token=null; //all'inizio è vuoto deh
	}
	public int getId() {
		return id;
	}
	public String getName() {
		return name;
	}
	public int getAge() {
		return age;
	}
	public Token getToken() {
		return token;
	}
	public void setToken(Token token) { //set solo per token, il server lo crea quando si crea la partita
		this.token=token;
	}
}
