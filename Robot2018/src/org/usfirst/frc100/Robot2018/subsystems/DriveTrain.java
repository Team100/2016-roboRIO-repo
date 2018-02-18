// RobotBuilder Version: 2.0
//
// This file was generated by RobotBuilder. It contains sections of
// code that are automatically generated and assigned by robotbuilder.
// These sections will be updated in the future when you export to
// Java from RobotBuilder. Do not put any code or make any change in
// the blocks indicating autogenerated code or it will be lost on an
// update. Deleting the comments indicating the section will prevent
// it from being updated in the future.


package org.usfirst.frc100.Robot2018.subsystems;

import org.usfirst.frc100.Robot2018.OI;
import org.usfirst.frc100.Robot2018.Robot;
import org.usfirst.frc100.Robot2018.RobotMap;
import org.usfirst.frc100.Robot2018.commands.*;

import edu.wpi.first.wpilibj.command.Subsystem;
// BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=IMPORTS
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

// END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=IMPORTS


/**
 *
 */
public class DriveTrain extends Subsystem {

    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTANTS

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTANTS

    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS
    private final WPI_TalonSRX rightMaster = RobotMap.driveTrainRightMaster;
    private final WPI_TalonSRX leftMaster = RobotMap.driveTrainLeftMaster;
    private final DifferentialDrive differentialDrive1 = RobotMap.driveTrainDifferentialDrive1;
    private final WPI_VictorSPX rightFollower = RobotMap.driveTrainRightFollower;
    private final WPI_VictorSPX leftFollower = RobotMap.driveTrainLeftFollower;
    private final Solenoid shiftingSolenoid = RobotMap.driveTrainShiftingSolenoid;

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS

    @Override
    public void initDefaultCommand() {
        // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DEFAULT_COMMAND

        setDefaultCommand(new Drive());

        // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DEFAULT_COMMAND

        // Set the default command for a subsystem here.
        // setDefaultCommand(new MySpecialCommand());
    }

    @Override
    public void periodic() {
        // Put code here to be run every loop
    //	differentialDrive1.arcadeDrive(Robot.oi.leftController.getRawAxis(1), Robot.oi.rightStick.getRawAxis(0));
    }
    public void getJoy(){
    	if(OI.leftController.getRawButton(1)){
        	RobotMap.driveTrainShiftingSolenoid.set(true);
        }else if(OI.rightStick.getRawButton(1)) {
        	
        	RobotMap.driveTrainShiftingSolenoid.set(false);
        }else if(OI.operator.getRawButtonPressed(1)) {
        	RobotMap.driveTrainShiftingSolenoid.set(true);
        }else if(OI.operator.getRawButtonPressed(2)) {
        	RobotMap.driveTrainShiftingSolenoid.set(false);
        }
        if(Robot.DriverStation){
        	SmartDashboard.putNumber("Left Stick", OI.leftController.getY());
        	SmartDashboard.putNumber("Right Stick", OI.rightStick.getY());
        	if(Robot.ArcadeDrive){
        		RobotMap.driveTrainDifferentialDrive1.arcadeDrive(OI.leftController.getY(), OI.rightStick.getX());
        	}else{
        		RobotMap.driveTrainDifferentialDrive1.tankDrive(OI.leftController.getY(), OI.rightStick.getY());
        	}
        }else{
        	SmartDashboard.putNumber("Logitech", OI.operator.getY());
        	if(Robot.ArcadeDrive){
        		RobotMap.driveTrainDifferentialDrive1.arcadeDrive(OI.operator.getRawAxis(1), -OI.operator.getRawAxis(4));
        	}else{
        		RobotMap.driveTrainDifferentialDrive1.tankDrive(-OI.operator.getRawAxis(1), -OI.operator.getRawAxis(5));
        	}
        	
        }
    }
    


    // Put methods for controlling this subsystem
    // here. Call these from Commands.

}

