# -*- coding: utf-8 -*-
"""
Created on Wed Feb 01 15:33:24 2017

@author: Team 100
"""

from datetime import datetime
import sys
import time
from PyQt4 import QtCore, QtGui, uic
from networktables import NetworkTables
import logging

logging.basicConfig(level=logging.DEBUG)
QT_CREATOR_FILE = "savefile.ui"
UI_MAIN_WINDOW, QT_BASE_CLASS = uic.loadUiType(QT_CREATOR_FILE)


class MyApp(QtGui.QMainWindow, UI_MAIN_WINDOW):
    def __init__(self,parent=None,selected=[],flag=0, *args):
        QtGui.QMainWindow.__init__(self)
        UI_MAIN_WINDOW.__init__(self)
        self.setupUi(self)
        self.ciEditLine = QtGui.QLineEdit()
        
    @QtCore.pyqtSlot()
    def on_start_pressed(self):
        global f        
        filename = 'Logging-%s.txt'%datetime.now().strftime('%Y-%m-%d_%H-%M')
        f = open(filename, 'w')
        f.write('Time (ms),Name,Value\n')
        NetworkTables.addGlobalListener(valueChanged)
        
    @QtCore.pyqtSlot()
    def on_stop_pressed(self):
        NetworkTables.removeGlobalListener(valueChanged)
        
    @QtCore.pyqtSlot()
    def on_ipConnect_editingFinished(self):
        ip = self.ciEditLine.text()
        NetworkTables.shutdown()
        NetworkTables.initialize(server=ip)

def valueChanged(key, value, isNew):
    timestamp =  int(round(time.time() * 1000))
    s = "[valueChanged: key: '%s'; value: %s; isNew: %s; timestamp: %s;]" % (key, value, isNew, timestamp)    
    print(s)
    f.writelines('"%s","%s","%s"\n' % (str(timestamp),str(key),str(value)))
    f.flush()

if __name__ == '__main__':
    app = QtGui.QApplication(sys.argv)
    form = MyApp(None)
    form.show()
    app.exec_()

    

