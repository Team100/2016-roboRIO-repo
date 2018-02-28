/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team100.robot;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
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
	private Encoder m_encoder;
	private DigitalInput m_high_limit;
	private DigitalInput m_low_limit;
	
	private DigitalInput m_EncoderA;
	private DigitalInput m_EncoderB;

	@Override
	public void robotInit() {
		m_motor1 = new TalonSRX (kMotorPort1);
		m_motor2 = new VictorSPX (kMotorPort2);
		m_motor3 = new VictorSPX (kMotorPort3);
		
		
		
		m_high_limit = new DigitalInput(kHighLimitPort);
		m_low_limit = new DigitalInput(kLowLimitPort);
		m_EncoderA = new DigitalInput(kEncoderAPort);
		m_EncoderB = new DigitalInput(kEncoderBPort);
		
		m_encoder = new Encoder(m_EncoderA, m_EncoderB);
		m_joystick = new Joystick(kJoystickPort);
	}

	@Override
	public void teleopInit() {
		m_motor1.set(ControlMode.PercentOutput, 0);
		m_motor2.set(ControlMode.PercentOutput, 0);
		//m_motor2.set(ControlMode.PercentOutput, 0);
		m_motor3.set(ControlMode.Follower, kMotorPort1);
		
		m_motor2.follow(m_motor1);
		m_motor3.follow(m_motor1);
		m_motor2.setInverted(false);
		m_motor3.setInverted(true);
		
	}

	@Override
	public void disabledPeriodic() {
		reportSensors();
	}

	@Override
	public void teleopPeriodic() {
		double val = m_joystick.getY() / 4.0; // limit to +/- 3 Volts
		m_motor1.set(ControlMode.PercentOutput, val);
		//m_motor2.set(ControlMode.PercentOutput, val);
		
		//m_motor2.set(ControlMode.Follower, kMotorPort1);
		//m_motor3.set(ControlMode.Follower, kMotorPort1);
		//m_motor3.set(ControlMode.PercentOutput, val);
		
		m_motor2.follow(m_motor1);
		m_motor3.follow(m_motor1);
		
		reportSensors();
		SmartDashboard.putNumber("Motor1", m_motor1.getMotorOutputPercent());
		SmartDashboard.putNumber("Motor2",  m_motor2.getMotorOutputPercent());
		SmartDashboard.putNumber("Motor3", m_motor3.getMotorOutputPercent());
	}
	
	void reportSensors() {
		SmartDashboard.putNumber("Encoder", m_encoder.getRaw());
		SmartDashboard.putBoolean("High Limit", m_high_limit.get());
		SmartDashboard.putBoolean("LowLimit", m_low_limit.get());
		
		SmartDashboard.putBoolean("EncA", m_EncoderA.get());
		SmartDashboard.putBoolean("EncB", m_EncoderB.get());
	}
}
