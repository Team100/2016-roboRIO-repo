// RobotBuilder Version: 2.0
//
// This file was generated by RobotBuilder. It contains sections of
// code that are automatically generated and assigned by robotbuilder.
// These sections will be updated in the future when you export to
// Java from RobotBuilder. Do not put any code or make any change in
// the blocks indicating autogenerated code or it will be lost on an
// update. Deleting the comments indicating the section will prevent
// it from being updated in the future.


package org.usfirst.frc100.Robot2017.subsystems;

import org.usfirst.frc100.Robot2017.Robot;
import org.usfirst.frc100.Robot2017.RobotMap;
import org.usfirst.frc100.Robot2017.commands.*;

import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.AnalogGyro;
import edu.wpi.first.wpilibj.CounterBase.EncodingType;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.Ultrasonic;
import edu.wpi.first.wpilibj.VictorSP;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;


/**
 *
 */
public class DriveTrain extends Subsystem {
	
	/*
    private final SpeedController leftMotor = RobotMap.driveTrainleftMotor;
    private final SpeedController rightMotor = RobotMap.driveTrainrightMotor;
    private final RobotDrive drive = RobotMap.driveTraindrive;
    private final Encoder leftEncoder = RobotMap.driveTrainleftEncoder;
    private final Encoder rightEncoder = RobotMap.driveTrainrightEncoder;
    private final Solenoid leftShifter = RobotMap.driveTrainleftShifter;
    private final Solenoid rightShifter = RobotMap.driveTrainrightShifter;
    private final AnalogGyro digialGyroUno = RobotMap.driveTraindigialGyroUno;
    private final Ultrasonic ultraSanic = RobotMap.driveTrainultraSanic;
    */
	public static Encoder driveTrainLeftEncoder = RobotMap.driveTrainLeftEncoder;
	public static Encoder driveTrainRightEncoder = RobotMap.driveTrainRightEncoder;

	
	private final RobotDrive robotDrive = RobotMap.driveTrainRobotDrive;
	
	public static ADXRS450_Gyro gyro = RobotMap.gyro;
	
    public double angle; 

    public void initDefaultCommand() {
        setDefaultCommand(new TankDrive()); 
    }
    
    public void driveRobot(Joystick joy, Joystick joy2){
    	robotDrive.tankDrive(joy.getRawAxis(1), -joy2.getRawAxis(1));
    }

    public void stop(){
    	robotDrive.tankDrive(0, 0);
    }

	public void updateDashboard() {
		SmartDashboard.putNumber("DriveTrain/LeftMotor: ", leftMotor.get());
		SmartDashboard.putNumber("DriveTrain/RightMotor: ", rightMotor.get());
		SmartDashboard.putNumber("DriveTrain/LeftEncoder Rate: ", leftEncoder.getRate());
		SmartDashboard.putNumber("DriveTrain/LeftEncoder Distance", leftEncoder.getDistance());
		SmartDashboard.putNumber("DriveTrain/LeftEncoder Count: ", leftEncoder.getRaw());
		SmartDashboard.putNumber("DriveTrain/RightEncoder Rate: ", rightEncoder.getRate());
		SmartDashboard.putNumber("DriveTrain/RightEncoder Distance", rightEncoder.getDistance());
		SmartDashboard.putNumber("DriveTrain/RightEncoder Count: ", rightEncoder.getRaw());
		SmartDashboard.putNumber("DriveTrain/Gyro Rate: ", digialGyroUno.getRate());
		SmartDashboard.putNumber("DriveTrain/Gyro Angle: ", digialGyroUno.getAngle());
		SmartDashboard.putNumber("DriveTrain/Ultrasonic Range: ", ultraSanic.getRangeInches());
		
	}
}


