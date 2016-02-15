// RobotBuilder Version: 2.0
//
// This file was generated by RobotBuilder. It contains sections of
// code that are automatically generated and assigned by robotbuilder.
// These sections will be updated in the future when you export to
// Java from RobotBuilder. Do not put any code or make any change in
// the blocks indicating autogenerated code or it will be lost on an
// update. Deleting the comments indicating the section will prevent
// it from being updated in the future.


package org.usfirst.frc100.Robot2016.subsystems;

import org.usfirst.frc100.Robot2016.RobotMap;
import org.usfirst.frc100.Robot2016.commands.TankDrive;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.command.Subsystem;


/**
 *
 */
public class DriveTrain extends Subsystem {

    private final SpeedController left = RobotMap.driveTrainLeft;
    private final SpeedController right = RobotMap.driveTrainRight;
    private final RobotDrive twoMotorDrive = RobotMap.driveTrainTwoMotorDrive;
    private final AnalogInput iRDistanceSensor = RobotMap.driveTrainIRDistanceSensor;
    private final Encoder leftEncoder = RobotMap.driveTrainLeftEncoder;
    private final Encoder rightEncoder = RobotMap.driveTrainRightEncoder;

    // Put methods for controlling this subsystem
    // here. Call these from Commands.

    public void initDefaultCommand() {

        setDefaultCommand(new TankDrive());
        // Set the default command for a subsystem here.
        // setDefaultCommand(new MySpecialCommand());

    }

    public void takeJoystickInputs(double x, double y){

    twoMotorDrive.arcadeDrive(x, y);

    }

    public void takeJoystickInputsReverse(double x,  double y){

        twoMotorDrive.arcadeDrive(-x, y);

    }

    public void stop(){

    	twoMotorDrive.drive(0, 0);

    }

    public double getAngles(){

    	return RobotMap.internalGyro.getAngle(); //add the gyro

    }

    }

