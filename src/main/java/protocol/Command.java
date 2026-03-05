package protocol;

import enums.CommandType;

public class Command {
    private final CommandType type;       // tipo comando (CONNECT, MOVE, QUIT, NEWGAME)
    private final String[] parameters;   // parametri comando

    public Command(CommandType type, String[] parameters) {
        this.type = type;
        this.parameters = parameters;
    }

    public CommandType getType() {
        return type;
    }

    public String[] getParameters() {
        return parameters;
    }
}