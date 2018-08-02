package org.usfirst.frc100.LegoArmWithEncoder.commands;

import java.util.Vector;

import org.usfirst.frc100.LegoArmWithEncoder.Robot;
import org.usfirst.frc100.LegoArmWithEncoder.util.SingleAxisPathPlanner;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class ArmGoToPosition extends Command {
	private SingleAxisPathPlanner m_pathPlanner;
	private double m_position;
	private double m_initPosition = 0;
	private boolean m_isDone = true;
	private Timer m_pathTimer = new Timer();
	
	private final static String s_keyMoveDistance = "ArmMoveDistance";
	private final static String s_keySlewVelocity = "ArmSlewVelocity";
	private final static String s_keyAccel = "ArmAcceleration";
	private final static String s_keyDecel = "ArmDeceleration";
	private final static String s_keyInitVelocity = "ArmInitVelocity";
	private final static String s_keyFinalVelocity = "ArmFinalVelocity";
	private final static String s_keyStartTime = "ArmStartTime";
	

    public ArmGoToPosition(double position) {
        // Use requires() here to declare subsystem dependencies
        requires(Robot.robotArm);
        m_position = position;
    }
    
    private void initMotionPrefs() {
    	// Note that for items in the Preferences table, if they don't exist, we must manually add them
    	// I believe that this behavior is different than in previous versions of the WPILIB Preferences class
    	
    	double dist = SingleAxisPathPlanner.s_defaultMoveDistance;
    	if (Robot.prefs.containsKey(s_keyMoveDistance)) {
    		dist = Robot.prefs.getDouble(s_keyMoveDistance, dist);
    	} else {
    		Robot.prefs.putDouble(s_keyMoveDistance, dist);
    	}
    	
    	double slewVelocity = SingleAxisPathPlanner.s_defaultSlewVelocity;
    	if (Robot.prefs.containsKey(s_keySlewVelocity)) {
    		slewVelocity = Robot.prefs.getDouble(s_keySlewVelocity, slewVelocity);
    	} else {
    		Robot.prefs.putDouble(s_keySlewVelocity, slewVelocity);
    	}
    	
    	double acceleration = SingleAxisPathPlanner.s_defaultAccel;
    	if (Robot.prefs.containsKey(s_keyAccel)) {
    		acceleration = Robot.prefs.getDouble(s_keyAccel, acceleration);
    	} else {
    		Robot.prefs.putDouble(s_keyAccel, acceleration);
    	}
    	
    	double deceleration = SingleAxisPathPlanner.s_defaultDecel;
    	if (Robot.prefs.containsKey(s_keyDecel)) {
    		deceleration = Robot.prefs.getDouble(s_keyDecel, deceleration);
    	} else {
    		Robot.prefs.putDouble(s_keyDecel, deceleration);
    	}
    	
    	double initVelocity = SingleAxisPathPlanner.s_defaultInitVelocity;
    	if (Robot.prefs.containsKey(s_keyInitVelocity)) {
    		initVelocity = Robot.prefs.getDouble(s_keyInitVelocity, initVelocity);
    	} else {
    		Robot.prefs.putDouble(s_keyInitVelocity, initVelocity);
    	}
    	
    	double finalVelocity = SingleAxisPathPlanner.s_defaultFinalVelocity;
    	if (Robot.prefs.containsKey(s_keyFinalVelocity)) {
    		finalVelocity = Robot.prefs.getDouble(s_keyFinalVelocity, finalVelocity);
    	} else {
    		Robot.prefs.putDouble(s_keyFinalVelocity, finalVelocity);
    	}
    	double startTime = SingleAxisPathPlanner.s_defaultStartTime;
    	if (Robot.prefs.containsKey(s_keyStartTime)) {
    		startTime = Robot.prefs.getDouble(s_keyStartTime, startTime);
    	} else {
    		Robot.prefs.putDouble(s_keyStartTime, startTime);
    	}
    	m_initPosition = Robot.robotArm.getEncoderPosition();
    	m_pathPlanner = new SingleAxisPathPlanner(dist, slewVelocity, acceleration, deceleration, initVelocity, finalVelocity, m_initPosition, startTime);
     }

    // Called just before this Command runs the first time
    protected void initialize() {
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
    	//System.out.println(Robot.robotArm.m_pidController.getSetpoint());
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
    	System.out.println("ArmGoToPosition complete");
    	Robot.robotArm.stop();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
