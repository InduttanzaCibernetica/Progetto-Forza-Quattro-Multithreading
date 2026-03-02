package messaggiClient;
import enums.ClientCommandType;

public class ConnectMessage implements ClientCommand {
	
	private ClientCommandType id = ClientCommandType.CONNECT;
	private String name;
	private int age;
	
	public ConnectMessage(String name, int age) {
		this.name = name;
		this.age = age;
	}
	
	public String getId() {
		return ClientCommand.enumToString(id);
	}
	
	public String getName() {
		return this.name;
	}
	
	public int getAge() {
		return this.age;
	}
	
	@Override
	public String toString() {
		return this.getId() + ";" + this.name + ";" + this.age;
	}
}