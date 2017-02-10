// RobotBuilder Version: 2.0
//
// This file was generated by RobotBuilder. It contains sections of
// code that are automatically generated and assigned by robotbuilder.
// These sections will be updated in the future when you export to
// Java from RobotBuilder. Do not put any code or make any change in
// the blocks indicating autogenerated code or it will be lost on an
// update. Deleting the comments indicating the section will prevent
// it from being updated in the future.


package org.usfirst.frc100.KitBotPneumatics;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import org.usfirst.frc100.KitBotPneumatics.commands.*;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.JoystickButton;


/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {

    public Joystick joystick1;
    public JoystickButton start; 
    public JoystickButton update;
    public JoystickButton calcDead;
    public JoystickButton openknewmatics;
    public JoystickButton closeknewmatics;

    public OI() {
        joystick1 = new Joystick(0);
        
        start = new JoystickButton(joystick1, 3);
        
        update = new JoystickButton(joystick1, 1);
        update.whenPressed(new updatePreferneces());
        
        calcDead = new JoystickButton(joystick1, 2);
        
        openknewmatics = new JoystickButton(joystick1, 5);
        openknewmatics.whenPressed(new OpenGear());
        closeknewmatics = new JoystickButton(joystick1, 6);
        closeknewmatics.whenPressed(new CloseGear());

        SmartDashboard.putData("ArcadeDrive", new ArcadeDrive());
    }

    public Joystick getJoystick1() {
        return joystick1;
    }
}
