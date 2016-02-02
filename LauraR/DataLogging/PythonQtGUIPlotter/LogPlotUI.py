# -*- coding: utf-8 -*-
"""
Created on Tue Nov 17 20:10:29 2015

@author: Laura
"""
from __future__ import print_function
import csv
from PyQt4 import QtCore, QtGui, uic
import sys

from matplotlib.backends.backend_qt4agg import (
    NavigationToolbar2QT as NavigationToolbar)

# create the GUI window layout dynamically from the .ui file
QT_CREATOR_FILE = "LogDataScreenDesign.ui"
UI_MAIN_WINDOW, QT_BASE_CLASS = uic.loadUiType(QT_CREATOR_FILE)

def read_log_file(fname):
    """
    Read in a log file generated by the FRC SmartDashboard. The data represents
    timestamps, variables, and values transmitted using the NetworkTables from
    the roboRIO to the Driver Station computer.

    :param fname: the file name of the log file to read in
    :returns: a tuple consisting of
        min_time: the minimum time in the log file in milliseconds
        max_time: the maximum time in the log file in milliseconds
        mydict: a dictionary with the variable name as key and time and data
                value lists as values in a format that can be directly used in
                matplotlib plots
        timedict: a dictionary with timestamps as the key and a dictionary of
                  variable names (key) and data values (value) as the contents
    """

    mydict = {}
    timedict = {}
    min_time = 10 ^ 20
    max_time = 0
    with open(fname) as csvfile:
        reader = csv.DictReader(csvfile)
        for row in reader:
            varname = row['Name']
            time = int(row['Time (ms)'])
            value = row['Value']
            if time not in timedict.keys():
                timedict[time] = {}
            if varname not in mydict.keys():
                mydict[varname] = [list(), list()]

            if time < min_time:
                min_time = time
            if time > max_time:
                max_time = time

            if value.startswith('true'):
                val = 1.0
            elif value.startswith('false'):
                val = 0.0
            else:
                val = float(value)

            mydict[varname][0].append(time/1000.0)
            mydict[varname][1].append(val)
            timedict[time][varname] = value
    return (min_time, max_time, mydict, timedict)

def write_spreadsheet_file(fname, rowheaders, colheaders, data):
    """
    Writes a .csv file with the data contained in the log files in a format
    which is more Excel friendly.

    :param fname: the file name to create
    :param rowheaders: the list of time stamps to capture
    :param colheaders: the list of variables to capture
    :param data: the timedict dictionary of data values, keyed by timestamp

    """
    with open(fname, 'wb') as csvfile:
        fieldnames = ['msec']
        fieldnames.extend(colheaders)
        print (fieldnames)

        sheet_writer = csv.DictWriter(csvfile, fieldnames=fieldnames)
        sheet_writer.writeheader()
        for time in rowheaders:
            rowdict = {}
            for var in data[time].keys():
                if var in colheaders:
                    rowdict[var] = data[time][var]
            if len(rowdict) > 0:
                rowdict['msec'] = time
                sheet_writer.writerow(rowdict)


class LogDataTableModel(QtCore.QAbstractTableModel):
    """
    Provides the model interface for the tableView

    """
    def rowCount(self, parent):
        """
        Returns the number of rows of data in the log data table.
        """
        return len(self.owner.timedict.keys())

    def columnCount(self, parent):
        """
        Returns the number of columns of data (number of distinct variables) in
        the log data table.
        """
        return len(self.owner.mydict.keys())

    def data(self, index, role):
        """
        Returns the data located at the index location in the log data table.
        """
        if not index.isValid():
            return None
        if index.row() > self.rowCount(0) or index.row() < 0 \
            or index.column() > self.columnCount(0) or index.column() < 0:
            return None
        if role == QtCore.Qt.DisplayRole:
            time = self.row_labels[index.row()]
            var = self.col_labels[index.column()]
            if self.owner.timedict[time].has_key(var):
                return self.owner.timedict[time][var]
            else:
                return None
        return None

    def headerData(self, section, orientation, role):
        """
        Returns the header data located at the specific row or column location.
        """
        if role == QtCore.Qt.DisplayRole:
            if orientation == QtCore.Qt.Horizontal:
                return self.col_labels[section]
            else:
                return self.row_labels[section]
        return None

    def initializeData(self, owner):
        """
        Initializes the data in the log data spreadsheet model
        """
        self.beginResetModel()
        self.owner = owner
        self.row_labels = self.owner.timedict.keys()
        self.row_labels.sort()
        self.col_labels = self.owner.mydict.keys()
        self.col_labels.sort()
        self.endResetModel()

class LogSortFilterProxyModel(QtGui.QSortFilterProxyModel):
    """
    Provides the filtered model proxy interface for the tableView

    """
    def __init__(self, parent):
        """
        Initializes the LogSortFilterProxyModel.
        """
        self.selected_vars = []
        super(self.__class__, self).__init__(parent)

    def filterAcceptsColumn(self, src_column, src_parent):
        """
        Tests whether the selected column should be displayed.
        """
        if len(self.selected_vars) == 0:
            return True
        else:
            return self.sourceModel().headerData(src_column, \
                         QtCore.Qt.Horizontal, \
                         QtCore.Qt.DisplayRole) \
                         in self.selected_vars

    def filterAcceptsRow(self, src_row, src_parent):
        """
        Tests whether the selected row should be displayed.
        """
        model = self.sourceModel()
        if len(self.selected_vars) == 0:
            return True
        else:
            doshow = False
            for col in range(model.columnCount(0)):
                if model.headerData(col, QtCore.Qt.Horizontal, \
                             QtCore.Qt.DisplayRole) in self.selected_vars:
                    if model.data(model.createIndex(src_row, col), \
                            QtCore.Qt.DisplayRole) != None:
                        doshow = True
            return doshow

    def set_selected_vars(self, vars):
        """
        Initializes the list of variables to be used as a filter to determine
        which rows and columns will be displayed in the QTableView.
        """
        self.selected_vars = vars
        self.invalidateFilter()

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
        self.setupUi(self)
        self.file_name = 'csv.txt'
        self.raw_data = LogDataTableModel()
        self.raw_data_proxy = LogSortFilterProxyModel(None)
        self.init_log_data(self.file_name)
        self.create_main_frame()
        self.on_draw()

    def create_main_frame(self):
        """
        Creates the Main Frame GUI of the Qt application
        """
        self.init_listWidget()
        # set column width to fit contents
        self.tableView.resizeColumnsToContents()

        self.raw_data.initializeData(self)
        self.raw_data_proxy.setSourceModel(self.raw_data)
        self.tableView.setModel(self.raw_data_proxy)

        def on_item_changed(item):
            """
            Reacts to whether a log variable name should be added or deleted
            from the selected_vars list.
            """
            name = str(item.text())
            if item.checkState() == QtCore.Qt.Checked:
                if name not in self.selected_vars:
                    self.selected_vars.append(name)
            else:
                if name in self.selected_vars:
                    self.selected_vars.remove(name)
            self.raw_data_proxy.set_selected_vars(self.selected_vars)
            self.on_draw()

        self.listWidget.itemChanged.connect(on_item_changed)
        self.mpl_toolbar = NavigationToolbar(self.mplwidget, self.centralwidget)
        self.verticalLayout.insertWidget(1, self.mpl_toolbar)

    def init_listWidget(self):
        """
        Initializes the listWidget with the names of all the variables in the
        log file along with checkboxes to select which ones should be plotted
        and displayed.
        """
        self.listWidget.clear()
        for var in self.mydict.keys():
            item = QtGui.QListWidgetItem()
            item.setText(var)
            item.setFlags(QtCore.Qt.ItemIsUserCheckable | \
                          QtCore.Qt.ItemIsEnabled | \
                          QtCore.Qt.ItemIsSelectable)
            item.setCheckState(QtCore.Qt.Unchecked)
            self.listWidget.addItem(item)
        self.listWidget.sortItems()


    def on_draw(self):
        """
        Draws the mathplotlib plot of the selected log variables.
        """
        self.mplwidget.figure.clear()
        if len(self.selected_vars) > 0:
            self.mplwidget.axes = self.mplwidget.figure.add_subplot(111)

            self.mplwidget.axes.hold(True)
            for var in self.selected_vars:
                if var in self.mydict.keys():
                    self.mplwidget.axes.plot(self.mydict[var][0], \
                        self.mydict[var][1], '-x', label=var)

            legend = self.mplwidget.axes.legend(loc='upper right', shadow=True, fontsize='large')
            legend.get_frame().set_facecolor('#00FFCC')
            self.mplwidget.axes.set_title(self.file_name)
            self.mplwidget.axes.get_xaxis().set_label_text('seconds')
            self.mplwidget.draw()


    def init_log_data(self, fname):
        """
        Initializes the data by reading the selected log file.
        """
        self.setWindowTitle(fname)
        (self.min_time, self.max_time, self.mydict, self.timedict) = \
            read_log_file(self.file_name)
        print ("min_time: ", self.min_time)
        print ("max_time: ", self.max_time)
        self.selected_vars = []
        self.raw_data_proxy.set_selected_vars(self.selected_vars)



    @QtCore.pyqtSlot()
    def on_actionOpen_triggered(self):
        """
        Opens the Windows file dialog to select a log file. This method is
        triggered by the "Open" menu item.
        """
        self.file_name = QtGui.QFileDialog.getOpenFileName(self, \
            "Open Log File", ".", "Text (*.txt)")
        self.init_log_data(self.file_name)
        self.init_listWidget()
        self.raw_data.initializeData(self)

        self.mplwidget.figure.clear()
        self.mplwidget.draw()


    @QtCore.pyqtSlot()
    def on_actionSave_triggered(self):
        """
        Saves the complete log data to an Excel friendly .csv file. Opens the
        Windows file system dialog to select a file. Method is triggered by
        the "Save" menu item.
        """
        savefile = QtGui.QFileDialog.getSaveFileName(self, \
            "Save SpreadsheetFile", ".", "CSV (*.csv)")
        write_spreadsheet_file(savefile, self.raw_data.row_labels, \
            self.raw_data.col_labels, self.timedict)

    @QtCore.pyqtSlot()
    def on_actionSave_Selected_triggered(self):
        """
        Saves the log data for the currently selected log variables to an Excel
        friendly .csv file. Opens the Windows file system dialog to select a
        file. Method is triggered by the "SaveSelected" menu item.
        """
        savefile = QtGui.QFileDialog.getSaveFileName(self, \
            "Save SpreadsheetFile", ".", "CSV (*.csv)")
        write_spreadsheet_file(savefile, self.raw_data.row_labels, \
            self.selected_vars, self.timedict)


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
    app = QtGui.QApplication(sys.argv)
    form = MyApp()
    form.show()
    app.exec_()


if __name__ == "__main__":
    main()
