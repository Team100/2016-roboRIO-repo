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

import edu.wpi.first.wpilibj.AnalogPotentiometer;
import edu.wpi.first.wpilibj.Counter;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;


/**
 *
 */
public class RobotArm extends Subsystem {


    Servo armContinuousRotationServo = RobotMap.robotArmArmContinuousRotationServo;
    
    DigitalInput armUpperLimit = RobotMap.robotArmArmUpperLimit;
    DigitalInput armLowerLimit = RobotMap.robotArmArmLowerLimit;
    
    AnalogPotentiometer armPositionPot = RobotMap.robotArmArmPositionPot;
    
    DigitalInput encoderA = RobotMap.robotArmEncoderA;
    DigitalInput encoderB = RobotMap.robotArmEncoderB;
    DigitalInput encoderIdx = RobotMap.robotArmEncoderIndex;
    Encoder robotArmEncoder = new Encoder(encoderA, encoderB);
    Counter indexCounter = new Counter(encoderIdx);
    
    private boolean m_isHomed = false;
    private double m_homePotValue = 0.0;
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
	    
	    stop(); // make sure the arm won't move if we go into Test mode first
	}

	public void initDefaultCommand() {
        setDefaultCommand(new HoldIt());
    }
    
    public void raise() {
    	if (isAtHighLimit()) {
    		stop();
    	} else {
    		armContinuousRotationServo.set(0.0);  
    	}
    }
    
    public void lower() {
    	if (isAtLowLimit()) {
    		stop();
    	}
    	else {
    		armContinuousRotationServo.set(1.0);
    	}
    }
    
    public void raiseAtSpeed(double speed) {
    	if (isAtHighLimit() || speed > 1.0 || speed < 0.0) {
    		stop();
    	} else {
    		armContinuousRotationServo.set( 0.5 - (speed/2.0));  
    	}
    }
    
    public void lowerAtSpeed(double speed) {
    	if (isAtLowLimit()|| speed > 1.0 || speed < 0.0) {
    		stop();
    	}
    	else {
    		armContinuousRotationServo.set(0.5 + (speed/2.0));
    	}
    }
    
    public void stop() {
    	armContinuousRotationServo.set(0.5);
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
    
    public void updateSpeedCalibrationPoint (double p_speedSP, double p_minSpeed, double p_maxSpeed, double p_avgSpeed) {
    	m_speedCalibrationData.addCurData(p_speedSP, p_minSpeed, p_maxSpeed, p_avgSpeed);
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
        SmartDashboard.putNumber(ntPrefix + "Arm Pot", getPotValue());
        SmartDashboard.putBoolean(ntPrefix + "Arm Hi", isAtHighLimit());
        SmartDashboard.putBoolean(ntPrefix + "Arm Lo", isAtLowLimit());
        //SmartDashboard.putBoolean(ntPrefix + "Arm Encoder A", encoderA.get());
        //SmartDashboard.putBoolean(ntPrefix + "Arm Encoder B", encoderB.get());
        //SmartDashboard.putBoolean(ntPrefix + "Arm Encoder Index", encoderIdx.get());
        SmartDashboard.putNumber(ntPrefix + "Arm Encoder Position", robotArmEncoder.getDistance());
        SmartDashboard.putNumber(ntPrefix + "Arm Encoder Rate", robotArmEncoder.getRate());
        SmartDashboard.putNumber(ntPrefix + "Arm Index Counter", indexCounter.get());
        SmartDashboard.putNumber(ntPrefix + "Servo", armContinuousRotationServo.get());
        SmartDashboard.putNumber(ntPrefix + "Arm Home Pot Value", getHomePotValue());

    }
}

