from networktables import NetworkTables  # Installed with pip
import time  # To create delays
import config  # To integrate the configurations from config.py
import datetime # To get the current time

# To see messages from networktables, you must setup logging
import logging

logging.basicConfig(level=logging.DEBUG)

def run_master():

    print("STARTED")
    NetworkTables.initialize(server=config.ip)  # Links to the network table

    print("Allocating time to connect")
    time.sleep(5)  # It takes a couple of seconds to connect to the Network Table
    table = NetworkTables.getTable(
        'SmartDashboard')  # All changes are in /SmartDashbaord/ to allow SmartDashbaord to see it
    print("GOT TABLE")

    # This retrieves a boolean at /SmartDashboard/foo
    foo = table.getBoolean('foo', True)  # Example of retrieving data

    table.putBoolean('bar', False)  # Example of putting data

    print("PUT VALUE IN NETWORK TABLE")

    def startMasterDelay():  # This is a function for creating random values
        table.putString('Time', datetime.datetime.now())  # Sends the current date and time
        time.sleep(0.02)  # Wait 20 milliseconds

    runLoop = True  # Boolean to run loop. Set to false to stop loop or terminate process
    print("Loop Started")
    while (runLoop):
        startMasterDelay()  # Call the function to generate random values


def run_slave():

    prev_val = str
    print("STARTED")
    NetworkTables.initialize(server=config.ip)  # Links to the network table

    print("Allocating time to connect")
    time.sleep(5)  # It takes a couple of seconds to connect to the Network Table
    table = NetworkTables.getTable(
        'SmartDashboard')  # All changes are in /SmartDashbaord/ to allow SmartDashbaord to see it
    print("GOT TABLE")

    # This retrieves a boolean at /SmartDashboard/foo
    foo = table.getBoolean('foo', True)  # Example of retrieving data

    table.putBoolean('bar', False)  # Example of putting data

    print("PUT VALUE IN NETWORK TABLE")

    def startSlaveDelay():  # This is a function for creating random values

        now = datetime.datetime.now()
        sentData = table.getString('Time')  # Chooses a random value between 1 and 1000
        if prev_val != sentData:
            difference = sentData - now
            print(difference)
            prev_val = sentData


    runLoop = True  # Boolean to run loop. Set to false to stop loop or terminate process
    print("Loop Started")
    while (runLoop):
        startSlaveDelay()  # Call the function to generate random values
