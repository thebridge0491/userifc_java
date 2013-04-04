package org.sandbox.userifc_java.aux;

import java.beans.PropertyChangeSupport;
import java.beans.PropertyChangeListener;

/** DocComment:
 * <p>Brief description.</p> */
public class Subject {
    /** Constructor.
     */
    public Subject() {
        this.observers = this.pcs.getPropertyChangeListeners();
    }
    
    private boolean equalObjects(Subject other) {
        return this.observers.equals(other.observers);
    }
    @Override
    public boolean equals(Object obj) {
        if (obj == this)
            return true;
        if (null == obj || getClass() != obj.getClass())
            return false;
        return equalObjects((Subject)obj);
    }
    
    @Override
    public int hashCode() {
        final int primeMul = 31;
        int primeAdd = 17;
        //long d_Bits = Double.doubleToLongBits(this.dblNum);
        return primeMul * (
            primeAdd + ((null != this.observers) ? 
                this.observers.hashCode() : 0));
    }
    
    @Override
    public String toString() {
        return String.format("%s{}", this.getClass().getSimpleName());
    }
    
    public void addObserver(Observer obs) {
        this.pcs.addPropertyChangeListener("data", obs);
    }
    
    public void deleteObserver(Observer obs) {
        this.pcs.removePropertyChangeListener("data", obs);
    }
    
    public void notifyObservers(Object arg) {
        String oldData = (1 > this.observers.length) ? "" : 
            ((Observer)this.observers[0]).getData();
        this.pcs.firePropertyChange("data", oldData,
            (null == arg) ? "" : (String)arg);
    }
    
    public static void main(String[] args) {
        Subject subj = new Subject();
        Observer obs = new Observer();

        subj.addObserver(obs);
        subj.notifyObservers("To be set -- HELP.");
        System.out.format("obs.getData(): %s\n", obs.getData());
    }
    
    private final PropertyChangeSupport pcs = new PropertyChangeSupport(this);
    private PropertyChangeListener[] observers;
}
