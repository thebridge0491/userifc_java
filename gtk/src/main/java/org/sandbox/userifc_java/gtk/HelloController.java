package org.sandbox.userifc_java.gtk;

import org.gnome.gdk.Event;
import org.gnome.gtk.Gtk;
import org.gnome.gtk.Widget;
import org.gnome.gtk.Window;
import org.gnome.gtk.Button;
import org.gnome.gtk.TextView;
import org.gnome.gtk.Dialog;
import org.gnome.gtk.Entry;
import org.gnome.gtk.ResponseType;

//import org.sandbox.intro_java.util.Library;

/** DocComment:
 * <p>Brief description.</p> */
public class HelloController {
    /** Constructor.
     * @param greetFile - greet filepath
     * @param rsrc_path - path of resources */
    public HelloController(String greetFile, String rsrc_path) {
        this.model = new HelloModel(greetFile, rsrc_path);
        //this.view1 = new HelloView();
        this.view1 = new HelloFormView(rsrc_path);
        
        ((Window)this.view1.widgets.get("window1")).connect(new Window.DeleteEvent() {
		    public boolean onDeleteEvent(Widget source, Event event) {
			    Gtk.mainQuit();
			    return false;
		}});
		((Button)this.view1.widgets.get("button1")).connect(new Button.Clicked() {
		    public void onClicked(Button source) {
			    ((Entry)view1.widgets.get("entry1")).setText("");
			    view1.widgets.get("dialog1").showAll();
			    view1.widgets.get("textview1").show();
		}});
		((Dialog)this.view1.widgets.get("dialog1")).connect(new Window.DeleteEvent() {
		    public boolean onDeleteEvent(Widget source, Event event) {
			    Gtk.mainQuit();
			    return false;
		}});
		((Dialog)this.view1.widgets.get("dialog1")).connect(new Dialog.Response() {
		    public void onResponse(Dialog source, ResponseType responseId) {
			    view1.widgets.get("entry1").activate();
			    view1.widgets.get("dialog1").hide();
		}});
		((Entry)this.view1.widgets.get("entry1")).connect(new Entry.Activate() {
		    public void onActivate(Entry source) {
			    view1.widgets.get("dialog1").hide();
			    model.notifyObservers(((Entry)view1.widgets.get(
			        "entry1")).getText());
		}});
	    
	    this.model.addObserver(this.view1);
        ((Window)this.view1.widgets.get("window1")).setDefaultSize(200, 160);
		((Window)this.view1.widgets.get("window1")).setTitle("Hello_gui");
		((Dialog)this.view1.widgets.get("dialog1")).setDefaultSize(160, 100);
		((Dialog)this.view1.widgets.get("dialog1")).setTitle("dialog1");
		this.view1.widgets.get("dialog1").hide();
		this.view1.widgets.get("window1").showAll();
		//return this.view1.widgets.get("window1");
    }
    public HelloController() { this("greet.txt", "src/main/resources");
    }
    public HelloController(String greetFile) {
        this(greetFile, "src/main/resources");
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
        return (this.model.equals(other.model) || this.model == other.model) &&
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
            primeAdd + ((null != this.model) ? this.model.hashCode() : 0) +
            primeAdd + ((null != this.view1) ? this.view1.hashCode() : 0));
    }
    
    @Override
    public String toString() {
        return String.format("%s{}", this.getClass().getSimpleName());
    }
    
    private HelloModel model;
    private HelloFormView view1;
}
