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
public class RollOut extends Command {

	boolean rollOutDirection = true;
	double speed;

	public RollOut() {
        requires(Robot.pickUpRoller);
	}
	public RollOut(double speed){
		requires(Robot.pickUpRoller);
		this.speed = speed;
	}

	public RollOut(boolean rollerOut) {
		rollOutDirection = rollerOut;
	}

	// Called just before this Command runs the first time
	protected void initialize() {

	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {

		//if(RobotMap.pickUpHomeLimit.get())
		//if(Math.abs(speed) > 0)
		Robot.pickUpRoller.setRollerSpeed(speed);
	//	else
		//Robot.moveRollIn.stop();
		//else
			//obot.moveRollIn.setRollerSpeed(0);
	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		return false;
	}

	// Called once after isFinished returns true
	protected void end() {
		Robot.pickUpRoller.stop();
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
		end();
	}
}