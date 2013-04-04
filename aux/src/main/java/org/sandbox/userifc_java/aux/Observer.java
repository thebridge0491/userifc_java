package org.sandbox.userifc_java.aux;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeEvent;

/** DocComment:
 * <p>Brief description.</p> */
public class Observer implements PropertyChangeListener {
    /** Constructor.
      */
    public Observer() {
        
    }
    
    /** Accessor for data.
     * @return data */
    public String getData() { return this.data;
    }
    
    /** Mutator for data. */
    public void setData(String newData) { this.data = newData;
    }
    
    private boolean equalObjects(Observer other) {
        return this.data.equals(other.data);
    }
    @Override
    public boolean equals(Object obj) {
        if (obj == this)
            return true;
        if (null == obj || getClass() != obj.getClass())
            return false;
        return equalObjects((Observer)obj);
    }
    
    @Override
    public int hashCode() {
        final int primeMul = 31;
        int primeAdd = 17;
        return primeMul * (
            primeAdd + ((null != this.data) ? this.data.hashCode() : 0));
    }
    
    @Override
    public String toString() {
        return String.format("%s{data=%s}", 
            this.getClass().getSimpleName(), this.data);
    }
    
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        this.data = (String)evt.getNewValue();
    }
    
    //@Override
    public void update(Object src, Object arg) {
        this.propertyChange(new PropertyChangeEvent(src, "data", 
            this.data, (String)arg));
    }
    
    private String data;
}
