package info.crlog.poseidon;

import java.awt.Color;
import java.awt.event.KeyEvent;

/**
 *
 * @author Courtney Robinson <courtney@crlog.info>
 */
public class PoseidonConsole extends BaseConsole {

    public PoseidonConsole() {
        this("~");
    }

    public PoseidonConsole(String path) {
        super(path);
    }

    public void clearConsole() {
        filter.clearText();
    }

    protected void fireCommand(PoseidonCommand command) {
        if (command.isValid()) {
            for (CommandListener listener : commandListeners) {
                listener.onCommand(command);
                if (listener.isConsumed()) {
                    break;
                }
            }
        }
    }

    public void addCommandListener(CommandListener listener) {
        commandListeners.push(listener);
    }

    @Override
    public void keyPressed(KeyEvent e) {
        super.keyPressed(e);//must invoke to get default functionality of setting new line offset
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            e.consume();
            printNewLine();
            if (console.getStyledDocument().getLength() > 0) {
                console.setCaretPosition(console.getStyledDocument().getLength());
            }
        }
    }

    public Color getBackgroundColour() {
        return backgroundColour;
    }
}
