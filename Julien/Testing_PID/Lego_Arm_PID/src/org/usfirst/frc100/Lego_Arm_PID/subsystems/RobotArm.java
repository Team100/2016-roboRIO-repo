// RobotBuilder Version: 1.5
//
// This file was generated by RobotBuilder. It contains sections of
// code that are automatically generated and assigned by robotbuilder.
// These sections will be updated in the future when you export to
// Java from RobotBuilder. Do not put any code or make any change in
// the blocks indicating autogenerated code or it will be lost on an
// update. Deleting the comments indicating the section will prevent
// it from being updated in the future.


package org.usfirst.frc100.Lego_Arm_PID.subsystems;

import org.usfirst.frc100.Lego_Arm_PID.RobotMap;
import org.usfirst.frc100.Lego_Arm_PID.commands.*;
import org.usfirst.frc100.Lego_Arm_PID.Preferences;
import org.usfirst.frc100.Lego_Arm_PID.PID;

import edu.wpi.first.wpilibj.*;

import edu.wpi.first.wpilibj.command.Subsystem;


/**
 *
 */
public class RobotArm extends Subsystem {
    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS
    Servo armContinuousRotationServo = RobotMap.robotArmArmContinuousRotationServo;
    DigitalInput armUpperLimit = RobotMap.robotArmArmUpperLimit;
    DigitalInput armLowerLimit = RobotMap.robotArmArmLowerLimit;
    AnalogPotentiometer armPositionPot = RobotMap.robotArmArmPositionPot;

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS

    
    // Put methods for controlling this subsystem
    // here. Call these from Commands.

    public void initDefaultCommand() {
        // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DEFAULT_COMMAND
        setDefaultCommand(new HoldIt());

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DEFAULT_COMMAND
	
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
    
    public void raise() {
    	if (isAtHighLimit()) {
    		stop();
    	} else {
    		armContinuousRotationServo.set(0.0);  
    	}
    }
    
    public void lower() {
    	if (isAtLowLimit()) {
    		stop();
    	}
    	else {
    		armContinuousRotationServo.set(1.0);
    	}
    }
    private PID RobotArmPID = new PID("RobotArm");
    public void stop() {
    	armContinuousRotationServo.set(0.5);
    }
    public void robotInit() {
    	Preferences.init();
    	RobotMap.init();}
    public boolean isAtLowLimit() {
    	return armLowerLimit.get();
    }
    
    public boolean isAtHighLimit() {
    	return armUpperLimit.get();
    }
    
    public double getPotValue() {
    	return armPositionPot.get();
    }

	public void setAutoTarget(double target) {
		// TODO Auto-generated method stub
		
	}

	public void updatePID() {
		// TODO Auto-generated method stub
		
	}

	public boolean isInPosition() {
		// TODO Auto-generated method stub
		return false;
	}

	public void activateBrake() {
		// TODO Auto-generated method stub
		
	}

	public void drive(int i, int j, int k) {
		// TODO Auto-generated method stub
		
	}

	public void updateDashboard() {
		// TODO Auto-generated method stub
		
	}

	
}

