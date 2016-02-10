package org.usfirst.frc100.Lego_Arm_PID.commands;

import org.usfirst.frc100.Lego_Arm_PID.Preferences;

import edu.wpi.first.wpilibj.command.Command;

/**
 * Read or writes the Preferences on either the SmartDashboard or the RoboRIO
 * file.
 */
public class SyncPreferences extends Command {

	private final boolean read;
	
	/**
	 * @param read - Reading or writing preferences
	 * @param fromFile - RoboRIO file or SmartDashboard
	 */
    public SyncPreferences(boolean read) {
    	this.read = read;
	}

	// Called just before this Command runs the first time
	protected void initialize() {
		if (read) {
			Preferences.read();
		} else {
			Preferences.write();
		}
	}

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
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
