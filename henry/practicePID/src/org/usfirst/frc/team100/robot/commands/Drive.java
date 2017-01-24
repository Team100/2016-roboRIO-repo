
package org.usfirst.frc.team100.robot.commands;

import edu.wpi.first.wpilibj.command.Command;

import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import org.usfirst.frc.team100.robot.OI;
import org.usfirst.frc.team100.robot.Robot;


/**
 *
 */
public class Drive extends Command {
    private double setpoint;
    private boolean endPID = false;
    
    
    
    public Drive(double setpoint) {
        this.setpoint = setpoint;
      //  endPID = true;
        requires(Robot.drive);
    }
    
    public Drive (boolean end){
    	endPID = end;
   // 	Robot.drive.pid.disable();
    }

    // Called just before this Command runs the first time
    protected void initialize() {
       Robot.drive.pid.enable();
       Robot.drive.pid.setSetpoint(setpoint);
       Robot.drive.pid.setAbsoluteTolerance(.5);

    }

    // Called repeatedly when this Command is scheduled to run
    public void execute() {
    	SmartDashboard.putNumber("setpoint", setpoint);
    	if(endPID == true){
    		 Robot.drive.pid.disable();
    	}
    
    }
    

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	
    	return endPID;
      // return Robot.drive.pid.onTarget();
        
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.drive.shooter.set(0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
   
}