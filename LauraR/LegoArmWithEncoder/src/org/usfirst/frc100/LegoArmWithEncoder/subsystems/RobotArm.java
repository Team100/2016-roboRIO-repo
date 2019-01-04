// RobotBuilder Version: 1.5
//
// This file was generated by RobotBuilder. It contains sections of
// code that are automatically generated and assigned by robotbuilder.
// These sections will be updated in the future when you export to
// Java from RobotBuilder. Do not put any code or make any change in
// the blocks indicating autogenerated code or it will be lost on an
// update. Deleting the comments indicating the section will prevent
// it from being updated in the future.


package org.usfirst.frc100.LegoArmWithEncoder.subsystems;

import org.usfirst.frc100.LegoArmWithEncoder.Robot;
import org.usfirst.frc100.LegoArmWithEncoder.RobotMap;
import org.usfirst.frc100.LegoArmWithEncoder.calibration.IndexCalibrationData;
import org.usfirst.frc100.LegoArmWithEncoder.calibration.IndexCalibrationData.IndexCalibrationPoint;
import org.usfirst.frc100.LegoArmWithEncoder.calibration.SpeedCalibrationData;
import org.usfirst.frc100.LegoArmWithEncoder.calibration.SpeedCalibrationData.SpeedCalibrationPoint;
import org.usfirst.frc100.LegoArmWithEncoder.commands.HoldIt;
import org.usfirst.frc100.LegoArmWithEncoder.devices.ParallaxContinuousRotationServo;
import org.usfirst.frc100.LegoArmWithEncoder.util.MotionPreferences;
import org.usfirst.frc100.LegoArmWithEncoder.util.MultiVarPIDController;

import edu.wpi.first.wpilibj.AnalogPotentiometer;
import edu.wpi.first.wpilibj.Counter;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;


/**
 *
 */
public class RobotArm extends Subsystem implements PIDOutput{
	public enum Direction { 
		kUp, kDown
	}
    
    private static final double s_minEncoderVal = -80000.0;
    private static final double s_maxEncoderVal = 80000.0;
    private static final double s_minEncoderSpd = -4000.0;
    private static final double s_maxEncoderSpd = 4000.0;
    
	ParallaxContinuousRotationServo armContinuousRotationServo = RobotMap.robotArmContinuousRotationServo;
    
    DigitalInput armUpperLimit = RobotMap.robotArmUpperLimit;
    DigitalInput armLowerLimit = RobotMap.robotArmLowerLimit;
    
    AnalogPotentiometer armPositionPot = RobotMap.robotArmPositionPot;
    
    DigitalInput encoderA = RobotMap.robotArmEncoderA;
    DigitalInput encoderB = RobotMap.robotArmEncoderB;
    DigitalInput encoderIdx = RobotMap.robotArmEncoderIndex;
    Encoder robotArmEncoder = new Encoder(encoderA, encoderB);
    Counter indexCounter = new Counter(encoderIdx);
    
    private boolean m_isHomed = false;
    private double m_homePotValue = 0.0;
    
    public MultiVarPIDController m_pidController;
    public final MotionPreferences m_motionPreferences;
    
    private IndexCalibrationData m_indexCalibrationData = new IndexCalibrationData(80);
    private SpeedCalibrationData m_speedCalibrationData = new SpeedCalibrationData(80);
    
    final static String ntPrefix = "RobotArm/"; // Prefix for network table variables
    

    
   	public RobotArm() {
   		// Add variables to Live Windows
	    this.addChild("Upper Limit", armUpperLimit);
	    this.addChild("Lower Limit", armLowerLimit);
	    this.addChild("Servo", armContinuousRotationServo);
	    this.addChild("EncoderA", encoderA);
	    this.addChild("EncoderB", encoderB);
	    this.addChild("EncoderIndex", encoderIdx);
	    this.addChild("Potentiometer", armPositionPot);
	    this.addChild("Encoder", robotArmEncoder);
	    this.addChild("IndexCounter", indexCounter); 
	    // try to read calibration data from file
	    m_indexCalibrationData.readCalibrationData();
	    m_speedCalibrationData.readCalibrationData();
	    
	    // arm position controller
	    // get parameters out of the Preferences file
	    m_motionPreferences = MotionPreferences.getInstance();
    	
	    m_pidController = new MultiVarPIDController(
	    		m_motionPreferences.get_posKp(),
	    		m_motionPreferences.get_posKi(),
	    		m_motionPreferences.get_posKd(),
	    		m_motionPreferences.get_posKf(),
	    		robotArmEncoder, // PID input
	    		this, // PID output
	    		0.020, // period in seconds
	    		(MultiVarPIDController.SetpointProvider)null);
	    setPIDforPositionControl(); 
	                 
	    stop(); // make sure the arm won't move if we go into Test mode first
	}
   	
   	public void setPIDforPositionControl() {    	
   		m_pidController.disable();
   		robotArmEncoder.setPIDSourceType(PIDSourceType.kDisplacement);
	    m_pidController.setPID(
	    		m_motionPreferences.get_posKp(),
	    		m_motionPreferences.get_posKi(),
	    		m_motionPreferences.get_posKd(),
	    		m_motionPreferences.get_posKf());
	    m_pidController.setInputRange(s_minEncoderVal, s_maxEncoderVal);
	    m_pidController.setName("Arm", "PositionPID");
	    m_pidController.setOutputRange(-1.0, 1.0);
	    m_pidController.setAbsoluteTolerance(100.0);	
	    
   	}
   	
   	public void setPIDforSpeedControl() {
   		m_pidController.disable();
   		robotArmEncoder.setPIDSourceType(PIDSourceType.kRate);
	    m_pidController.setPID(
	    		m_motionPreferences.get_velKp(),
	    		m_motionPreferences.get_velKi(),
	    		m_motionPreferences.get_velKd(),
	    		m_motionPreferences.get_velKf());
	    m_pidController.setInputRange(s_minEncoderSpd, s_maxEncoderSpd);
	    m_pidController.setName("Arm", "SpeedPID");
	    m_pidController.setOutputRange(-1.0, 1.0);
	    m_pidController.setAbsoluteTolerance(100.0);	   		
   	}

	public void initDefaultCommand() {
        setDefaultCommand(new HoldIt());
    }
    
    public void raise() {
    	if (isAtHighLimit()) {
    		stop();
    	} else {
    		//armContinuousRotationServo.set(0.0); 
    		armContinuousRotationServo.set(-1.0);
    	}
    }
    
    public void lower() {
    	if (isAtLowLimit()) {
    		stop();
    	}
    	else {
    		//armContinuousRotationServo.set(1.0);
    		armContinuousRotationServo.set(1.0);
    	}
    }
    
    public void raiseAtSpeed(double speed) {
    	if (isAtHighLimit() || speed > 1.0 || speed < 0.0) {
    		stop();
    	} else {
    		//armContinuousRotationServo.set( 0.5 - (speed/2.0)); 
    		armContinuousRotationServo.set(-speed);
    	}
    }
    
    public void lowerAtSpeed(double speed) {
    	if (isAtLowLimit()|| speed > 1.0 || speed < 0.0) {
    		stop();
    	}
    	else {
    		//armContinuousRotationServo.set(0.5 + (speed/2.0));
    		armContinuousRotationServo.set(speed);
    	}
    }


	@Override
	public void pidWrite(double pOutput) {
		if (isAtLowLimit() && (pOutput < 0.0)){
			// don't allow movement past limit
			stop();
		}
		else if (isAtHighLimit() && (pOutput > 0.0)) {
			// don't allow movement past limit
    		stop();
    	}
    	else {
    		// change sense so that servo goes in correct direction to correct for errors.
    		armContinuousRotationServo.set(-pOutput); 
    	}
		
	}
    public void stop() {
    	//armContinuousRotationServo.set(0.5);
    	armContinuousRotationServo.set(0);
    	
    }
    
    public boolean isAtLowLimit() {
    	return armLowerLimit.get();
    }
    
    public boolean isAtHighLimit() {
    	return armUpperLimit.get();
    }
    
    public double getPotValue() {
    	return armPositionPot.get();
    }
    
    private void resetEncoder () {
    	robotArmEncoder.reset();
    }
    
    public boolean isHomed() {
    	return m_isHomed;
    }
    
    public void clearIsHomed() {
    	m_isHomed = false;
    }
    
    public double getHomePotValue () {
    	return m_homePotValue;
    }
    
    public void setHome() {
    	m_isHomed = true;
    	m_homePotValue = getPotValue();
    	resetEncoder();
    }
    
    public double getEncoderRate() {
    	return robotArmEncoder.getRate();
    }
    
    public double getEncoderPosition() {
    	return robotArmEncoder.getDistance();
    }
    
    public void updateIndexCalibrationPoint () {
    	m_indexCalibrationData.addCurData(robotArmEncoder.get(), armPositionPot.get());
    }
    
    public void resetIndexCalibration() {
    	m_indexCalibrationData.resetCurPtr();
    }
    
    public void printIndexCalibration(){
    	for (int i = 0; i < m_indexCalibrationData.m_curPtr; i ++) {
    		IndexCalibrationPoint data = m_indexCalibrationData.m_calibrationData[i];
    		System.out.println(data);
    	}
    	Robot.calibration.putCalibrationData(m_indexCalibrationData);
    }
    
    public void updateSpeedCalibrationPoint (double p_speedSP, double p_minSpeed, double p_maxSpeed, double p_avgSpeed, double p_potRate) {
    	m_speedCalibrationData.addCurData(p_speedSP, p_minSpeed, p_maxSpeed, p_avgSpeed, p_potRate);
    }
    
    public void resetSpeedCalibration() {
    	m_speedCalibrationData.resetCurPtr();
    }
    
    public void printSpeedCalibration(){
    	for (int i = 0; i < m_speedCalibrationData.m_curPtr; i ++) {
    		SpeedCalibrationPoint data = m_speedCalibrationData.m_calibrationData[i];
    		System.out.println(data);
    	}
    	Robot.calibration.putCalibrationData(m_speedCalibrationData);
    }
    
    public void updateDashboard()
    {
    	// Show variables in smart Dashboard
        SmartDashboard.putNumber(ntPrefix + "Arm Pot", 0.001 * Math.round(getPotValue()*1000));
        SmartDashboard.putBoolean(ntPrefix + "Arm Hi", isAtHighLimit());
        SmartDashboard.putBoolean(ntPrefix + "Arm Lo", isAtLowLimit());
        SmartDashboard.putBoolean(ntPrefix + "Arm Is Homed", isHomed());
        //SmartDashboard.putBoolean(ntPrefix + "Arm Encoder A", encoderA.get());
        //SmartDashboard.putBoolean(ntPrefix + "Arm Encoder B", encoderB.get());
        //SmartDashboard.putBoolean(ntPrefix + "Arm Encoder Index", encoderIdx.get());
        SmartDashboard.putNumber(ntPrefix + "Arm Encoder Position", robotArmEncoder.getDistance());
        SmartDashboard.putNumber(ntPrefix + "Arm Encoder Rate", 0.001 * Math.round(robotArmEncoder.getRate()*1000));
        SmartDashboard.putNumber(ntPrefix + "Arm Index Counter", indexCounter.get());
        SmartDashboard.putNumber(ntPrefix + "Servo", 0.001 * Math.round(armContinuousRotationServo.get()*1000));
        SmartDashboard.putNumber(ntPrefix + "Arm Home Pot Value", getHomePotValue());
        SmartDashboard.putNumber(ntPrefix + "Arm Position Error", m_pidController.getError());
        SmartDashboard.putString(ntPrefix + "CurrentCommand", getCurrentCommandName());
    }


}
