package org.sandbox.userifc_java.swing;

import javax.swing.*;

import java.beans.PropertyChangeEvent;

//import org.sandbox.intro_java.util.Library;
import org.sandbox.userifc_java.aux.Observer;

/** DocComment:
 * <p>Brief description.</p> */
public class HelloView extends Observer {
    /** Constructor.
     * @param rsrc_path - path of resources */
    public HelloView(String rsrc_path) {
        this.widgets = new java.util.HashMap<String, java.awt.Component>();
        this.setData("");
        
        this.widgets.put("frame1", new JFrame());
        this.widgets.put("vbox2", new JPanel());
        this.widgets.put("vbox1", new JPanel(new java.awt.GridLayout(3, 1)));
        this.widgets.put("label1", new JLabel("label1"));
        this.widgets.put("button1", new JButton("button1"));
        this.widgets.put("textview1", new JTextArea());
        this.widgets.put("entry1", new JTextField(15));
        this.widgets.put("dialog1", new JDialog(
            (JFrame)this.widgets.get("frame1")));
        
        ((JPanel)this.widgets.get("vbox1")).add(this.widgets.get("label1"));
        ((JPanel)this.widgets.get("vbox1")).add(this.widgets.get("button1"));
        ((JPanel)this.widgets.get("vbox1")).add(this.widgets.get("textview1"));
        ((JPanel)this.widgets.get("vbox2")).add(this.widgets.get("entry1"));
        ((JFrame)this.widgets.get("frame1")).add(this.widgets.get("vbox1"));
        ((JDialog)this.widgets.get("dialog1")).add(this.widgets.get("vbox2"));
        ((JFrame)this.widgets.get("frame1")).pack();
        ((JDialog)this.widgets.get("dialog1")).pack();
        ((JFrame)this.widgets.get("frame1")).setSize(
            new java.awt.Dimension(200, 160));
        ((JDialog)this.widgets.get("dialog1")).setSize(
            new java.awt.Dimension(200, 64));
        ((JFrame)this.widgets.get("frame1")).setLocation(200, 300);
        ((JDialog)this.widgets.get("dialog1")).setLocation(200, 300);
    }
    public HelloView() { this("src/main/resources");
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
