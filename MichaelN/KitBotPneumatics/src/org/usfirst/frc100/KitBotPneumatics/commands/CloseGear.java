package org.usfirst.frc100.KitBotPneumatics.commands;

import org.usfirst.frc100.KitBotPneumatics.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class CloseGear extends Command {

    public CloseGear() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.gear);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	Robot.gear.setPiston(true);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
