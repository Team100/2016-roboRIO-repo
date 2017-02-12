// RobotBuilder Version: 2.0
//
// This file was generated by RobotBuilder. It contains sections of
// code that are automatically generated and assigned by robotbuilder.
// These sections will be updated in the future when you export to
// Java from RobotBuilder. Do not put any code or make any change in
// the blocks indicating autogenerated code or it will be lost on an
// update. Deleting the comments indicating the section will prevent
// it from being updated in the future.


package org.usfirst.frc100.KitBotPneumatics.subsystems;


import org.usfirst.frc100.KitBotPneumatics.Robot;
import org.usfirst.frc100.KitBotPneumatics.RobotMap;
import org.usfirst.frc100.KitBotPneumatics.commands.*;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;



/**
 *
 */
public class DriveTrain extends Subsystem {
	private static final double DEFAULT_DRIVE_TRAIN_KP = .9; //.004
	private static final double DEFAULT_DRIVE_TRAIN_KI = 0.00;
	private static final double DEFAULT_DRIVE_TRAIN_KF = .58823;

	public double driveTrain_kP;
	public double driveTrain_kI;
	public double driveTrain_kF;

    private final SpeedController right = RobotMap.driveTrainright;
    private final SpeedController left = RobotMap.driveTrainleft;
    private final RobotDrive robotDrive = RobotMap.driveTrainRobotDrive;
    public double maxOutput;
    public double maxSpeedReached = 0;
    public int countL = 0;
    public int countR = 0;
    public int count = 0;
    public int countTwo = 0;
    
    public boolean highGear = false;
    public boolean lowGear = true;

    public DriveTrain() {
    	if (!Robot.prefs.containsKey("driveTrain_kP")) {
			Robot.prefs.putDouble("driveTrain_kP", DEFAULT_DRIVE_TRAIN_KP);
		}
		if (!Robot.prefs.containsKey("driveTrain_kI")) {
			Robot.prefs.putDouble("driveTrain_kI", DEFAULT_DRIVE_TRAIN_KI);
		}
		if (!Robot.prefs.containsKey("driveTrain_kF")) {
			Robot.prefs.putDouble("driveTrain_kF", DEFAULT_DRIVE_TRAIN_KF);
		}

		driveTrain_kP = Robot.prefs.getDouble("driveTrain_kP",
				DEFAULT_DRIVE_TRAIN_KP);
		driveTrain_kI = Robot.prefs.getDouble("driveTrain_kI",
				DEFAULT_DRIVE_TRAIN_KI);
		driveTrain_kF = Robot.prefs.getDouble("driveTrain_kF",
				DEFAULT_DRIVE_TRAIN_KF);
    	
    	
    }
    // Put methods for controlling this subsystem
    // here. Call these from Commands.

    @Override
	public void initDefaultCommand() {
        setDefaultCommand(new ArcadeDrive());
    }
    
	public void driveRobot(Joystick joy) {
		robotDrive.tankDrive(-joy.getRawAxis(1), joy.getRawAxis(5));
		//RobotMap.leftMaster.set(joy.getRawAxis(1));
		SmartDashboard.putNumber("joystick output",joy.getRawAxis(1));//, -joystick1.getRawAxis(2));		
	}

	public void stop() {
		robotDrive.arcadeDrive(0, 0);
		countTwo = 0;
		count = 0;
		// TODO Auto-generated method stub
		
	}
	
	public void shiftHighGear(){
		highGear = true;
		lowGear = false;
	}
	
	public void shiftLowGear(){
		highGear = false;
		lowGear = true;
	}
}

