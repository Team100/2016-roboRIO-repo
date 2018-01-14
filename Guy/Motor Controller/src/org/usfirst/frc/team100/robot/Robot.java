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

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.Spark;

/**
 * This sample program shows how to control a motor using a joystick. In the
 * operator control part of the program, the joystick is read and the value is
 * written to the motor.
 *
 * <p>Joystick analog values range from -1 to 1 and speed controller inputs also
 * range from -1 to 1 making it easy to work together.
 */
public class Robot extends IterativeRobot {

	private static final int kJoystickPort = 0;
	static int positionSetpoint = 10000;
	private TalonSRX m_motor;
	private Joystick m_joystick;

	@Override
	public void robotInit() {
		m_motor = new TalonSRX(1);
		m_joystick = new Joystick(kJoystickPort);
        m_motor.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, 0);
        m_motor.configOpenloopRamp(0, 0);
        m_motor.configMotionCruiseVelocity(70, 0);
        m_motor.configMotionAcceleration(70, 0);
        m_motor.selectProfileSlot(0, 0);
        m_motor.config_kP(0, 0.5, 0);
        m_motor.config_kI(0, 0, 0);
        m_motor.config_kD(0, 0, 0);
        m_motor.config_kF(0, 14.614, 0);
        m_motor.setInverted(true);
        m_motor.setSensorPhase(true);
        m_motor.configNominalOutputForward(0.0f, 0);
        m_motor.configNominalOutputReverse(0.0f, 0);
        

        

	}

	@Override
	public void teleopPeriodic() {
		SmartDashboard.putNumber("TalonSRXVoltage", m_motor.getMotorOutputVoltage());
		SmartDashboard.putNumber("EncoderValueForTalonSRX1", m_motor.getSelectedSensorPosition(0));
		SmartDashboard.putNumber("TalonSRX1Velocity", m_motor.getSelectedSensorVelocity(0));
		SmartDashboard.putNumber("Desiried SetPoint", positionSetpoint);
		if (m_joystick.getRawButton(1)) {
			double targetPos = m_joystick.getY() * 1680 * 10.0;
			m_motor.set(ControlMode.MotionMagic, positionSetpoint);
			

		} else {
			m_motor.set(ControlMode.PercentOutput, -m_joystick.getY());
		}
	}
}
