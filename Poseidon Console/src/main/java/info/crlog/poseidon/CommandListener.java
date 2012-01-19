package info.crlog.poseidon;

/**
 *
 * @author Courtney Robinson <courtney@crlog.info>
 */
public interface CommandListener {
    /**
     * Gives the command processor a reference to the terminal
     * @param console
     */
    public void setConsole(PoseidonConsole console);

    /**
     * Determines whether the command is propagated to any other listeners. All
     * listeners prior to this would have already gotten this command only
     * subsequent listeners will not receive this
     *
     * @return true if this command listener wants to consume this command and
     * stop others getting it false otherwise
     */
    public boolean isConsumed();

    /**
     * Fired when a new command is entered on the console
     *
     * @param command The command that was entered
     */
    public void onCommand(PoseidonCommand command);
}
