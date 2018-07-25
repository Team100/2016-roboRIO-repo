// RobotBuilder Version: 1.5
//
// This file was generated by RobotBuilder. It contains sections of
// code that are automatically generated and assigned by robotbuilder.
// These sections will be updated in the future when you export to
// Java from RobotBuilder. Do not put any code or make any change in
// the blocks indicating autogenerated code or it will be lost on an
// update. Deleting the comments indicating the section will prevent
// it from being updated in the future.


package org.usfirst.frc100.LegoArmWithEncoder.subsystems;

import org.usfirst.frc100.LegoArmWithEncoder.RobotMap;
import org.usfirst.frc100.LegoArmWithEncoder.commands.*;

import edu.wpi.first.wpilibj.*;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;


/**
 *
 */
public class DriveTrain extends Subsystem {
    private final Victor left = RobotMap.driveTrainLeft;
    private final Victor right = RobotMap.driveTrainRight;
    private final AnalogInput iRDistanceSensor = RobotMap.driveTrainIRDistanceSensor;
    private final DifferentialDrive twoMotorDrive; 

	public DriveTrain() {
		left.setInverted(true);
        right.setInverted(false);
        addChild("Left DD Motor",  left);
	    addChild("Right DD Motor",  right);	    
	    addChild("IR Distance", iRDistanceSensor);
	    	    
        twoMotorDrive = new DifferentialDrive(left, right);        
        twoMotorDrive.setSafetyEnabled(false);
        twoMotorDrive.setExpiration(0.1);
        twoMotorDrive.setMaxOutput(1.0);
        addChild("Drive", twoMotorDrive);      		
	}
	
    // Put methods for controlling this subsystem
    // here. Call these from Commands.

    public void initDefaultCommand() {
        // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DEFAULT_COMMAND
        setDefaultCommand(new TankDrive());

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DEFAULT_COMMAND
	
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
	public void tankIt(Joystick stick) {
    	twoMotorDrive.tankDrive(stick.getRawAxis(1), -stick.getRawAxis(3));
    }
    
    public void stop() {
    	twoMotorDrive.tankDrive(0.0, 0.0);
    }
    
    public double getDistance() {
    	return iRDistanceSensor.getValue();
    }
}

