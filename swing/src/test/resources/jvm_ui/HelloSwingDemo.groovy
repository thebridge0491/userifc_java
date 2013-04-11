#!/bin/sh
/*
exec groovy -cp src $0 $@
exit
*/
//#!/usr/bin/env groovy

import javax.swing.*
import java.awt.event.*

import org.sandbox.uiform_swing.HelloFormSwing

/**Brief description. <p>
* Copyleft notice. Use & modify freely. Original author rights retained.
* @version $Id:$ */
class HelloSwingDemo { 
  Map<String, java.awt.Component> widgets = [:]
     
  public HelloSwingDemo() {
    /*widgets << ["frame1": new JFrame(), "vbox2": new JPanel(),
      "vbox1": new JPanel(new java.awt.GridLayout(3, 1)),
      "label1": new JLabel("label1"), "button1": new JButton("button1"),
      "textview1": new JTextArea(), "entry1": new JTextField(15)]
    this.widgets["dialog1"] = new JDialog(this.widgets["frame1"])
    this.widgets["vbox2"].setLayout new BoxLayout(this.widgets["vbox2"], 
      BoxLayout.Y_AXIS)
    for (o in [this.widgets["label1"], this.widgets["button1"], 
        this.widgets["textview1"]])
      this.widgets["vbox1"].add o
    this.widgets["vbox2"].add this.widgets["entry1"]
    this.widgets["frame1"].getContentPane().add this.widgets["vbox1"]
    this.widgets["dialog1"].getContentPane().add this.widgets["vbox2"]
    this.widgets["frame1"].pack()
    this.widgets["dialog1"].pack()
    this.widgets["frame1"].setSize new java.awt.Dimension(200, 160)
    this.widgets["dialog1"].setSize new java.awt.Dimension(200, 64)
    this.widgets["frame1"].setLocation(200, 300)
    this.widgets["dialog1"].setLocation(200, 300)
    */
    //def rsrc_path = System.env.getOrDefault("RSRC_PATH",
    //  System.props.getOrDefault("rsrcPath", "src/main/resources"))
    def view = new HelloFormSwing()
    widgets << ["frame1": view, "label1": view.getjLabel1(),
      "button1": view.getjButton1(), "textview1": view.getjTextView1(), 
      "dialog1": view.getjDialog1(), "entry1": view.getjEntry1()]
    
    this.widgets["frame1"].setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE)
    //this.widgets["dialog1"].addWindowListener(new Dialog1Adapter())
    //this.widgets["button1"].addActionListener(new Button1Listener())
    //this.widgets["entry1"].addActionListener(new Entry1Listener())
    this.widgets["dialog1"].addWindowListener([windowClosing: { evt -> 
      onDialog1Close(evt) }] as WindowAdapter)
    this.widgets["button1"].addActionListener({ evt -> onButton1Action(evt) })
    this.widgets["entry1"].addActionListener({ evt -> onEntry1Action(evt) })
    
    this.widgets["frame1"].setVisible true
  }
  
  void onDialog1Close(WindowEvent evt) {
    //this.widgets["frame1"].setVisible true
    System.exit 0
  }
  
  void onButton1Action(ActionEvent evt) {
    this.widgets["frame1"].setVisible true
    this.widgets["entry1"].setText ""
    this.widgets["dialog1"].setVisible true
    this.widgets["textview1"].setVisible true
  }
  
  void onEntry1Action(ActionEvent evt) {
    this.widgets["textview1"].setText("Hello, " + 
      this.widgets["entry1"].getText() + ".")
    this.widgets["dialog1"].setVisible false
    this.widgets["frame1"].setVisible true
  }
  
  /*class Dialog1Adapter extends WindowAdapter {
    @Override
    public void windowClosing(WindowEvent evt) { onDialog1Close(evt) }
  }
  
  class Button1Listener implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent evt) { onButton1Action(evt) }
  }
  
  class Entry1Listener implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent evt) { onEntry1Action(evt) }
  }*/
  
  public static class App implements Runnable {
    String pretext
    
    public App(String pretext) {
      this.pretext = pretext
    }
    
    @Override
    public void run() {
      def gui = new HelloSwingDemo()
      gui.widgets["textview1"].setText this.pretext
    }
  }
  
  static void main(String[] args) {        
    def pretext = String.format("(Groovy %s) Java %s GUI\n", 
      GroovySystem.version, System.getProperty("java.version"))
    //java.awt.EventQueue.invokeLater(new App(pretext))
    def gui = new HelloSwingDemo()
    gui.widgets["textview1"].setText pretext
  }
}


// (initial): mvn [-f uiform_swing/pom.xml] [-Dmaven.test.skip=true] package install

// groovy -cp uiform_swing.jar HelloSwingDemo.groovy
// or
// java -cp <path>/groovy.jar:uiform_swing.jar groovy.ui.GroovyMain HelloSwingDemo.groovy
// or
// groovyc -d classes -cp uiform_swing.jar HelloSwingDemo.groovy
// java -cp <path>/groovy.jar:uiform_swing.jar:classes HelloSwingDemo
