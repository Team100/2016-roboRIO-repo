// RobotBuilder Version: 1.5
//
// This file was generated by RobotBuilder. It contains sections of
// code that are automatically generated and assigned by robotbuilder.
// These sections will be updated in the future when you export to
// Java from RobotBuilder. Do not put any code or make any change in
// the blocks indicating autogenerated code or it will be lost on an
// update. Deleting the comments indicating the section will prevent
// it from being updated in the future.

package org.usfirst.frc0.Robot2.subsystems;

import org.usfirst.frc0.Robot2.PID;
import org.usfirst.frc0.Robot2.Robot;
import org.usfirst.frc0.Robot2.RobotMap;
import org.usfirst.frc0.Robot2.commands.*;
import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.command.PIDSubsystem;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class DriveTrain extends PIDSubsystem {
	// BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS
	SpeedController speedController4 = RobotMap.driveTrainSpeedController4;
	SpeedController speedController1 = RobotMap.driveTrainSpeedController1;
	SpeedController speedController2 = RobotMap.driveTrainSpeedController2;
	SpeedController speedController3 = RobotMap.driveTrainSpeedController3;
	RobotDrive robotDrive41 = RobotMap.driveTrainRobotDrive41;
	AnalogGyro gyros = RobotMap.gyro;
	// END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS
	// public int returnRange;
    
	double angle;
	//public Object stop;
	static double integrolValue = TankDrive.iValue2;
	// Put methods for controlling this subsystem
	// here. Call these from Commands.
	public DriveTrain()
	{
		 super("DriveTrain", PID.p(), PID.I() , PID.D());
		 getPIDController().setContinuous(false);
		 
		 /*
	        if (Robot.isSimulation()) { // Check for simulation and update PID values
	            getPIDController().setPID(.2, .2, 0, 0);
	        }
	        */
	        setAbsoluteTolerance(.5);
	        LiveWindow.addActuator("drivetrian", "PID", getPIDController());
	        //LiveWindow.addSensor("stuff", "gyro", (AnalogPotentiometer) pots);
	        LiveWindow.addSensor("gyro" , "gyro", RobotMap.internalGyro);

	}
	public void initDefaultCommand() {
		// BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DEFAULT_COMMAND
		setDefaultCommand(new TankDrive());

		// END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DEFAULT_COMMAND

		// Set the default command for a subsystem here.
		// setDefaultCommand(new MySpecialCommand());
	}

	public void robotInit() {
		// turns on automatic mode
	}

	// public void ultrasonicSample() {
	// double range = ultra.getRangeInches(); // reads the range on the
	// ultrasonic sensor
	// }
	// public double range()
	// {
	// double range = ultra.getRangeInches();
	// return range;
	// }

	public void tankIt(Joystick stick) {
		robotDrive41.arcadeDrive(stick.getRawAxis(1), -stick.getRawAxis(2));
		// robotDrive41.arcadeDrive(stick.getRawAxis(1), -stick.getRawAxis(2));
		// robotDrive41.arcadeDrive(-stick.getRawAxis(1), stick.getRawAxis(2));
	}

	public void tankItReverse(Joystick joy) {
		robotDrive41.arcadeDrive(-joy.getRawAxis(1), -joy.getRawAxis(2));

	}

	public void stop() {
		robotDrive41.arcadeDrive(0.0, 0.0);
	}

	public void drives(double d, double e) {
		// TODO Auto-generated method stub
		robotDrive41.drive(d, e);

	}
    public void initiGyro()
    {
    	
    	gyros.initGyro();
    }
	public void resetGyro() {
		gyros.reset();
		

		// drive towards heading 0
	}

	public double getAngleOfGyro() {
		angle = gyros.getAngle();
		return angle;
	}
	public void drive()
	{
		drives(.15, -getAngleOfGyro()*.03);//.getAngleOfGyro());
		SmartDashboard.putNumber("heading", getAngleOfGyro()*0.03);
	}
	public void goToAngle(double destinationAngle)
	{
		SmartDashboard.putNumber("angle", gyros.getAngle());
		SmartDashboard.putNumber("destination", destinationAngle);
		if ( gyros.getAngle() >= destinationAngle + 2 || gyros.getAngle()<= destinationAngle -.5) {
		    //we're there, stop turning
			robotDrive41.tankDrive(0,0);
		} else if ( destinationAngle  > gyros.getAngle()) {
		    //rotate clockwise
			robotDrive41.tankDrive(.40, -.40);
		} else {
		    //rotate counter-clockwise
			robotDrive41.tankDrive(-.40, .40);
		}
	}
	@Override
	protected double returnPIDInput() {
		// TODO Auto-generated method stub
		return RobotMap.internalGyro.getAngle();
	}
	@Override
	protected void usePIDOutput(double output) {
	RobotMap.driveTrainSpeedController1.pidWrite(output/1.5);
	RobotMap.driveTrainSpeedController2.pidWrite(output/1.5);
		
	}
	
	public void updateSetpoint(double incrementalAngle)
	{
		//double sp = Robot.driveTrain.getSetpoint();
		// sp += incrementalAngle;
		 Robot.driveTrain.setSetpoint(incrementalAngle);
		 //return sp;
		 
	}

	
		
}




