// RobotBuilder Version: 2.0
//
// This file was generated by RobotBuilder. It contains sections of
// code that are automatically generated and assigned by robotbuilder.
// These sections will be updated in the future when you export to
// Java from RobotBuilder. Do not put any code or make any change in
// the blocks indicating autogenerated code or it will be lost on an
// update. Deleting the comments indicating the section will prevent
// it from being updated in the future.


package org.usfirst.frc100.Robot2016;

import org.usfirst.frc100.Robot2016.commands.*;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.*;

import org.usfirst.frc100.Robot2016.subsystems.*;


/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {
    //// CREATING BUTTONS
    // One type of button is a joystick button which is any button on a joystick.
    // You create one by telling it which joystick it's on and which button
    // number it is.
    // Joystick stick = new Joystick(port);
    // Button button = new JoystickButton(stick, buttonNumber);

    // There are a few additional built in buttons you can use. Additionally,
    // by subclassing Button you can create custom triggers and bind those to
    // commands the same as any other Button.

    //// TRIGGERING COMMANDS WITH BUTTONS
    // Once you have a button, it's trivial to bind it to a button in one of
    // three ways:

    // Start the command when the button is pressed and let it run the command
    // until it is finished as determined by it's isFinished method.
    // button.whenPressed(new ExampleCommand());

    // Run the command while the button is being held down and interrupt it once
    // the button is released.
    // button.whileHeld(new ExampleCommand());

    // Start the command when the button is released  and let it run the command
    // until it is finished as determined by it's isFinished method.
    // button.whenReleased(new ExampleCommand());


    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS
    public JoystickButton reverseOrient;
    public JoystickButton forwardOrient;
    public JoystickButton turnAround;
    public Joystick driverController1;
    public Joystick driverController2;
    public JoystickButton movePickupArm;
    public JoystickButton spinIn;
    public JoystickButton spinOut;
    public JoystickButton flipperOutside;
    public JoystickButton flipperHold;
    public JoystickButton flipperShoot;
    public JoystickButton turretRight;
    public JoystickButton turretLeft;
    public JoystickButton fastShoot;
    public JoystickButton mediumshoot;
    public JoystickButton slowShoot;
    public JoystickButton autoLine;
    public JoystickButton shoot;
    public Joystick operator;
    public Joystick danielCustomControllers;

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS
    JoystickButton forwardOrientDrive;
    JoystickButton backwardOrientDrive;
    public OI() {
        // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTRUCTORS

        danielCustomControllers = new Joystick(3);

        operator = new Joystick(0);

        shoot = new JoystickButton(operator, 1);
        shoot.whenPressed(new Shooting());
        autoLine = new JoystickButton(operator, 1);
        autoLine.whenPressed(new AutoAlignHighGoal());
        slowShoot = new JoystickButton(operator, 1);
        slowShoot.whileHeld(new Shooting());
        mediumshoot = new JoystickButton(operator, 1);
        mediumshoot.whileHeld(new Shooting());
        fastShoot = new JoystickButton(operator, 1);
        fastShoot.whileHeld(new Shooting());
        turretLeft = new JoystickButton(operator, 1);
        turretLeft.whileHeld(new AdjustLeft());
        turretRight = new JoystickButton(operator, 1);
        turretRight.whileHeld(new AdjustRight());
        flipperShoot = new JoystickButton(operator, 1);
        flipperShoot.whenPressed(new ShootFlippers());
        flipperHold = new JoystickButton(operator, 1);
        flipperHold.whenPressed(new HoldFlippers());
        flipperOutside = new JoystickButton(operator, 1);
        flipperOutside.whenPressed(new OutSideFlippers());
        spinOut = new JoystickButton(operator, 5);
        spinOut.whileHeld(new RollOut());
        spinIn = new JoystickButton(operator, 6);
        spinIn.whileHeld(new RollIn());
        movePickupArm = new JoystickButton(operator, 1);
        movePickupArm.whileHeld(new MovePickUp());
        driverController2 = new Joystick(2);

        driverController1 = new Joystick(1);

        turnAround = new JoystickButton(driverController1, 2);
        turnAround.whenPressed(new Turn180());
        forwardOrient = new JoystickButton(driverController1, 1);
        forwardOrient.whenPressed(new ChangeCameraOrientation());
        reverseOrient = new JoystickButton(driverController1, 1);
        reverseOrient.whenPressed(new ChangeCameraOrientation());


        // SmartDashboard Buttons
        SmartDashboard.putData("Autonomous Command", new AutonomousCommand());
        SmartDashboard.putData("AutonomousDriveForward", new AutonomousDriveForward());
        SmartDashboard.putData("Turn180", new Turn180());
        SmartDashboard.putData("ChangeCameraOrientation", new ChangeCameraOrientation());
        SmartDashboard.putData("MovePickUp", new MovePickUp());
        SmartDashboard.putData("RollIn", new RollIn());
        SmartDashboard.putData("RollOut", new RollOut());
        SmartDashboard.putData("OutSideFlippers", new OutSideFlippers());
        SmartDashboard.putData("HoldFlippers", new HoldFlippers());
        SmartDashboard.putData("ShootFlippers", new ShootFlippers());
        SmartDashboard.putData("AdjustLeft", new AdjustLeft());
        SmartDashboard.putData("AdjustRight", new AdjustRight());
        SmartDashboard.putData("AutoAlignHighGoal", new AutoAlignHighGoal());
        SmartDashboard.putData("ShootingSpeed", new ShootingSpeed());
        SmartDashboard.putData("Shooting", new Shooting());

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTRUCTORS
      forwardOrientDrive = new JoystickButton(driverController2, 5);
         backwardOrientDrive = new JoystickButton(driverController2, 4);
        forwardOrientDrive.whenPressed(new TankDrive(false));
        backwardOrientDrive.whenPressed(new TankDrive(true));


    }

    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=FUNCTIONS
    public Joystick getDriverController1() {
        return driverController1;
    }

    public Joystick getDriverController2() {
        return driverController2;
    }

    public Joystick getOperator() {
        return operator;
    }

    public Joystick getDanielCustomControllers() {
        return danielCustomControllers;
    }


    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=FUNCTIONS
}

