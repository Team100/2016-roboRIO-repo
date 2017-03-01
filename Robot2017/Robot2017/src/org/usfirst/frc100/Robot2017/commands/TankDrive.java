package org.usfirst.frc100.Robot2017.commands;

import org.usfirst.frc100.Robot2017.Robot;
import org.usfirst.frc100.Robot2017.RobotMap;

import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class TankDrive extends Command {
	
	 boolean direction = true;
	 int distanceTraveled;
	 int goToAngles; 
	 public int setpoint;
	 public double iValue;
	 public static double iValue2;
     int incrementalAngle;
 
	 public TankDrive(){
		requires(Robot.driveTrain); 
	 }
	 
	 public TankDrive(boolean driving) {
    	direction = driving; 
    	requires(Robot.driveTrain); 
    }
	 
	public TankDrive(int incrementalAngle){
		this.incrementalAngle = incrementalAngle; 
		requires(Robot.driveTrain);
	}
	 
    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	SmartDashboard.putNumber("Angle", RobotMap.gyro.getAngle()); 
    	//SmartDashboard.putNumber("Number", Robot.driveTrain.getSetPoint());
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        //return Robot.driveTrain.onTarget(); 
    	return false; 
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.driveTrain.stop(); 
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	this.end(); 
    }
}