package org.usfirst.frc100.Robot2017.commands;

import org.usfirst.frc100.Robot2017.Robot;
import org.usfirst.frc100.Robot2017.subsystems.BallHandling.BallHandlingState;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class Shoot extends Command {
	
	private double t;
	private Timer timer = new Timer();
	
	private boolean firstTime = true;
	
	private BallHandlingState iState;
	private BallHandlingState cState;

    public Shoot(float defultClearingTime) {
    	requires(Robot.ballHandling);
    	t = defultClearingTime;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	iState = Robot.ballHandling.getState();
		switch(iState){
			case shooting: 
			case readyToShoot:
				Robot.ballHandling.setState(BallHandlingState.shooting);
				cState = Robot.ballHandling.getState();
				break;
			case pickingUp:
			case readyToPickupOrDump: 
			case dumping:
				Robot.ballHandling.setState(BallHandlingState.clearPickUp);
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
				Robot.ballHandling.dumperLift.set(false);
	    		Robot.ballHandling.pickUpFlap.set(false);
	    		Robot.ballHandling.setElevator(1);		//add pref for speed?
		    	Robot.ballHandling.setOutsideRoller(1);	//add pref for speed?
				
				Robot.ballHandling.setState(BallHandlingState.clearElevator);
				cState = Robot.ballHandling.getState();
				
				break;
			case pickingUp:
			case readyToPickupOrDump: 
			case dumping:
				System.out.println("uh your not supposte to be here");
				
		    	Robot.ballHandling.setState(BallHandlingState.clearPickUp);
				cState = Robot.ballHandling.getState();
				
				break;
			case clearElevator:
				System.out.println("uh your not supposte to be here");
				
		    	Robot.ballHandling.setState(BallHandlingState.shooting);
				cState = Robot.ballHandling.getState();
				
				break;
			case clearPickUp:
				if(firstTime){
					timer.reset();
					timer.start();
					firstTime = false;
				}
				
				Robot.ballHandling.dumperLift.set(false);
				Robot.ballHandling.pickUpFlap.set(true);
				Robot.ballHandling.setElevator(-1); 		//add pref for speed?
		    	Robot.ballHandling.setOutsideRoller(1); 	//add pref for speed?
		    	
				if(intermediantStepDone()){
					Robot.ballHandling.setState(BallHandlingState.shooting);
					cState = Robot.ballHandling.getState();
				}
				
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

    protected void interrupted() {
    	Robot.ballHandling.setState(cState);
    	timer.stop();
    }
    
    protected boolean intermediantStepDone() {
    	return timer.get() > t;
    }
}
