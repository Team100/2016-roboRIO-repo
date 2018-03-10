package org.usfirst.frc100.Robot2018.commands;

import org.usfirst.frc100.Robot2018.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class passTime extends Command {
	public static double timePassed;
	public static boolean done;
	double t;
    public passTime(double time) {
    	t = time;
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	//taking the time in nanoseconds and  converting to milliseconds 
    	timePassed = System.nanoTime()/1000000;
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	//Robot.RequestedTime
    	if(System.nanoTime() ==  timePassed + 1000*(t)) {
    		done = true;
    	}
    	
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return done;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}