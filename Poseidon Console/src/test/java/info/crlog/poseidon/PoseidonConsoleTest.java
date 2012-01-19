/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package info.crlog.poseidon;

import java.awt.Color;
import java.awt.event.KeyEvent;
import javax.swing.event.CaretEvent;
import javax.swing.text.AttributeSet;
import javax.swing.text.DocumentFilter.FilterBypass;
import junit.framework.TestCase;

/**
 *
 * @author Courtney Robinson <courtney@crlog.info>
 */
public class PoseidonConsoleTest extends TestCase {

    public PoseidonConsoleTest(String testName) {
        super(testName);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    /**
     * Test of clearConsole method, of class PoseidonConsole.
     */
    public void testClearConsole() {
        System.out.println("clearConsole");
        PoseidonConsole instance = new PoseidonConsole();
        instance.clearConsole();
        assertTrue(true);
    }
//    /**
//     * Test of addCommandListener method, of class PoseidonConsole.
//     */
//    public void testAddCommandListener() {
//        System.out.println("addCommandListener");
//        CommandListener listener = null;
//        PoseidonConsole instance = new PoseidonConsole();
//        instance.addCommandListener(listener);
//    }
//
//    /**
//     * Test of addEventListener method, of class PoseidonConsole.
//     */
//    public void testAddEventListener() {
//        System.out.println("addEventListener");
//        PoseidonEventListener listener = null;
//        PoseidonConsole instance = new PoseidonConsole();
//        instance.addEventListener(listener);
//    }
//
//    /**
//     * Test of keyPressed method, of class PoseidonConsole.
//     */
//    public void testKeyPressed() {
//        System.out.println("keyPressed");
//        KeyEvent e = null;
//        PoseidonConsole instance = new PoseidonConsole();
//        instance.keyPressed(e);
//    }
//
//    /**
//     * Test of keyReleased method, of class PoseidonConsole.
//     */
//    public void testKeyReleased() {
//        System.out.println("keyReleased");
//        KeyEvent e = null;
//        PoseidonConsole instance = new PoseidonConsole();
//        instance.keyReleased(e);
//    }
//
//    /**
//     * Test of keyTyped method, of class PoseidonConsole.
//     */
//    public void testKeyTyped() {
//        System.out.println("keyTyped");
//        KeyEvent e = null;
//        PoseidonConsole instance = new PoseidonConsole();
//        instance.keyTyped(e);
//    }
//
//    /**
//     * Test of caretUpdate method, of class PoseidonConsole.
//     */
//    public void testCaretUpdate() {
//        System.out.println("caretUpdate");
//        CaretEvent e = null;
//        PoseidonConsole instance = new PoseidonConsole();
//        instance.caretUpdate(e);
//    }
//
//    /**
//     * Test of getBackgroundColour method, of class PoseidonConsole.
//     */
//    public void testGetBackgroundColour() {
//        System.out.println("getBackgroundColour");
//        PoseidonConsole instance = new PoseidonConsole();
//        Color expResult = null;
//        Color result = instance.getBackgroundColour();
//        assertEquals(expResult, result);
//    }
//
//    /**
//     * Test of fireCommand method, of class PoseidonConsole.
//     */
//    public void testFireCommand() {
//        System.out.println("fireCommand");
//        PoseidonCommand command = null;
//        PoseidonConsole instance = new PoseidonConsole();
//        instance.fireCommand(command);
//    }
//
//    /**
//     * Test of fireOnRemove method, of class PoseidonConsole.
//     */
//    public void testFireOnRemove() {
//        System.out.println("fireOnRemove");
//        FilterBypass fb = null;
//        int offset = 0;
//        int length = 0;
//        PoseidonConsole instance = new PoseidonConsole();
//        instance.fireOnRemove(fb, offset, length);
//    }
//
//    /**
//     * Test of fireOnInsert method, of class PoseidonConsole.
//     */
//    public void testFireOnInsert() {
//        System.out.println("fireOnInsert");
//        FilterBypass fb = null;
//        int offset = 0;
//        String string = "";
//        AttributeSet attr = null;
//        PoseidonConsole instance = new PoseidonConsole();
//        instance.fireOnInsert(fb, offset, string, attr);
//    }
//
//    /**
//     * Test of fireOnReplace method, of class PoseidonConsole.
//     */
//    public void testFireOnReplace() {
//        System.out.println("fireOnReplace");
//        FilterBypass fb = null;
//        int offset = 0;
//        int length = 0;
//        String text = "";
//        AttributeSet attrs = null;
//        PoseidonConsole instance = new PoseidonConsole();
//        instance.fireOnReplace(fb, offset, length, text, attrs);
//    }
}
