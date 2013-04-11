package org.sandbox.userifc_java.swing;

import java.beans.PropertyChangeSupport;
import java.beans.PropertyChangeListener;

//import org.sandbox.intro_java.util.Library;
import org.sandbox.userifc_java.aux.Subject;
import org.sandbox.userifc_java.aux.Observer;

/** DocComment:
 * <p>Brief description.</p> */
public class HelloModel extends Subject {
    /** Constructor.
     * @param greetFile - greet filepath
     * @param rsrc_path - path of resources */
    public HelloModel(String greetFile, String rsrc_path) {
        //this.observers = new java.util.HashSet<Observer>();
        this.observers = this.pcs.getPropertyChangeListeners();
        java.io.InputStream istrm = null;
        
        try {
            istrm = new java.io.FileInputStream(new java.io.File(rsrc_path +
                "/" + greetFile));
            //istrm = this.getClass().getClassLoader().getResourceAsStream(
            //    rsrc_path + "/" + greetFile);
            //istrm = this.getClass().getResourceAsStream(
            //  rsrc_path + "/" + greetFile);
        } catch (java.io.IOException exc0) {
            System.out.format("(exc: %s) Bad env var RSRC_PATH: %s\n",
                exc0, rsrc_path);
            try {
                istrm = HelloModel.class.getResourceAsStream("/" + greetFile);
            } catch (Exception exc1) {
                exc0.printStackTrace();
                exc1.printStackTrace();
            }
        }
        try {
            assert(null != istrm);
            java.io.BufferedReader fileBuffer = new java.io.BufferedReader(
                new java.io.InputStreamReader(istrm));
            hellopfx = fileBuffer.readLine();
            istrm.close();
        } catch (Exception exc) {
            exc.printStackTrace();
        }
    }
    public HelloModel() { this("greet.txt", "src/main/resources");
    }
    public HelloModel(String greetFile) {
        this(greetFile, "src/main/resources");
    }
    @Override
    public void addObserver(Observer obs) {
        //this.observers.add((Observer)obs);
        this.pcs.addPropertyChangeListener("data", obs);
    }
    
    @Override
    public void deleteObserver(Observer obs) {
        //this.observers.remove((Observer)obs);
        this.pcs.removePropertyChangeListener("data", obs);
    }
    
    @Override
    public void notifyObservers(Object arg) {
        /*this.setChanged();
        for (Observer obs : this.observers)
            obs.update(this, String.format("%s%s!", this.hellopfx,
                (null == arg) ? "" : (String)arg));
        this.clearChanged();*/
        String oldData = (1 > this.observers.length) ? "" : 
            ((Observer)this.observers[0]).getData();
        this.pcs.firePropertyChange("data", oldData,
            String.format("%s%s!", this.hellopfx,
            (null == arg) ? "" : (String)arg));
    }
    
    public static void main(String[] args) {
        HelloModel model = new HelloModel("greet.txt");
        HelloFormView view1 = new HelloFormView();

        model.addObserver(view1);
        model.notifyObservers("To be set -- HELP.");
        System.out.format("view1.getData(): %s\n", view1.getData());
    }
    
    private final PropertyChangeSupport pcs = new PropertyChangeSupport(this);
    //java.util.HashSet<Observer> observers;
    PropertyChangeListener[] observers;
    private String hellopfx;
}
