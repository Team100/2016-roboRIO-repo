// RobotBuilder Version: 2.0
//
// This file was generated by RobotBuilder. It contains sections of
// code that are automatically generated and assigned by robotbuilder.
// These sections will be updated in the future when you export to
// Java from RobotBuilder. Do not put any code or make any change in
// the blocks indicating autogenerated code or it will be lost on an
// update. Deleting the comments indicating the section will prevent
// it from being updated in the future.

package org.usfirst.frc100.BALLista.commands;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import org.usfirst.frc100.BALLista.Robot;
import org.usfirst.frc100.BALLista.RobotMap;

/**
 *
 */
public class RollIn extends Command {

	boolean rollInDirection = true;
	double speed;
	public RollIn() {
		requires(Robot.moveRollIn);
	}

	public RollIn(boolean rollerIn) {
		rollInDirection = rollerIn;
	}
	public RollIn(double speed){
		this.speed = speed;
		requires(Robot.moveRollIn);
	}

	// Called just before this Command runs the first time
	protected void initialize() {

	}

	// Called repeatedly when this Command is scheduled to run

	protected void execute() {
		SmartDashboard.putNumber("pick up running", 123123);
		if (RobotMap.pickUpHomeLimit.get())
			Robot.moveRollIn.setRollerSpeed(.8); //.5
		else
			Robot.moveRollIn.setRollerSpeed(0);
		
	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		return false;
	}

	// Called once after isFinished returns true
	protected void end() {
		Robot.moveRollIn.stop();
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
		end();
	}
}
