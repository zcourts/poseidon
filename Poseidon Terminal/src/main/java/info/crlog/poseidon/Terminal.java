package info.crlog.poseidon;

import java.net.UnknownHostException;
import java.util.HashMap;

/**
 *
 * @author Courtney Robinson <courtney@crlog.info>
 */
public class Terminal extends javax.swing.JFrame {

    /**
     * Reference to all currently opened tabs
     */
    private HashMap<Integer, Tab> tabRefs;
    private static Terminal instance;

    /**
     * Creates new form Terminal
     */
    public Terminal() {
        instance = this;
        tabRefs = new HashMap<Integer, Tab>();
        initComponents();
        setSize(500, 500);
        //remove tab pane until multiple tabs created
        getContentPane().remove(tabs);
        //create a new tab
        newTab("~");
        //add the new tab in place of the tab pane
        getContentPane().add(tabRefs.get(0));
    }

    private void newTab(String title) {
        int currentTabIndex = tabs.getTabCount();
        if (tabRefs.size() == 1) {
            //creating more than one tab so show tab pane instead of single terminal
            getContentPane().remove(tabRefs.get(0));
            getContentPane().add(tabs);
            currentTabIndex = 1;
            validate();
            //tabs weren't visible so current tab was removed from tab pane
            tabs.addTab(tabRefs.get(0).getTitle(), tabRefs.get(0));
        }
        tabRefs.put(currentTabIndex, new Tab(getHomeDir()));
        tabRefs.get(currentTabIndex).setTitle(title);
        tabRefs.get(currentTabIndex).setID(currentTabIndex);
        tabs.addTab(title, tabRefs.get(currentTabIndex));
        tabs.setSelectedComponent(tabRefs.get(currentTabIndex));
    }

    public static void closeTab(int tabID) {
        if (instance.tabRefs.size() == 1) {
            //if there's only one tab then exit program
            System.exit(0);
        } else {
            if (tabID == -1) {
                //close current tab
                if (instance.tabs.getSelectedComponent() instanceof Tab) {
                    tabID = ((Tab) instance.tabs.getSelectedComponent()).getID();
                } else {
                    //hmmmm - why do we have something that's not an instance of tab?
                    return;
                }
            }
            instance.tabs.remove(instance.tabRefs.get(tabID));
            instance.tabRefs.remove(tabID);
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        tabs = new javax.swing.JTabbedPane();
        menus = new javax.swing.JMenuBar();
        menuFile = new javax.swing.JMenu();
        menuNewTab = new javax.swing.JMenuItem();
        menuEdit = new javax.swing.JMenu();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Poseidon Terminal");
        setBackground(new java.awt.Color(0, 0, 0));
        setForeground(java.awt.Color.white);
        getContentPane().setLayout(new java.awt.GridLayout(1, 1));

        tabs.setBackground(new java.awt.Color(0, 0, 0));
        tabs.setTabLayoutPolicy(javax.swing.JTabbedPane.SCROLL_TAB_LAYOUT);
        tabs.setAutoscrolls(true);
        tabs.setDoubleBuffered(true);
        getContentPane().add(tabs);

        menuFile.setText("File");

        menuNewTab.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_T, java.awt.event.InputEvent.CTRL_MASK));
        menuNewTab.setText("New Tab");
        menuNewTab.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuNewTabActionPerformed(evt);
            }
        });
        menuFile.add(menuNewTab);

        menus.add(menuFile);

        menuEdit.setText("Edit");
        menus.add(menuEdit);

        setJMenuBar(menus);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void menuNewTabActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuNewTabActionPerformed
        newTab(getHomeDir());
    }//GEN-LAST:event_menuNewTabActionPerformed
    /**
     * Gets the username@host prefix that starts every new line with the folder
     * set to ~ i.e. user's home directory
     *
     * @return
     */
    public static String getLinePrefix() {
        return getLinePrefix("~");
    }

    /**
     * Gets the username@host prefix that starts every new line
     *
     * @return
     */
    public static String getLinePrefix(String folder) {
        return "[" + getUsername() + "@" + getHostname() + " " + folder + "]$>";
    }

    /**
     * Get the System provided path to the user's home directory
     *
     * @return
     */
    public static String getHomeDir() {
        return System.getProperty("user.home");
    }

    /**
     * Gets the current user's name
     *
     * @return
     */
    public static String getUsername() {
        return System.getProperty("user.name");
    }

    public static String getHostname() {
        try {
            return java.net.InetAddress.getLocalHost().getHostName();
        } catch (UnknownHostException ex) {
            return "~";
        }
    }

    /**
     * Gets / or \ depending on OS
     *
     * @return
     */
    public static String getFileSeparator() {
        return System.getProperty("file.separator");
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /*
         * Set the Nimbus look and feel
         */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /*
         * If Nimbus (introduced in Java SE 6) is not available, stay with the
         * default look and feel. For details see
         * http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
         */
//        try {
//            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
//                if ("Nimbus".equals(info.getName())) {
//                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
//                    break;
//                }
//            }
//        } catch (ClassNotFoundException ex) {
//            java.util.logging.Logger.getLogger(Terminal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (InstantiationException ex) {
//            java.util.logging.Logger.getLogger(Terminal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (IllegalAccessException ex) {
//            java.util.logging.Logger.getLogger(Terminal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
//            java.util.logging.Logger.getLogger(Terminal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        }
        //</editor-fold>

        /*
         * Create and display the form
         */
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                new Terminal().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenu menuEdit;
    private javax.swing.JMenu menuFile;
    private javax.swing.JMenuItem menuNewTab;
    private javax.swing.JMenuBar menus;
    private javax.swing.JTabbedPane tabs;
    // End of variables declaration//GEN-END:variables
}
