package org.sandbox.userifc_java.javafx;

import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.DialogPane;
import javafx.scene.control.TextField;
import javafx.scene.Scene;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.event.EventHandler;
import javafx.event.ActionEvent;

//import org.sandbox.intro_java.util.Library;

/** DocComment:
 * <p>Brief description.</p> */
public class HelloController {
    /** Constructor.
     * @param stageX - JavaFX stage
     * @param greetFile - greet filepath
     * @param rsrc_path - path of resources */
    public HelloController(Stage stageX, String greetFile, String rsrc_path) {
        this.stage = stageX;
        this.model = new HelloModel(greetFile, rsrc_path);
        //this.view1 = new HelloView();
        this.view1 = new HelloFormView(rsrc_path);
        
        /*((Button)this.view1.widgets.get("button1")).setOnAction(
          new Button1EventHandler());
        ((TextField)this.view1.widgets.get("entry1")).setOnAction(
          new Entry1EventHandler());*/
        ((Button)this.view1.widgets.get("button1")).setOnAction(
                new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent evt) { onButton1Action(evt); }
        });
        ((TextField)this.view1.widgets.get("entry1")).setOnAction(
                new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent evt) { onEntry1Action(evt); }
        });
        
        //this.stage.setOnCloseRequest(new StageCloseHandler());
        this.stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent evt) { onStageClose(evt); }
        });
        
        this.model.addObserver(this.view1);
        this.stage.setTitle(this.view1.parent.getId());
        this.view1.parent.setVisible(true);
    }
    public HelloController(Stage stageX) {
        this(stageX, "greet.txt", "src/main/resources");
    }
    public HelloController(Stage stageX, String greetFile) {
        this(stageX, greetFile, "src/main/resources");
    }
    
    /** Accessor for model.
     * @return model of the controller */
    public HelloModel getModel() { return this.model;
    }
    public HelloFormView getView1() { return this.view1;
    }
    
    /** Mutator for model. */
    public void setModel(HelloModel modelX) { this.model = modelX;
    }
    public void setView1(HelloFormView viewX) { this.view1 = viewX;
    }
    
    private boolean equalObjects(HelloController other) {
        return (this.stage.equals(other.stage) || this.stage == other.stage) &&
            (this.model.equals(other.model) || this.model == other.model) &&
            (this.view1.equals(other.view1) || this.view1 == other.view1);
    }
    @Override
    public boolean equals(Object obj) {
        if (obj == this)
            return true;
        if (null == obj || getClass() != obj.getClass())
            return false;
        return equalObjects((HelloController)obj);
    }
    
    @Override
    public int hashCode() {
        final int primeMul = 31;
        int primeAdd = 17;
        //long d_Bits = Double.doubleToLongBits(this.dblNum);
        return primeMul * (
            primeAdd + ((null != this.stage) ? this.stage.hashCode() : 0) +
            primeAdd + ((null != this.model) ? this.model.hashCode() : 0) +
            primeAdd + ((null != this.view1) ? this.view1.hashCode() : 0));
    }
    
    @Override
    public String toString() {
        return String.format("%s{}", this.getClass().getSimpleName());
    }
    
    void onStageClose(WindowEvent evt) {
        if (WindowEvent.WINDOW_CLOSE_REQUEST == evt.getEventType())
          //evt.getTarget().close();
          stage.close();
    }
    
    void onButton1Action(ActionEvent evt) {
        view1.widgets.get("pane1").setVisible(false);
        view1.widgets.get("dialog1").setVisible(true);
        ((TextField)view1.widgets.get("entry1")).setText("");
    }
    
    void onEntry1Action(ActionEvent evt) {
        model.notifyObservers(((TextField)view1.widgets.get("entry1")
            ).getText());
        view1.widgets.get("dialog1").setVisible(false);
        view1.widgets.get("pane1").setVisible(true);
    }
    
    /*class StageCloseHandler implements EventHandler<WindowEvent> {
        @Override
        public void handle(WindowEvent evt) { onStageClose(evt); }
    }

    class Button1EventHandler implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent evt) { onButton1Action(evt); }
    }

    class Entry1EventHandler implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent evt) { onEntry1Action(evt); }
    }*/
    
    public static class JfxApp extends Application {
        @Override
        public void start(Stage stagePrime) {
            Application.Parameters params = this.getParameters();
            String param0 = params.getRaw().size() > 0 ?
                params.getRaw().get(0) : "";
            
            HelloController gui = new HelloController(stagePrime,
                params.getNamed().get("greetFile"),
                params.getNamed().get("rsrc_path"));
        	((TextArea)gui.getView1().widgets.get("textview1")).setText(
        	    params.getNamed().get("pretext"));
            stagePrime.setScene(new Scene(gui.getView1().parent, 200, 160));
            stagePrime.show();
        }
    }
    
    public static void main(String[] args) {
        String pretext = String.format("(Java %s) JavaFX %s GUI\n",
            System.getProperty("java.version"),
            System.getProperty("javafx.version"));
        String[] params = {"--greetFile=" + "greet.txt",
            "--rsrc_path=" + "src/main/resources", "--pretext=" + pretext};
        
        //JfxApp.launch(JfxApp.class, params);
        Application.launch(JfxApp.class, params);
    }
    
    public Stage stage;
    private HelloModel model;
    private HelloFormView view1;
}
