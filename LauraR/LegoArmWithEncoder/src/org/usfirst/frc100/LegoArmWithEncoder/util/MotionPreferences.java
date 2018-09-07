package org.usfirst.frc100.LegoArmWithEncoder.util;
import org.usfirst.frc100.LegoArmWithEncoder.Robot;

public class MotionPreferences {
	
	// static variable single instance of type MotionPreferences
	private static MotionPreferences instance = null;
	
	public static final double s_defaultMoveDistance = 1000; // encoder ticks
	public static final double s_defaultSlewVelocity = 1000; //encoder ticks per second
	public static final double s_defaultAccel = 2000; //encoder ticks per second per second
	public static final double s_defaultDecel = 1000; //encoder ticks per second per second
	public static final double s_defaultInitVelocity = 0;
	public static final double s_defaultFinalVelocity = 0;
	public static final double s_defaultInitPosition = 0;
	public static final double s_defaultStartTime = 0;
	
	private final static String s_keyMoveDistance = "ArmMoveDistance";
	private final static String s_keySlewVelocity = "ArmSlewVelocity";
	private final static String s_keyAccel = "ArmAcceleration";
	private final static String s_keyDecel = "ArmDeceleration";
	private final static String s_keyInitVelocity = "ArmInitVelocity";
	private final static String s_keyFinalVelocity = "ArmFinalVelocity";
	private final static String s_keyStartTime = "ArmStartTime";
	
	private double s_moveDistance;
	private double s_slewVelocity; //encoder ticks per second
	public double s_accel; //encoder ticks per second per second
	public double s_decel; //encoder ticks per second per second
	public double s_initVelocity;
	public double s_finalVelocity;
	public double s_initPosition;
	public double s_startTime;
	
	public static MotionPreferences getInstance() {
		if (instance == null) {
			instance = new MotionPreferences();
		}
		return instance;
	}
	
	private MotionPreferences() {
		update();		
	}
	
	public void update() {
		// Note that for items in the Preferences table, if they don't exist, we must manually add them
    	// I believe that this behavior is different than in previous versions of the WPILIB Preferences class
    	
    	s_moveDistance = SingleAxisPathPlanner.s_defaultMoveDistance;
    	if (Robot.prefs.containsKey(s_keyMoveDistance)) {
    	} else {
    		Robot.prefs.putDouble(s_keyMoveDistance, s_moveDistance);
    	}
    	
    	s_slewVelocity = SingleAxisPathPlanner.s_defaultSlewVelocity;
    	if (Robot.prefs.containsKey(s_keySlewVelocity)) {
    		s_slewVelocity = Robot.prefs.getDouble(s_keySlewVelocity, s_slewVelocity);
    	} else {
    		Robot.prefs.putDouble(s_keySlewVelocity, s_slewVelocity);
    	}
    	
    	s_accel = SingleAxisPathPlanner.s_defaultAccel;
    	if (Robot.prefs.containsKey(s_keyAccel)) {
    		s_accel = Robot.prefs.getDouble(s_keyAccel, s_accel);
    	} else {
    		Robot.prefs.putDouble(s_keyAccel, s_accel);
    	}
    	
    	s_decel = SingleAxisPathPlanner.s_defaultDecel;
    	if (Robot.prefs.containsKey(s_keyDecel)) {
    		s_decel = Robot.prefs.getDouble(s_keyDecel, s_decel);
    	} else {
    		Robot.prefs.putDouble(s_keyDecel, s_decel);
    	}
    	
    	s_initVelocity = SingleAxisPathPlanner.s_defaultInitVelocity;
    	if (Robot.prefs.containsKey(s_keyInitVelocity)) {
    		s_initVelocity = Robot.prefs.getDouble(s_keyInitVelocity, s_initVelocity);
    	} else {
    		Robot.prefs.putDouble(s_keyInitVelocity, s_initVelocity);
    	}
    	
    	s_finalVelocity = SingleAxisPathPlanner.s_defaultFinalVelocity;
    	if (Robot.prefs.containsKey(s_keyFinalVelocity)) {
    		s_finalVelocity = Robot.prefs.getDouble(s_keyFinalVelocity, s_finalVelocity);
    	} else {
    		Robot.prefs.putDouble(s_keyFinalVelocity, s_finalVelocity);
    	}
    	s_startTime = SingleAxisPathPlanner.s_defaultStartTime;
    	if (Robot.prefs.containsKey(s_keyStartTime)) {
    		s_startTime = Robot.prefs.getDouble(s_keyStartTime, s_startTime);
    	} else {
    		Robot.prefs.putDouble(s_keyStartTime, s_startTime);
    	}
	}

	public double get_moveDistance() {
		return s_moveDistance;
	}

	public double get_slewVelocity() {
		return s_slewVelocity;
	}

	public double get_accel() {
		return s_accel;
	}

	public double get_decel() {
		return s_decel;
	}

	public double get_initVelocity() {
		return s_initVelocity;
	}

	public double get_finalVelocity() {
		return s_finalVelocity;
	}

	public double get_initPosition() {
		return s_initPosition;
	}

	public double get_startTime() {
		return s_startTime;
	}

}
