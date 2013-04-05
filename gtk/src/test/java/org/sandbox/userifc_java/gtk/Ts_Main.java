package org.sandbox.userifc_java.gtk;

@org.junit.runner.RunWith(org.junit.runners.Suite.class)
@org.junit.runners.Suite.SuiteClasses({ViewControllerTest.class})
public class Ts_Main {
    public static void main(String[] args) {
        if (1 > args.length)
            org.junit.runner.JUnitCore.main(Ts_Main.class.getName());
        else {
            for (String s : args)
                try {
                    Class<?> cls = Class.forName(s);
                } catch (ClassNotFoundException exc) {
                    System.err.println(exc); //exc.printStackTrace();
                    System.exit(1);
                }
            org.junit.runner.JUnitCore.main(args);
        }
    }
}
