/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team100.robot;

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
	private static final int kMotorPort1 = 0;
	private static final int kMotorPort2 = 1;
	private static final int kMotorPort3 = 2;
	private static final int kHighLimitPort = 0;
	private static final int kLowLimitPort = 1;
	private static final int kEncoderAPort = 2;
	private static final int kEncoderBPort = 3;
	
	private static final int kJoystickPort = 0;

	private SpeedController m_motor1;
	private SpeedController m_motor2;
	private SpeedController m_motor3;
	private Joystick m_joystick;
	private Encoder m_encoder;
	private DigitalInput m_high_limit;
	private DigitalInput m_low_limit;
	
	

	@Override
	public void robotInit() {
		m_motor1 = new Victor (kMotorPort1);
		m_motor2 = new Victor (kMotorPort2);
		m_motor3 = new Victor (kMotorPort3);
		m_high_limit = new DigitalInput(kHighLimitPort);
		m_low_limit = new DigitalInput(kLowLimitPort);
		m_encoder = new Encoder(kEncoderAPort, kEncoderBPort);
		m_joystick = new Joystick(kJoystickPort);
	}

	@Override
	public void disabledPeriodic() {
		reportSensors();
	}

	@Override
	public void teleopPeriodic() {
		double val = m_joystick.getY() / 4.0; // limit to +/- 3 Volts
		m_motor1.set(val);
		m_motor2.set(-val);
		m_motor3.set(val);
		reportSensors();
		SmartDashboard.putNumber("Motor1", m_motor1.get());
		SmartDashboard.putNumber("Motor2",  m_motor2.get());
		SmartDashboard.putNumber("Motor3", m_motor3.get());
	}
	
	void reportSensors() {
		SmartDashboard.putNumber("Encoder", m_encoder.getRaw());
		SmartDashboard.putBoolean("High Limit", m_high_limit.get());
		SmartDashboard.putBoolean("LowLimit", m_low_limit.get());
		
	}
}
