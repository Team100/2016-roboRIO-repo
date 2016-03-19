// RobotBuilder Version: 2.0
//
// This file was generated by RobotBuilder. It contains sections of
// code that are automatically generated and assigned by robotbuilder.
// These sections will be updated in the future when you export to
// Java from RobotBuilder. Do not put any code or make any change in
// the blocks indicating autogenerated code or it will be lost on an
// update. Deleting the comments indicating the section will prevent
// it from being updated in the future.

package org.usfirst.frc100.BALLista;

import org.usfirst.frc100.BALLista.commands.*;
import org.usfirst.frc100.BALLista.subsystems.*;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.*;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */

public class OI {

	public JoystickButton reverseOrient;
	public JoystickButton forwardOrient;
	public JoystickButton turnAround;
	public Joystick driverController1;
	public Joystick driverController2;
	public JoystickButton movePickupArm;
	public JoystickButton spinIn;
	public JoystickButton spinOut;
	public JoystickButton spinnerAdjust;
	public JoystickButton fastShoot;
	public JoystickButton mediumshoot;
	public JoystickButton slowShoot;
	public JoystickButton autoLine;
	public JoystickButton reverseshoot;
	public Joystick operator;
	public JoystickButton turn180;
	public JoystickButton LoadBall;
	public JoystickButton moveAway;
	public JoystickButton hold;
	public JoystickButton top;
	public JoystickButton mid;
	public JoystickButton bot;
	public JoystickButton resetPidOfArm;
	public JoystickButton backwardOrientDrive;
	public JoystickButton putBallInShooterPosition;
	public JoystickButton putBallinDrivePosition;
	public JoystickButton putArmToGround;
	public JoystickButton turn90Right;
	public JoystickButton turn90Left;
	public JoystickButton lineUpRobotWithGoal;
	public JoystickButton overrideBottomLimit;
	public JoystickButton defultArmJoystick;
	public JoystickButton disableAline;
	public InternalButton testUpDPad;
	public InternalButton testRightDPad;
	public InternalButton testDownDPad;
	public InternalButton testLeftDPad;

	public Joystick autoModeSelect;
		public JoystickButton binary1;
		public JoystickButton binary2;
		public JoystickButton binary3;
		public JoystickButton binary4;

	public OI() {
		operator = new Joystick(0);
		driverController1 = new Joystick(1);
		driverController2 = new Joystick(2);

		// shoot = new JoystickButton(operator, 3);
		// shoot.whenPressed(new Shooting());

		 autoLine = new JoystickButton(driverController2, 8);
		 autoLine.whenPressed(new AutoAlignHighGoal(true));
		 disableAline = new JoystickButton(driverController2, 9);
		 disableAline.whenPressed(new AutoAlignHighGoal(false));

		// slowShoot = new JoystickButton(operator, 1);
		// slowShoot.whileHeld(new ShootingSpeed(0));

		// mediumshoot = new JoystickButton(operator, 4);
		// mediumshoot.whileHeld(new ShootingSpeed(1900));

		// fastShoot = new JoystickButton(operator, 3);
		// fastShoot.whileHeld(new ShootingSpeed(2600));

		// hold = new JoystickButton(driverController1, 2);
		// hold.whileHeld(new holdCurrentGyroPosition());

		// LoadBall = new JoystickButton(operator, 10);
		// LoadBall.whileHeld(new MovePickUpWithPID(.5));
		// moveAway = new JoystickButton(operator, 9);
		// moveAway.whileHeld(new MovePickUpWithPID(.4));

		reverseshoot = new JoystickButton(operator, 10);
		//reverseshoot.whenPressed(new ShootingSpeed(-.2));
		reverseshoot.whenPressed(new AutoAlignHighGoal());

		// autoLine = new JoystickButton(operator, 1);
		// autoLine.whenPressed(new AutoAlignHighGoal());

		slowShoot = new JoystickButton(operator, 9);
		slowShoot.whenPressed(new ShootingSpeed(.25));

		mediumshoot = new JoystickButton(operator, 8);
		mediumshoot.whenPressed(new ShootingSpeed(0));

		fastShoot = new JoystickButton(operator, 6);
		fastShoot.whenPressed(new ShootingSpeed(.5));

		top = new JoystickButton(operator, 1);
		top.whileHeld(new MovePickUpWithPID(Robot.prefs.getDouble("shooter_top", 0.413)));
		mid = new JoystickButton(operator, 4);
		mid.whileHeld(new MovePickUpWithPID(Robot.prefs.getDouble("shooter_mid", 0.558)));
		bot = new JoystickButton(operator, 3);
		bot.whileHeld(new MovePickUpWithPID(Robot.prefs.getDouble("shooter_bot", 0.658)));
		hold = new JoystickButton(driverController1, 2);
		hold.whileHeld(new holdCurrentGyroPosition());
		turn90Right = new JoystickButton(driverController2, 5);
		turn90Right.whileHeld(new TurnToAngle(90));
		turn90Left = new JoystickButton(driverController2, 4);
		turn90Left.whileHeld(new TurnToAngle(-90));
		spinOut = new JoystickButton(driverController1, 1);
		spinOut.whileHeld(new RollOut(Robot.prefs.getDouble("MoveRollIn_rolloutSpeed", -0.1)));

		spinIn = new JoystickButton(driverController2, 1);
		spinIn.whileHeld(new RollIn());

		turn180 = new JoystickButton(driverController2, 3);
		turn180.whileHeld(new TurnToAngle(170));
		reverseOrient = new JoystickButton(driverController1, 11);
		forwardOrient = new JoystickButton(driverController1, 10);
		reverseOrient.whenPressed(new TankDrive(false));
		forwardOrient.whenPressed(new TankDrive(true));


		testUpDPad = new InternalButton();
		//testUpDPad.whenPressed(new DoNothing(1111111111));

		testRightDPad = new InternalButton();
		//testRightDPad.whenPressed(new DoNothing(2222222));

		testDownDPad = new InternalButton();
		//testDownDPad.whenPressed(new DoNothing(3333343));

		testLeftDPad = new InternalButton();
		//testLeftDPad.whenPressed(new DoNothing(444));


		// movePickupArm = new JoystickButton(operator, 1);
		// movePickupArm.whileHeld(new MovePickUp());

		// forwardOrient = new JoystickButton(driverController1, 1);
		// forwardOrient.whenPressed(new ChangeCameraOrientation());

		// reverseOrient = new JoystickButton(driverController1, 1);
		// reverseOrient.whileHeld(new ChangeCameraOrientation());

		// backwardOrientDrive = new JoystickButton(driverController1, 3);
		// backwardOrientDrive.whileHeld(new TankDrive(false));
		//hey michael wassup
		//wyd
		//yoooooooooooooooo

		autoModeSelect = new Joystick(3);
		{
			binary1 = new JoystickButton(autoModeSelect, 1);
			binary2 = new JoystickButton(autoModeSelect, 2);
			binary3 = new JoystickButton(autoModeSelect, 3);
			binary4 = new JoystickButton(autoModeSelect, 4);
		}

		putBallInShooterPosition = new JoystickButton(driverController1, 10);
		putBallinDrivePosition = new JoystickButton(driverController1, 9);
		putArmToGround = new JoystickButton(driverController1, 11);
		lineUpRobotWithGoal = new JoystickButton(driverController1, 12);

		// SmartDashboard Buttons
		SmartDashboard.putData("Autonomous Command", new AutonomousCommand());
		SmartDashboard.putData("Turn180", new TurnToAngle());
		SmartDashboard.putData("ChangeCameraOrientation",
				new ChangeCameraOrientation());
		SmartDashboard.putData("MovePickUp", new MovePickUp());
		SmartDashboard.putData("RollIn", new RollIn());
		SmartDashboard.putData("RollOut", new RollOut());
		SmartDashboard.putData("AutoAlignHighGoal", new AutoAlignHighGoal());
		// SmartDashboard.putData("ShootingSpeed", new ShootingSpeed());
		SmartDashboard.putData("Shooting", new Shooting());
	}

	public Joystick getDriverController1() {
		return driverController1;
	}

	public Joystick getDriverController2() {
		return driverController2;
	}

	public Joystick getOperator() {
		return operator;
	}

	public int selector() {
		boolean bin1Val = binary1.get();
		boolean bin2Val = binary2.get();
		boolean bin3Val = binary3.get();
		boolean bin4Val = binary4.get();
		int total = 0;

		if (bin1Val) {
			total += 1;
		}
		if (bin2Val) {
			total += 2;
		}
		if (bin3Val) {
			total += 4;
		}
		if (bin4Val) {
			total += 8;
		}
		return total;
	}

	private int getDPad(Joystick stick) {
		if(stick.getPOV() < 0) {
			return -1;
		} else {
			return stick.getPOV()/45;
		}
	}

	public void updateDPad() {
		int value = getDPad(operator);

		testUpDPad.setPressed(value == 0);
		testRightDPad.setPressed(value == 2);
		testDownDPad.setPressed(value == 4);
		testLeftDPad.setPressed(value == 6);
	}
}