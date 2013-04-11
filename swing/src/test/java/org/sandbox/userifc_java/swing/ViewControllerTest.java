package org.sandbox.userifc_java.swing;

import org.junit.Test;
import static org.junit.Assert.*;

import javax.swing.*;
import java.awt.event.*;

import org.sandbox.intro_java.util.Library;
import org.sandbox.userifc_java.aux.Observer;

public class ViewControllerTest {
	//private float tolerance = 2.0f * Float.MIN_VALUE;
	private float epsilon = 1.0e-7f;
	private int delaymsecs = 2500;
	private static HelloController uicontroller = null;
	
    public ViewControllerTest() {
    }
    
	public void refreshUI(int delayMsecs) {
		try {
			Thread.sleep(delayMsecs);
		} catch (InterruptedException exc) {
			exc.printStackTrace();
		}
	}

    @org.junit.BeforeClass
    public static void setUpClass() throws Exception {
    	String rsrc_path = null != System.getenv("RSRC_PATH") ? 
            System.getenv("RSRC_PATH") :
            System.getProperty("rsrcPath", "src/main/resources");
        uicontroller = new HelloController("greet.txt", rsrc_path);
    	System.err.println("###setup TestCase###");
    }
    @org.junit.AfterClass
    public static void tearDownClass() throws Exception {
    	System.err.println("###teardown TestCase###");
    }
    
    @org.junit.Before
    public void setUp() {
    	System.err.println("setup Test ...");
    }
    @org.junit.After
    public void tearDown() {
    	System.err.println("... teardown Test");
    }
	
    @Test
    public void test_classExists() {
        try {
            Class.forName(String.format("%s.HelloController",
                this.getClass().getPackage().getName()));
        } catch(ClassNotFoundException exc) {
            //fail("Class(es) not existent: " + HelloController.class.getName());
            fail(String.format("%s %s", "Class(es) not existent:",
                java.util.Arrays.toString(new String[]{
                HelloController.class.getName()})));
        }
    }
    
	@Test
    public void test_onButton1Action() {
        ((JButton)this.uicontroller.getView1().widgets.get(
		    "button1")).doClick();
        refreshUI(delaymsecs);
        JDialog dialog1 = (JDialog)uicontroller.getView1().widgets.get(
		    "dialog1");
		JFrame frame1 = (JFrame)uicontroller.getView1().widgets.get(
		    "frame1");
		assertTrue("dialog1 not visible", dialog1.isVisible());
		//assertTrue("frame1 visible", !frame1.isVisible());
		assertTrue("dialog1 not visible and frame1 not visible",
		    dialog1.isVisible() && frame1.isVisible());
		JTextField entry1 = (JTextField)uicontroller.getView1().widgets.get(
		    "entry1");
		assertEquals("entry1.getText() != ''", "", entry1.getText());
    }
    @Test
    public void test_onDialog1Close() {
        JDialog dialog1 = (JDialog)uicontroller.getView1().widgets.get(
		    "dialog1");
		dialog1.setVisible(true);
		
		dialog1.dispose();
		refreshUI(delaymsecs);
		assertTrue("dialog1 visible", !dialog1.isVisible());
    }
    @Test
    public void test_onEntry1Action() {
        JTextField entry1 = (JTextField)uicontroller.getView1().widgets.get(
            "entry1");
        JDialog dialog1 = (JDialog)uicontroller.getView1().widgets.get(
		    "dialog1");
		JTextArea textview1 = (JTextArea)uicontroller.getView1().widgets.get(
            "textview1");
        dialog1.setVisible(true);
        entry1.setText("John Doe");
        
        entry1.postActionEvent();
        refreshUI(delaymsecs);
        /*//for (Observer obs : this.uicontroller.getModel().observers) {
        for (java.beans.PropertyChangeListener obs : this.uicontroller.getModel().observers)
            assertEquals("textview1.getText() != obs[n].getData()",
                ((Observer)obs).getData(),
                this.uicontroller.getView1().getData());*/
        assertEquals("textview1.getText() != view1.getData()",
            textview1.getText(), this.uicontroller.getView1().getData());
        assertTrue("dialog1 visible", !dialog1.isVisible());
    }
}
