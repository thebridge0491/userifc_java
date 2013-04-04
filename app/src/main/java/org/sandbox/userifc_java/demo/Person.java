package org.sandbox.userifc_java.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/** DocComment:
 * <p>Brief description.</p> */
public class Person {
	private static final Logger pracLogger = LoggerFactory.getLogger("prac");
	
    /** Constructor.
     * @param name - name of the person
     * @param age - age of the person */
    public Person(String name, int age) {
        pracLogger.info("Person()");
        this.name = name;
        this.age = age;
    }
    public Person() { this("ToDo", 10);
    }
    
    /** Accessor for name.
     * @return name of the user */
    public String getName() { return this.name;
    }
    public int getAge() { return this.age;
    }
    
    /** Mutator for name.
     * @param name - new name */
    public void setName(String name) { this.name = name;
    }
    public void setAge(int age) { this.age = age;
    }
    
    private boolean equalObjects(Person other) {
        boolean isEqual = true;
        
        //isEqual &= Double.doubleToLongBits(this.dblNum) == 
        //        Double.doubleToLongBits(other.dblNum);
        isEqual &= (null != this.name) ? this.name.equals(other.name) : 
                null == other.name;
        isEqual &= this.age == other.age;
        //isEqual &= Arrays.equals(this.intArr, other.intArr);
        
        return isEqual;
    }
    @Override
    public boolean equals(Object obj) {
        if (obj == this)
            return true;
        if (null == obj || getClass() != obj.getClass())
            return false;
        return equalObjects((Person)obj);
    }
    
    @Override
    public int hashCode() {
        final int prime = 31;
        int hash = 17;
        //long d_Bits = Double.doubleToLongBits(this.dblNum);
        
        //hash = prime * hash + (int)(d_Bits ^ (d_Bits >>> 32));
        hash = prime * hash + ((null != this.name) ? this.name.hashCode() : 0);
        hash = prime * hash + this.age;
        //hash = prime * hash + Arrays.hashCode(this.intArr);
        
        return hash;
    }
    
    @Override
    public String toString() {
        return String.format("%s{name=%s, age=%d}", 
        	this.getClass().getSimpleName(), this.name, this.age);
    }
    
    private String name;
    private int age;
}
