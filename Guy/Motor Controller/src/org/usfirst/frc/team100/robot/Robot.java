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

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.hal.AnalogJNI;
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
	static double positionSetpoint = 50;
	public static TalonSRX m_motor;
	public static Joystick m_joystick;
	public static Preferences prefs;
	public static double P;
	public static double F;
	public static double I;
	public static double D;
	public static int Accel;
	public static int DesiredVelocity;
	public static DigitalInput lim1;
	public static AnalogInput pot;

	

	@Override
	public void robotInit() {
		prefs = Preferences.getInstance();
		prefs.putDouble("P", 5);
		prefs.putDouble("I", 0.01);
		prefs.putDouble("D", 1);
		prefs.putDouble("F", 0.5);
		prefs.putDouble("Setpoint", 0);
		prefs.putDouble("Accel", 0);
		prefs.putDouble("DesiredVelocity", 0);
		lim1 = new DigitalInput(0);
		pot = new AnalogInput(0);

		m_motor = new TalonSRX(1);
		m_joystick = new Joystick(kJoystickPort);
        m_motor.configClosedloopRamp(0, 0);
        m_motor.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, 0);
        m_motor.configOpenloopRamp(0, 0);
       // m_motor.configMotionCruiseVelocity(70, 0);
       // m_motor.configMotionAcceleration(10, 0);
        m_motor.selectProfileSlot(0, 0);
        m_motor.config_kP(0, 5.0, 0);
        m_motor.config_kI(0, 0.01, 0);
        m_motor.config_kD(0, 1, 0);
        m_motor.config_kF(0, 0.5, 0);
        m_motor.setInverted(true);
        m_motor.setSensorPhase(true);
        m_motor.configNominalOutputForward(0.0f, 0);
        m_motor.configNominalOutputReverse(0.0f, 0);
   
	}

	@Override
	public void teleopPeriodic() {
		positionSetpoint = prefs.getDouble("Setpoint",0);

		SmartDashboard.putBoolean("limitSwitch", lim1.get());
		SmartDashboard.putNumber("Potentiometer", pot.getValue());
		SmartDashboard.putNumber("TalonSRXVoltage", m_motor.getMotorOutputVoltage());
		SmartDashboard.putNumber("TalonSRXCurrent", m_motor.getOutputCurrent());

		SmartDashboard.putNumber("TalonSRXError", m_motor.getClosedLoopError(0));
		SmartDashboard.putNumber("EncoderValueForTalonSRX1", m_motor.getSelectedSensorPosition(0));
		SmartDashboard.putNumber("TalonSRX1Velocity", m_motor.getSelectedSensorVelocity(0));
		SmartDashboard.putNumber("Desiried SetPoint", positionSetpoint);

		if (m_joystick.getRawButton(1)) {
			double targetPos = m_joystick.getY() * 1680 * 10.0;
			m_motor.set(ControlMode.MotionMagic, pot.getValue()*2);
			 if(lim1.get() == true){
					m_motor.set(ControlMode.MotionMagic, 5000);
				}
		//} else if(m_joystick.getRawButton(2)){
	        //m_motor.configClosedloopRamp(0, 0);
			
			//m_motor.set(ControlMode.Position, 16000);
		}else{
			m_motor.set(ControlMode.PercentOutput, -m_joystick.getY());
		}
	}

	@Override
	public void teleopInit() {
		Accel = prefs.getInt("Accel",100);
		DesiredVelocity = prefs.getInt("DesiredVelocity",200);
		P = prefs.getDouble("P", 0);
		I = prefs.getDouble("I", 0);
		D = prefs.getDouble("D", 0);
		F = prefs.getDouble("F", 0);
        m_motor.config_kP(0, P, 0);
        m_motor.config_kI(0, I, 0);
        m_motor.config_kD(0, D, 0);
        m_motor.config_kF(0, F, 0);
        m_motor.configMotionCruiseVelocity(DesiredVelocity, 0);
        m_motor.configMotionAcceleration(Accel, 0);
	}
}
