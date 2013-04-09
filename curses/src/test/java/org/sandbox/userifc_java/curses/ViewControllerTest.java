package org.sandbox.userifc_java.curses;

import org.junit.Test;
import static org.junit.Assert.*;

import org.sandbox.wrapper_java.curses.CursesC;
import org.sandbox.wrapper_java.curses.SWIGTYPE_p_WINDOW;
import org.sandbox.wrapper_java.curses.SWIGTYPE_p_PANEL;

import org.sandbox.intro_java.util.Library;
import org.sandbox.userifc_java.aux.Observer;

public class ViewControllerTest {
	//private float tolerance = 2.0f * Float.MIN_VALUE;
	private float epsilon = 1.0e-7f;
	private int delaymsecs = 2500;
	private static HelloController uicontroller = null;
	
    public ViewControllerTest() {
    }
    
	public void refreshUI(HelloController ctrlr, int delayMsecs) {
		if (ctrlr.step_virtualscr())
		    //CursesC.update_panels();
		    CursesC.doupdate();
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
    public void test_keyEnterCb() {
		CursesC.ungetch((int)HelloView.Keys.KEY_ENTER.value);
		CursesC.top_panel(this.uicontroller.getView1().panels.get(
		    "panel_input"));
		/*
		//CursesC.mvwprintw(CursesC.panel_window(
		//    this.uicontroller.getView1().panels.get("panel_input")),
        //    1, 1, "xxxxxxx%sxxxxxxx", " Push Enter key ");
        CursesC.mvwaddstr(CursesC.panel_window(
            this.uicontroller.getView1().panels.get("panel_input")),
            1, 1, "xxxxxxx Push Enter key xxxxxxx");
        CursesC.wrefresh(CursesC.panel_window(
            this.uicontroller.getView1().panels.get("panel_input")));
        Thread.sleep(2000);
        */
        //refreshUI(this.uicontroller, delaymsecs);
        assertTrue("input panel not visible on ENTER key",
		    1 != CursesC.panel_hidden(
		    this.uicontroller.getView1().panels.get("panel_input")));
        String input = "John Doe";
        //CursesC.mvwprintw(CursesC.panel_window(
        //    this.uicontroller.getView1().panels.get("panel_input")),
        //    1, 1, "%s", input);
        CursesC.mvwaddstr(CursesC.panel_window(
            this.uicontroller.getView1().panels.get("panel_input")), 1, 1,
            input);
        this.uicontroller.getModel().notifyObservers(input);
        //java.util.regex.Pattern re = java.util.regex.Pattern.compile(
        ////  ".*John Doe.*", java.util.regex.Pattern.CASE_INSENSITIVE);
        //    "(?i).*John Doe.*");
        java.util.regex.Pattern re = java.util.regex.Pattern.compile(
            String.format(".*%s.*", input));
        
        /*//for (Observer obs : this.uicontroller.getModel().observers) {
        for (java.beans.PropertyChangeListener obs : this.uicontroller.getModel().observers) {
			java.util.regex.Matcher m = re.matcher(((Observer)obs).getData());
            assertTrue(String.format("'%s' not in obs[n].getData(%s)",
                input, ((Observer)obs).getData()), m.matches());
		}*/
        java.util.regex.Matcher m = re.matcher(
            this.uicontroller.getView1().getData());
        assertTrue(String.format("'%s' not in this.uicontroller.getView1().getData(%s)",
            re.pattern(), this.uicontroller.getView1().getData()), 
            m.matches());
    }
    @Test
    public void test_keyRunCb() {
        CursesC.ungetch((int)HelloView.Keys.KEY_RUN.value);
        refreshUI(this.uicontroller, delaymsecs);
        assertTrue("output panel not visible on RUN key",
		    1 != CursesC.panel_hidden(
		    this.uicontroller.getView1().panels.get("panel_output")));
    }
    @Test
    public void test_keyEscapeCb() {
        CursesC.ungetch((int)HelloView.Keys.KEY_ESC.value);
        assertTrue("step_virtualscr not False on ESC key",
		    !this.uicontroller.step_virtualscr());
    }
    @Test
    public void test_keyUnmappedCb() {
        CursesC.ungetch((int)'Z');
		refreshUI(this.uicontroller, delaymsecs);
		assertTrue("output panel not visible on unmapped key",
		    1 != CursesC.panel_hidden(
		    this.uicontroller.getView1().panels.get("panel_output")));
    }
}
