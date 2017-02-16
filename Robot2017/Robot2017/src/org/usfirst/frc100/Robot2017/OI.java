// RobotBuilder Version: 2.0
//
// This file was generated by RobotBuilder. It contains sections of
// code that are automatically generated and assigned by robotbuilder.
// These sections will be updated in the future when you export to
// Java from RobotBuilder. Do not put any code or make any change in
// the blocks indicating autogenerated code or it will be lost on an
// update. Deleting the comments indicating the section will prevent
// it from being updated in the future.


package org.usfirst.frc100.Robot2017;

import org.usfirst.frc100.Robot2017.commands.*;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

import org.usfirst.frc100.Robot2017.subsystems.*;


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

	//Joysticks

    public Joystick leftController;
    public Joystick rightController;
    public Joystick operater;
    public Joystick autoModeSelect;
    
    
    //Manipulator Commands

    public JoystickButton shootBalls;
    public JoystickButton outTakeBalls;
    public JoystickButton inTakeBalls;
    public JoystickButton stopPickUpMotors;
    public JoystickButton openGear;
    public JoystickButton closeGear;
    public JoystickButton openFlap;
    public JoystickButton closeFlap;
    public JoystickButton manipulatorClimb;
    
    //Driver Commands

    public JoystickButton l90Degrees;
    public JoystickButton r90Degrees;
    public JoystickButton turnAround;
    public JoystickButton lineUp;
    public JoystickButton driverClimb;
    
    //Things?
    
 	public JoystickButton binary1;
 	public JoystickButton binary2;
 	public JoystickButton binary3;
 	public JoystickButton binary4;
 	

    public OI() {



        autoModeSelect = new Joystick(3);
        operater = new Joystick(2);
        rightController = new Joystick(1);
        leftController = new Joystick(0);
        
        // SmartDashboard Buttons
        SmartDashboard.putData("Autonomous Command", new AutonomousCommand());
        SmartDashboard.putData("Collect", new Collect());
        SmartDashboard.putData("Dump", new Dump());
        SmartDashboard.putData("Command 1", new TankDrive());
        binary1 = new JoystickButton(autoModeSelect, 1);
      	binary2 = new JoystickButton(autoModeSelect, 2);
      	binary3 = new JoystickButton(autoModeSelect, 3);
      	binary4 = new JoystickButton(autoModeSelect, 4);
    }

    public Joystick getleftController() {
        return leftController;
    }

    public Joystick getrightController() {
        return rightController;
    }

    public Joystick getoperater() {
        return operater;
    }

    public Joystick getautoModeSelect() {
        return autoModeSelect;
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


}

