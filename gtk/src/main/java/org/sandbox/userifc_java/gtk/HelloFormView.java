package org.sandbox.userifc_java.gtk;

import org.gnome.gtk.Widget;
import org.gnome.gtk.TextView;
import org.gnome.gtk.Builder;

import java.beans.PropertyChangeEvent;

//import org.sandbox.intro_java.util.Library;
import org.sandbox.userifc_java.aux.Observer;

/** DocComment:
 * <p>Brief description.</p> */
public class HelloFormView extends Observer {
    /** Constructor.
     * @param rsrc_path - path of resources */
    public HelloFormView(String rsrc_path) {
        //this.widgets = new java.util.HashMap<String, org.gnome.glib.Object>();
        this.widgets = new java.util.HashMap<String, Widget>();
        this.setData("");
        
        //String uiform = (2 == Gtk.Global.MajorVersion) ?
        //    "helloForm-gtk2.glade" : "helloForm-gtk3.glade";
        String uiform = "helloForm-gtk3.glade";
        java.io.InputStream uiStrm;
        try {
            uiStrm = new java.io.FileInputStream(rsrc_path + "/gtk/" + uiform);
        } catch (java.io.IOException exc0) {
            System.out.format("(exc: %s) Bad env var RSRC_PATH: %s\n",
                exc0, rsrc_path);
            
            uiStrm = HelloFormView.class.getResourceAsStream("/gtk/" + uiform);
        }
        String uistr = null;
        try (java.util.Scanner scanr = new java.util.Scanner(uiStrm,
                java.nio.charset.StandardCharsets.UTF_8.name())) {
            uistr = scanr.useDelimiter("\\A").next();
        }
        Builder builder = new Builder();
		try {
		    builder.addFromString(uistr);
		    //builder.addFromFile(rsrc_path + "/gtk/" + uiform);
		} catch (Exception exc) {
		    exc.printStackTrace();
		}
		String[] names = {"window1", "dialog1", "button1", "textview1",
			"entry1"};
		for (String name : names)
		    this.widgets.put(name, (Widget)builder.getObject(name));
    }
    public HelloFormView() { this("src/main/resources");
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
