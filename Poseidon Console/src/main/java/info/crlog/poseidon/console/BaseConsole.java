package info.crlog.poseidon.console;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.net.UnknownHostException;
import javax.swing.*;
import javax.swing.event.CaretListener;
import javax.swing.text.DocumentFilter.FilterBypass;
import javax.swing.text.*;
import org.apache.log4j.Logger;

/**
 *
 * @author Courtney Robinson <courtney@crlog.info>
 */
public abstract class BaseConsole extends JPanel implements KeyListener, CaretListener {

	protected Color backgroundColour;
	protected Color foregroundColour;
	protected Color caretColour;
	protected Font font;
	protected SimpleAttributeSet attr;
	protected JScrollPane scroller;
	protected PoseidonTextPane console;
	protected String currentDirectory = "~";
	protected PoseidonDocumentFilter filter;
	protected boolean usingPrefix = true;
	protected boolean pasting = false;

	public BaseConsole(String path) {
		currentDirectory = path;
		init();
	}

	private void init() {
		font = new Font(Font.SANS_SERIF, Font.PLAIN, 14);
		backgroundColour = Color.BLACK;
		foregroundColour = Color.GRAY.brighter();
		caretColour = Color.GRAY.brighter();
		attr = new SimpleAttributeSet();
		console = new PoseidonTextPane();
		console.addCaretListener(this);
		console.addKeyListener(this);
		console.setCaretColor(caretColour);
		console.setBackground(backgroundColour);
		console.setForeground(foregroundColour);
		console.setFont(font);
		console.setBorder(null);
		scroller = new JScrollPane(console);
		scroller.setBorder(null);
		scroller.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		scroller.setBackground(backgroundColour);
		scroller.setForeground(foregroundColour);
		add(scroller);
		setLayout(new GridLayout(1, 1));
		filter = new PoseidonDocumentFilter(this);
		((AbstractDocument) console.getStyledDocument()).setDocumentFilter(filter);
		// Add Copy functionality to the Console
		//   and remove Cut/Paste functionality
		JTextComponent.KeyBinding newBindings[] = {
			new JTextComponent.KeyBinding(
			KeyStroke.getKeyStroke(KeyEvent.VK_C, KeyEvent.CTRL_DOWN_MASK),
			javax.swing.text.DefaultEditorKit.copyAction),
			new JTextComponent.KeyBinding(
			KeyStroke.getKeyStroke(KeyEvent.VK_V, KeyEvent.CTRL_DOWN_MASK),
			javax.swing.text.DefaultEditorKit.pasteAction),
			new JTextComponent.KeyBinding(
			KeyStroke.getKeyStroke(KeyEvent.VK_X, KeyEvent.CTRL_DOWN_MASK),
			javax.swing.text.DefaultEditorKit.beepAction)
		};
		Keymap k = console.getKeymap();
		JTextComponent.loadKeymap(k, newBindings, console.getActions());
		printNewLine();
	}

	public final void printNewLine() {
		print("\n");
		filter.updateCaretPosition();
	}

	public final void printWithoutPrefix(String contents) {
		if (!isUsingPrefix()) {
			print(contents);
		} else {
			setUsingPrefix(false);
			print(contents);
			setUsingPrefix(true);//don't print the cmd prefix just the line we pass it
		}
	}

	public final void output(String contents) {
		if (!isUsingPrefix()) {
			printNewLine();
			printInplace(contents);
			printNewLine();
		} else {
			setUsingPrefix(false);
			printNewLine();
			printInplace(contents);
			setUsingPrefix(true);
			printNewLine();
		}
	}

	public final void print(String processed) {
		print(processed, attr, false);
	}

	public void printInplace(String content) {
		if (isUsingPrefix()) {
			setUsingPrefix(false);
			print(content, attr, true);
			setUsingPrefix(true);
		} else {
			print(content, attr, true);
		}
	}

	public void print(String output, SimpleAttributeSet style, boolean inplace) {
		try {
			String pre = "";
			if (!inplace) {
				if (output.equalsIgnoreCase("\n")) {
					//if it's not the very first insert then do a new line before the prefix
					if (getConsoleOffset() != 0) {
						pre = output;
					}
					output = "";
				}
			}
			console.getStyledDocument().insertString(getConsoleOffset(), pre + getLinePrefix() + output, attr);
		} catch (BadLocationException ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * Gets the username@host prefix that starts every new line with the folder
	 * set to ~ i.e. user's home directory
	 *
	 * @return
	 */
	public String getLinePrefix() {
		return getLinePrefix(currentDirectory);
	}

	/**
	 * Gets the username@host prefix that starts every new line
	 *
	 * @return
	 */
	public String getLinePrefix(String folder) {
		if (isUsingPrefix()) {
			String sep = System.getProperty("line.separator");
			folder = folder.replace(sep, "poseidonconsoleseperator");
			String[] paths = folder.split("poseidonconsoleseperator");
			folder = paths[paths.length - 1];
			return "[" + getUsername() + "@" + getHostname() + " " + folder + "]$>";
		} else {
			return "";
		}
	}

	/**
	 * Get the System provided path to the user's home directory
	 *
	 * @return
	 */
	public String getHomeDir() {
		return System.getProperty("user.home");
	}

	/**
	 * Gets the current user's name
	 *
	 * @return
	 */
	public String getUsername() {
		return System.getProperty("user.name");
	}

	public String getHostname() {
		try {
			return java.net.InetAddress.getLocalHost().getHostName();
		} catch (UnknownHostException ex) {
			return "~";
		}
	}

	/**
	 * Fires a key released event which handles the enter key to capture input
	 * for every invocation of this method the system tries to get whatever
	 * command is currently typed and moves to the next line
	 */
	public void simulateEnterKeyPressed() {
		int id = (int) (System.currentTimeMillis() / 1000);
		keyReleased(new KeyEvent(this, id, System.nanoTime(), 0, KeyEvent.VK_ENTER, KeyEvent.CHAR_UNDEFINED));
	}

	/**
	 * Handles input when the user presses Ctrl+V
	 */
	protected void handlePasting() {
		pasting = true;
		String pastedStr;
		Clipboard clip = Toolkit.getDefaultToolkit().getSystemClipboard();
		Transferable contents = clip.getContents(this);
		if (contents != null) {
			try {
				pastedStr = (String) contents.getTransferData(DataFlavor.stringFlavor);
				String[] lines = pastedStr.split("\n");
				for (int i = 0; i < lines.length; i++) {
					lines[i] = lines[i].replace("\n", "").replace(System.getProperty("line.separator"), "");
					if (i == 0) {
						printWithoutPrefix(lines[i] + "\n");
					} else if (i == lines.length - 1) {
						print(lines[i]);//don't add new line when we get to the end of the pasted character
					} else {
						print(lines[i] + "\n");
					}
					if (lines.length > 1 && i != (lines.length - 1)) {
						//if we have more than one line and we're not on the last line
						simulateEnterKeyPressed();
					}
				}
			} catch (Exception ex) {
				fatal(BaseConsole.class, ex.getMessage());
			}
		}
		try {
			Rectangle bottom = console.modelToView(getConsoleOffset());
			console.scrollRectToVisible(bottom);
			//now update the caret position as well
			console.setCaretPosition(getConsoleOffset());
		} catch (BadLocationException e) {
			System.err.println("Could not scroll to " + e);
		}
		pasting = false;
	}

	/**
	 * Gets the current end of the document
	 *
	 * @return
	 */
	public int getConsoleOffset() {
		return console.getStyledDocument().getEndPosition().getOffset() - 1;
	}

	/**
	 * Get a reference to the JTextPane
	 *
	 * @return
	 */
	public JTextPane getConsole() {
		return console;
	}

	/**
	 * Sets whether or not the console is prefixed
	 *
	 * @param using if true then a prefix such as user@host> is shown if false
	 * then nothing
	 */
	public void setUsingPrefix(boolean using) {
		usingPrefix = using;
	}

	/**
	 * @return true if the console is using a prefix, false otherwise
	 */
	public boolean isUsingPrefix() {
		return usingPrefix;
	}

	public static void debug(Class className, Object message) {
		Logger.getLogger(className).debug(message);
	}

	public static void fatal(Class className, Object message) {
		Logger.getLogger(className).fatal(message);
	}

	public static void error(Class className, Object message) {
		Logger.getLogger(className).error(message);
	}

	protected abstract void fireCommand(PoseidonCommand command);

	protected abstract void fireOnRemove(FilterBypass fb, int offset, int length);

	protected abstract void fireOnInsert(FilterBypass fb, int offset, String string, AttributeSet attr);

	protected abstract void fireOnReplace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs);

	protected abstract void fireKeyReleased(KeyEvent e);

	protected abstract void fireKeyPressed(KeyEvent e);

	protected abstract void fireKeyTyped(KeyEvent e);
}
