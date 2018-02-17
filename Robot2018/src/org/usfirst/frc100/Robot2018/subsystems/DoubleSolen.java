package org.usfirst.frc100.Robot2018.subsystems;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.command.Subsystem;

import org.usfirst.frc100.Robot2018.RobotMap;
import org.usfirst.frc100.Robot2018.commands.DoubleSolenoidControl;

/**
 *
 */
public class DoubleSolen extends Subsystem {
	private static DoubleSolenoid duosole = RobotMap.DuoSol;
    // Put methods for controlling this subsystem
    // here. Call these from Commands.

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        setDefaultCommand(new DoubleSolenoidControl());
    }
}

