package org.sandbox.userifc_java.gtk;

import org.gnome.gtk.Widget;
import org.gnome.gtk.Window;
import org.gnome.gtk.Frame;
import org.gnome.gtk.VBox;
import org.gnome.gtk.Button;
import org.gnome.gtk.Label;
import org.gnome.gtk.TextView;
import org.gnome.gtk.TextBuffer;
import org.gnome.gtk.Dialog;
import org.gnome.gtk.Entry;
import org.gnome.gtk.Builder;

import java.beans.PropertyChangeEvent;

//import org.sandbox.intro_java.util.Library;
import org.sandbox.userifc_java.aux.Observer;

/** DocComment:
 * <p>Brief description.</p> */
public class HelloView extends Observer {
    /** Constructor.
     * @param rsrc_path - path of resources */
    public HelloView(String rsrc_path) {
        //this.widgets = new java.util.HashMap<String, org.gnome.glib.Object>();
        this.widgets = new java.util.HashMap<String, Widget>();
        this.setData("");
        
        this.widgets.put("window1", new Window());
    	this.widgets.put("frame1", new Frame("frame1"));
    	this.widgets.put("vbox1", new VBox(false, 3));
    	this.widgets.put("label1", new Label("label1"));
    	this.widgets.put("button1", new Button("button1"));
    	this.widgets.put("textview1", new TextView(new TextBuffer()));
    	this.widgets.put("entry1", new Entry());
    	this.widgets.put("dialog1", new Dialog("Enter Name", 
    	    (Window)this.widgets.get("window1"), true));
    	
    	((VBox)this.widgets.get("vbox1")).add(this.widgets.get("label1"));
    	((VBox)this.widgets.get("vbox1")).add(this.widgets.get("button1"));
    	((VBox)this.widgets.get("vbox1")).add(this.widgets.get("textview1"));
    	((Window)this.widgets.get("window1")).add(this.widgets.get("vbox1"));
    	((Dialog)this.widgets.get("dialog1")).add(this.widgets.get(
    	    "entry1"));
    }
    public HelloView() { this("src/main/resources");
    }
    
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        this.setData((String)evt.getNewValue());
        ((TextView)this.widgets.get("textview1")).getBuffer().setText(
            this.getData());
    }
    
    @Override
    //public void update(java.util.Observable src, Object arg) {
    public void update(Object src, Object arg) {
        /*this.setData((String)arg);
        ((TextView)this.widgets.get("textview1")).getBuffer().setText(
            this.getData());*/
        this.propertyChange(new PropertyChangeEvent(src, "data", 
            this.getData(), (String)arg));
    }
    
    //public java.util.HashMap<String, org.gnome.glib.Object> widgets;
    public java.util.HashMap<String, Widget> widgets;
}
