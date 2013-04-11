#!/usr/bin/env jython

from __future__ import (absolute_import, division, print_function,
  unicode_literals)

import sys, os, logging, inspect
#from future.builtins import (ascii, filter, hex, map, oct, zip, str)

if 'java' not in sys.platform.lower():
  raise Exception('This package can only be used on Jython.')

#VERSION_UIFORMSWING = os.getenv('VERSION_UIFORMSWING', '2013.04')
##HERE = os.path.dirname(__file__)
##sys.path.extend([os.path.join(HERE, 'uiform_swing-{0}.jar'.format(VERSION_UIFORMSWING))])
#sys.path.extend(['{0}/.m2/repository/org/sandbox/uiform_swing/{1}/uiform_swing-{1}.jar'.format(os.getenv('HOME', '.'), VERSION_UIFORMSWING)])

from java.lang import System
from java.io import FileInputStream
from java.net import URL

#from javafx.embed.swing import JFXPanel
#_ = JFXPanel() # ? prevent JavaFX Toolkit not initialized exception
#from javafx.geometry import Pos
#from javafx.scene.layout import StackPane, Pane, VBox
#from javafx.scene.control import (Label, Button, TextArea, DialogPane,
#  TextField)
from javafx.fxml import FXMLLoader
from javafx.scene import Scene
from javafx.application import Application
from javafx.stage import WindowEvent
#from javafx.event import EventHandler

def userifc_version():
  import platform

  return "(Jython {0}) Java {1} JavaFX {2}".format(platform.python_version(),
    System.getProperty("java.version"), System.getProperty("javafx.version"))

class HelloJavafxDemo(object):
  '''Description:: '''

  def __init__(self, stageX):
    '''Construct object'''
    
    super(HelloJavafxDemo, self).__init__()
    
    self.stage, self.widgets = stageX, {}
    
    #self.parent = StackPane(id='stackpane1', alignment=Pos.CENTER)
    #self.widgets.update({'parent': self.parent, 'pane1': Pane(id='pane1'),
    #  'vbox1': VBox(id='vbox1', alignment=Pos.CENTER),
    #  'label1': Label('label1', id='label1', alignment=Pos.CENTER),
    #  'button1': Button('button1', id='button1', alignment=Pos.CENTER),
    #  'textview1': TextArea(id='textview1'),
    #  'entry1': TextField(id='entry1', promptText='Enter name')})
    #self.widgets['dialog1'] = DialogPane(id='dialog1', headerText='dialog1',
    #  content=self.widgets['entry1'])
    ##self.widgets['vbox1'].children.add(self.widgets['label1'])
    #self.widgets['vbox1'].children.addAll(self.widgets['label1'], 
    #  self.widgets['button1'], self.widgets['textview1'])
    #self.widgets['pane1'].children.add(self.widgets['vbox1'])
    #self.widgets['parent'].children.addAll(self.widgets['pane1'], 
    #  self.widgets['dialog1'])
    #self.widgets['dialog1'].visible = False
    #self.widgets['parent'].setPrefSize(202.0, 208.0)
    #self.widgets['pane1'].setPrefSize(198.0, 205.0)
    #self.widgets['vbox1'].setPrefSize(195.0, 205.0)
    #self.widgets['label1'].setPrefSize(174.0, 38.0)
    #self.widgets['button1'].setPrefSize(174.0, 48.0)
    #self.widgets['textview1'].setPrefSize(174.0, 107.0)
    
    uiform = 'jvm_ui/helloForm-javafx.fxml'
    rsrc_path = os.environ.get('RSRC_PATH', 'resources')
    #self.parent = FXMLLoader.load(URL('file:' + rsrc_path + '/' + uiform))
    fxmlloader = FXMLLoader()
    self.parent = fxmlloader.load(FileInputStream(rsrc_path + '/' + uiform))
    self.widgets.update({'parent': self.parent.lookup('#stackpane1'),
      'pane1': self.parent.lookup('#pane1'),
      'vbox1': self.parent.lookup('#vbox1'),
      'label1': self.parent.lookup('#label1'),
      'button1': self.parent.lookup('#button1'),
      'textview1': self.parent.lookup('#textview1'),
      'dialog1': self.parent.lookup('#dialog1'),
      'entry1': self.parent.lookup('#entry1')})
    
    #self.widgets['button1'].setOnAction(self.Button1EventHandler())
    #self.widgets['entry1'].setOnAction(self.Entry1EventHandler())
    self.widgets['button1'].setOnAction(self.onButton1Action)
    self.widgets['entry1'].setOnAction(self.onEntry1Action)
    
    #self.stage.setOnCloseRequest(self.StageCloseHandler())
    self.stage.setOnCloseRequest(self.onStageClose)
    self.stage.setTitle(self.parent.id)
    self.parent.visible = True

#  class StageCloseHandler(EventHandler):
#    '''Window listener for stage'''
#    
#    def handle(self, event):
#      if WindowEvent.WINDOW_CLOSE_REQUEST == event.eventType:
#        #event.target.close()
#        HelloJavafxDemo.stage.close()
    
#  class Button1EventHandler(EventHandler):
#    '''Action listener for button1'''
#    
#    def handle(self, event):
#      HelloJavafxDemo.widgets['pane1'].visible = False
#      HelloJavafxDemo.widgets['dialog1'].visible = True
#      HelloJavafxDemo.widgets['entry1'].text = ""

#  class Entry1EventHandler(EventHandler):
#    '''Action listener for entry1'''
#    
#    def handle(self, event):
#      HelloJavafxDemo.widgets['dialog1'].visible = False
#      HelloJavafxDemo.widgets['pane1'].visible = True
#      HelloJavafxDemo.widgets['textview1'].text = 'Hello, ' + \
#        HelloJavafxDemo.widgets['entry1'].text + '.'
  
  def onStageClose(self, event):
    '''Window listener for stage'''
    
    if WindowEvent.WINDOW_CLOSE_REQUEST == event.eventType:
      #event.target.close()
      self.stage.close()
  
  def onButton1Action(self, event):
    '''Action listener for button1'''
    
    self.widgets['pane1'].visible = False
    self.widgets['dialog1'].visible = True
    self.widgets['entry1'].text = ""

  def onEntry1Action(self, event):
    '''Action listener for entry1'''
    
    self.widgets['dialog1'].visible = False
    self.widgets['pane1'].visible = True
    self.widgets['textview1'].text = 'Hello, ' + \
      self.widgets['entry1'].text + '.'

class App(Application):
  def start(self, stagePrime):
    params = self.getParameters()
    param0 = params.getRaw()[0] if 0 < len(params.getRaw()) else ''
    
    view = HelloJavafxDemo(stagePrime)
    view.widgets['textview1'].text = params.getNamed()['pretext']
    stagePrime.setScene(Scene(view.parent, 200, 160))
    stagePrime.show()

def main(argv=None):
  pretext = '{0} GUI\n'.format(userifc_version())
  
  #App.launch(App, ['--pretext=' + pretext])
  Application.launch(App, ['--pretext=' + pretext])
  
  return 0

if '__main__' == __name__:
  sys.exit(main(sys.argv[1:]))


# jython -J-cp <path>/jfxrt.jar hellojavafxemo.py
# or
# java -cp <path>/jython-standalone.jar:<path>/jfxrt.jar org.python.util.jython hellojavafxdemo.py
