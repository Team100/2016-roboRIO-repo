// RobotBuilder Version: 2.0
//
// This file was generated by RobotBuilder. It contains sections of
// code that are automatically generated and assigned by robotbuilder.
// These sections will be updated in the future when you export to
// Java from RobotBuilder. Do not put any code or make any change in
// the blocks indicating autogenerated code or it will be lost on an
// update. Deleting the comments indicating the section will prevent
// it from being updated in the future.


package org.usfirst.frc100.Robot2018.commands;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import org.usfirst.frc100.Robot2018.OI;
import org.usfirst.frc100.Robot2018.Robot;
import org.usfirst.frc100.Robot2018.RobotMap;

import com.ctre.phoenix.motorcontrol.ControlMode;

import org.usfirst.frc100.Robot2018.Robot;

/**
 *
 */
public class Home extends Command {
	
	public static double offset;
	public static boolean done; 
	public static int Vel;
	public static double Offset;

    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=VARIABLE_DECLARATIONS
 
    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=VARIABLE_DECLARATIONS

    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTRUCTOR
    public Home() {

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTRUCTOR
        // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=VARIABLE_SETTING

        // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=VARIABLE_SETTING
        // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=REQUIRES
        
    	requires(Robot.driveTrain);

        // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=REQUIRES
    }

    // Called just before this Command runs the first time
    @Override
    protected void initialize() {
    	done = false;
    	Vel = 30;
    }

    // Called repeatedly when this Command is scheduled to run
    @Override
    protected void execute() {
    	//System.out.println("run");
    	//RobotMap.driveTrainDifferentialDrive1.arcadeDrive(OI.operator.getRawAxis(1), -OI.operator.getRawAxis(4));
    	RobotMap.elevatorElevatorTalon.set(ControlMode.PercentOutput, -Vel);
    	if(RobotMap.elevatorElevatorLim1.get()){
    		Vel = 0; 
    		offset = RobotMap.elevatorElevatorTalon.getSelectedSensorPosition(0);
    		RobotMap.elevatorElevatorTalon.getSelectedSensorPosition(0);
    		RobotMap.elevatorElevatorTalon.set(ControlMode.PercentOutput, Vel);
    		if(RobotMap.elevatorElevatorTalon.getSelectedSensorVelocity(0) == 0){
    			done = true;
    		} else { 
    			done = false;
    		}
    	}
    	
    }

    // Make this return true when this Command no longer needs to run execute()
    @Override
    protected boolean isFinished() {
        return done;
    }

    // Called once after isFinished returns true
    @Override
    protected void end() {
    	System.out.println("done");
    	RobotMap.elevatorElevatorTalon.getSelectedSensorPosition(0);
    	RobotMap.elevatorElevatorTalon.set(ControlMode.PercentOutput, Vel);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    @Override
    protected void interrupted() {
    }
}