package info.crlog.poseidon.terminal;

/**
 * Set of internal commands implemented
 *
 * @author Courtney Robinson <courtney@crlog.info>
 */
public enum EmulatorCommand {

    EXIT,
    CD,
    CLEAR,
    NOT_SUPPORTED;

    /**
     * Gets the Enum type from a string, case insensitive.
     *
     * @param command the command to get
     * @return an enum value of the string or the enum
     * <code>EmulatorCommand.NOT_SUPPORTED</code>
     */
    public static EmulatorCommand get(String command) {
        for (EmulatorCommand c : values()) {
            if (command.equalsIgnoreCase(c.toString())) {
                return c;
            }
        }
        return NOT_SUPPORTED;
    }
}
