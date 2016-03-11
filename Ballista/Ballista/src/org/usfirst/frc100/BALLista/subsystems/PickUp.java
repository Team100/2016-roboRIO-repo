// RobotBuilder Version: 2.0
//
// This file was generated by RobotBuilder. It contains sections of
// code that are automatically generated and assigned by robotbuilder.
// These sections will be updated in the future when you export to
// Java from RobotBuilder. Do not put any code or make any change in
// the blocks indicating autogenerated code or it will be lost on an
// update. Deleting the comments indicating the section will prevent
// it from being updated in the future.

package org.usfirst.frc100.BALLista.subsystems;

import org.usfirst.frc100.BALLista.Robot;
import org.usfirst.frc100.BALLista.RobotMap;
import org.usfirst.frc100.BALLista.commands.*;

import edu.wpi.first.wpilibj.AnalogPotentiometer;
import edu.wpi.first.wpilibj.CounterBase.EncodingType;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.command.PIDSubsystem;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class PickUp extends PIDSubsystem {

	private final DigitalInput upperLimit = RobotMap.pickUpUpperLimit;
	private final DigitalInput lowerLimit = RobotMap.pickUpLowerLimit;
	private final DigitalInput homeLimit = RobotMap.pickUpHomeLimit;
	private final SpeedController armAngleMotor = RobotMap.pickUpArmAngleMotor;
	private final AnalogPotentiometer pickUpPot = RobotMap.pickUpPickUpPot;

	public void updateDashboard() {

		SmartDashboard.putBoolean("PickUp/UpperLimit", upperLimit.get());
		SmartDashboard.putBoolean("PickUp/LowerLimit", lowerLimit.get());
		SmartDashboard.putBoolean("PickUp/HomeLimit", homeLimit.get());
		SmartDashboard.putNumber("PickUp/ArmAngleMotor", armAngleMotor.get());
		SmartDashboard.putNumber("PickUp/PickUpPot", pickUpPot.get());
		SmartDashboard.putBoolean("PickUp/TopLimit", RobotMap.pickUpUpperLimit.get());
    	SmartDashboard.putBoolean("PickUp/LowLimit", RobotMap.pickUpLowerLimit.get());
    	SmartDashboard.putNumber("PickUp/SetPoint", Robot.pickUp.getSetpoint());
    	SmartDashboard.putBoolean("PickUp/HomeLimit", RobotMap.pickUpHomeLimit.get());
    	SmartDashboard.putNumber("PickUp/Posison",Robot.pickUp.getArmPosVal());

	}

	// public PIDController pid;
	public PickUp() {
		super(6.0, .02, 0);
		/*
		 * pid = new PIDController(4.04, .5, 0, new PIDSource() { //.04 0 0 for
		 * 180 // .04 .02 0 for like 1 degree PIDSourceType m_sourceType =
		 * PIDSourceType.kDisplacement;
		 *
		 * public double pidGet() { return RobotMap.pickUpPickUpPot.get(); }
		 *
		 * @Override public void setPIDSourceType(PIDSourceType pidSource) {
		 * m_sourceType = pidSource; }
		 *
		 * @Override public PIDSourceType getPIDSourceType() { return
		 * m_sourceType; } }, new PIDOutput() { public void pidWrite(double d) {
		 *
		 * armAngleMotor.pidWrite(-d/4); // /2 }});
		 */
	}

	// Put methods for controlling this subsystem
	// here. Call these from Commands.
	public void initDefaultCommand() {
		setDefaultCommand(new MovePickUp());

		// Set the default command for a subsystem here.
		// setDefaultCommand(new MySpecialCommand());
	}

	public void takeJoystickInputs(double op) {
		armAngleMotor.set(op);
	}

	public double getArmAngleMotor() {
		return armAngleMotor.get();
	}

	public boolean hitUpper() {
		return !upperLimit.get(); // on testborad the polarity of the limit
									// swiches is normally true
	}

	public boolean hitLower() {
		return !lowerLimit.get(); // on testborad the polarity of the limit
									// swiches is normally true
	}

	public void manualControl(double speed) {

		if (Robot.pickUp.hitUpper()) {
			if (Robot.oi.operator.getRawAxis(1) > 0) {
				armAngleMotor.set(speed);
			} else {
				Robot.pickUp.stop();
			}
		} else if (Robot.pickUp.hitLower()) { // || !Robot.pickUp.hitLower() &&
												// !Robot.pickUp.hitUpper() &&
												// RobotMap.pickUpMidLimit.get()){
			if (Robot.oi.operator.getRawAxis(1) < 0) {
				armAngleMotor.set(speed);
			} else {
				armAngleMotor.set(.1);
			}
		} else {
			armAngleMotor.set(speed);
		}

		// armAngleMotor.set(speed);

		// Robot.pickUp.pickUpPot.get();
	}

	public void goToTop() {
		if (RobotMap.pickUpUpperLimit.get())
			armAngleMotor.set(.3);
		else
			armAngleMotor.set(0);

		if (!RobotMap.pickUpUpperLimit.get()) {
			// pid.setSetpoint(Robot.pickUp.pickUpPot.get());
		}
	}

	public void goToMid() {
		/*
		 * if(RobotMap.pickUpUpperLimit.get()){ armAngleMotor.set(-.5); } else
		 * if(RobotMap.pickUpLowerLimit.get()){ armAngleMotor.set(.5); } else {
		 * armAngleMotor.set(0); }
		 */
		if (RobotMap.pickUpUpperLimit.get())
			armAngleMotor.set(.5);
		if (!RobotMap.pickUpUpperLimit.get())
			armAngleMotor.set(-.5);
		if (!RobotMap.pickUpMidLimit.get()) {
			armAngleMotor.set(0);
			// pid.setSetpoint(Robot.pickUp.pickUpPot.get());
		}
	}

	public void goToBot() {
		if (RobotMap.pickUpUpperLimit.get())
			armAngleMotor.set(-.5);
		if (!RobotMap.pickUpLowerLimit.get()) {
			armAngleMotor.set(0);
			// pid.setSetpoint(Robot.pickUp.pickUpPot.get());
		}
	}

	public void stop() {
		armAngleMotor.set(0);
	}

	public double getArmPosVal() {
		return pickUpPot.get();
	}

	@Override
	protected double returnPIDInput() {
		// TODO Auto-generated method stub
		return RobotMap.pickUpPickUpPot.get();
	}

	@Override
	protected void usePIDOutput(double output) {
		armAngleMotor.set(-output / 2); // /2
	}
}
