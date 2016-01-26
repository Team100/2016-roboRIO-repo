
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
    
    
    public Drive(double setpoint) {
        this.setpoint = setpoint;
        requires(Robot.drive);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
        Robot.drive.enable();
       Robot.drive.setSetpoint(setpoint);

    }

    // Called repeatedly when this Command is scheduled to run
    public void execute() {
    	//SmartDashboard.putNumber("setpoint", setpoint);
    
    }
    

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	
        return Robot.drive.onTarget();
       
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
   
}