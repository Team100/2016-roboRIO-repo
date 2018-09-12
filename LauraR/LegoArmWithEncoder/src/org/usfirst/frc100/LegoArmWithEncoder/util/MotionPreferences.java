package org.usfirst.frc100.LegoArmWithEncoder.util;
import org.usfirst.frc100.LegoArmWithEncoder.Robot;

public class MotionPreferences {
	
	// static variable single instance of type MotionPreferences
	private static MotionPreferences instance = null;
	
	// Path Motion Parameters
	public static final double s_defaultMoveDistance = 1000; // encoder ticks
	public static final double s_defaultSlewVelocity = 1000; //encoder ticks per second
	public static final double s_defaultAccel = 2000; //encoder ticks per second per second
	public static final double s_defaultDecel = 1000; //encoder ticks per second per second
		
	private final static String s_keyMoveDistance = "ArmMoveDistance";
	private final static String s_keySlewVelocity = "ArmSlewVelocity";
	private final static String s_keyAccel = "ArmAcceleration";
	private final static String s_keyDecel = "ArmDeceleration";
	
	// Homing Parameters
	private static final double s_defaultHomingSpeedFast = 1.0;
    private static final double s_defaultHomingSpeedMedium = 0.1;
    private static final double s_defaultHomingSpeedSlow = 0.05;
    private static final double s_defaultHomingMaxTime = 45.0;
    
    private final static String s_keyHomingSpeedFast = "ArmHomingSpeedFast";
	private final static String s_keyHomingSpeedMedium = "ArmHomingSpeedMedium";
	private final static String s_keyHomingSpeedSlow = "ArmHomingSpeedSlow";
	private final static String s_keyHomingMaxTime = "ArmHomingMaxTime";   
	
	// Position Control Loop Gains
	private static final double s_defaultPosKP = 10.0;
    private static final double s_defaultPosKD = 0.0;
    private static final double s_defaultPosKI = 1.0;
    private static final double s_defaultPosKF = 0.277;
    
	private static final String s_keyPosKP = "ArmPosKPx1000";
    private static final String s_keyPosKD = "ArmPosKDx1000";
    private static final String s_keyPosKI = "ArmPosKIx1000";
    private static final String s_keyPosKF = "ArmPosKFx1000";
     
    // Velocity Control Loop Gains
	private static final double s_defaultVelKP = 10.0;
    private static final double s_defaultVelKD = 0.0;
    private static final double s_defaultVelKI = 1.0;
    private static final double s_defaultVelKF = 0.277;
    
	private static final String s_keyVelKP = "ArmVelKPx1000";
    private static final String s_keyVelKD = "ArmVelKDx1000";
    private static final String s_keyVelKI = "ArmVelKIx1000";
    private static final String s_keyVelKF = "ArmVelKFx1000";
 
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
		
		// Path Motion Preferences
    	
    	double moveDistance = s_defaultMoveDistance;
    	if (Robot.prefs.containsKey(s_keyMoveDistance)) {
    		moveDistance = Robot.prefs.getDouble(s_keyMoveDistance,  moveDistance);
    	} else {
    		Robot.prefs.putDouble(s_keyMoveDistance, moveDistance);
    	}
    	
    	double slewVelocity = s_defaultSlewVelocity;
    	if (Robot.prefs.containsKey(s_keySlewVelocity)) {
    		slewVelocity = Robot.prefs.getDouble(s_keySlewVelocity, slewVelocity);
    	} else {
    		Robot.prefs.putDouble(s_keySlewVelocity, slewVelocity);
    	}
    	
    	double accel = s_defaultAccel;
    	if (Robot.prefs.containsKey(s_keyAccel)) {
    		accel = Robot.prefs.getDouble(s_keyAccel, accel);
    	} else {
    		Robot.prefs.putDouble(s_keyAccel, accel);
    	}
    	
    	double decel = s_defaultDecel;
    	if (Robot.prefs.containsKey(s_keyDecel)) {
    		decel = Robot.prefs.getDouble(s_keyDecel, decel);
    	} else {
    		Robot.prefs.putDouble(s_keyDecel, decel);
    	}
    	
    	// Homing Parameters
    	double homingSpeedFast = s_defaultHomingSpeedFast;
    	if (Robot.prefs.containsKey(s_keyHomingSpeedFast)) {
    		homingSpeedFast = Robot.prefs.getDouble(s_keyHomingSpeedFast, homingSpeedFast);
    	} else {
    		Robot.prefs.putDouble(s_keyHomingSpeedFast, homingSpeedFast);
    	}
    	
    	double homingSpeedMedium = s_defaultHomingSpeedMedium;
    	if (Robot.prefs.containsKey(s_keyHomingSpeedMedium)) {
    		homingSpeedMedium = Robot.prefs.getDouble(s_keyHomingSpeedMedium, homingSpeedMedium);
    	} else {
    		Robot.prefs.putDouble(s_keyHomingSpeedMedium, homingSpeedMedium);
    	}
    	
    	double homingSpeedSlow = s_defaultHomingSpeedSlow;
    	if (Robot.prefs.containsKey(s_keyHomingSpeedSlow)) {
    		homingSpeedSlow = Robot.prefs.getDouble(s_keyHomingSpeedSlow, homingSpeedSlow);
    	} else {
    		Robot.prefs.putDouble(s_keyHomingSpeedSlow, homingSpeedSlow);
    	}
    	
    	double homingMaxTime = s_defaultHomingMaxTime;
    	if (Robot.prefs.containsKey(s_keyHomingMaxTime)) {
    		homingMaxTime = Robot.prefs.getDouble(s_keyHomingMaxTime, homingMaxTime);
    	} else {
    		Robot.prefs.putDouble(s_keyHomingMaxTime, homingMaxTime);
    	}
    	
    	// Position Control Loop Gains
    	double posKp = s_defaultPosKP;
    	if (Robot.prefs.containsKey(s_keyPosKP)) {
    		posKp = Robot.prefs.getDouble(s_keyPosKP, posKp);
    	} else {
    		Robot.prefs.putDouble(s_keyPosKP, posKp);
    	}
    	double posKd = s_defaultPosKD;
    	if (Robot.prefs.containsKey(s_keyPosKD)) {
    		posKd = Robot.prefs.getDouble(s_keyPosKD, posKd);
    	} else {
    		Robot.prefs.putDouble(s_keyPosKD, posKd);
    	}
    	double posKi = s_defaultPosKI;
    	if (Robot.prefs.containsKey(s_keyPosKI)) {
    		posKi = Robot.prefs.getDouble(s_keyPosKI, posKi);
    	} else {
    		Robot.prefs.putDouble(s_keyPosKI, posKi);
    	}
    	double posKf = s_defaultPosKF;
    	if (Robot.prefs.containsKey(s_keyPosKF)) {
    		posKf = Robot.prefs.getDouble(s_keyPosKF, posKf);
    	} else {
    		Robot.prefs.putDouble(s_keyPosKF, posKf);
    	}
    	
    	
    	// Velocity Control Loop Gains
    	double velKp = s_defaultVelKP;
    	if (Robot.prefs.containsKey(s_keyVelKP)) {
    		velKp = Robot.prefs.getDouble(s_keyVelKP, velKp);
    	} else {
    		Robot.prefs.putDouble(s_keyVelKP, velKp);
    	}
    	double velKd = s_defaultVelKD;
    	if (Robot.prefs.containsKey(s_keyVelKD)) {
    		velKd = Robot.prefs.getDouble(s_keyVelKD, velKd);
    	} else {
    		Robot.prefs.putDouble(s_keyVelKD, velKd);
    	}
    	double velKi = s_defaultVelKI;
    	if (Robot.prefs.containsKey(s_keyVelKI)) {
    		velKi = Robot.prefs.getDouble(s_keyVelKI, velKi);
    	} else {
    		Robot.prefs.putDouble(s_keyVelKI, velKi);
    	}
    	double velKf = s_defaultVelKF;
    	if (Robot.prefs.containsKey(s_keyVelKF)) {
    		velKf = Robot.prefs.getDouble(s_keyVelKF, velKf);
    	} else {
    		Robot.prefs.putDouble(s_keyVelKF, velKf);
    	}
	}

	public double get_moveDistance() {
		return Robot.prefs.getDouble(s_keyMoveDistance,  s_defaultMoveDistance);
	}

	public double get_slewVelocity() {
		return Robot.prefs.getDouble(s_keySlewVelocity, s_defaultSlewVelocity);
	}

	public double get_accel() {
		return Robot.prefs.getDouble(s_keyAccel, s_defaultAccel);
	}

	public double get_decel() {
		return Robot.prefs.getDouble(s_keyDecel, s_defaultDecel);
	}
	
	public double get_homingSpeedFast (){
		return Robot.prefs.getDouble(s_keyHomingSpeedFast, s_defaultHomingSpeedFast);
	}
	public double get_homingSpeedMedium (){
		return Robot.prefs.getDouble(s_keyHomingSpeedMedium, s_defaultHomingSpeedMedium);
	}
	public double get_homingSpeedSlow(){
		return Robot.prefs.getDouble(s_keyHomingSpeedSlow, s_defaultHomingSpeedSlow);
	}
	public double get_homingMaxTime(){
		return Robot.prefs.getDouble(s_keyHomingMaxTime, s_defaultHomingMaxTime);
	}
	
	public double get_posKp() {
		return Robot.prefs.getDouble(s_keyPosKP, s_defaultPosKP) / 1000.0;
	}
	public double get_posKi() {
		return Robot.prefs.getDouble(s_keyPosKI, s_defaultPosKI) / 1000.0;
	}
	public double get_posKd() {
		return Robot.prefs.getDouble(s_keyPosKD, s_defaultPosKD) / 1000.0;
	}
	public double get_posKf() {
		return Robot.prefs.getDouble(s_keyPosKF, s_defaultPosKF) / 1000.0;
	}
	
	public double get_velKp() {
		return Robot.prefs.getDouble(s_keyVelKP, s_defaultVelKP) / 1000.0;
	}
	public double get_velKi() {
		return Robot.prefs.getDouble(s_keyVelKI, s_defaultVelKI) / 1000.0;
	} 
	public double get_velKd() {	
		return Robot.prefs.getDouble(s_keyVelKD, s_defaultVelKD) / 1000.0;
	}
	public double get_velKf() {
		return Robot.prefs.getDouble(s_keyVelKF, s_defaultVelKF) / 1000.0;
	}

	
}
