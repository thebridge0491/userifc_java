#!/usr/bin/env jython

from __future__ import (absolute_import, division, print_function,
  unicode_literals)

import sys, os, logging, inspect
#from future.builtins import (ascii, filter, hex, map, oct, zip, str)

if 'java' not in sys.platform.lower():
  raise Exception('This package can only be used on Jython.')

VERSION_UIFORMSWING = os.getenv('VERSION_UIFORMSWING', '2013.04')
#HERE = os.path.dirname(__file__)
#sys.path.extend([os.path.join(HERE, 'uiform_swing-{0}.jar'.format(VERSION_UIFORMSWING))])
sys.path.extend(['{0}/.m2/repository/org/sandbox/uiform_swing/{1}/uiform_swing-{1}.jar'.format(os.getenv('HOME', '.'), VERSION_UIFORMSWING)])

from java.lang import System, Runnable
from java.awt import EventQueue, GridLayout
from java.awt.event import WindowAdapter #, ActionEvent, ActionListener
from javax.swing import (JFrame, JPanel, JLabel, JButton, JTextArea, 
  JTextField, JDialog, BoxLayout)

from org.sandbox.uiform_swing import HelloFormSwing

def userifc_version():
  import platform

  return "(Jython {0}) Java {1} Swing".format(platform.python_version(),
    System.getProperty("java.version"))

class HelloSwingDemo(object):
  '''Description:: '''

  def __init__(self):
    '''Construct object'''
    
    super(HelloSwingDemo, self).__init__()
    
    self.widgets = {}
    
    #self.widgets.update({'frame1': JFrame(),
    #  'vbox1': JPanel(GridLayout(3, 1)), 'vbox2': JPanel(),
    #  'label1': JLabel("jLabel1"), 'button1': JButton("jButton1"),
    #  'textview1': JTextArea(), 'entry1': JTextField(15)})
    #self.widgets['dialog1'] = JDialog(self.widgets['frame1'],
    #  preferredSize = (200, 64), title = 'jDialog1')
    #self.widgets['vbox2'].setLayout(BoxLayout(self.widgets['vbox2'], 
    #  BoxLayout.Y_AXIS))
    #for widget in map(self.widgets.get, ['label1', 'button1', 'textview1']):
    #  self.widgets['vbox1'].add(widget)
    #self.widgets['vbox2'].add(self.widgets['entry1'])
    #self.widgets['frame1'].getContentPane().add(self.widgets['vbox1'])
    #self.widgets['dialog1'].getContentPane().add(self.widgets['vbox2'])
    #self.widgets['frame1'].pack()
    #self.widgets['dialog1'].pack()
    #self.widgets['frame1'].setSize(200, 160)
    #self.widgets['dialog1'].setSize(200, 64)
    #self.widgets['frame1'].setLocation(200, 300)
    #self.widgets['dialog1'].setLocation(200, 300)
    
    #rsrc_path = os.environ.get('RSRC_PATH')
    view = HelloFormSwing()
    self.widgets.update({'frame1': view, 'label1': view.getjLabel1(),
      'button1': view.getjButton1(), 'textview1': view.getjTextView1(),
      'dialog1': view.getjDialog1(), 'entry1': view.getjEntry1()})
    
    self.widgets['frame1'].setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE)
    self.widgets['dialog1'].addWindowListener(self.Dialog1Listener())
    #self.widgets['button1'].addActionListener(self.Button1Listener())
    #self.widgets['entry1'].addActionListener(self.Entry1Listener())
    self.widgets['button1'].addActionListener(self.onButton1Action)
    self.widgets['entry1'].addActionListener(self.onEntry1Action)
    
    self.widgets['frame1'].visible = True

  class Dialog1Listener(WindowAdapter):
    '''Window adapter/listener for dialog1'''
    
    def windowClosing(self, event):
      sys.exit(0)
  
#  class Button1Listener(ActionListener):
#    '''Action listener for button1'''
#    
#    def actionPerformed(self, event):
#      HelloSwingDemo.widgets['frame1'].visible = False
#      HelloSwingDemo.widgets['dialog1'].visible = True
#      HelloSwingDemo.widgets['entry1'].text = ""

#  class Entry1Listener(ActionListener):
#    '''Action listener for entry1'''
#    
#    def actionPerformed(self, event):
#      HelloSwingDemo.widgets['dialog1'].visible = False
#      HelloSwingDemo.widgets['frame1'].visible = True
#      HelloSwingDemo.widgets['textview1'].text = 'Hello, ' + \
#        HelloSwingDemo.widgets['entry1'].text + '.'
  
  def onButton1Action(self, event):
    '''Action listener for button1'''
    
    self.widgets['frame1'].visible = False
    self.widgets['dialog1'].visible = True
    self.widgets['entry1'].text = ""

  def onEntry1Action(self, event):
    '''Action listener for entry1'''
    
    self.widgets['dialog1'].visible = False
    self.widgets['frame1'].visible = True
    self.widgets['textview1'].text = 'Hello, ' + \
      self.widgets['entry1'].text + '.'

class App(Runnable):
  def __init__(self, pretext):
    self.pretext = pretext
  
  def run(self):
    gui = HelloSwingDemo()
    gui.widgets['textview1'].text = self.pretext

def main(argv=None):
  import time
  
  pretext = '{0} GUI\n'.format(userifc_version())
  #EventQueue.invokeLater(App(pretext))
  gui = HelloSwingDemo()
  gui.widgets['textview1'].text = pretext
  time.sleep(30)
  return 0

if '__main__' == __name__:
  sys.exit(main(sys.argv[1:]))


# (initial): mvn [-f uiform_swing/pom.xml] [-Dmaven.test.skip=true] package install

# jython [-J-cp uiform_swing.jar] helloswingdemo.py
# or
# java -cp <path>/jython-standalone.jar[:uiform_swing.jar] org.python.util.jython helloswingdemo.py
