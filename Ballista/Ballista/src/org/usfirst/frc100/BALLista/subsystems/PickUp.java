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

/**
 *
 */
public class PickUp extends PIDSubsystem {

	private final DigitalInput upperLimit = RobotMap.pickUpUpperLimit;
	private final DigitalInput lowerLimit = RobotMap.pickUpLowerLimit;
	private final DigitalInput homeLimit = RobotMap.pickUpHomeLimit;
	private final SpeedController armAngleMotor = RobotMap.pickUpArmAngleMotor;
	private final AnalogPotentiometer pickUpPot = RobotMap.pickUpPickUpPot;
	
	private final static double DEFAULT_PICKUP_KP = 6.0;
	private final static double DEFAULT_PICKUP_KI = 0.02;
	private final static double DEFAULT_PICKUP_KD = 0.0;

	private double pickup_kP;
	private double pickup_kI;
	private double pickup_kD;
	
	// public PIDController pid;

	public PickUp() {
		super(DEFAULT_PICKUP_KP, DEFAULT_PICKUP_KI, DEFAULT_PICKUP_KD);
		
		
		if (!Robot.prefs.containsKey("Pickup_kP")){
			Robot.prefs.putDouble("Pickup_kP", DEFAULT_PICKUP_KP);
		}
		if (!Robot.prefs.containsKey("Pickup_kI")){
			Robot.prefs.putDouble("Pickup_kI", DEFAULT_PICKUP_KI);
		}
		if (!Robot.prefs.containsKey("Pickup_kD")){
			Robot.prefs.putDouble("Pickup_kD", DEFAULT_PICKUP_KD);
		}
		
		pickup_kP = Robot.prefs.getDouble("Pickup_kP", DEFAULT_PICKUP_KP);
		pickup_kI = Robot.prefs.getDouble("Pickup_kI", DEFAULT_PICKUP_KI);
		pickup_kD = Robot.prefs.getDouble("Pickup_kD", DEFAULT_PICKUP_KD);
		
		getPIDController().setPID(pickup_kP, pickup_kI, pickup_kD);
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
