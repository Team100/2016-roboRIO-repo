package org.usfirst.frc100.LegoArmWithEncoder.commands;

import org.usfirst.frc100.LegoArmWithEncoder.Robot;
import org.usfirst.frc100.LegoArmWithEncoder.RobotMap;
import org.usfirst.frc100.LegoArmWithEncoder.commands.ArmCalibrateIndices.IndexInterruptTask;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.InterruptableSensorBase.WaitResult;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class ArmHome extends Command {
	public enum HomingState {
	    INIT,
	    FAST_TO_LOW_LIMIT,
	    AT_LOW_LIMIT,
	    SLOW_TO_CLEAR_LIMIT,
	    LIMIT_CLEARED,
	    SLOW_TO_CAPTURE_LIMIT,
	    HOME_DONE,
	    HOME_ERROR
	}
	
	DigitalInput m_lowLimit = RobotMap.robotArmLowerLimit;
	private HomingState m_homingState = HomingState.INIT;
	private final double m_fast_homing_speed = 1.0;
	private final double m_slow_homing_speed = 0.1;
	private final double m_maxHomeTime = 45.0; // seconds
	private Timer m_homingTimer = new Timer();
	private Thread m_task;
	private boolean m_done = false;
	
	public class HomingInterruptTask implements Runnable {
		public void run() {
			boolean is_done = false;
			
			m_lowLimit.requestInterrupts();
			while (!(is_done || Thread.interrupted())) {
				WaitResult result = m_lowLimit.waitForInterrupt(0.5, true);
				if (result == WaitResult.kRisingEdge){
					Robot.robotArm.setHome();
					is_done = true;
				} 			
			}
			m_lowLimit.cancelInterrupts();
		}
	}	
    public ArmHome() {
        // Use requires() here to declare subsystem dependencies
        requires(Robot.robotArm);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	m_homingState = HomingState.INIT;
    	m_homingTimer.reset();
    	m_done = false;
    	m_task = new Thread(new HomingInterruptTask(), "LowLimitWatcher");
    	
    	m_done = false;
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	switch (m_homingState) {
    	case INIT:
    		m_done = false;
    		if (Robot.robotArm.isAtLowLimit()) {
    			// we're already at the limit, proceed to next step to find the edge
    			m_homingState = HomingState.AT_LOW_LIMIT;
    		} else {
	    		// Start moving quickly toward the low limit
	    		Robot.robotArm.lowerAtSpeed(m_fast_homing_speed);
	    		m_homingState = HomingState.FAST_TO_LOW_LIMIT;
	    		m_homingTimer.start();
	    		
    		}
    		break;
    	case FAST_TO_LOW_LIMIT:
    		if (Robot.robotArm.isAtLowLimit()){
    			// We've arrived (and probably shot past the low limit   			
    			Robot.robotArm.stop();
    			m_homingState = HomingState.AT_LOW_LIMIT;
    		} else {
    			if (m_homingTimer.hasPeriodPassed(m_maxHomeTime)) {
    				Robot.robotArm.stop();
    				m_homingState = HomingState.HOME_ERROR;
    			} else {
	    			// Keep moving
	    			Robot.robotArm.lowerAtSpeed(m_fast_homing_speed);
    			}
    		}
    		break;
    	case AT_LOW_LIMIT:
    		// start moving slowly up until limit is just cleared.
    		m_homingState = HomingState.SLOW_TO_CLEAR_LIMIT;
    		Robot.robotArm.stop();
    		break;

    	case SLOW_TO_CLEAR_LIMIT:
    		Robot.robotArm.raiseAtSpeed(m_slow_homing_speed);
    		if (!Robot.robotArm.isAtLowLimit()) {
    			m_homingState = HomingState.LIMIT_CLEARED;
    			Robot.robotArm.stop();
    		}    		
    		break;
    	case LIMIT_CLEARED:	
    		m_task.start(); // begin looking for the rising edge of the home and take data
    		Robot.robotArm.lowerAtSpeed(m_slow_homing_speed);
    		m_homingState = HomingState.SLOW_TO_CAPTURE_LIMIT;
    		break;
    	case SLOW_TO_CAPTURE_LIMIT:
    		if (Robot.robotArm.isAtLowLimit()) {
    			// we've just triggered the limit
    			m_homingState = HomingState.HOME_DONE;
    			Robot.robotArm.stop();  			
    		} 
    		break;
    	case HOME_DONE:
    		Robot.robotArm.stop();  
    		m_done = true;
    		break;
    	case HOME_ERROR:
    		Robot.robotArm.stop();
    		m_done = true;
    		System.out.println("Arm Home Command Error");
    		break;
    	default:
    		m_homingState = HomingState.HOME_ERROR;
    		break;
    	}
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return (m_done);
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.robotArm.stop();
    	m_homingTimer.stop();
    	if (m_task.isAlive()) {
    		m_task.interrupt();
    	}   	
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
