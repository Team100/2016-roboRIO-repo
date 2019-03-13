package org.usfirst.frc100.LegoArmWithEncoder.commands;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc100.LegoArmWithEncoder.Robot;

/**
 *
 */
public class ClawOpen extends Command {

    public ClawOpen() {
        // Use requires() here to declare subsystem dependencies
        requires (Robot.claw);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	Robot.claw.open();
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return true;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
