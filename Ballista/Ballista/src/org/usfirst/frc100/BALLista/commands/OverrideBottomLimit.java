package org.usfirst.frc100.BALLista.commands;

import org.usfirst.frc100.BALLista.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class OverrideBottomLimit extends Command {

    public OverrideBottomLimit(boolean armjoystick) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	Robot.pickUp.setReverseJoysticksAndByPassLimits(armjoystick);
    	requires(Robot.pickUp);
    }

    // Called just before this Command runs the first time
    protected void initialize() {

    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	if(Robot.pickUp.getReverseJoysticksAndByPassLimits()){
        	Robot.pickUp.JoysticksAndByPassLimits();
        	}
        	else{
        	Robot.pickUp.ReverseJoysticksAndByPassLimits();
        	}

        	if(Robot.pickUp.getReverseJoysticksAndByPassLimits()){
        	Robot.pickUp.JoysticksAndByPassLimits();
        	}else{
        	Robot.pickUp.ReverseJoysticksAndByPassLimits();
        	}
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.moveRollIn.stop();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
