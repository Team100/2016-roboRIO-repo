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
import org.usfirst.frc100.LegoArmWithEncoder.commands.TankDrive;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;


/**
 *
 */
public class DriveTrain extends Subsystem {
    private final Victor left = RobotMap.driveTrainLeft;
    private final Victor right = RobotMap.driveTrainRight;
    private final AnalogInput iRDistanceSensor = RobotMap.driveTrainIRDistanceSensor;
    private final DifferentialDrive twoMotorDrive; 
    final static String ntPrefix = "DriveTrain/"; // Prefix for network table variables

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
	
    public void initDefaultCommand() {
        setDefaultCommand(new TankDrive());
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
    public void updateDashboard()
    {
    	// Show variables in SmartDashboard
        SmartDashboard.putNumber(ntPrefix + "IR Distance", getDistance());
        SmartDashboard.putNumber(ntPrefix + "LeftMotor", 0.001 * Math.round(left.get()*1000));
        SmartDashboard.putNumber(ntPrefix + "RightMotor", 0.001 * Math.round(right.get()*1000));
    }
}

