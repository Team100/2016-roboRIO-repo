package org.usfirst.frc.team100.robot;

import com.ctre.phoenix.motorcontrol.ControlMode;

import edu.wpi.first.wpilibj.command.Command;

public class command1 extends Command {
	public static boolean upOne;
    public command1() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    }

    // Called just before this Command runs the first time
    protected void initialize() {  
    	System.out.println("Works");
    	Robot.m_motor.set(ControlMode.MotionMagic, 10000);

    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	Robot.m_motor.set(ControlMode.MotionMagic, 10000);

    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    	System.out.println("done");
    	
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}