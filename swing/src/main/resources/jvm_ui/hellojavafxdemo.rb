#!/usr/bin/env jruby -w

=begin rdoc
Description:: Brief description
Version:: $Id:$
=end
require 'English'
#require 'jruby/core_ext'

#if !(defined? RUBY_ENGINE && 'jruby' == RUBY_ENGINE)
if !('java' == RUBY_PLATFORM)
  raise 'This package can only be used on JRuby.'
end

require 'java'
#VERSION_UIFORMSWING = ENV['VERSION_UIFORMSWING'] || '2013.04'
##HERE = File.join('.', File.dirname(__FILE__))
##require File.join(HERE, 'uiform_swing-%s.jar' % [VERSION_UIFORMSWING])
#require '%s/.m2/repository/org/sandbox/uiform_swing/%s/uiform_swing-%s.jar' % [ENV['HOME'] || '.', VERSION_UIFORMSWING, VERSION_UIFORMSWING]

java_import 'java.lang.System'
java_import 'java.io.FileInputStream'
java_import 'java.net.URL'

java_import 'javafx.embed.swing.JFXPanel'
_ = JFXPanel.new  # ? prevent JavaFX Toolkit not initialized exception
java_import 'javafx.geometry.Pos'
['StackPane', 'Pane', 'VBox'].each { |o| 
  java_import 'javafx.scene.layout.%s' % o }
['Label', 'Button', 'TextArea', 'DialogPane', 'TextField'].each { |o| 
  java_import 'javafx.scene.control.%s' % o }
java_import 'javafx.fxml.FXMLLoader'
java_import 'javafx.scene.Scene'
java_import 'javafx.application.Application'
java_import 'javafx.stage.WindowEvent'
java_import 'javafx.event.EventHandler'

class HelloJavafxDemo
  attr_reader :widgets, :parent, :stage
  
  def initialize(stageX)
    @stage, @widgets = stageX, {}
=begin
    @parent = StackPane.new #(id='stackpane1', alignment=Pos::CENTER)
    @parent.id = 'stackpane1' ; @parent.alignment = Pos::CENTER
    @widgets.merge!({'parent' => @parent, 'pane1' => Pane.new,
      'vbox1' => VBox.new, 'label1' => Label.new('label1'),
      'button1' => Button.new('button1'), 'textview1' => TextArea.new,
      'entry1' => TextField.new('Enter name')})
    @widgets['pane1'].id = 'pane1' ; @widgets['vbox1'].id = 'vbox1'
    @widgets['label1'].id = 'label1' ; @widgets['button1'].id = 'button1'
    @widgets['textview1'].id = 'textview1' ; @widgets['entry1'].id = 'entry1'
    @widgets['vbox1'].alignment = Pos::CENTER
    @widgets['label1'].alignment = Pos::CENTER
    @widgets['button1'].alignment = Pos::CENTER
    @widgets['dialog1'] = DialogPane.new #(id='dialog1', headerText='dialog1',
    #  content=@widgets['entry1'])
    @widgets['dialog1'].id = 'dialog1'
    @widgets['dialog1'].headerText = 'dialog1'
    @widgets['dialog1'].content = @widgets['entry1']
    #@widgets['vbox1'].children.add(@widgets['label1'])
    @widgets['vbox1'].children.addAll(@widgets['label1'], 
      @widgets['button1'], @widgets['textview1'])
    @widgets['pane1'].children.add(@widgets['vbox1'])
    @widgets['parent'].children.addAll(@widgets['pane1'], 
      @widgets['dialog1'])
    @widgets['dialog1'].visible = false
    @widgets['parent'].setPrefSize(202.0, 208.0)
    @widgets['pane1'].setPrefSize(198.0, 205.0)
    @widgets['vbox1'].setPrefSize(195.0, 205.0)
    @widgets['label1'].setPrefSize(174.0, 38.0)
    @widgets['button1'].setPrefSize(174.0, 48.0)
    @widgets['textview1'].setPrefSize(174.0, 107.0)
=end
    uiform = 'jvm_ui/helloForm-javafx.fxml'
    rsrc_path = ENV.fetch('RSRC_PATH', 'resources')
    #@parent = FXMLLoader.load(URL.new('file:' + rsrc_path + '/' + uiform))
    fxmlloader = FXMLLoader.new
    @parent = fxmlloader.load(FileInputStream.new(rsrc_path + '/' + uiform))
    @widgets.merge!({'parent' => @parent.lookup('#stackpane1'),
      'pane1' => @parent.lookup('#pane1'),
      'vbox1' => @parent.lookup('#vbox1'),
      'label1' => @parent.lookup('#label1'),
      'button1' => @parent.lookup('#button1'),
      'textview1' => @parent.lookup('#textview1'),
      'dialog1' => @parent.lookup('#dialog1'),
      'entry1' => @parent.lookup('#entry1')})
    
    #@widgets['button1'].setOnAction(Button1EventHandler.new)
    #@widgets['entry1'].setOnAction(Entry1EventHandler.new)
    @widgets['button1'].setOnAction { |event| onButton1Action(event) }
    @widgets['entry1'].setOnAction { |event| onEntry1Action(event) }
    
    #@stage.setOnCloseRequest(StageCloseHandler.new)
    @stage.setOnCloseRequest { |event| onStageClose(event) }
    @stage.setTitle(@parent.id)
    @parent.setVisible(true)
  end
  
  private
  
#  class StageCloseHandler
#    include EventHandler
#    # Window listener for stage
#    
#    def handle(event)
#      if WindowEvent::WINDOW_CLOSE_REQUEST == event.eventType
#        #event.target.close()
#        HelloJavafxDemo.stage.close()
#      end
#    end
#  end
  
#  class Button1EventHandler
#    include EventHandler
#    # Action listener for button1
#    def handle(event)
#      HelloJavafxDemo.widgets['frame1'].setVisible(false)
#      HelloJavafxDemo.widgets['dialog1'].setVisible(true)
#      HelloJavafxDemo.widgets['entry1'].text = ''
#    end
#  end

#  class Entry1EventHandler
#    include EventHandler
#    # Action listener for entry1
#    def handle(event)
#      HelloJavafxDemo.widgets['dialog1'].setVisible(false)
#      HelloJavafxDemo.widgets['frame1'].setVisible(true)
#      HelloJavafxDemo.widgets['textview1'].text = 'Hello, ' + \
#        HelloJavafxDemo.widgets['entry1'].text + '.'
#    end
#  end
  
  def onStageClose(event)
    # Window listener for stage
    
    if WindowEvent::WINDOW_CLOSE_REQUEST == event.eventType
      #event.target.close()
      @stage.close()
    end
  end
  
  def onButton1Action(event)
    # Action listener for button1
    
    @widgets['pane1'].setVisible(false)
    @widgets['dialog1'].setVisible(true)
    @widgets['entry1'].text = ""
  end
  
  def onEntry1Action(event)
    # Action listener for entry1
    
    @widgets['dialog1'].setVisible(false)
    @widgets['pane1'].setVisible(true)
    @widgets['textview1'].text = 'Hello, ' + @widgets['entry1'].text + '.'
  end
  
end # end class HelloJavafxDemo

class App < Application
  def start(stagePrime)
    params = getParameters()
    param0 = params.getRaw().length > 0 ? params.getRaw()[0] : ''
    
    view = HelloJavafxDemo.new(stagePrime)
    view.widgets['textview1'].text = params.getNamed()['pretext']
    stagePrime.setScene(Scene.new(view.parent, 200, 160))
    stagePrime.show()
  end
end # end class App

if $PROGRAM_NAME == __FILE__
  pretext = '(JRuby %s) Java %s JavaFX %s GUI' % [JRUBY_VERSION, 
    System.getProperty('java.version'), System.getProperty("javafx.version")]
  
  params = (['--pretext=' + pretext]).to_java :string
  #App.launch(App.become_java!, params)
  Application.launch(App.become_java!, params)
  
  exit 0
end


# jruby -J-cp <path>/jfxrt.jar hellojavafxdemo.rb
# or
# java -cp <path>/jruby-complete.jar:<path>/jfxrt.jar org.jruby.Main hellojavafxdemo.rb