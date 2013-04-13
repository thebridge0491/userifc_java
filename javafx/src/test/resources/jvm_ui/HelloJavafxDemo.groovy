#!/bin/sh
/*
exec groovy -cp src $0 $@
exit
*/
//#!/usr/bin/env groovy

//import javafx.embed.swing.JFXPanel
//_ = new JFXPanel() // ? prevent JavaFX Toolkit not initialized exception
import javafx.geometry.Pos
import javafx.scene.layout.StackPane
import javafx.scene.layout.Pane
import javafx.scene.layout.VBox
import javafx.scene.control.Label
import javafx.scene.control.Button
import javafx.scene.control.TextArea
import javafx.scene.control.DialogPane
import javafx.scene.control.TextField
import javafx.fxml.FXMLLoader
import javafx.scene.Parent
import javafx.scene.Scene
import javafx.application.Application
import javafx.stage.Stage
import javafx.stage.WindowEvent
import javafx.event.EventHandler
import javafx.event.ActionEvent

/**Brief description. <p>
* Copyleft notice. Use & modify freely. Original author rights retained.
* @version $Id:$ */
class HelloJavafxDemo { 
  Map<String, javafx.scene.layout.Region> widgets = [:]
  Parent parent
  Stage stage
    
  public HelloJavafxDemo(Stage stageX) {
    stage = stageX
    
    /*parent = new StackPane() //(id="stackpane1", alignment=Pos.CENTER)
    parent.setId "stackpane1" ; parent.setAlignment Pos.CENTER
    widgets << ["parent": parent, "pane1": new Pane(),
      "vbox1": new VBox(), "label1": new Label("label1"),
      "button1": new Button("button1"), "textview1": new TextArea(),
      "entry1": new TextField("Enter name")]
    widgets["pane1"].setId "pane1" ; widgets["vbox1"].setId "vbox1"
    widgets["label1"].setId "label1" ; widgets["button1"].setId "button1"
    widgets["textview1"].setId "textview1" ; widgets["entry1"].setId "entry1"
    widgets["vbox1"].setAlignment Pos.CENTER
    widgets["label1"].setAlignment Pos.CENTER
    widgets["button1"].setAlignment Pos.CENTER
    widgets["dialog1"] = new DialogPane() //(id="dialog1", 
    //  headerText="dialog1", content=widgets["entry1"])
    widgets["dialog1"].setId "dialog1"
    widgets["dialog1"].setHeaderText "dialog1"
    widgets["dialog1"].setContent widgets["entry1"]
    //widgets["vbox1"].children.add(widgets["label1"])
    widgets["vbox1"].children.addAll(widgets["label1"], 
      widgets["button1"], widgets["textview1"])
    widgets["pane1"].children.add(widgets["vbox1"])
    widgets["parent"].children.addAll(widgets["pane1"], 
      widgets["dialog1"])
    widgets["dialog1"].setVisible false
    widgets["parent"].setPrefSize(202.0, 208.0)
    widgets["pane1"].setPrefSize(198.0, 205.0)
    widgets["vbox1"].setPrefSize(195.0, 205.0)
    widgets["label1"].setPrefSize(174.0, 38.0)
    widgets["button1"].setPrefSize(174.0, 48.0)
    widgets["textview1"].setPrefSize(174.0, 107.0)
    */
    def uiform = "jvm_ui/helloForm-javafx.fxml"
    def rsrc_path = System.env.getOrDefault("RSRC_PATH",
      System.props.getOrDefault("rsrcPath", "src/main/resources"))
    //parent = FXMLLoader.load(new URL("file:" + rsrc_path + "/" + uiform))
    def fxmlloader = new FXMLLoader()
    parent = fxmlloader.load(new FileInputStream(rsrc_path + "/" + uiform))
    widgets << ["parent": parent.lookup("#stackpane1"),
      "pane1": parent.lookup("#pane1"), "vbox1": parent.lookup("#vbox1"),
      "label1": parent.lookup("#label1"),
      "button1": parent.lookup("#button1"),
      "textview1": parent.lookup("#textview1"),
      "dialog1": parent.lookup("#dialog1"),
      "entry1": parent.lookup("#entry1")]
    
    //widgets["button1"].setOnAction(new Button1EventHandler())
    //widgets["entry1"].setOnAction(new Entry1EventHandler())
    widgets["button1"].setOnAction({ evt -> onButton1Action(evt) })
    widgets["entry1"].setOnAction({ evt -> onEntry1Action(evt) })
    
    //stage.setOnCloseRequest(new StageCloseHandler())
    stage.setOnCloseRequest({ evt -> onStageClose(evt) })
    stage.setTitle parent.getId()
    parent.setVisible true
  }
  
  void onStageClose(WindowEvent evt) {
    if (WindowEvent.WINDOW_CLOSE_REQUEST == evt.getEventType())
      //evt.getTarget().close()
      this.stage.close()
  }
  
  void onButton1Action(ActionEvent evt) {
    this.widgets["pane1"].setVisible false
    this.widgets["dialog1"].setVisible true
    this.widgets["entry1"].setText ""
  }
  
  void onEntry1Action(ActionEvent evt) {
    this.widgets["dialog1"].setVisible false
    this.widgets["pane1"].setVisible true
    this.widgets["textview1"].setText "Hello, " + 
      this.widgets["entry1"].getText() + "."
  }
  
  /*class StageCloseHandler implements EventHandler<WindowEvent> {
    @Override
    public void handle(WindowEvent evt) { onStageClose(evt) }
  }
  
  class Button1EventHandler implements EventHandler<ActionEvent> {
    @Override
    public void handle(ActionEvent evt) { onButton1Action(evt) }
  }
  
  class Entry1EventHandler implements EventHandler<ActionEvent> {
    @Override
    public void handle(ActionEvent evt) { onEntry1Action(evt) }
  }*/
  
  public static class App extends Application {
    @Override
    public void start(Stage stagePrime) {
      def params = this.getParameters()
      def param0 = params.getRaw().size() > 0 ? params.getRaw()[0] : ''
      
      def view = new HelloJavafxDemo(stagePrime)
      view.widgets["textview1"].setText params.getNamed()['pretext']
      stagePrime.setScene(new Scene(view.parent, 200, 160))
      stagePrime.show()
    }
  }
  
  static void main(String[] args) {        
    def pretext = String.format("(Groovy %s) Java %s JavaFX %s GUI\n", 
      GroovySystem.version, System.getProperty("java.version"),
      System.getProperty("javafx.version"))
    String[] params = ['--pretext=' + pretext]
    
    //App.launch(App.class, params)
    Application.launch(App.class, params)
  }
}


// groovy -cp <path>/jfxrt.jar HelloJavafxDemo.groovy
// or
// java -cp <path>/groovy.jar:<path>/jfxrt.jar groovy.ui.GroovyMain HelloJavafxDemo.groovy
// or
// groovyc -d classes -cp <path>/jfxrt.jar HelloJavafxDemo.groovy
// java -cp <path>/groovy.jar:<path>/jfxrt.jar:classes HelloJavafxDemo
