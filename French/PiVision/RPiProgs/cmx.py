#!/usr/bin/env python
#
# This program allows the RoboRIO to control the camera brightness. Brightness
# is reduced to enable easier target detection (illuminated target stands 
# out better from dark background.  Brightness is restored for driving. 
#
# This is a NetworkTables client (eg, the DriverStation/coprocessor side).
# You need to tell it the IP address of the NetworkTables server (the
# robot or simulator).
#
# 
# This will print out any changes detected on the SmartDashboard
# table.
#

import subprocess

import sys
import time
from networktables import NetworkTable

# To see messages from networktables, you must setup logging
# import logging
# logging.basicConfig(level=logging.DEBUG)

if len(sys.argv) != 2:
    print("Error: specify an IP to connect to!")
    exit(0)

ip = sys.argv[1]

NetworkTable.setIPAddress(ip)
NetworkTable.setClientMode()
NetworkTable.initialize()

def valueChanged(table, key, value, isNew):
#   print("valueChanged: key: '%s'; value: %s; isNew: %s" % (key, value, isNew))
    
    if(key == 'camBright'):
#     print("Execute change brightness statement") 
      v4l2Args = '--set-ctrl=brightness=' + str(int(value))
      subprocess.call(["/usr/bin/v4l2-ctl", v4l2Args])
#     subprocess.call(["/usr/bin/v4l2-ctl", "--set-ctrl=brightness=130"])


class ConnectionListener:

    def connected(self, table):
#       print("Connected", table)
        pass

    def disconnected(self, table):
#       print("Disconnected", table)
	pass

c_listener = ConnectionListener()

# sd = NetworkTable.getTable("Taco/CAM")
sd = NetworkTable.getTable("SmartDashboard")
sd.addTableListener(valueChanged)
sd.addConnectionListener(c_listener)

while True:
    time.sleep(10)
