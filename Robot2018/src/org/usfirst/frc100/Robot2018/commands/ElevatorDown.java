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

import org.usfirst.frc100.Robot2018.Robot;
import org.usfirst.frc100.Robot2018.RobotMap;

import com.ctre.phoenix.motorcontrol.ControlMode;

/**
 *
 */
public class ElevatorDown extends Command {
	private boolean done;
    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=VARIABLE_DECLARATIONS
 
    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=VARIABLE_DECLARATIONS

    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTRUCTOR
    public ElevatorDown() {

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTRUCTOR
        // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=VARIABLE_SETTING

        // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=VARIABLE_SETTING
        // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=REQUIRES
        requires(Robot.elevator);

        // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=REQUIRES
    }

    // Called just before this Command runs the first time
    @Override
    protected void initialize() {
    	done=false;
    	RobotMap.elevatorElevatorTalon.set(ControlMode.MotionMagic, 500);
    }

    // Called repeatedly when this Command is scheduled to run
    @Override
    protected void execute() {
    	//RobotMap.elevatorElevatorTalon.set(ControlMode.MotionMagic, 100);
    	SmartDashboard.putNumber("ElevatorMaster",RobotMap.elevatorElevatorTalon.getMotorOutputPercent());
    	SmartDashboard.putNumber("ElevatorVel", RobotMap.elevatorElevatorTalon.getSelectedSensorVelocity(0));
    	SmartDashboard.putNumber("ElevatorSlave1",RobotMap.elevatorElevatorVictor.getMotorOutputPercent());
    	SmartDashboard.putNumber("ElevatorSlave2", RobotMap.elevatorElevatorVictor2.getMotorOutputPercent());
    	SmartDashboard.putNumber("MMTrajVelo", RobotMap.elevatorElevatorTalon.getActiveTrajectoryVelocity());
    	SmartDashboard.putNumber("MMTrajPos", RobotMap.elevatorElevatorTalon.getActiveTrajectoryPosition());
    	/*if(RobotMap.elevatorElevatorTalon.getSelectedSensorPosition(0) == 510 && RobotMap.elevatorElevatorTalon.getSelectedSensorPosition(0) == 490){
    		done = true;
    	}*/
    }

    // Make this return true when this Command no longer needs to run execute()
    @Override
    protected boolean isFinished() {
    	return done;
    }

    // Called once after isFinished returns true
    @Override
    protected void end() {
    	//RobotMap.elevatorElevatorTalon.set(ControlMode.PercentOutput, 0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    @Override
    protected void interrupted() {
    	end();
    }
}