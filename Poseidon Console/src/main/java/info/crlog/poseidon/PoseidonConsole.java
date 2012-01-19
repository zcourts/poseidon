package info.crlog.poseidon;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedList;
import javax.swing.event.CaretEvent;
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
    protected HashMap<Integer, String> history;
    /**
     * The position of the history log which increases or decreases when the
     * user uses the up or down arrow keys and gets reset to the most current
     * item's position when enter is clicked
     */
    protected int historyPosition;

    public PoseidonConsole() {
        this("~");
        eventListeners = new LinkedList<PoseidonEventListener>();
        commandListeners = new LinkedList<CommandListener>();
        history = new HashMap<Integer, String>();
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
        if (e.isControlDown()) {
            if (e.getKeyCode() == KeyEvent.VK_V) {
                e.consume();//must consume event to prevent default paste action
                handlePasting();
                return;
            }
        }
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            e.consume();
            printNewLine();
        }
    }

    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            PoseidonCommand command = filter.getCommand();
            fireCommand(command);
            if (!command.toString().isEmpty()) {
                //reset history position to the end (historyPosition is 0 based so -1)
                historyPosition = history.size() - 1;
                history.put(historyPosition++, command.toString());
            }
        }
        if (e.getKeyCode() == KeyEvent.VK_UP) {
            if ((historyPosition - 1) >= 0) {
                historyPosition--;
                if (history.containsKey(historyPosition)) {
                    filter.clearUserInput();
                    printInplace(history.get(historyPosition));
                }
            }
        }
        if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            if ((historyPosition + 1) <= history.size()) {
                historyPosition++;
                if (history.containsKey(historyPosition)) {
                    filter.clearUserInput();
                    printInplace(history.get(historyPosition));
                }
            }
        }
    }

    public void keyTyped(KeyEvent e) {
    }

    public void caretUpdate(CaretEvent e) {
        int location = e.getDot();
        if (e.getDot() == e.getMark() & !pasting) {
            //only try to handle caret positioning if the user hasn't pasted
            if (!filter.validCaretPosition(location)) {
                //don't change the range that the caret is allowed to be in
                //just move the caret into the allowed range
                filter.updateCaretPosition(false);
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
//        for (PoseidonEventListener listener : eventListeners) {
//            listener.onInsert(fb, offset, string, attr);
//        }
    }

    @Override
    protected void fireOnReplace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) {
        for (PoseidonEventListener listener : eventListeners) {
            listener.onReplace(fb, offset, length, text, attrs);
        }
    }
}
