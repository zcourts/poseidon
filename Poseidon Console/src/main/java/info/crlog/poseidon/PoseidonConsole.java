package info.crlog.poseidon;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import javax.swing.text.AttributeSet;
import javax.swing.text.DocumentFilter.FilterBypass;

/**
 * Concrete implementation of BaseConsole which mainly focuses on events and
 * event dispatching
 *
 * @author Courtney Robinson <courtney@crlog.info>
 */
public class PoseidonConsole extends BaseConsole {

    protected Deque<PoseidonEventListener> eventListeners;
    protected Deque<CommandListener> commandListeners;

    public PoseidonConsole() {
        this("~");
        eventListeners = new LinkedList<PoseidonEventListener>();
        commandListeners = new LinkedList<CommandListener>();

    }

    public PoseidonConsole(String path) {
        super(path);
    }

    public void clearConsole() {
        filter.clearText();
    }

    /**
     * Adds a command listener which is notified when the user enters a new
     * command i.e. when the user types a string and presses the enter key
     *
     * @param listener is added at the end of the queue of listeners
     */
    public void addCommandListener(CommandListener listener) {
        commandListeners.add(listener);
    }

    /**
     * Adds an Event Listener which will be notified all events not consumed by
     * prior listeners
     *
     * @param listener is added at the end of the queue of listeners
     */
    public void addEventListener(PoseidonEventListener listener) {
        eventListeners.add(listener);
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

    @Override
    protected void fireOnRemove(FilterBypass fb, int offset, int length) {
        for (PoseidonEventListener listener : eventListeners) {
            listener.onRemove(fb, offset, length);
        }
    }

    @Override
    protected void fireOnInsert(FilterBypass fb, int offset, String string, AttributeSet attr) {
        for (PoseidonEventListener listener : eventListeners) {
            listener.onInsert(fb, offset, string, attr);
        }
    }

    @Override
    protected void fireOnReplace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) {
        for (PoseidonEventListener listener : eventListeners) {
            listener.onReplace(fb, offset, length, text, attrs);
        }
    }
}
