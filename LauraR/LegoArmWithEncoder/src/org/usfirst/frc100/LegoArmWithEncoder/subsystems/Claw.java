// RobotBuilder Version: 1.5
//
// This file was generated by RobotBuilder. It contains sections of
// code that are automatically generated and assigned by robotbuilder.
// These sections will be updated in the future when you export to
// Java from RobotBuilder. Do not put any code or make any change in
// the blocks indicating autogenerated code or it will be lost on an
// update. Deleting the comments indicating the section will prevent
// it from being updated in the future.


package org.usfirst.frc100.LegoArmWithEncoder.subsystems;

import org.usfirst.frc100.LegoArmWithEncoder.RobotMap;
import org.usfirst.frc100.LegoArmWithEncoder.commands.ClawOpen;

import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class Claw extends Subsystem {
    Servo clawServo = RobotMap.clawServo;
    final static String ntPrefix = "Claw/"; // Prefix for network table variables

    /**
	 * 
	 */
	public Claw() {
		this.addChild("Claw Servo", clawServo);
		open();
	}

	public void initDefaultCommand() {
        // Set the default command for a subsystem here.
    }
	
	public void open() {
		clawServo.setPosition(0.5);
	}
	
	public void close() {
		clawServo.setPosition(1.0);
	}
	
	public void updateDashboard()
    {
    	// Show variables in SmartDashboard
        SmartDashboard.putNumber(ntPrefix + "Servo", clawServo.get());
        SmartDashboard.putString(ntPrefix + "CurrentCommand", getCurrentCommandName());
        
    }
}
