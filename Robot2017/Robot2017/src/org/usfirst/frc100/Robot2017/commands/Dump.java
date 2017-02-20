package org.usfirst.frc100.Robot2017.commands;

import org.usfirst.frc100.Robot2017.Robot;
import org.usfirst.frc100.Robot2017.subsystems.BallHandling.BallHandlingState;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class Dump extends Command {
	
	private double t;
	private Timer timer = new Timer();
	
	private boolean firstTime = true;
	
	private BallHandlingState iState;
	private BallHandlingState cState;
	
    public Dump(float defultClearingTime) {
    	requires(Robot.ballHandling);
    	t = defultClearingTime;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	iState = Robot.ballHandling.getState();
		timer.reset();
		timer.start();
		switch(iState){
			case shooting: 
			case readyToShoot:
				Robot.ballHandling.setState(BallHandlingState.clearElevator);
				cState = Robot.ballHandling.getState();
				break;
			case pickingUp:
			case readyToPickupOrDump: 
			case dumping:
				Robot.ballHandling.setState(BallHandlingState.dumping);
				cState = Robot.ballHandling.getState();
				break;
			case clearElevator:
			case clearPickUp:
				System.out.println("uh your not supposte to be here");
				break;
		}
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	switch(cState){
			case shooting: 
			case readyToShoot:
				System.out.println("uh your not supposte to be here");
				
				Robot.ballHandling.setState(BallHandlingState.clearElevator);
				cState = Robot.ballHandling.getState();
				
				break;
			case pickingUp:
			case readyToPickupOrDump: 
			case dumping:
	    		Robot.ballHandling.dumperLift.set(true);
	    		Robot.ballHandling.pickUpFlap.set(true);
	    		Robot.ballHandling.setElevator(1);		//add pref for speed?
		    	Robot.ballHandling.setOutsideRoller(-1);//add pref for speed?
		    		
		    	Robot.ballHandling.setState(BallHandlingState.dumping);
				cState = Robot.ballHandling.getState();
				
				break;
			case clearElevator:
				if(firstTime){
					firstTime = false;
				}
				
				Robot.ballHandling.dumperLift.set(true);
    			Robot.ballHandling.pickUpFlap.set(true);
    			Robot.ballHandling.setElevator(-1); 		//add pref for speed?
		    	Robot.ballHandling.setOutsideRoller(-1); 	//add pref for speed?
		    	
				if(intermediantStepDone()){
					Robot.ballHandling.setState(BallHandlingState.dumping);
					cState = Robot.ballHandling.getState();
				}
				
				break;
			case clearPickUp:
				System.out.println("uh your not supposte to be here");

		    	Robot.ballHandling.setState(BallHandlingState.dumping);
				cState = Robot.ballHandling.getState();
				
				break;
		}
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	Robot.ballHandling.setState(cState);
    }
    
    protected boolean intermediantStepDone() {
    	return timer.get() > t;
    }
}
