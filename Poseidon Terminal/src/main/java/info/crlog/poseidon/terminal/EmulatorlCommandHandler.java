package info.crlog.poseidon.terminal;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import info.crlog.poseidon.console.PoseidonCommand;
import info.crlog.poseidon.console.PoseidonConsole;
import info.crlog.poseidon.event.PoseidonCommandListener;
import info.crlog.poseidon.event.PoseidonEventListener;
import info.crlog.poseidon.event.PoseidonKeyListener;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import javax.swing.text.AttributeSet;
import javax.swing.text.DocumentFilter.FilterBypass;
import static info.crlog.poseidon.console.PoseidonConsole.*;

/**
 *
 * @author Courtney Robinson <courtney@crlog.info>
 */
public class EmulatorlCommandHandler implements PoseidonCommandListener, PoseidonEventListener, PoseidonKeyListener {

    public static int EXIT;
    private boolean consumed = false;
    private PoseidonConsole console;
    private Tab tab;
    private final Terminal terminal;
    private HashMap<String, String> commands;
    private ProcessBuilder processBuilder;
    private String lastCommand;
    private Process process;

    public EmulatorlCommandHandler(Tab tab, Terminal term) {
        this.tab = tab;
        terminal = term;
        commands = new HashMap<String, String>();
        initializePathCommands();
    }

    public boolean isConsumed() {
        if (consumed) {
            consumed = false;
            return true;
        } else {
            return consumed;
        }
    }

    public void onCommand(final PoseidonCommand command) {

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
                    if (commands.containsKey(command.getCommand())) {
                        //on a new thread to prevent locking up UI thread
                        new Thread(new Runnable() {

                            public void run() {
                                handleExternal(command);
                            }
                        }).start();
                    } else {
                        console.printInplace(command.getCommand() + " is not a known program, i.e. it is not in your system PATH");
                        console.printNewLine();
                    }
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
        //TODO basic naive implementation needs to handle "interactive mode"
        //to allow both input and output to and from the program
//		if (lastCommand == null) {
        lastCommand = command.getCommand();
        processBuilder = new ProcessBuilder(command.getExternalCommand(commands.get(command.getCommand())));
        processBuilder.directory(new File(tab.getCurrentDir()));
        processBuilder.redirectErrorStream(true);
        try {
            process = processBuilder.start();

            InputStream in = process.getInputStream();
            process.waitFor();
            console.output(streamToString(in));
        } catch (Exception ex) {
            fatal(EmulatorlCommandHandler.class, ex);
        }
//		} else {
//			if (command.getCommand() == null ? lastCommand == null : command.getCommand().equals(lastCommand)) {
//				//the last command is the same as the current so
//
//			}
//		}
    }

    public static String streamToString(InputStream is) throws IOException {
        if (is != null) {
            Writer writer = new StringWriter();
            char[] buffer = new char[1024];
            try {
                Reader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
                int n;
                while ((n = reader.read(buffer)) != -1) {
                    writer.write(buffer, 0, n);
                }
            } finally {
                is.close();
            }
            return writer.toString();
        } else {
            return "";
        }
    }

    private void initializePathCommands() {
        String path = System.getenv("PATH");
//		Map<String, String> env = System.getenv();
//		Iterator<String> it = env.keySet().iterator();
//		while (it.hasNext()) {
//			String key = it.next();
//			System.out.println(key + ":" + env.get(key));
//		}
        String[] directories;
        String os = System.getProperty("os.name").toLowerCase();
        System.out.println("OS:" + os + " ~ PATH:" + path);
        if (os.contains("windows")) {
            directories = path.split(";");
        } else {
            directories = path.split(":");
        }

        for (String dir : directories) {
            //System.out.println(dir);
            File folder = new File(dir);
            File[] listOfFiles = folder.listFiles();
            if (listOfFiles != null) {
                for (File file : listOfFiles) {
                    String name = file.getName();
                    if (file.isFile() && file.canExecute() && !file.isHidden()) {
                        if (os.contains("windows")) {
                            if (name.endsWith(".exe") || name.endsWith(".jar") || name.endsWith(".bat")) {
                                name = name.substring(0, name.length() - 4);
                                commands.put(name, file.getAbsolutePath());
                                debug(EmulatorlCommandHandler.class, "Adding " + name + " to available commands with path => " + file.getAbsolutePath());
                            }
                        } else {
                            commands.put(name, file.getAbsolutePath());
                            debug(EmulatorlCommandHandler.class, "Adding " + name + " to available commands with path => " + file.getAbsolutePath());
                        }
                    }
                }
            }
        }
    }

    public void onRemove(FilterBypass fb, int offset, int length) {
    }

    public void onInsert(FilterBypass fb, int offset, String string, AttributeSet attr) {
    }

    public void onReplace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) {
    }

    public void keyTyped(KeyEvent e) {
    }

    public void keyPressed(KeyEvent e) {
    }

    public void keyReleased(KeyEvent e) {
        //handle tab events
    }
}
