## Check out the NT documentation at: http://robotpy.readthedocs.io/en/stable/guide/nt.html#networktables-guide

from networktables import NetworkTables  # Installed with pip
import time  # To create delays
import random  # To randomly generate numbers
import config  # To integrate the configurations from config.py

# To see messages from networktables, you must setup logging
import logging

logging.basicConfig(level=logging.DEBUG)

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


def generateRandomValues():  # This is a function for creating random values
    table.putNumber('Int1', random.randint(1, 1000))  # Chooses a random value between 1 and 1000
    table.putNumber('Int2', random.randint(1, 1000))
    testBool = random.randint(0, 1)  # This determines if the bool should be 1 (true) or 0 (false)
    if (testBool == 1):
        table.putBoolean('myBool', True)
    else:
        table.putBoolean('myBool', False)
    num_string = str(random.randint(0, 100))
    table.putString('Debug', num_string)
    print(table.getString('Debug', True))
    time.sleep(0.02)  # Wait 20 milliseconds


runLoop = True  # Boolean to run loop. Set to false to stop loop or terminate process
print("Loop Started")
while (runLoop):
    generateRandomValues()  # Call the function to generate random values
