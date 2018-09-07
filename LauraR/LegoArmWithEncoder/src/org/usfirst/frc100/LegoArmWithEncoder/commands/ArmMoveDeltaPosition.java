package org.usfirst.frc100.LegoArmWithEncoder.commands;

import org.usfirst.frc100.LegoArmWithEncoder.Robot;
import org.usfirst.frc100.LegoArmWithEncoder.subsystems.RobotArm;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class ArmMoveDeltaPosition extends Command {
	final RobotArm.Direction direction;

    public ArmMoveDeltaPosition(RobotArm.Direction dir) {
        // Use requires() here to declare subsystem dependencies
    	requires(Robot.robotArm);
    	direction = dir;
    	
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	// get most recent position, speed, accel, decel settings from preferences
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	System.out.println("ArmMoveDeltaPosition" + (direction == RobotArm.Direction.kUp ? "Up" : "Down"));
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
