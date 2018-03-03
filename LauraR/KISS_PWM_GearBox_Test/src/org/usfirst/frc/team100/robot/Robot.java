/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team100.robot;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * This sample program shows how to control a motor using a joystick. In the
 * operator control part of the program, the joystick is read and the value is
 * written to the motor.
 *
 * <p>Joystick analog values range from -1 to 1 and speed controller inputs also
 * range from -1 to 1 making it easy to work together.
 */
public class Robot extends IterativeRobot {
	
	public static Preferences prefs = Preferences.getInstance(); //Creates preferences object
	
	private double P;
	private double I;
	private double D;
	private double A;
	
	private static final int kMotorPort1 = 5;
	private static final int kMotorPort2 = 6;
	private static final int kMotorPort3 = 7;
	private static final int kHighLimitPort = 0;
	private static final int kLowLimitPort = 1;
	private static final int kEncoderAPort = 2;
	private static final int kEncoderBPort = 3;
	
	private static final int kJoystickPort = 0;

	private TalonSRX m_motor1;
	private VictorSPX m_motor2;
	private VictorSPX m_motor3;
	private Joystick m_joystick;
	//private Encoder m_encoder;
	private DigitalInput m_high_limit;
	private DigitalInput m_low_limit;
	
	//private DigitalInput m_EncoderA;
	//private DigitalInput m_EncoderB;

	@Override
	public void robotInit() {
		m_motor1 = new TalonSRX (kMotorPort1);
		m_motor2 = new VictorSPX (kMotorPort2);
		m_motor3 = new VictorSPX (kMotorPort3);
		
		
		
		m_high_limit = new DigitalInput(kHighLimitPort);
		m_low_limit = new DigitalInput(kLowLimitPort);
		//m_EncoderA = new DigitalInput(kEncoderAPort);
		//m_EncoderB = new DigitalInput(kEncoderBPort);
		
		//m_encoder = new Encoder(m_EncoderA, m_EncoderB);
		m_joystick = new Joystick(kJoystickPort);
		
m_motor1.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, 0);
		
		if (!prefs.containsKey("elevP")) {
        	prefs.putDouble("elevP", 0.01);
        }
        if (!prefs.containsKey("elevI")) {
        	prefs.putDouble("elevI", 0);
        }
        if (!prefs.containsKey("elevD")) {
        	prefs.putDouble("elevD", 0);
        }
        if (!prefs.containsKey("elevA")) {
        	prefs.putDouble("elevA", 0);
        }

        
        
        
		
		
		
	}

	@Override
	public void teleopInit() {

		

		P = Robot.prefs.getDouble("P",
				0);
    	I = Robot.prefs.getDouble("I",
				0);
    	D = Robot.prefs.getDouble("D",
				0);
    	A = Robot.prefs.getDouble("A",             //.45
				0.2);
    	Robot.prefs.putDouble("A", 0.2);

    	m_motor1.config_kP(0, P, 10);
    	m_motor1.config_kI(0, I, 10);
    	m_motor1.config_kD(0, D, 10);
    	m_motor1.config_kF(0, A, 10);
       m_motor1.configClosedloopRamp(1, 0);
       m_motor1.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, 0);
       m_motor1.selectProfileSlot(0, 0);


       m_motor1.setSensorPhase(false);
       m_motor1.configNominalOutputForward(0.0f, 0);
       m_motor1.configNominalOutputReverse(0.0f, 0);
       m_motor1.configMotionAcceleration(10, 0);
       m_motor1.configMotionCruiseVelocity(10, 0);
       m_motor1.configPeakOutputForward(.2, 0);
       m_motor1.configPeakOutputReverse(.2, 0);
       
		m_motor1.setSelectedSensorPosition(0, 0, 10);

       m_motor1.setInverted(false);
		m_motor2.setInverted(false);
		m_motor3.setInverted(true);
		m_motor1.set(ControlMode.MotionMagic, 1000); //This is showing as MotionMagic in WebDash
		
		m_motor2.follow(m_motor1);
		m_motor3.follow(m_motor1);
		System.out.println("LOADED");

		
	}

	@Override
	public void disabledPeriodic() {
		reportSensors();
	}

	@Override
	public void teleopPeriodic() {
//		double val = m_joystick.getY() / 4.0; // limit to +/- 3 Volts
//		m_motor1.set(ControlMode.PercentOutput, val);
		//m_motor2.set(ControlMode.PercentOutput, val);
		
		//m_motor2.set(ControlMode.Follower, kMotorPort1);
		//m_motor3.set(ControlMode.Follower, kMotorPort1);
		//m_motor3.set(ControlMode.PercentOutput, val);
//		
//		m_motor2.follow(m_motor1);
//		m_motor3.follow(m_motor1);
		
		reportSensors();
		SmartDashboard.putNumber("Motor1", m_motor1.getMotorOutputPercent());
		SmartDashboard.putNumber("Motor2",  m_motor2.getMotorOutputPercent());
		SmartDashboard.putNumber("Motor3", m_motor3.getMotorOutputPercent());
		
		
	
	}
	
	void reportSensors() {
		//SmartDashboard.putNumber("Encoder", m_encoder.getRaw());
		SmartDashboard.putBoolean("High Limit", m_high_limit.get());
		SmartDashboard.putBoolean("LowLimit", m_low_limit.get());
		
		//SmartDashboard.putBoolean("EncA", m_EncoderA.get());
		//SmartDashboard.putBoolean("EncB", m_EncoderB.get());
		SmartDashboard.putNumber("TalonENC", m_motor1.getSelectedSensorPosition(0));
		SmartDashboard.putNumber("TalonVEL", m_motor1.getSelectedSensorVelocity(0));
	}
}
