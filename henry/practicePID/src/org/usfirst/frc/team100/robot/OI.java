package org.usfirst.frc.team100.robot;

import edu.wpi.first.wpilibj.Joystick;

import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

import org.usfirst.frc.team100.robot.commands.Drive;
import org.usfirst.frc.team100.robot.commands.updatePreferneces;
//import org.usfirst.frc.team100.robot.commands.SetElevatorSetpoint;
//import org.usfirst.frc.team100.robot.commands.SetElevatorSetpoint;
//import org.usfirst.frc100.RobotAndrew.Robot;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {
	 public Joystick joy = new Joystick(0);
	
	 public JoystickButton start; 
	 public double shooter_speed;
	 public JoystickButton stop;
	 public JoystickButton update;
	 public OI()
	 {
		 /*
		 if (!Robot.prefs.containsKey("shooter_speed")) {
				Robot.prefs.putDouble("shooter_speed", 	100);
			}

			shooter_speed = Robot.prefs.getDouble("shooter_speed",
					0);
					*/
		 /*
		 JoystickButton go1 = new JoystickButton(joy, 5);
		 JoystickButton go2 = new JoystickButton(joy, 7);
		 go1.whenPressed(new Drive(90));
	     go2.whenPressed(new Drive(-90));
	     */
		 //go.whenPressed(new Drive(0.2)); 
		  JoystickButton update = new JoystickButton(joy, 3);
		  update.whenPressed(new updatePreferneces());
		  JoystickButton start = new JoystickButton(joy, 1);
		  start.whenPressed((new Drive(9)));
		  
		  JoystickButton stop = new JoystickButton(joy, 2);
		  stop.whenPressed((new Drive(true)));
	    
		  
	 }
	 
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
}

