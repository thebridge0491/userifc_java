package org.sandbox.userifc_java.swing;

import javax.swing.*;

import java.beans.PropertyChangeEvent;

//import org.sandbox.intro_java.util.Library;
import org.sandbox.userifc_java.aux.Observer;
import org.sandbox.uiform_swing.HelloFormSwing;

/** DocComment:
 * <p>Brief description.</p> */
public class HelloFormView extends Observer {
    /** Constructor.
     * @param rsrc_path - path of resources */
    public HelloFormView(String rsrc_path) {
        this.widgets = new java.util.HashMap<String, java.awt.Component>();
        this.setData("");
        
        HelloFormSwing view = new HelloFormSwing();
        this.widgets.put("frame1", view);
        this.widgets.put("label1", view.getjLabel1());
        this.widgets.put("button1", view.getjButton1());
        this.widgets.put("textview1", view.getjTextView1());
        this.widgets.put("dialog1", view.getjDialog1());
        this.widgets.put("entry1", view.getjEntry1());
    }
    public HelloFormView() { this("src/main/resources");
    }
    
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        this.setData((String)evt.getNewValue());
        ((JTextArea)this.widgets.get("textview1")).setText(this.getData());
    }
    
    @Override
    //public void update(java.util.Observable src, Object arg) {
    public void update(Object src, Object arg) {
        /*this.setData((String)arg);
        ((JTextArea)this.widgets.get("textview1")).setText(this.getData());*/
        this.propertyChange(new PropertyChangeEvent(src, "data", 
            this.getData(), (String)arg));
    }
    
    public java.util.HashMap<String, java.awt.Component> widgets;
}
