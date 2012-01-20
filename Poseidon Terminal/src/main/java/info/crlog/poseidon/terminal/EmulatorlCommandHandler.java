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
        if (console != null) {
            switch(EmulatorCommand.get(command.getCommand())){
                case CLEAR:handleClear(command);break;
                case EXIT :handleExit(command);break;
                case CD:handleCD(command);break;
                case NOT_SUPPORTED:
                default:handleExternal(command);
            }
//            if (command.toString().equalsIgnoreCase("clear")) {
//                console.clearConsole();
//            }
//            if (command.toString().equalsIgnoreCase("clear -prefix")) {
//                console.setUsingPrefix(false);
//                console.clearConsole();
//            }
//            if (command.toString().equalsIgnoreCase("clear -prefix=true")) {
//                console.setUsingPrefix(true);
//                console.clearConsole();
//            }
//            if (command.toString().equals("exit")) {
//                terminal.closeTab(tab.getID());
//            }
        }
    }

    public void setConsole(PoseidonConsole pc) {
        console = pc;
    }

    private void handleClear(PoseidonCommand command) {

    }

    private void handleExit(PoseidonCommand command) {

    }

    private void handleCD(PoseidonCommand command) {

    }

    private void handleExternal(PoseidonCommand command) {

    }
}
