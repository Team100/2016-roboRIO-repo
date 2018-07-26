package org.usfirst.frc100.LegoArmWithEncoder.commands;

import org.usfirst.frc100.LegoArmWithEncoder.Robot;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class ArmHome extends Command {
	public enum HomingState {
	    INIT,
	    FAST_TO_LOW_LIMIT,
	    AT_LOW_LIMIT,
	    BUMP_IN_UP_DIRECTION,
	    BUMP_IN_UP_DIRECTION_DONE,
	    HOME_DONE,
	    HOME_ERROR
	}
	private HomingState m_homingState = HomingState.INIT;
	private final double m_fast_homing_speed = 1.0;
	private final double m_slow_homing_speed = 0.1;
	private final double m_maxHomeTime = 45.0; // seconds
	private Timer m_homingTimer = new Timer();
	
	
    public ArmHome() {
        // Use requires() here to declare subsystem dependencies
        requires(Robot.robotArm);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	m_homingState = HomingState.INIT;
    	m_homingTimer.reset();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	switch (m_homingState) {
    	case INIT:
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
    		m_homingState = HomingState.BUMP_IN_UP_DIRECTION;
    		Robot.robotArm.stop();
    		break;
    	case BUMP_IN_UP_DIRECTION:
    		m_homingState = HomingState.BUMP_IN_UP_DIRECTION_DONE;
    		Robot.robotArm.raiseAtSpeed(m_slow_homing_speed);
    		break;
    	case BUMP_IN_UP_DIRECTION_DONE:
    		Robot.robotArm.stop();
    		if (!Robot.robotArm.isAtLowLimit()) {
    			// we've just cleared the limit
    			m_homingState = HomingState.HOME_DONE;
    		} else {
    			m_homingState = HomingState.BUMP_IN_UP_DIRECTION;
    		}
    		break;
    	case HOME_DONE:
    		Robot.robotArm.stop();
        	Robot.robotArm.resetEncoder();
    		break;
    	case HOME_ERROR:
    		Robot.robotArm.stop();
    		break;
    	default:
    		m_homingState = HomingState.HOME_ERROR;
    		break;
    	}
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return (m_homingState == HomingState.HOME_DONE || 
        		m_homingState == HomingState.HOME_ERROR);
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.robotArm.stop();
    	m_homingTimer.stop();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
