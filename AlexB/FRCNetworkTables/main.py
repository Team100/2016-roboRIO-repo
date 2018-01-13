## Check out the documentation at: http://robotpy.readthedocs.io/en/stable/guide/nt.html#networktables-guide

from networktables import NetworkTables #Installed with pip
import time
import random
import config

# To see messages from networktables, you must setup logging
import logging
logging.basicConfig(level=logging.DEBUG)

print("STARTED")
NetworkTables.initialize(server=config.ip)

print("Allocating time to connect")
time.sleep(5)
table = NetworkTables.getTable('SmartDashboard')
print("GOT TABLE")


# This retrieves a boolean at /SmartDashboard/foo
foo = table.getBoolean('foo', True)

table.putBoolean('bar',False)
print("PUT VALUE IN NETWORKTABLE")
def generateRandomValues():
    table.putNumber('Int1',random.randint(1,1000))
    table.putNumber('Int2', random.randint(1, 1000))
    testBool = random.randint(0,1)
    if(testBool == 1):
        table.putBoolean('myBool',True)
    else:
        table.putBoolean('myBool',False)
    time.sleep(0.02)  #Wait 20 milliseconds

runLoop = True
print("Loop Started")
while(runLoop):
    generateRandomValues()  # Call my function
