import subprocess
import sys
import time
from networktables import NetworkTable

try:
    NetworkTable.setTeam(100) 
    NetworkTable.setIPAddress('169.254.2.14')
    NetworkTable.setClientMode()
    NetworkTable.initialize()
    print("Initializing Network Tables...")
except:
    print("Network Tables already initialized")
    
def valueChanged(table, key, value, isNew):
    if(key==0): # driving mode
        v4l2Args = ['--set-ctrl white_balance_temperature_auto=1','--set-ctrl exposure_auto=3', '--set-ctrl brightness=110', '--set-ctrl saturation=130']
    else:
        v4l2Args = ['--set-ctrl white_balance_temperature_auto=0','--set-ctrl exposure_auto=1', '--set-ctrl exposure_absolute=10', '--set-ctrl brightness=30', '--set-ctrl saturation=200']

    for args in v4l2Args:
            subprocess.call(["/usr/bin/v4l2-ctl", v4l2Args[args]])
            
class ConnectionListener:
    def connected(self, table):
        pass
    def disconnected(self, table):
        pass

c_listener = ConnectionListener()

sd = NetworkTable.getTable("SmartDashboard")
sd.addTableListener(valueChanged)
sd.addConnectionListener(c_listener)

while True:
    time.sleep(10)