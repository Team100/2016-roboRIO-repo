package org.usfirst.frc100.LegoArmWithEncoder.commands;

import org.usfirst.frc100.LegoArmWithEncoder.Robot;
import org.usfirst.frc100.LegoArmWithEncoder.subsystems.RobotArm;
import org.usfirst.frc100.LegoArmWithEncoder.util.MotionPreferences;
import org.usfirst.frc100.LegoArmWithEncoder.util.SingleAxisPathPlanner;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class ArmMoveDeltaPosition extends Command {
	final RobotArm.Direction direction;
	private SingleAxisPathPlanner m_pathPlanner;
	private double m_initPosition = 0;
	private boolean m_isDone = true;
	private Timer m_pathTimer = new Timer();

    public ArmMoveDeltaPosition(RobotArm.Direction dir) {
        // Use requires() here to declare subsystem dependencies
    	requires(Robot.robotArm);
    	direction = dir;
    	
    }
    private void initMotionPrefs() {
    	// Note that for items in the Preferences table, if they don't exist, we must manually add them
    	// I believe that this behavior is different than in previous versions of the WPILIB Preferences class
    	MotionPreferences mp = MotionPreferences.getInstance();
    	mp.update();
    	m_initPosition = Robot.robotArm.getEncoderPosition();
    	m_pathPlanner = new SingleAxisPathPlanner(direction == RobotArm.Direction.kUp ? mp.get_moveDistance(): -mp.get_moveDistance(), 
    												mp.get_slewVelocity(), 
    												mp.get_accel(), 
    												mp.get_decel(), 
    												mp.get_initVelocity(),
    												mp.get_finalVelocity(), 
    												m_initPosition, 
    												mp.get_startTime());
     }


    // Called just before this Command runs the first time
    protected void initialize() {
    	// get most recent position, speed, accel, decel settings from preferences
    	System.out.println("ArmMoveDeltaPosition: " + (direction == RobotArm.Direction.kUp ? "Up" : "Down"));
    	initMotionPrefs();
    	System.out.println(m_pathPlanner);
    	m_isDone = false;
    	Robot.robotArm.setSpeedOffset(0.0);
    	
    	Robot.robotArm.resetPIDParameters();
    	Robot.robotArm.m_pidController.setSetpointProvider(m_pathPlanner);
    	Robot.robotArm.m_pidController.startSetpointProvider();
    	Robot.robotArm.m_pidController.enable();
    	m_pathTimer.reset();
    	m_pathTimer.start();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	double time = m_pathTimer.get();
    	System.out.println(Robot.robotArm.m_pidController.getSetpoint());
    	if (time > m_pathPlanner.get_endTime()) {
    		m_isDone = true;
    	}
	}

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return m_isDone;
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.robotArm.m_pidController.disable();
    	Robot.robotArm.m_pidController.setSetpointProvider(null);
    	System.out.println("ArmMoveDeltaPosition complete");
    	Robot.robotArm.stop();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
