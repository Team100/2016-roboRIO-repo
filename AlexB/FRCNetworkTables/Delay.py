from networktables import NetworkTables  # Installed with pip
import time  # To create delays
import config  # To integrate the configurations from config.py
import datetime # To get the current time



# To see messages from networktables, you must setup logging
import logging

logging.basicConfig(level=logging.DEBUG)

i = 0
def run_master():

    print("STARTED")
    print(config.ip)
    NetworkTables.initialize(server=config.ip)  # Links to the network table

    print("Allocating time to connect")
    time.sleep(5)  # It takes a couple of seconds to connect to the Network Table
    table1 = NetworkTables.getTable(
        'table1')  # All changes are in /SmartDashbaord/ to allow SmartDashbaord to see it

    table2 = NetworkTables.getTable(
        'table2')
    if(table1):
        print("TABLE EXISTS")
    print("GOT TABLE")
    time.sleep(2)

    #

    print("PUT VALUE IN NETWORK TABLE")

    def entryListener(table, key, value, isNew):
        global i
        if(table == table1):
            table2.putString("fromMaster",value)
            print("RECIEVED AND SENT DATA ", i)
            i += 1

    table1.addEntryListener(entryListener)

    while(1):
        print("",end="")
        time.sleep(1)


def run_slave():



    prevval = ""
    print("STARTED")
    NetworkTables.initialize(server=config.ip)  # Links to the network table

    print("Allocating time to connect")
    time.sleep(5)  # It takes a couple of seconds to connect to the Network Table
    table1 = NetworkTables.getTable(
        'table1')  # All changes are in /SmartDashbaord/ to allow SmartDashbaord to see it

    table2 = NetworkTables.getTable(
        'table2')
    print("GOT TABLE")

    def entryListener(table, key, value, isNew):
        if(table==table2):
            now = datetime.datetime.utcnow().strftime("%y-%m-%d-%H-%M-%S-%f")
            # print("ORIGINAL:", datetime.datetime.strptime(value, "%y-%m-%d-%H-%M-%S-%f"))
            # print("     NOW:", now)
            difference = datetime.datetime.strptime(now, "%y-%m-%d-%H-%M-%S-%f") - datetime.datetime.strptime(value, "%y-%m-%d-%H-%M-%S-%f")
            print(difference)
            # print("GOT STRING")
            table1.putString("fromSlave",datetime.datetime.utcnow().strftime("%y-%m-%d-%H-%M-%S-%f"))



    table2.addEntryListener(entryListener)
    table1.putString("fromSlave", datetime.datetime.utcnow().strftime("%y-%m-%d-%H-%M-%S-%f"))
    print("PUT DATA")

    while (1):
        print("", end="")
        table1.putString("fromSlave", datetime.datetime.utcnow().strftime("%y-%m-%d-%H-%M-%S-%f"))
        time.sleep(1)
