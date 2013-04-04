package org.sandbox.userifc_java.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.Scanner;

/** DocComment:
 * <p>Brief description.</p> */
public final class Demo {
    private static final Logger pracLogger = LoggerFactory.getLogger("prac");
    
    public static String greeting(String rsrc_path, String greetFile,
            String name) {
        String buf = "HELP";
        java.io.InputStream istrm = null;  
        
        pracLogger.info("greeting()");
        
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
                istrm = Demo.class.getResourceAsStream("/" + greetFile);
            } catch (Exception exc1) {
                exc0.printStackTrace();
                exc1.printStackTrace();
            }
        }
        try {
            assert(null != istrm);
            java.io.BufferedReader fileBuffer = new java.io.BufferedReader(
                new java.io.InputStreamReader(istrm));
            buf = fileBuffer.readLine();
            istrm.close();
        } catch (Exception exc) {
            exc.printStackTrace();
        }
        //try {
        //    //istrm = new java.io.FileInputStream(new java.io.File(
        //    //    "src/main/resources/" + greetFile));
        //    //istrm = this.getClass().getClassLoader().getResourceAsStream(
        //    //    "src/main/resources/" + greetFile);
        //    //istrm = this.getClass().getResourceAsStream(
        //    //    "src/main/resources/" + greetFile);
        //    istrm = Demo.class.getResourceAsStream("/" + greetFile);
        //    //assert(null != istrm);
        //    java.io.BufferedReader fileBuffer = new java.io.BufferedReader(
        //        new java.io.InputStreamReader(istrm));
        //    buf = fileBuffer.readLine();
        //    istrm.close();
        //} catch (java.io.IOException exc) {
        //    exc.printStackTrace();
        //} catch (Exception exc) {
        //    exc.printStackTrace();
        //}
        return buf + name;
    }
    
    public static char delay_char(int msecs) {
        char ch = '\0';
        Scanner keyboard = null;
        
        //keyboard = new Scanner(System.in);
        if (null != System.console())
            keyboard = new Scanner(System.console().reader());
        while (true) {
            try {
                Thread.sleep(msecs);
            } catch (InterruptedException exc) {
                exc.printStackTrace();
            }
            if (null != keyboard) {
                System.out.println("Type any character when ready.");
                ch = keyboard.next().charAt(0);
                
                if ('\n' == ch || '\0' == ch)
                    continue;
            } else
                System.out.println("Console not available.");
            break;
        }
        return ch;
    }
    
    public static void main(String[] args) {
        char ch = delay_char(3000);
        System.out.format("delay_char(%d)\n", 3000);
    }
}
