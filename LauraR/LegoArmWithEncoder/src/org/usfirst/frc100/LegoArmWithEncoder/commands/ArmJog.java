package org.usfirst.frc100.LegoArmWithEncoder.commands;

import org.usfirst.frc100.LegoArmWithEncoder.Robot;
import org.usfirst.frc100.LegoArmWithEncoder.subsystems.RobotArm;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class ArmJog extends Command {
	final RobotArm.Direction direction;
	private boolean m_isDone = true;

    public ArmJog(RobotArm.Direction dir) {
        // Use requires() here to declare subsystem dependencies
    	requires(Robot.robotArm);
    	direction = dir;
    	
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	System.out.println("ArmJog " + (direction == RobotArm.Direction.kUp ? "Up" : "Down"));
    	m_isDone = false;
    	// get most recent jog speed from preferences
    	Robot.robotArm.setPIDforSpeedControl();
    	double slewVelocity = Robot.robotArm.m_motionPreferences.get_slewVelocity();
    	double sp = (direction == RobotArm.Direction.kUp) ? slewVelocity : - slewVelocity;   			
    	Robot.robotArm.m_pidController.setSetpoint(sp);
    	Robot.robotArm.m_pidController.enable();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	//Just let the PID controller do it's thing while running
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return m_isDone;
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.robotArm.m_pidController.disable();
    	System.out.println("ArmJog complete");
    	Robot.robotArm.stop();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
