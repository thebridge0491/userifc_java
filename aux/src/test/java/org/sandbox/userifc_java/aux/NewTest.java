package org.sandbox.userifc_java.aux;

import org.junit.Test;
import static org.junit.Assert.*;

import org.sandbox.intro_java.util.Library;
import org.sandbox.userifc_java.aux.*;

public class NewTest {
	//private float tolerance = 2.0f * Float.MIN_VALUE;
	private float epsilon = 1.0e-7f;
	
    public NewTest() {
    }

    @org.junit.BeforeClass
    public static void setUpClass() throws Exception {
    	System.err.println("###setup TestCase###");
    }
    @org.junit.AfterClass
    public static void tearDownClass() throws Exception {
    	System.err.println("###teardown TestCase###");
    }
    
    @org.junit.Before
    public void setUp() {
    	System.err.println("setup Test ...");
    }
    @org.junit.After
    public void tearDown() {
    	System.err.println("... teardown Test");
    }
	
    @Test
    public void test_classExists() {
        try {
            Class.forName(String.format("%s.Subject",
                this.getClass().getPackage().getName()));
        } catch(ClassNotFoundException exc) {
            //fail("Class(es) not existent: " + Subject.class.getName());
            fail(String.format("%s %s", "Class(es) not existent:",
                java.util.Arrays.toString(new String[]{
                Subject.class.getName()})));
        }
    }
    
	@Test
    public void test_method() { assertEquals(4, 2 * 2);
    }
    @Test
    public void test_dblMethod() {
		//assertEquals(100.001f, 100.001f, epsilon);
		assertTrue(Library.in_epsilon(epsilon, 100.001f, 100.001f));
    }
    @Test
    public void test_strMethod() { assertEquals("Hello", "Hello");
    }
    @Test(timeout = 1000)
    public void test_timeoutMethod() { for (int i = 0; 1.0e6f > i; ++i);
    }
    @org.junit.Ignore @Test
    public void test_ignoredMethod() { assertEquals(5, 2 * 2);
    }
    @Test //(expected = AssertionError.class)
    public void test_failMethod() { fail();
    }
    @Test(expected = IllegalArgumentException.class)
    public void test_exceptionMethod() {
        throw new IllegalArgumentException();
    }
    
    @Test
    public void test_observerMethod() {
        Subject subj = new Subject();
        Observer obs = new Observer();
        subj.addObserver(obs);
        String data = "To be set -- HELP.";
        subj.notifyObservers(data);
        
        assertEquals(data, obs.getData());
    }
}
