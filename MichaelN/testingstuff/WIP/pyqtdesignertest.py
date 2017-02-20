# -*- coding: utf-8 -*-

# Form implementation generated from reading ui file 'pyqtdesignertest.ui'
#
# Created: Sat Jan 21 13:02:26 2017
#      by: PyQt4 UI code generator 4.11.3
#
# WARNING! All changes made in this file will be lost
# WARNING! All changes made in this file will be lost
from PyQt4 import QtCore, QtGui

import sys
import time
from networktables import NetworkTables

# To see messages from networktables, you must setup logging
import logging
logging.basicConfig(level=logging.DEBUG)

if len(sys.argv) != 2:
    print("Error: specify an IP to connect to!")
    exit(0)
        
ip = sys.argv[1]
    
NetworkTables.initialize(server=ip)

namesToAdd = []
try:
    _fromUtf8 = QtCore.QString.fromUtf8
except AttributeError:
    def _fromUtf8(s):
        return s

try:
    _encoding = QtGui.QApplication.UnicodeUTF8
    def _translate(context, text, disambig):
        return QtGui.QApplication.translate(context, text, disambig, _encoding)
except AttributeError:
    def _translate(context, text, disambig):
        return QtGui.QApplication.translate(context, text, disambig)

class Ui_MainWindow(object):
    def setupUi(self, MainWindow):
        MainWindow.setObjectName(_fromUtf8("MainWindow"))
        MainWindow.resize(1422, 1324)
        MainWindow.setMaximumSize(QtCore.QSize(3800, 2000))
        self.centralwidget = QtGui.QWidget(MainWindow)
        self.centralwidget.setObjectName(_fromUtf8("centralwidget"))
        self.gridLayout = QtGui.QGridLayout(self.centralwidget)
        self.gridLayout.setObjectName(_fromUtf8("gridLayout"))
        self.qwtPlot = Qwt5.QwtPlot(self.centralwidget)
        self.qwtPlot.setEnabled(True)
        self.qwtPlot.setProperty("propertiesDocument", _fromUtf8(""))
        self.qwtPlot.setObjectName(_fromUtf8("qwtPlot"))
        self.gridLayout.addWidget(self.qwtPlot, 3, 1, 1, 1)
        self.horizontalLayout_2 = QtGui.QHBoxLayout()
        self.horizontalLayout_2.setObjectName(_fromUtf8("horizontalLayout_2"))
        self.tree = QtGui.QTreeWidget(self.centralwidget)
        self.tree.setAcceptDrops(True)
        self.tree.setDragDropOverwriteMode(False)
        self.tree.setUniformRowHeights(True)
        self.tree.setObjectName(_fromUtf8("tree"))
#        item_0 = QtGui.QTreeWidgetItem(self.tree)
#        item_0.setCheckState(0, QtCore.Qt.Unchecked)
#        item_0.setFlags(QtCore.Qt.ItemIsSelectable|QtCore.Qt.ItemIsEditable|QtCore.Qt.ItemIsDragEnabled|QtCore.Qt.ItemIsDropEnabled|QtCore.Qt.ItemIsUserCheckable|QtCore.Qt.ItemIsEnabled|QtCore.Qt.ItemIsTristate)       
#        item_1 = QtGui.QTreeWidgetItem(item_0)
#        item_1.setCheckState(0, QtCore.Qt.Unchecked)
#        item_1.setFlags(QtCore.Qt.ItemIsSelectable|QtCore.Qt.ItemIsEditable|QtCore.Qt.ItemIsDragEnabled|QtCore.Qt.ItemIsDropEnabled|QtCore.Qt.ItemIsUserCheckable|QtCore.Qt.ItemIsEnabled|QtCore.Qt.ItemIsTristate)
#        item_1 = QtGui.QTreeWidgetItem(item_0)
#        item_1.setCheckState(0, QtCore.Qt.Unchecked)
#        item_1.setFlags(QtCore.Qt.ItemIsSelectable|QtCore.Qt.ItemIsEditable|QtCore.Qt.ItemIsDragEnabled|QtCore.Qt.ItemIsDropEnabled|QtCore.Qt.ItemIsUserCheckable|QtCore.Qt.ItemIsEnabled|QtCore.Qt.ItemIsTristate)
#        item_0 = QtGui.QTreeWidgetItem(self.tree)
#        item_0.setCheckState(0, QtCore.Qt.Unchecked)
#        item_0.setFlags(QtCore.Qt.ItemIsSelectable|QtCore.Qt.ItemIsEditable|QtCore.Qt.ItemIsDragEnabled|QtCore.Qt.ItemIsDropEnabled|QtCore.Qt.ItemIsUserCheckable|QtCore.Qt.ItemIsEnabled|QtCore.Qt.ItemIsTristate)
#        item_1 = QtGui.QTreeWidgetItem(item_0)
#        item_1.setCheckState(0, QtCore.Qt.Unchecked)
#        item_1.setFlags(QtCore.Qt.ItemIsSelectable|QtCore.Qt.ItemIsEditable|QtCore.Qt.ItemIsDragEnabled|QtCore.Qt.ItemIsDropEnabled|QtCore.Qt.ItemIsUserCheckable|QtCore.Qt.ItemIsEnabled|QtCore.Qt.ItemIsTristate)
#        item_1 = QtGui.QTreeWidgetItem(item_0)
#        item_1.setCheckState(0, QtCore.Qt.Unchecked)
#        item_1.setFlags(QtCore.Qt.ItemIsSelectable|QtCore.Qt.ItemIsEditable|QtCore.Qt.ItemIsDragEnabled|QtCore.Qt.ItemIsDropEnabled|QtCore.Qt.ItemIsUserCheckable|QtCore.Qt.ItemIsEnabled|QtCore.Qt.ItemIsTristate)
        self.horizontalLayout_2.addWidget(self.tree)
        self.table = QtGui.QTableWidget(self.centralwidget)
        self.table.setAcceptDrops(True)
        self.table.setLineWidth(2)
        self.table.setAlternatingRowColors(True)
        self.table.setWordWrap(True)
        self.table.setObjectName(_fromUtf8("table"))
        #self.table.setColumnCount(len(namesToAdd))
        self.table.setRowCount(2)
        item = QtGui.QTableWidgetItem()
       # for i in enumerate(namesToAdd):
       #     self.table.setHorizontalHeaderItem(i[0], item)
       #     item = QtGui.QTableWidgetItem()
        self.table.setVerticalHeaderItem(0, item)
        item = QtGui.QTableWidgetItem()
        self.table.setVerticalHeaderItem(1, item)
        item = QtGui.QTableWidgetItem()
        #self.table.setHorizontalHeaderItem(0, item)
        #item = QtGui.QTableWidgetItem()
        #self.table.setHorizontalHeaderItem(1, item)
        #item = QtGui.QTableWidgetItem()
        item.setFlags(QtCore.Qt.ItemIsSelectable|QtCore.Qt.ItemIsEditable|QtCore.Qt.ItemIsDragEnabled|QtCore.Qt.ItemIsDropEnabled|QtCore.Qt.ItemIsUserCheckable|QtCore.Qt.ItemIsEnabled|QtCore.Qt.ItemIsTristate)
        self.table.setItem(0, 0, item)
        item = QtGui.QTableWidgetItem()
        item.setFlags(QtCore.Qt.ItemIsSelectable|QtCore.Qt.ItemIsEditable|QtCore.Qt.ItemIsDragEnabled|QtCore.Qt.ItemIsDropEnabled|QtCore.Qt.ItemIsUserCheckable|QtCore.Qt.ItemIsEnabled|QtCore.Qt.ItemIsTristate)
        self.table.setItem(0, 1, item)
        item = QtGui.QTableWidgetItem()
        item.setFlags(QtCore.Qt.ItemIsSelectable|QtCore.Qt.ItemIsEditable|QtCore.Qt.ItemIsDragEnabled|QtCore.Qt.ItemIsDropEnabled|QtCore.Qt.ItemIsUserCheckable|QtCore.Qt.ItemIsEnabled|QtCore.Qt.ItemIsTristate)
        self.table.setItem(1, 0, item)
        item = QtGui.QTableWidgetItem()
        item.setFlags(QtCore.Qt.ItemIsSelectable|QtCore.Qt.ItemIsEditable|QtCore.Qt.ItemIsDragEnabled|QtCore.Qt.ItemIsDropEnabled|QtCore.Qt.ItemIsUserCheckable|QtCore.Qt.ItemIsEnabled|QtCore.Qt.ItemIsTristate)
        self.table.setItem(1, 1, item)
        self.table.horizontalHeader().setDefaultSectionSize(150)
        self.horizontalLayout_2.addWidget(self.table)
        self.gridLayout.addLayout(self.horizontalLayout_2, 2, 1, 1, 1)
        MainWindow.setCentralWidget(self.centralwidget)
        self.menubar = QtGui.QMenuBar(MainWindow)
        self.menubar.setGeometry(QtCore.QRect(0, 0, 1422, 38))
        self.menubar.setObjectName(_fromUtf8("menubar"))
        self.menuFile = QtGui.QMenu(self.menubar)
        self.menuFile.setObjectName(_fromUtf8("menuFile"))
        MainWindow.setMenuBar(self.menubar)
        self.statusbar = QtGui.QStatusBar(MainWindow)
        self.statusbar.setObjectName(_fromUtf8("statusbar"))
        MainWindow.setStatusBar(self.statusbar)
        self.actionOpen = QtGui.QAction(MainWindow)
        self.actionOpen.setObjectName(_fromUtf8("actionOpen"))
        self.actionSave = QtGui.QAction(MainWindow)
        self.actionSave.setObjectName(_fromUtf8("actionSave"))
        self.actionExit = QtGui.QAction(MainWindow)
        self.actionExit.setObjectName(_fromUtf8("actionExit"))
        self.menuFile.addAction(self.actionOpen)
        self.menuFile.addAction(self.actionSave)
        self.menuFile.addSeparator()
        self.menuFile.addAction(self.actionExit)
        self.menubar.addAction(self.menuFile.menuAction())
        
        def valueChanged(key, value, isNew):
                        
            print("valueChanged: key: '%s'; value: %s; isNew: %s" % (key, value, isNew))
            
            #add namesToAdd
            if isNew:
#                
#                item = QtGui.QTableWidgetItem()
#                self.table.setColumnCount(i+1)                
#                self.table.setHorizontalHeaderItem(i, item)
                namesToAdd.append(key)
#                __sortingEnabled = self.tree.isSortingEnabled()
#                self.tree.setSortingEnabled(False)
#                
#                for nValue in namesToAdd:
#                    
#                    sep = nValue.split('/')
#                    
#                    item_0 = QtGui.QTreeWidgetItem(self.tree)
#                    item_0.setCheckState(0, QtCore.Qt.Unchecked)
#                    item_0.setFlags(QtCore.Qt.ItemIsSelectable|QtCore.Qt.ItemIsEditable|QtCore.Qt.ItemIsDragEnabled|QtCore.Qt.ItemIsDropEnabled|QtCore.Qt.ItemIsUserCheckable|QtCore.Qt.ItemIsEnabled|QtCore.Qt.ItemIsTristate)
#                    self.tree.topLevelItem(i).setText(0, _translate("MainWindow", sep[1], None))
##                    for childnum in range(0,len(sep)-1):
#                    item_1 = QtGui.QTreeWidgetItem(item_0)
#                    item_1.setCheckState(0, QtCore.Qt.Unchecked)
#                    item_1.setFlags(QtCore.Qt.ItemIsSelectable|QtCore.Qt.ItemIsEditable|QtCore.Qt.ItemIsDragEnabled|QtCore.Qt.ItemIsDropEnabled|QtCore.Qt.ItemIsUserCheckable|QtCore.Qt.ItemIsEnabled|QtCore.Qt.ItemIsTristate)
#                    self.tree.topLevelItem(i).child(0).setText(0, _translate('MainWindow', sep[2], None))
#                    namesToAdd.pop(0)
#                i+=1
#                    
#                self.tree.setSortingEnabled(__sortingEnabled)
                
        NetworkTables.addGlobalListener(valueChanged)
        self.retranslateUi(MainWindow)
        QtCore.QMetaObject.connectSlotsByName(MainWindow)

        
    def retranslateUi(self, MainWindow):
        MainWindow.setWindowTitle(_translate("MainWindow", "app", None))
#        self.tree.headerItem().setText(0, _translate("MainWindow", "SubSystems", None))
#        __sortingEnabled = self.tree.isSortingEnabled()
#        self.tree.setSortingEnabled(False)
#        self.tree.topLevelItem(0).setText(0, _translate("MainWindow", "headerItem1", None))
#        self.tree.topLevelItem(0).child(0).setText(0, _translate("MainWindow", "Item1", None))
#        self.tree.topLevelItem(0).child(1).setText(0, _translate("MainWindow", "Item2", None))
#        self.tree.topLevelItem(1).setText(0, _translate("MainWindow", "headerItem2", None))
#        self.tree.topLevelItem(1).child(0).setText(0, _translate("MainWindow", "item3", None))
#        self.tree.topLevelItem(1).child(1).setText(0, _translate("MainWindow", "item4", None))
#        self.tree.setSortingEnabled(__sortingEnabled)
                
        for name in enumerate(namesToAdd):
            print(name[0])
            item = self.table.horizontalHeaderItem(name[0])
            item.setText(_translate("MainWindow", namesToAdd[name[0]], None))
            
            
        
        
        #item.setText(_translate("MainWindow", "column1", None))
        #item = self.table.horizontalHeaderItem(1)
        #item.setText(_translate("MainWindow", "column2", None))
        __sortingEnabled = self.table.isSortingEnabled()
        self.table.setSortingEnabled(False)
        self.table.setSortingEnabled(__sortingEnabled)
        self.menuFile.setTitle(_translate("MainWindow", "File", None))
        self.actionOpen.setText(_translate("MainWindow", "Open", None))
        self.actionSave.setText(_translate("MainWindow", "Save", None))
        self.actionExit.setText(_translate("MainWindow", "Exit", None))

        
            
#        i = 0
#        item = ui.table.verticalHeaderItem(i)
#        i = i+1
#        item.setText(_translate("MainWindow", "column"+str(i), None))   
    
from PyQt4 import Qwt5

if __name__ == "__main__":
    import sys

    time.sleep(1)
    app = QtGui.QApplication(sys.argv)
    MainWindow = QtGui.QMainWindow()
    ui = Ui_MainWindow()
    ui.setupUi(MainWindow)
    MainWindow.show()
    print(len(namesToAdd))
    
    
    sys.exit(app.exec_())


