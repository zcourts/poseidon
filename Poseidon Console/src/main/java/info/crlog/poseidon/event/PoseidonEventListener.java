package info.crlog.poseidon.event;

import javax.swing.text.AttributeSet;
import javax.swing.text.DocumentFilter;

/**
 *
 * @author Courtney Robinson <courtney@crlog.info>
 */
public interface PoseidonEventListener {

    /**
     * @return true if this listener has consumed the event and does not want
     * any other listener to be notified of this event.
     */
    public boolean isConsumed();

    /**
     * Fired when a request is received to remove some text from the console
     *
     * @param fb FilterBypass that can be used to mutate the document
     * @param offset the offset from the beginning >= 0
     * @param length the number of characters to remove >= 0
     */
    public void onRemove(DocumentFilter.FilterBypass fb, int offset, int length);

    /**
     * Invoked prior to insertion of text into the specified Document.
     * Subclasses that want to conditionally allow insertion should override
     * this and only call supers implementation as necessary, or call directly
     * into the FilterBypass.
     *
     * @param fb FilterBypass that can be used to mutate Document
     * @param offset the offset into the document to insert the content >= 0.
     * All positions that track change at or after the given location will move.
     * @param string the string to insert
     * @param attr the attributes to associate with the inserted content. This
     * may be null if there are no attributes.
     * @exception BadLocationException the given insert position is not a valid
     * position within the document
     */
    public void onInsert(DocumentFilter.FilterBypass fb, int offset, String string, AttributeSet attr);

    /**
     * Invoked prior to replacing a region of text in the specified Document.
     * Subclasses that want to conditionally allow replace should override this
     * and only call supers implementation as necessary, or call directly into
     * the FilterBypass.
     *
     * @param fb FilterBypass that can be used to mutate Document
     * @param offset Location in Document
     * @param length Length of text to delete
     * @param text Text to insert, null indicates no text to insert
     * @param attrs AttributeSet indicating attributes of inserted text, null is
     * legal.
     * @exception BadLocationException the given insert position is not a valid
     * position within the document
     */
    public void onReplace(DocumentFilter.FilterBypass fb, int offset, int length, String text, AttributeSet attrs);
}
