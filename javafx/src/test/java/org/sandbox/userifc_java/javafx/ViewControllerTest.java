package org.sandbox.userifc_java.javafx;

import org.junit.Test;
import static org.junit.Assert.*;

import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.DialogPane;
import javafx.scene.control.TextField;
import javafx.scene.Scene;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.application.Platform;
import javafx.stage.WindowEvent;
import javafx.event.ActionEvent;

import org.sandbox.intro_java.util.Library;
import org.sandbox.userifc_java.aux.Observer;

public class ViewControllerTest {
	//private float tolerance = 2.0f * Float.MIN_VALUE;
	private float epsilon = 1.0e-7f;
	private int delaymsecs = 2500;
	private static HelloController uicontroller = null;
	private static Stage stage = null;
	
    public ViewControllerTest() {
    }
    
	public void refreshUI(int delayMsecs) {
		try {
			Thread.sleep(delayMsecs);
		} catch (InterruptedException exc) {
			exc.printStackTrace();
		}
	}
    
    public static class JfxApp extends Application {
        @Override
        public void start(Stage stagePrime) {
            Application.Parameters params = this.getParameters();
            String param0 = params.getRaw().size() > 0 ?
                params.getRaw().get(0) : "";
            
            HelloController gui = new HelloController(stagePrime,
                params.getNamed().get("greetFile"),
                params.getNamed().get("rsrc_path"));
        	//((TextArea)gui.getView1().widgets.get("textview1")).setText(
        	//    ViewControllerTest.getClass().getPackage().getName());
        	ViewControllerTest.stage = stagePrime;
        	ViewControllerTest.uicontroller = gui;
            stagePrime.setScene(new Scene(gui.getView1().parent, 200, 160));
            stagePrime.show();
        }
    }
    
    public static class JfxThread extends Thread {
        public JfxThread(String greetFile, String rsrc_path) {
            this.greetFile = greetFile;
            this.rsrc_path = rsrc_path;
        }
        
        @Override
        public void run() {
            System.err.println("###Running JfxThread...###");
            String[] params = {"--greetFile=" + this.greetFile,
                "--rsrc_path=" + this.rsrc_path};
            
            //JfxApp.launch(JfxApp.class, params);
            Application.launch(JfxApp.class, params);
        }
        
        String greetFile;
        String rsrc_path;
    }

    @org.junit.BeforeClass
    public static void setUpClass() throws Exception {
    	String rsrc_path = null != System.getenv("RSRC_PATH") ? 
            System.getenv("RSRC_PATH") :
            System.getProperty("rsrcPath", "src/main/resources");
    	System.err.println("###setup TestCase###");
    	Thread threadJfx = new JfxThread("greet.txt", rsrc_path);
    	threadJfx.setDaemon(true);
    	threadJfx.start();
    	threadJfx.sleep(2500);
    	//refreshUI(2500);
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
        ((Button)this.uicontroller.getView1().widgets.get(
		    "button1")).fire();
        refreshUI(delaymsecs);
        DialogPane dialog1 = (DialogPane)uicontroller.getView1().widgets.get(
		    "dialog1");
		assertTrue("dialog1 not visible", dialog1.isVisible());
		TextField entry1 = (TextField)uicontroller.getView1().widgets.get(
		    "entry1");
		assertEquals("entry1.getText() != ''", "", entry1.getText());
    }
    @Test
    public void test_onStageWindowClosing() {
        Stage stageX = uicontroller.stage;
		//Platform.runLater(stageX.close);
		Platform.runLater(new Runnable() {
		    @Override
		    public void run() {
		        stageX.fireEvent(new WindowEvent(stageX, 
		            WindowEvent.WINDOW_CLOSE_REQUEST));
		    }
		});
		refreshUI(delaymsecs);
		assertTrue("stage showing", !stage.isShowing());
    }
    @Test
    public void test_onEntry1Action() {
        TextField entry1 = (TextField)uicontroller.getView1().widgets.get(
            "entry1");
        DialogPane dialog1 = (DialogPane)uicontroller.getView1().widgets.get(
		    "dialog1");
		TextArea textview1 = (TextArea)uicontroller.getView1().widgets.get(
            "textview1");
        dialog1.setVisible(true);
        entry1.setText("John Doe");
        
        entry1.fireEvent(new ActionEvent());
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
