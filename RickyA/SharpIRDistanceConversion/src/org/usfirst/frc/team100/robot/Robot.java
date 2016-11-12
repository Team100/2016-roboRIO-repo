
package org.usfirst.frc.team100.robot;

import java.text.DecimalFormat;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot {

	AnalogInput sharpIR;
	DecimalFormat formatter;
	
    public void robotInit() {
      sharpIR = new AnalogInput(2);
      formatter = new DecimalFormat("#0.00");
    }
    
	
    public void autonomousInit() {

    }

  
    public void autonomousPeriodic() {
    	
    }
    		
    
    public void teleopPeriodic() {
       SmartDashboard.putNumber("Voltage: ", sharpIR.getVoltage());
       /* Original Equation */
       //SmartDashboard.putString("Distance: ", formatter.format((15.627*Math.pow(sharpIR.getVoltage(), -0.817))) + " inches");
       
       SmartDashboard.putString("Distance: ", formatter.format((12.627*Math.pow(sharpIR.getVoltage(), -0.750))) + " inches");

    }
   
    public void testPeriodic() {
    
    }
    
}
