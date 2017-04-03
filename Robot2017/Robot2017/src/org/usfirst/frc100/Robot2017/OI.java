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
	//Joysticks
    public Joystick leftController;
    public Joystick rightController;
    public Joystick operater;
    public Joystick autoModeSelect;
    
    //Manipulator Commands
    public JoystickButton shoot;
    public JoystickButton pickUp;
    public JoystickButton dump;
    public JoystickButton stopMotors;
    public JoystickButton openGear;
    public JoystickButton closeGear;
    public JoystickButton openFlap;
    public JoystickButton closeFlap;
    public JoystickButton climberNudgeUpButton;
    public JoystickButton climberNudgeDownButton;
    
    //Driver Commands
    public JoystickButton updatePrefs;
    public JoystickButton autoDrive;
    public JoystickButton turnAround;
    public JoystickButton lineUp;
    public JoystickButton driverClimb;
    public JoystickButton lowshift;
    public JoystickButton highShift;
    public JoystickButton tester;
    
    //Things?
 	public JoystickButton binary1;
 	public JoystickButton binary2;
 	public JoystickButton binary3;
 	public JoystickButton binary4;

    public OI() {
    	//Prefrences
    	
    	// Joysticks
        autoModeSelect = new Joystick(3);
        operater = new Joystick(2);
        rightController = new Joystick(1);
        leftController = new Joystick(0);
        
        // SmartDashboard Buttons
        SmartDashboard.putData("Autonomous Command", new AutonomousCommand());
        SmartDashboard.putData("Command 1", new TankDrive());
        binary1 = new JoystickButton(autoModeSelect, 1);
      	binary2 = new JoystickButton(autoModeSelect, 2);
      	binary3 = new JoystickButton(autoModeSelect, 3);
      	binary4 = new JoystickButton(autoModeSelect, 4);
      	
      	//manipulator things
      	shoot = new JoystickButton(operater, 4);
      	shoot.whenPressed(new Shoot(.5));
      	
        pickUp = new JoystickButton(operater, 3);
        pickUp.whenPressed(new PickUp(.75));
        
        pickUp = new JoystickButton(operater, 1);
        pickUp.whenPressed(new PickUp(.75));
        
        stopMotors = new JoystickButton(operater, 2);
        stopMotors.whenPressed(new StopMotors());
        
        openGear = new JoystickButton(operater, 8);
        openGear.whenPressed(new OpenGear(0.75));
        
        closeGear = new JoystickButton(operater, 6);
        closeGear.whenPressed(new CloseGear(0.25));
        
        openFlap = new JoystickButton(operater, 7);
        openFlap.whenPressed(new OpenFlap(0.75));
        
        closeFlap = new JoystickButton(operater, 5);
        closeFlap.whenPressed(new CloseFlap(0.25));
        
        climberNudgeDownButton = new JoystickButton(operater, 9);
        climberNudgeDownButton.whenPressed(new ClimbNudge("down", 4));
        
        climberNudgeUpButton = new JoystickButton(operater, 10);
        climberNudgeUpButton.whenPressed(new ClimbNudge("up", 4));
        
        //driver things
        updatePrefs = new JoystickButton(leftController, 4);
        updatePrefs.whenPressed(new updatePreferneces());
        
        autoDrive = new JoystickButton(autoModeSelect, 9);
        autoDrive.whenPressed(new StraightAuto());
        
        turnAround = new JoystickButton(leftController, 5);
       turnAround.whenPressed(new TurnToAngle("vision"));    
        
        driverClimb = new JoystickButton(rightController, 5);
        driverClimb.whenPressed(new Climb());

        //lowshift = new JoystickButton(leftController, 1);
        //lowshift.whenPressed(new LowGearShift(0.25));
        
        highShift = new JoystickButton(rightController, 1);
        highShift.whenPressed(new HighGearShift(0.25));
        
     //   tester = new JoystickButton(rightController, 3)
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

