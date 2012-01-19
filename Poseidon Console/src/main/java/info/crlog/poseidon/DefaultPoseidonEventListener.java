package info.crlog.poseidon;

import javax.swing.text.AttributeSet;
import javax.swing.text.DocumentFilter.FilterBypass;

/**
 * A default "do nothing" implementation of the Event listener. Intended for
 * someone to extend and only override the method for the event they're
 * interested in. All implemented methods do nothing, EXCEPT for
 * <code>isConsumed()</code> which returns false by default indicating that the
 * event should also be sent to any listeners not already notified of the
 * current event
 *
 * @author Courtney Robinson <courtney@crlog.info>
 */
public class DefaultPoseidonEventListener implements PoseidonEventListener {

    public boolean isConsumed() {
        return false;
    }

    public void onRemove(FilterBypass fb, int offset, int length) {
    }

    public void onInsert(FilterBypass fb, int offset, String string, AttributeSet attr) {
    }

    public void onReplace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) {
    }
}
