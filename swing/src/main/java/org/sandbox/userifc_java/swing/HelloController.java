package org.sandbox.userifc_java.swing;

import javax.swing.*;
import java.awt.event.*;

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
        
        /*((JFrame)this.view1.widgets.get("frame1")).setDefaultCloseOperation(
            JFrame.EXIT_ON_CLOSE);
        ((JDialog)this.view1.widgets.get("dialog1")).addWindowListener(
          new Dialog1Adapter());
        ((JButton)this.view1.widgets.get("button1")).addActionListener(
            new Button1Listener());
        ((JTextField)this.view1.widgets.get("entry1")).addActionListener(
            new Entry1Listener());*/
        ((JDialog)this.view1.widgets.get("dialog1")).addWindowListener(
                new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent evt) {
                onDialog1Close(evt); }
        });
        ((JButton)this.view1.widgets.get("button1")).addActionListener(
                new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                onButton1Action(evt); }
        });
        ((JTextField)this.view1.widgets.get("entry1")).addActionListener(
                new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                onEntry1Action(evt); }
        });
        
        this.model.addObserver(this.view1);
        ((JFrame)this.view1.widgets.get("frame1")).setVisible(true);
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
    
    void onDialog1Close(WindowEvent evt) {
        //((JFrame)view1.widgets.get("frame1")).setVisible(true);
        System.exit(0);
    }
    
    void onButton1Action(ActionEvent evt) {
        ((JFrame)view1.widgets.get("frame1")).setVisible(true);
        ((JTextField)view1.widgets.get("entry1")).setText("");
        ((JDialog)view1.widgets.get("dialog1")).setVisible(true);
        ((JTextArea)view1.widgets.get("textview1")).setVisible(true);
    }
    
    void onEntry1Action(ActionEvent evt) {
        model.notifyObservers(((JTextField)view1.widgets.get("entry1")
            ).getText());
        ((JDialog)view1.widgets.get("dialog1")).setVisible(false);
        ((JFrame)view1.widgets.get("frame1")).setVisible(true);
    }
    
    /*class Dialog1Adapter extends WindowAdapter {
        @Override
        public void windowClosing(WindowEvent evt) { onDialog1Close(evt); }
    }

    class Button1Listener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent evt) { onButton1Action(evt); }
    }

    class Entry1Listener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent evt) { onEntry1Action(evt); }
    }*/
    
    public static class App implements Runnable {
        public App(String greetFile, String rsrc_path, String pretext) {
            this.greetFile = greetFile;
            this.rsrc_path = rsrc_path;
            this.pretext = pretext;
        }
        
        @Override
        public void run() {
            HelloController ctrlr = new HelloController(this.greetFile,
                this.rsrc_path);
            ((JTextArea)ctrlr.getView1().widgets.get("textview1")).setText(
                this.pretext);
        }
        
        String greetFile;
        String rsrc_path;
        String pretext;
    }
    
    private HelloModel model;
    private HelloFormView view1;
}
