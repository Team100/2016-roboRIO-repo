# -*- coding: utf-8 -*-
"""
Created on Wed Feb 01 15:33:24 2017

@author: Team 100
"""

from datetime import datetime
import sys
import time
from networktables import NetworkTables
import logging
filename = 'C:/Users/Team 100/Documents/GitHub/2016-roboRIO-repo/MichaelN//testingstuff/Logging-%s.txt'%datetime.now().strftime('%Y-%m-%d_%H-%M')
f = open(filename, 'w')
r = open(filename, 'r')
logging.basicConfig(level=logging.DEBUG)
f.write('Time (ms),Name,Value\n')
if len(sys.argv) != 2:
    print("Error: specify an IP to connect to!")
    exit(0)

ip = sys.argv[1]

NetworkTables.initialize(server=ip)

def valueChanged(key, value, isNew):
    timestamp =  int(round(time.time() * 1000))
    s = "[valueChanged: key: '%s'; value: %s; isNew: %s; timestamp: %s;]" % (key, value, isNew, timestamp)    
    print(s)
    
    f.writelines('"%s","%s","%s"\n' % (str(timestamp),str(key),str(value)))
    
NetworkTables.addGlobalListener(valueChanged)
while True:
    time.sleep(5)
    f.flush()
    

