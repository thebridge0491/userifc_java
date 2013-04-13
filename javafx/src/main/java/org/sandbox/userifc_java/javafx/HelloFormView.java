package org.sandbox.userifc_java.javafx;

//import javafx.embed.swing.JFXPanel;
//_ = new JFXPanel() // ? prevent JavaFX Toolkit not initialized exception
import javafx.scene.layout.StackPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.DialogPane;
import javafx.scene.control.TextField;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

import java.beans.PropertyChangeEvent;

//import org.sandbox.intro_java.util.Library;
import org.sandbox.userifc_java.aux.Observer;

/** DocComment:
 * <p>Brief description.</p> */
public class HelloFormView extends Observer {
    /** Constructor.
     * @param rsrc_path - path of resources */
    public HelloFormView(String rsrc_path) {
        this.widgets = new java.util.HashMap<String, javafx.scene.layout.Region>();
        this.setData("");
        
        String uiform = "helloForm-javafx.fxml";
        try {
            //this.parent = FXMLLoader.load(
            //    new java.net.URL("file:" + rsrc_path + "/jvm_ui/" + uiform));
            FXMLLoader fxmlloader = new FXMLLoader();
            this.parent = fxmlloader.load(
                new java.io.FileInputStream(rsrc_path + "/jvm_ui/" + uiform));
        } catch (java.io.IOException exc0) {
            System.out.format("(exc: %s) Bad env var RSRC_PATH: %s\n",
                exc0, rsrc_path);
            
            try {
                //this.parent = FXMLLoader.load(
                //    new java.net.URL("file:" + "/jvm_ui/" + uiform));
                FXMLLoader fxmlloader = new FXMLLoader();
                //this.parent = fxmlloader.load(
                //    new java.io.FileInputStream("/jvm_ui/" + uiform));
                this.parent = fxmlloader.load(
                	HelloFormView.class.getResourceAsStream(
                	"/jvm_ui/" + uiform));
            } catch (java.io.IOException exc1) {
                exc1.printStackTrace();
            }
        }
        
        this.widgets.put("parent", 
            (StackPane)this.parent.lookup("#stackpane1"));
        this.widgets.put("pane1", (Pane)this.parent.lookup("#pane1"));
        this.widgets.put("vbox1", (VBox)this.parent.lookup("#vbox1"));
        this.widgets.put("label1", (Label)this.parent.lookup("#label1"));
        this.widgets.put("button1", (Button)this.parent.lookup("#button1"));
        this.widgets.put("textview1", 
            (TextArea)this.parent.lookup("#textview1"));
        this.widgets.put("dialog1", 
            (DialogPane)this.parent.lookup("#dialog1"));
        this.widgets.put("entry1", (TextField)this.parent.lookup("#entry1"));
    }
    public HelloFormView() { this("src/main/resources");
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
