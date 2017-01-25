# -*- coding: utf-8 -*-
"""
Created on Wed Jan 25 12:34:13 2017

@author: Laura
"""

from PyQt4 import QtCore, QtGui, uic
import sys
from networktables import NetworkTables

# To see messages from networktables, you must setup logging
import logging
logging.basicConfig(level=logging.DEBUG)

# create the GUI window layout dynamically from the .ui file
QT_CREATOR_FILE = "NettableListenerTest.ui"

UI_MAIN_WINDOW, QT_BASE_CLASS = uic.loadUiType(QT_CREATOR_FILE)
class MyApp(QtGui.QMainWindow, UI_MAIN_WINDOW):
    """
    The main window application for the LogPlot Qt application.
    """
    
    
    def __init__(self):
        """
        Initializes the main application.
        """
        QtGui.QMainWindow.__init__(self)
        UI_MAIN_WINDOW.__init__(self)
        self.myNames = ['one', 'two']
        self.setupUi(self)
        self.create_main_frame()
        self.on_draw()

    def create_main_frame(self):
        """
        Creates the Main Frame GUI of the Qt application
        """
        self.init_listWidget()


        def on_item_changed(item):
            pass
        
        self.listWidget.itemChanged.connect(on_item_changed)
        
        def valueChanged(key, value, isNew):
            print("valueChanged: key: '%s'; value: %s; isNew: %s" % (key, value, isNew))
            if isNew:
                item = QtGui.QListWidgetItem()
                item.setText(key)
                item.setFlags(QtCore.Qt.ItemIsUserCheckable | \
                          QtCore.Qt.ItemIsEnabled | \
                          QtCore.Qt.ItemIsSelectable)
                item.setCheckState(QtCore.Qt.Unchecked)
                self.listWidget.addItem(item)

        NetworkTables.addGlobalListener(valueChanged)


    def init_listWidget(self):
        """
        Initializes the listWidget with the names of all the variables in the
        log file along with checkboxes to select which ones should be plotted
        and displayed.
        """
        self.listWidget.clear()
        for var in self.myNames:
            item = QtGui.QListWidgetItem()
            item.setText(var)
            item.setFlags(QtCore.Qt.ItemIsUserCheckable | \
                          QtCore.Qt.ItemIsEnabled | \
                          QtCore.Qt.ItemIsSelectable)
            item.setCheckState(QtCore.Qt.Unchecked)
            self.listWidget.addItem(item)
        self.listWidget.sortItems()


    def on_draw(self):
        pass


    @QtCore.pyqtSlot()
    def on_actionExit_triggered(self):
        """
        Closes the GUI window and exits the application.
        """
        self.close()

def main():
    """
    The main program routine.
    """
    if len(sys.argv) != 2:
        print("Error: specify an IP to connect to!")
        exit(0)

    ip = sys.argv[1]

    NetworkTables.initialize(server=ip)
    app = QtGui.QApplication(sys.argv)
    form = MyApp()
    form.show()
    app.exec_()


if __name__ == "__main__":
    main()