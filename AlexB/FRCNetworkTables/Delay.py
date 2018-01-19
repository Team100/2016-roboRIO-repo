from networktables import NetworkTables  # Installed with pip
import time  # To create delays
import config  # To integrate the configurations from config.py
import datetime # To get the current time



# To see messages from networktables, you must setup logging
import logging

logging.basicConfig(level=logging.DEBUG)

def run_master():

    print("STARTED")
    print(config.ip)
    NetworkTables.initialize(server=config.ip)  # Links to the network table

    print("Allocating time to connect")
    time.sleep(5)  # It takes a couple of seconds to connect to the Network Table
    table = NetworkTables.getTable(
        'SmartDashboard')  # All changes are in /SmartDashbaord/ to allow SmartDashbaord to see it
    if(table):
        print("TABLE EXISTS")
    print("GOT TABLE")
    time.sleep(2)

    # This retrieves a boolean at /SmartDashboard/foo
    foo = table.getBoolean('foo', True)  # Example of retrieving data

    table.putBoolean('bar', False)  # Example of putting data

    print("PUT VALUE IN NETWORK TABLE")

    def startMasterDelay():  # This is a function for creating random values
        table.putString('Time', datetime.datetime.now().strftime("%y-%m-%d-%H-%M-%S-%f")[:-3])  # Sends the current date and time
        datetime.datetime.now().strftime("%y-%m-%d-%H-%M-%S-%f")[:-3]
        print("YR-MO-DY-HR-MN-SC-MSC")
        print(datetime.datetime.now().strftime("%y-%m-%d-%H-%M-%S-%f")[:-3])
        time.sleep(0.02)  # Wait 20 milliseconds
        print('PUT DATA')


    runLoop = True  # Boolean to run loop. Set to false to stop loop or terminate process
    print("Loop Started")
    while (runLoop):
        startMasterDelay()  # Call the function to generate random values


def run_slave():



    prevval = ""
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




    print("Loop Started")
    runLoop = True
    while(runLoop):
        if prevval == "":
            prevval = " "
        now = datetime.datetime.now()
        sentData = table.getString('Time', 'ERROR')  # Chooses a random value between 1 and 1000
        if prevval != sentData and sentData != 'ERROR':
            difference = datetime.datetime.strptime(sentData,"%y-%m-%d-%H-%M-%S-%f") - now
            print(difference)
            prevval = sentData
            print("GOT STRING")
        else:
            print("Invalid String:", sentData)

