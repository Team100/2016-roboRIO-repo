
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
        requires(Robot.drive);
        endPID = false;
  
    }
    
    public Drive (boolean end){
    	endPID = end;
    	
    	 
   // 	Robot.drive.pid.disable();
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	if(!endPID){
    	Robot.drive.pid.reset();
    	Robot.drive.pid.setSetpoint(setpoint);
        Robot.drive.pid.enable();
        Robot.drive.pid.setAbsoluteTolerance(.5);
    	}
    	else{
    	Robot.drive.pid.setSetpoint(0);
    	Robot.drive.pid.disable();
    	
    	}

    }

    // Called repeatedly when this Command is scheduled to run
    public void execute() {
    	Robot.drive.moveRightSide(Robot.oi.joy);
    	SmartDashboard.putNumber("set", Robot.drive.pid.getSetpoint());
    
    }
    

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	
    	return endPID;
      // return Robot.drive.pid.onTarget();
        
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.rightMaster.set(0);
   }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
   
}