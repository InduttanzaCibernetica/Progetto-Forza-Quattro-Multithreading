package protocol;

import enums.CommandType;

public class MessageParser { // trasformazione da stringa raw a un command

    public Command parse(String raw) {
        try {
            String[] parts = raw.trim().split(";"); // rimuove spazi ai lati e divide la stringa in parti usando ";" <--(separatore)
            CommandType type = CommandType.valueOf(parts[0]); // es: "CONNECT" --> CommandType.CONNECT
            String[] parameters = new String[parts.length - 1];
            for (int i = 0; i < parameters.length; i++) { // scorre i parametri
                parameters[i] = parts[i + 1];
            }
            return new Command(type, parameters); // restituisce il command da parts[1]
        } catch (Exception e) {
            return null; // comando non riconosciuto
        }
    }
}