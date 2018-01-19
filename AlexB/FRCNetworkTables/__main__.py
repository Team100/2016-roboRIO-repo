## Check out the NT documentation at: http://robotpy.readthedocs.io/en/stable/guide/nt.html#networktables-guide
import TestData
import Delay
import programs
def mode(name):
    if (name == programs.test_data):
        TestData.run()
    elif(name == programs.delay_master):
        Delay.run_master()
    elif(name == programs.delay_slave):
        Delay.run_slave()
    else:
        print("ERROR: INCORRECT MODE")
        exit(0)


mode(programs.delay_master)