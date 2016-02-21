// RobotBuilder Version: 2.0
//
// This file was generated by RobotBuilder. It contains sections of
// code that are automatically generated and assigned by robotbuilder.
// These sections will be updated in the future when you export to
// Java from RobotBuilder. Do not put any code or make any change in
// the blocks indicating autogenerated code or it will be lost on an
// update. Deleting the comments indicating the section will prevent
// it from being updated in the future.


package org.usfirst.frc100.Robot2016.commands;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import org.usfirst.frc100.Robot2016.Robot;
import org.usfirst.frc100.Robot2016.RobotMap;

/**
 *
 */
public class ShootingSpeed extends Command {

	private double speed;

    public ShootingSpeed(double speed) {

    	this.speed = speed;
        requires(Robot.shooter);

    }

	// Called just before this Command runs the first time
    protected void initialize() {
    	 // Robot.shooter.enable();
         // Robot.shooter.setSetpoint(speed);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	RobotMap.shooterFlyMotor.set(speed);
    	
    	
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	return Robot.shooter.onTarget();
    }

    // Called once after isFinished returns true
    protected void end() {
    	RobotMap.shooterFlyMotor.set(0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
