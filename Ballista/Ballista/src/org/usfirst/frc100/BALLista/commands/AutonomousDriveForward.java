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

import org.usfirst.frc100.BALLista.Robot;
import org.usfirst.frc100.BALLista.RobotMap;

/**
 *
 */
public class AutonomousDriveForward extends Command {
	double distance;
    public AutonomousDriveForward(int distance) {
    	this.distance = distance;
        requires(Robot.driveTrain);

    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	RobotMap.driveTrainRightEncoder.reset();
    	RobotMap.driveTrainRightEncoder.reset();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	Robot.driveTrain.drives();
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return (!((RobotMap.driveTrainRightEncoder.getDistance() +RobotMap.driveTrainLeftEncoder.getDistance()/2) >= distance));
    }

    // Called once after isFinished returns true
    protected void end() {
    	RobotMap.driveTrainTwoMotorDrive.drive(0, 0);
    }
    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}