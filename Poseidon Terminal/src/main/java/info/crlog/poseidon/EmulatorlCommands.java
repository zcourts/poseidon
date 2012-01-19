package info.crlog.poseidon;

/**
 *
 * @author Courtney Robinson <courtney@crlog.info>
 */
public class EmulatorlCommands implements CommandListener {

    private boolean consumed = false;
    private PoseidonConsole console;

    public boolean isConsumed() {
        if (consumed) {
            consumed = false;
            return true;
        } else {
            return consumed;
        }
    }

    public void onCommand(PoseidonCommand command) {
        if (command.toString().equalsIgnoreCase("clear") && console != null) {
            console.clearConsole();
        }
        if (command.toString().equalsIgnoreCase("clear -prefix") && console != null) {
            console.setUsingPrefix(false);
            console.clearConsole();
        }
        if (command.toString().equalsIgnoreCase("clear -prefix=true") && console != null) {
            console.setUsingPrefix(true);
            console.clearConsole();
        }
        System.out.println(command.toString());
    }

    public void setConsole(PoseidonConsole pc) {
        console = pc;
    }
}
