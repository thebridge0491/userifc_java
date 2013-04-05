package org.sandbox.userifc_java.gtk;

import org.junit.Test;
import static org.junit.Assert.*;

import org.gnome.gtk.Gtk;
import org.gnome.gtk.Button;
import org.gnome.gtk.TextView;
import org.gnome.gtk.Dialog;
import org.gnome.gtk.Entry;
import org.gnome.gtk.ResponseType;

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
		while (Gtk.eventsPending())
			//(new org.gnome.gtk.Application("Test.app")).run(null);
			Gtk.mainIterationDo(false);
		try {
			Thread.sleep(delayMsecs);
		} catch (InterruptedException exc) {
			exc.printStackTrace();
		}
	}

    @org.junit.BeforeClass
    public static void setUpClass() throws Exception {
    	Gtk.init(null);
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
    public void test_button1Clicked() {
		((Button)this.uicontroller.getView1().widgets.get(
		    "button1")).emitClicked();
		refreshUI(delaymsecs);
		Dialog dialog1 = (Dialog)uicontroller.getView1().widgets.get(
		    "dialog1");
		TextView textview1 = (TextView)uicontroller.getView1().widgets.get(
		    "textview1");
		Entry entry1 = (Entry)uicontroller.getView1().widgets.get("entry1");
		assertTrue("dialog1 and textview1 not visible",
		    dialog1.getWindow().isViewable() && 
		    textview1.getWindow().isViewable());
		assertEquals("entry1.text != ''", "", entry1.getText());
    }
    @Test
    public void test_dialog1Response() {
        Dialog dialog1 = (Dialog)uicontroller.getView1().widgets.get(
            "dialog1");
        dialog1.show();
        
        dialog1.emitResponse(ResponseType.OK);
        refreshUI(delaymsecs);
        assertTrue("dialog1 visible", !dialog1.getWindow().isViewable());
    }
    @Test
    public void test_entry1Activate() {
        Dialog dialog1 = (Dialog)uicontroller.getView1().widgets.get(
            "dialog1");
        Entry entry1 = (Entry)uicontroller.getView1().widgets.get("entry1");
        dialog1.show();
        entry1.setText("John Doe");
        
        entry1.activate();
        refreshUI(delaymsecs);
        TextView textview1 = (TextView)uicontroller.getView1().widgets.get(
            "textview1");
        /*for (Observer obs : this.uicontroller.getModel().observers)
            assertEquals("textview1.buffer != obs[n].data",
                obs.getData(), this.uicontroller.getView1().getData());*/
        assertEquals("textview1.buffer != view1.data",
            textview1.getBuffer().getText(),
            this.uicontroller.getView1().getData());
        assertTrue("dialog1 visible", !dialog1.getWindow().isViewable());
    }
}
