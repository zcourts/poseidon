package info.crlog.poseidon;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTextPane;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;
import javax.swing.text.StyledDocument;

/**
 *
 * @author Courtney Robinson <courtney@crlog.info>
 */
public class PoseidonDocumentFilter extends DocumentFilter {

    BaseConsole console;
    //range of where the current line starts and ends
    private int newLineOffset = -1, oldNewLineOffset = 0;
    private boolean forceClear = false;

    public PoseidonDocumentFilter(BaseConsole console) {
        this.console = console;
    }

    /**
     * Removes all text from the current document
     */
    public void clearText() {
        try {
            forceClear = true;
            int width = console().getStyledDocument().getLength();
            console().getStyledDocument().remove(0, width);
            console.printNewLine();
            forceClear = false;
        } catch (Exception exc) {
            exc.printStackTrace();
        }
    }

    /**
     * Gets the command without changing the offset used to calculate the diff
     * between the command and the prefix if updateOldOffset is false if its
     * true then its updated
     *
     * @param updateOldOffset whether to change the offset used by
     * <code>getInput()</code>
     * @return
     */
    private String getCommand(boolean updateOldOffset) {
        String input = getInput(updateOldOffset);
        String prefixStr = console.getLinePrefix();
        if (prefixStr == null ? input == null : prefixStr.equals(input)) {
            return "";//no command just the prefix
        }
        return input.replace(prefixStr, "").trim();
    }

    public PoseidonCommand getCommand() {
        return new PoseidonCommand(getCommand(true));
    }

    /**
     * Clear only the text the user has typed since they last hit enter
     */
    public void clearUserInput() {
        String input = getInput(false);
        StyledDocument doc = console().getStyledDocument();
        try {
            doc.remove(doc.getLength() - input.length(), input.length());
        } catch (BadLocationException ex) {
            Logger.getLogger(PoseidonDocumentFilter.class.getName()).log(Level.SEVERE, ex.getMessage());
        }
    }

    private String getInput(boolean updateOldOffest) {
        StyledDocument doc = console().getStyledDocument();
        String input = "";
        try {
            //get up to the end of the document from where we last stopped
            int length = doc.getLength() - oldNewLineOffset;
            if (length > 0) {
                //start,length
                input = doc.getText(oldNewLineOffset, length);
                if (updateOldOffest) {
                    //next time start from where the previous end was
                    oldNewLineOffset = doc.getLength();
                }
            }
        } catch (BadLocationException ex) {
            Logger.getLogger(PoseidonDocumentFilter.class.getName()).log(Level.SEVERE, ex.getMessage());
        }
        return input;
    }

    /**
     * Invoked prior to removal of the specified region in the specified
     * Document. Subclasses that want to conditionally allow removal should
     * override this and only call supers implementation as necessary, or call
     * directly into the
     * <code>FilterBypass</code> as necessary.
     *
     * @param fb FilterBypass that can be used to mutate Document
     * @param offset the offset from the beginning >= 0
     * @param length the number of characters to remove >= 0
     * @exception BadLocationException some portion of the removal range was not
     * a valid part of the document. The location in the exception is the first
     * bad position encountered.
     */
    @Override
    public void remove(FilterBypass fb, int offset, int length) throws
            BadLocationException {
        console.fireOnRemove(fb, offset, length);
        if (forceClear) {
            newLineOffset = -1;
            oldNewLineOffset = 0;
            fb.remove(0, length);
        } else if ((offset >= (newLineOffset + console.getLinePrefix().length()))) {
            fb.remove(offset, length);
        }
    }

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
    @Override
    public void insertString(FilterBypass fb, int offset, String string,
            AttributeSet attr) throws BadLocationException {
        console.fireOnInsert(fb, offset, string, attr);
        fb.insertString(offset, string, attr);
    }

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
    @Override
    public void replace(FilterBypass fb, int offset, int length, String text,
            AttributeSet attrs) throws BadLocationException {
        console.fireOnReplace(fb, offset, length, text, attrs);
        fb.replace(offset, length, text, attrs);
    }

    /**
     * Gets the Jtextpane which represents the console
     *
     * @return
     */
    private JTextPane console() {
        return console.getConsole();
    }

    public boolean validCaretPosition(int location) {
        int length = newLineOffset;// + console.getLinePrefix().length();
        if (location >= length) {
            return true;
        }
        return false;
    }

    /**
     * Updates the Caret's position, setting the range within wich the caret is
     * allowed to be and moving the caret within that range if need be
     */
    public void updateCaretPosition() {
        updateCaretPosition(true);
    }

    /**
     * @see
     * <code>updateCaretPosition()</code>
     * @param updateOldOffset if true then the lower end of the range that the
     * caret is allowed to be in is updated, if false then only the caret's
     * position is forced into the allowed range.
     */
    public void updateCaretPosition(boolean updateOldOffset) {
        if (updateOldOffset) {
            //set the new line offset
            int newRangeStart = console.getConsoleOffset();
            if (newLineOffset >= 0) {
                oldNewLineOffset = newLineOffset;
            }
            newLineOffset = newRangeStart;
        }
        //then add the default console prompt (MUST BE DONE IN THIS ORDER!!!)
        console().setCaretPosition(console.getConsoleOffset());
    }
}
