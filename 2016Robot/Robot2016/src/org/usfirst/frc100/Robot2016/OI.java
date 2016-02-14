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
    public JoystickButton spinnerAdjust;
    public JoystickButton fastShoot;
    public JoystickButton mediumshoot;
    public JoystickButton slowShoot;
    public JoystickButton autoLine;
    public JoystickButton shoot;
    public Joystick operator;
    public Joystick danielCustomControllers;
    public JoystickButton turn180;
    JoystickButton forwardOrientDrive;
    JoystickButton backwardOrientDrive;

    public OI() {

        danielCustomControllers = new Joystick(3);
        operator = new Joystick(0);
        driverController2 = new Joystick(2);
        driverController1 = new Joystick(1);

        shoot = new JoystickButton(operator, 3);
        shoot.whenPressed(new Shooting());

        autoLine = new JoystickButton(operator, 1);
        autoLine.whenPressed(new AutoAlignHighGoal());

        slowShoot = new JoystickButton(operator, 1);
        slowShoot.whileHeld(new ShootingSpeed(0));

        mediumshoot = new JoystickButton(operator, 4);
        mediumshoot.whileHeld(new ShootingSpeed(1900));

        fastShoot = new JoystickButton(operator, 3);
        fastShoot.whileHeld(new ShootingSpeed(2600));

        spinnerAdjust = new JoystickButton(operator, 2);
        spinnerAdjust.whileHeld(new AdjustSpinner());

        /*
        flipperShoot = new JoystickButton(operator, 1);
        flipperShoot.whenPressed(new ShootFlippers());

        flipperHold = new JoystickButton(operator, 1);
        flipperHold.whenPressed(new HoldFlippers());

        flipperOutside = new JoystickButton(operator, 1);
        flipperOutside.whenPressed(new OutSideFlippers());
        */

        spinOut = new JoystickButton(operator, 7);
        spinOut.whileHeld(new RollOut());

        spinIn = new JoystickButton(operator, 5);
        spinIn.whileHeld(new RollIn());

        movePickupArm = new JoystickButton(operator, 1);
        movePickupArm.whileHeld(new MovePickUp());

        turnAround = new JoystickButton(driverController2, 3);
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
        SmartDashboard.putData("AdjustSpinner", new AdjustSpinner());
        SmartDashboard.putData("AutoAlignHighGoal", new AutoAlignHighGoal());
        //SmartDashboard.putData("ShootingSpeed", new ShootingSpeed());
        SmartDashboard.putData("Shooting", new Shooting());

        forwardOrientDrive = new JoystickButton(driverController1, 5);
        backwardOrientDrive = new JoystickButton(driverController1, 4);
        forwardOrientDrive.whenPressed(new TankDrive(false));
        backwardOrientDrive.whenPressed(new TankDrive(true));

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

    public Joystick getDanielCustomControllers() {
        return danielCustomControllers;
    }

}

