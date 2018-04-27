package org.usfirst.frc100.RobotAndrew.commands;

import org.usfirst.frc100.RobotAndrew.Robot;
import org.usfirst.frc100.RobotAndrew.RobotMap;

import com.ctre.CANTalon;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Joystick.AxisType;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import com.ctre.CANTalon.FeedbackDevice;
import com.ctre.CANTalon.StatusFrameRate;
import com.ctre.CANTalon.TalonControlMode;
public class ExecuteMotionProfile extends IterativeRobot{
	CANTalon _talon = RobotMap.right;
	LoadMotionProfile lmp = new LoadMotionProfile(_talon);
	Joystick _joyJoystick = Robot.oi.joystick1;

	public ExecuteMotionProfile() { 
		_talon.setFeedbackDevice(CANTalon.FeedbackDevice.QuadEncoder);
		_talon.configEncoderCodesPerRev(420);
		_talon.reverseSensor(false); 
	}
    public void teleopPeriodic() {
    	SmartDashboard.putNumber("encoderS", _talon.getEncVelocity());
    	SmartDashboard.putNumber("encoderP", _talon.getEncPosition());
    	SmartDashboard.putNumber("error:", _talon.getClosedLoopError());
		lmp.control();
		_talon.changeControlMode(TalonControlMode.MotionProfile);
			
		CANTalon.SetValueMotionProfile setOutput = lmp.getSetValue();
		SmartDashboard.putNumber("output", setOutput.value);
		_talon.set(setOutput.value);
		lmp.startMotionProfile();
			
	}
	public void disabledPeriodic() {
		_talon.changeControlMode(TalonControlMode.PercentVbus);
		_talon.set(0);
		lmp.reset();
	}
}