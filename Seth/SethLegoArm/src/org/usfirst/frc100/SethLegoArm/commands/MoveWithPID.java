package org.usfirst.frc100.SethLegoArm.commands;

import org.usfirst.frc100.SethLegoArm.Robot;
import org.usfirst.frc100.SethLegoArm.RobotMap;

import edu.wpi.first.wpilibj.AnalogPotentiometer;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.PIDSubsystem;

public class MoveWithPID extends Command {
	
	public MoveWithPID(){
		
	}
	 protected void initialize() {
    	Robot.robotArm.enable();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	Robot.robotArm.raise();
    }

	protected boolean isFinished() {
		// TODO Auto-generated method stub
		return false;
	}
	
    protected void end() {
    	Robot.robotArm.stop();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }

}