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

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc100.Robot2016.Robot;
import org.usfirst.frc100.Robot2016.RobotMap;

/**
 *
 */
public class Turn180 extends Command {
	int distances;
	private final SpeedController left = RobotMap.driveTrainLeft;
    private final SpeedController right = RobotMap.driveTrainRight;

	public Turn180()
	{

	 requires(Robot.driveTrain);
    
    
 }
	public Turn180(int angles)
	{
	 distances = angles;
	 requires(Robot.driveTrain);
    
    
 }

 // Called just before this Command runs the first time
 protected void initialize() {
 	// Get everything in a safe starting state.
	 Robot.driveTrain.pid.setPID(Robot.prefs.getDouble("pValue", .04), Robot.prefs.getDouble("iValue", .00), Robot.prefs.getDouble("dValue", .00), 0);
	 Robot.driveTrain.pid.setAbsoluteTolerance(0.2);
	 Robot.driveTrain.pid.setSetpoint((Robot.driveTrain.getAngles()+distances));  //Robot.driveTrain.getAngles+1
	 Robot.driveTrain.pid.reset();
	 Robot.driveTrain.pid.enable();
 }

 // Called repeatedly when this Command is scheduled to run
 protected void execute() {}

 // Make this return true when this Command no longer needs to run execute()
 protected boolean isFinished() {
     return Robot.driveTrain.pid.onTarget();
 }

 // Called once after isFinished returns true
 protected void end() {
 	// Stop PID and the wheels
	 Robot.driveTrain.pid.disable();
     Robot.driveTrain.stop();
 }

 // Called when another command which requires one or more of the same
 // subsystems is scheduled to run
 protected void interrupted() {
     end();
 }

}
