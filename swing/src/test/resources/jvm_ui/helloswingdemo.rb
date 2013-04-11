#!/usr/bin/env jruby -w

=begin rdoc
Description:: Brief description
Version:: $Id:$
=end
require 'English'

#if !(defined? RUBY_ENGINE && 'jruby' == RUBY_ENGINE)
if !('java' == RUBY_PLATFORM)
  raise 'This package can only be used on JRuby.'
end

require 'java'
VERSION_UIFORMSWING = ENV['VERSION_UIFORMSWING'] || '2013.04'
#HERE = File.join('.', File.dirname(__FILE__))
#require File.join(HERE, 'uiform_swing-%s.jar' % [VERSION_UIFORMSWING])
require '%s/.m2/repository/org/sandbox/uiform_swing/%s/uiform_swing-%s.jar' % [ENV['HOME'] || '.', VERSION_UIFORMSWING, VERSION_UIFORMSWING]

java_import 'java.lang.System'
['JFrame', 'JPanel', 'JLabel', 'JButton', 'JTextArea', 'JTextField',
    'JDialog', 'BoxLayout'].each { |o| 
  java_import 'javax.swing.%s' % o }
java_import 'java.awt.GridLayout'
['WindowAdapter', 'ActionEvent', 'ActionListener'].each { |o| 
  java_import 'java.awt.event.%s' % o }

java_import 'org.sandbox.uiform_swing.HelloFormSwing'

class HelloSwingDemo
  attr_reader :widgets
  
  def initialize
    @widgets = {}
=begin    
    @widgets.merge!({'frame1' => JFrame.new,
      'vbox1' => JPanel.new(GridLayout.new(3, 1)), 'vbox2' => JPanel.new,
      'label1' => JLabel.new('label1'), 'button1' => JButton.new('button1'),
      'textview1' => JTextArea.new, 'entry1' => JTextField.new(15)})    
    @widgets['dialog1'] = JDialog.new(@widgets['frame1'], true)
    @widgets['vbox2'].setLayout(BoxLayout.new(@widgets['vbox2'], 
      BoxLayout::Y_AXIS))
    [@widgets['label1'], @widgets['button1'], @widgets['textview1']].each { 
      |o| @widgets['vbox1'].add(o) }      
    @widgets['vbox2'].add(@widgets['entry1'])
    @widgets['frame1'].getContentPane.add(@widgets['vbox1'])
    @widgets['dialog1'].getContentPane.add(@widgets['vbox2'])
    [@widgets['frame1'], @widgets['dialog1']].each { |o| o.pack }
    @widgets['frame1'].setSize 200, 160
    @widgets['dialog1'].setSize 200, 64
    @widgets['frame1'].setLocation 200, 300
    @widgets['dialog1'].setLocation 200, 300
=end    
    #rsrc_path = ENV.fetch('RSRC_PATH', 'resources')
    view = HelloFormSwing.new
    @widgets.merge!({'frame1' => view, 'label1' => view.getjLabel1,
      'button1' => view.getjButton1, 'textview1' => view.getjTextView1,
      'dialog1' => view.getjDialog1, 'entry1' => view.getjEntry1})
    
    @widgets['frame1'].defaultCloseOperation = JFrame::EXIT_ON_CLOSE
    @widgets['dialog1'].addWindowListener(Dialog1Listener.new)
    #@widgets['button1'].addActionListener { |a| Button1Listener(a) }
    #@widgets['entry1'].addActionListener { |a| Entry1Listener(a) }
    @widgets['button1'].addActionListener { |e| onButton1Action(e) }
    @widgets['entry1'].addActionListener { |e| onEntry1Action(e) }
    
    @widgets['frame1'].setVisible(true)
  end
  
  private
  
  class Dialog1Listener < WindowAdapter
    # Window adapter/listener for dialog1
    
    def windowClosing(event)
      System.exit 0
    end
  end
  
# class Button1Listener < ActionListener
#   # Action listener for button1
#   def actionPerformed(event)
#     HelloSwingDemo.widgets['frame1'].setVisible(false)
#     HelloSwingDemo.widgets['dialog1'].setVisible(true)
#     HelloSwingDemo.widgets['entry1'].text = ''
#   end
# end

# class Entry1Listener < ActionListener
#   # Action listener for entry1
#   def actionPerformed(event)
#     HelloSwingDemo.widgets['dialog1'].setVisible(false)
#     HelloSwingDemo.widgets['frame1'].setVisible(true)
#     HelloSwingDemo.widgets['textview1'].text = 'Hello, ' + \
#       HelloSwingDemo.widgets['entry1'].text + '.'
#   end
# end

  def onButton1Action(event)
    # Action listener for button1
    
    @widgets['frame1'].setVisible(false)
    @widgets['dialog1'].setVisible(true)
    @widgets['entry1'].text = ""
  end
  
  def onEntry1Action(event)
    # Action listener for entry1
    
    @widgets['dialog1'].setVisible(false)
    @widgets['frame1'].setVisible(true)
    @widgets['textview1'].text = 'Hello, ' + @widgets['entry1'].text + '.'
  end
  
end # end class HelloSwingDemo

class App
  include java.lang.Runnable
  
  def initialize(pretext)
    @pretext = pretext
  end
  
  def run()
    gui = HelloSwingDemo.new
    gui.widgets['textview1'].text = @pretext
  end
end # end class App

if $PROGRAM_NAME == __FILE__
  pretext = '(JRuby %s) Java %s Swing GUI' % [JRUBY_VERSION, 
    System.getProperty('java.version')]
  #java.awt.EventQueue.invokeLater {App.new(pretext).run}
  gui = HelloSwingDemo.new
  gui.widgets['textview1'].text = pretext
  sleep 30
  exit 0
end


# (initial): mvn [-f uiform_swing/pom.xml] [-Dmaven.test.skip=true] package install

# jruby [-J-cp uiform_swing.jar] helloswingdemo.rb
# or
# java -cp <path>/jruby-complete.jar[:uiform_swing.jar] org.jruby.Main helloswingdemo.rb