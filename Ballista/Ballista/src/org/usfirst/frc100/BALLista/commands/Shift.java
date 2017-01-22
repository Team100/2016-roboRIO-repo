package org.usfirst.frc100.BALLista.commands;

import edu.wpi.first.wpilibj.command.Command;

import org.usfirst.frc100.BALLista.Robot;

/**
 * Shifts to low gear while the command is running, then returns to high gear
 * when it terminates.
 */
public class Shift extends Command {

	public Shift() {
	}

	// Called just before this Command runs the first time
	protected void initialize() {
		Robot.driveTrain.shift(false);
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		if(Robot.driveTrain.lowGear){
			Robot.driveTrain.highGear = true;
		}
	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		return false;
	}

	// Called once after isFinished returns true
	protected void end() {
		Robot.driveTrain.shift(true);
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
		end();
	}
}
