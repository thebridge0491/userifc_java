package org.sandbox.userifc_java.javafx;

//import javafx.embed.swing.JFXPanel;
//_ = new JFXPanel() // ? prevent JavaFX Toolkit not initialized exception
import javafx.geometry.Pos;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.DialogPane;
import javafx.scene.control.TextField;
import javafx.scene.Parent;

import java.beans.PropertyChangeEvent;

//import org.sandbox.intro_java.util.Library;
import org.sandbox.userifc_java.aux.Observer;

/** DocComment:
 * <p>Brief description.</p> */
public class HelloView extends Observer {
    /** Constructor.
     * @param rsrc_path - path of resources */
    public HelloView(String rsrc_path) {
        this.widgets = new java.util.HashMap<String, javafx.scene.layout.Region>();
        this.setData("");
        
        this.parent = new StackPane();
        this.parent.setId("stackpane1");
        ((StackPane)this.parent).setAlignment(Pos.CENTER);
        this.widgets.put("parent", (StackPane)this.parent);
        this.widgets.put("pane1", new Pane());
        this.widgets.put("vbox1", new VBox());
        this.widgets.put("label1", new Label("label1"));
        this.widgets.put("button1", new Button("button1"));
        this.widgets.put("textview1", new TextArea());
        this.widgets.put("entry1", new TextField("Enter name"));
        this.widgets.put("dialog1", new DialogPane());
        
        this.widgets.get("pane1").setId("pane1");
        this.widgets.get("vbox1").setId("vbox1");
        this.widgets.get("label1").setId("label1");
        this.widgets.get("button1").setId("button1");
        this.widgets.get("textview1").setId("textview1");
        this.widgets.get("dialog1").setId("dialog1");
        ((DialogPane)this.widgets.get("dialog1")).setHeaderText("dialog1");
        this.widgets.get("entry1").setId("entry1");
        ((VBox)this.widgets.get("vbox1")).setAlignment(Pos.CENTER);
        ((Label)this.widgets.get("label1")).setAlignment(Pos.CENTER);
        ((Button)this.widgets.get("button1")).setAlignment(Pos.CENTER);
        ((DialogPane)this.widgets.get("dialog1")).setContent(
            this.widgets.get("entry1"));
        //((VBox)this.widgets.get("vbox1")).getChildren().add(
        //    this.widgets.get("label1"));
        ((VBox)this.widgets.get("vbox1")).getChildren().addAll(
            this.widgets.get("label1"), this.widgets.get("button1"),
            this.widgets.get("textview1"));
        ((Pane)this.widgets.get("pane1")).getChildren().add(
            (VBox)this.widgets.get("vbox1"));
        ((StackPane)this.widgets.get("parent")).getChildren().addAll(
            this.widgets.get("pane1"), this.widgets.get("dialog1"));
        this.widgets.get("dialog1").setVisible(false);
        this.widgets.get("parent").setPrefSize(202.0, 208.0);
        this.widgets.get("pane1").setPrefSize(198.0, 205.0);
        this.widgets.get("vbox1").setPrefSize(195.0, 205.0);
        this.widgets.get("label1").setPrefSize(174.0, 38.0);
        this.widgets.get("button1").setPrefSize(174.0, 48.0);
        this.widgets.get("textview1").setPrefSize(174.0, 107.0);
    }
    public HelloView() { this("src/main/resources");
    }
    
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        this.setData((String)evt.getNewValue());
        ((TextArea)this.widgets.get("textview1")).setText(this.getData());
    }
    
    @Override
    //public void update(java.util.Observable src, Object arg) {
    public void update(Object src, Object arg) {
        /*this.setData((String)arg);
        ((TextArea)this.widgets.get("textview1")).setText(this.getData());*/
        this.propertyChange(new PropertyChangeEvent(src, "data", 
            this.getData(), (String)arg));
    }
    
    public java.util.HashMap<String, javafx.scene.layout.Region> widgets;
    public Parent parent;
}
