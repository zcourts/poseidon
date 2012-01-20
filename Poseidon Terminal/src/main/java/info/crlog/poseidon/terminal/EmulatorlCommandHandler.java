package info.crlog.poseidon.terminal;

import info.crlog.poseidon.console.PoseidonCommand;
import info.crlog.poseidon.console.PoseidonConsole;
import info.crlog.poseidon.event.PoseidonCommandListener;

/**
 *
 * @author Courtney Robinson <courtney@crlog.info>
 */
public class EmulatorlCommandHandler implements PoseidonCommandListener {

    public static int EXIT;
    private boolean consumed = false;
    private PoseidonConsole console;
    private Tab tab;
    private final Terminal terminal;

    public EmulatorlCommandHandler(Tab tab, Terminal term) {
        this.tab = tab;
        terminal = term;
    }

    public boolean isConsumed() {
        if (consumed) {
            consumed = false;
            return true;
        } else {
            return consumed;
        }
    }

    public void onCommand(PoseidonCommand command) {

        if (console != null && command.isValid()) {
            switch (EmulatorCommand.get(command.getCommand())) {
                case CLEAR:
                    handleClear(command);
                    break;
                case EXIT:
                    handleExit(command);
                    break;
                case CD:
                    handleCD(command);
                    break;
                case NOT_SUPPORTED:
                default:
                    handleExternal(command);
            }
        }
    }

    public void setConsole(PoseidonConsole pc) {
        console = pc;
    }

    private void handleClear(PoseidonCommand command) {
        if (!command.hasArguments()) {
            console.clearConsole();
        } else if (command.getBool("-prefix")) {
            console.setUsingPrefix(true);
            console.clearConsole();
        } else {
            console.setUsingPrefix(false);
            console.clearConsole();
        }
    }

    private void handleExit(PoseidonCommand command) {
        terminal.closeTab(tab.getID());
    }

    private void handleCD(PoseidonCommand command) {
    }

    private void handleExternal(PoseidonCommand command) {
    }
}
