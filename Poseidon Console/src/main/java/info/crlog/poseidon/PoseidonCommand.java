package info.crlog.poseidon;

/**
 *
 * @author Courtney Robinson <courtney@crlog.info>
 */
public class PoseidonCommand {

    private String command;

    /**
     * Creates a new command from the given string
     *
     * @param command
     */
    public PoseidonCommand(String command) {
        this.command = command;
    }
    /**
     * Checks if this is a valid command or not. Mainly if its just an empty string
     * @return true if valid false otherwise
     */
    public boolean isValid() {
        return !command.isEmpty();
    }

    @Override
    public String toString() {
        return command;
    }
}
