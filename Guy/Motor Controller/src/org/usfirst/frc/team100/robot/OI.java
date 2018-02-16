package org.usfirst.frc.team100.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

public class OI {
    public static JoystickButton button1;
    public OI(){
    	button1 = new JoystickButton(Robot.m_joystick, 2);
    	button1.whenPressed(new command1());
    }
    
    public Joystick getJoystick1() {
        return Robot.m_joystick;
    }
}
