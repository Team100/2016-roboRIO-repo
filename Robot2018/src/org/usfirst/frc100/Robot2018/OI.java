// RobotBuilder Version: 2.0
//
// This file was generated by RobotBuilder. It contains sections of
// code that are automatically generated and assigned by robotbuilder.
// These sections will be updated in the future when you export to
// Java from RobotBuilder. Do not put any code or make any change in
// the blocks indicating autogenerated code or it will be lost on an
// update. Deleting the comments indicating the section will prevent
// it from being updated in the future.


package org.usfirst.frc100.Robot2018;


import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.*;
import edu.wpi.first.wpilibj.command.Command;

import org.usfirst.frc100.Robot2018.commands.*;
import org.usfirst.frc100.Robot2018.subsystems.*;

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
    public JoystickButton button1;
    public JoystickButton button2;
    public JoystickButton button3;
    public JoystickButton button4;
    public JoystickButton button5;
    public JoystickButton button6;
    public JoystickButton button7;
    public JoystickButton button8;
    public JoystickButton button9;
    public JoystickButton button10;
    public JoystickButton button11;
    public JoystickButton button12;
    public JoystickButton rightS; 

    public JoystickButton buttona;
    public JoystickButton buttonb;
    
    public JoystickButton BS; 
    public JoystickButton BR; 
    public JoystickButton leftS;
    public JoystickButton updatePrefs;
    


    public static Joystick leftController;
    public static Joystick rightStick;


    public Joystick autoModeSelect;
    public static Joystick operator;
    public static JoystickButton shiftoff;
    public JoystickButton binary1;
 	public JoystickButton binary2;
 	public JoystickButton binary3;
 	public JoystickButton binary4;



    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS

    public OI() {
        // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTRUCTORS
    	SmartDashboard.putBoolean("LOADED", false); //This has NOT been added to the UpdateSmartDashboard due to its purpose
        operator = new Joystick(2);
        
        autoModeSelect = new Joystick(3);
        
        rightStick = new Joystick(1);
        
        leftController = new Joystick(0);
        binary1 = new JoystickButton(autoModeSelect, 1);
      	binary2 = new JoystickButton(autoModeSelect, 2);
      	binary3 = new JoystickButton(autoModeSelect, 3);
      	binary4 = new JoystickButton(autoModeSelect, 4);

        button12 = new JoystickButton(operator, 1);
        button12.whileHeld(new WinchWind());
        button11 = new JoystickButton(leftController, 11);
        button11.whileHeld(new ClimbAdjust());
        button10 = new JoystickButton(leftController, 10);
        button10.whenPressed(new ClimbDown());
        button9 = new JoystickButton(leftController, 9);
        button9.whenPressed(new ClimbUp());
        
        buttona = new JoystickButton(operator, 7);
        buttona.whenPressed(new ElevatorArmUp());        
        buttonb = new JoystickButton(operator, 8);
        buttonb.whenPressed(new ElevatorArmDown());
        
        button8 = new JoystickButton(operator, 5);
        button8.whenPressed(new IntakeOut());
        button7 = new JoystickButton(operator, 6);
        button7.whileHeld(new IntakeIn());
        button6 = new JoystickButton(leftController, 6);
        button6.whileHeld(new ElevatorAdjust());
        button5 = new JoystickButton(operator, 3);
        button5.whenPressed(new ElevatorDown());
        button4 = new JoystickButton(operator, 4);
        button4.whenPressed(new ElevatorUp());
        button3 = new JoystickButton(operator, 2);
        button3.whenPressed(new shiftOff());
        /*
        button2 = new JoystickButton(operator, 1);
        button2.whenPressed(new shift());

        */
       // button3 = new JoystickButton(operator, 2);
        //button3.whenPressed(new PathFinding("BackR"));
        //button2 = new JoystickButton(operator, 1);
        //button2.whenPressed(new PathFinding("BS"));
        //BS = new JoystickButton(operator, 5); 
        //BS.whenPressed(new PathFinding("Left"));
        //BR = new JoystickButton(operator, 8); 
        //BR.whenPressed(new GoToAngle(20));

        button1 = new JoystickButton(leftController, 1);
        //rightS = new JoystickButton(operator, 3);
        //rightS.whenPressed(new PathFinding("Right"));
        //leftS = new JoystickButton(operator, 6); 
        //leftS.whenPressed(new PathFinding("ScaleTurnLeft"));
    


        // SmartDashboard Buttons
        new UpdateSmartDashboard();
      //  button1.whenPressed(new shift());
        shiftoff = new JoystickButton(rightStick, 1);
        //shiftoff.whenPressed(new shiftOff());
        

        SmartDashboard.putData("WinchWind", new WinchWind());


        // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTRUCTORS
    }

    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=FUNCTIONS
    public Joystick getLeftController() {
        return leftController;
    }

    public Joystick getRightStick() {
        return rightStick;
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

    public Joystick getAutoModeSelect() {
        return autoModeSelect;
    }

    public Joystick getOperator() {
        return operator;
    }


    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=FUNCTIONS
}

