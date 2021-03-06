package info.crlog.poseidon.terminal;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

/**
 *
 * @author Courtney Robinson <courtney@crlog.info>
 */
public class Tab extends JPanel {

    private String currentDirectory;
    private String title;
    private int id;
    private EmulatorlCommandHandler internalCommands;
    private Terminal terminal;

    /**
     * Creates new form Tab
     */
    public Tab(String path, Terminal term) {
        currentDirectory = path;
        terminal = term;
        internalCommands = new EmulatorlCommandHandler(this, terminal);
        initComponents();
        setOpaque(false);
        setBackground(console.getBackgroundColour());
        setBorder(BorderFactory.createEmptyBorder());
        console.addCommandListener(internalCommands);
        internalCommands.setConsole(console);
    }

    public String getCurrentDir() {
        return currentDirectory;
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        console = new info.crlog.poseidon.console.PoseidonConsole();

        setLayout(new java.awt.GridLayout(1, 1));
        add(console);

        getAccessibleContext().setAccessibleParent(this);
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private info.crlog.poseidon.console.PoseidonConsole console;
    // End of variables declaration//GEN-END:variables

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setID(int currentTabIndex) {
        id = currentTabIndex;
    }

    public int getID() {
        return id;
    }
}
