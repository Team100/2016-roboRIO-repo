package org.usfirst.frc100.Robot2017.commands;

import org.usfirst.frc100.Robot2017.Robot;
import org.usfirst.frc100.Robot2017.subsystems.BallHandling.BallHandlingState;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class OpenFlap extends Command {
	
	private double t;
	private Timer timer = new Timer();
	
	private boolean firstTime = true;
	private boolean done = false;
	
	private BallHandlingState iState;
	private BallHandlingState cState;

    public OpenFlap(float defultClearingTime) {
    	requires(Robot.gearMech);
    	requires(Robot.ballhandling);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	iState = Robot.ballhandling.getState();
		switch(iState){
			case shooting: 
			case readyToShoot:
				Robot.ballhandling.setState(BallHandlingState.clearElevator);
				cState = Robot.ballhandling.getState();
				break;
			case pickingUp:
			case readyToPickupOrDump: 
			case dumping:
				Robot.ballhandling.setState(BallHandlingState.pickingUp);
				cState = Robot.ballhandling.getState();
				break;
			case clearElevator:
			case clearPickUp:
				System.out.println("uh your not supposte to be here");
				break;
		}
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	if(BallHandlingState.pickingUp == Robot.ballhandling.getState()){
    		Robot.gearMech.setGearMechFlap(true);
    		done = true;
    	}else{
    		switch(cState){
				case shooting: 
				case readyToShoot:
					System.out.println("uh your not supposte to be here");
					
					Robot.ballhandling.setState(BallHandlingState.clearElevator);
					cState = Robot.ballhandling.getState();
					
					break;
				case pickingUp:
				case readyToPickupOrDump: 
				case dumping:
		    		Robot.ballhandling.dumperLift.set(true);
		    		Robot.ballhandling.pickUpFlap.set(true);
		    		Robot.ballhandling.setElevator(-1);		//add pref for speed?
			    	Robot.ballhandling.setOutsideRoller(1);	//add pref for speed?
			    		
			    	Robot.ballhandling.setState(BallHandlingState.pickingUp);
					cState = Robot.ballhandling.getState();
					
					break;
				case clearElevator:
					if(firstTime){
						timer.reset();
						timer.start();
						firstTime = false;
					}
					
					Robot.ballhandling.dumperLift.set(true);
					Robot.ballhandling.pickUpFlap.set(true);
					Robot.ballhandling.setElevator(-1); 		//add pref for speed?
			    	Robot.ballhandling.setOutsideRoller(-1); 	//add pref for speed?
			    	
					if(intermediantStepDone()){
						Robot.ballhandling.setState(BallHandlingState.pickingUp);
						cState = Robot.ballhandling.getState();
						
						Robot.ballhandling.dumperLift.set(true);
			    		Robot.ballhandling.pickUpFlap.set(true);
			    		Robot.ballhandling.setElevator(-1);		//add pref for speed?
				    	Robot.ballhandling.setOutsideRoller(1);	//add pref for speed?
					}
					
					break;
				case clearPickUp:
					System.out.println("uh your not supposte to be here");
					
					Robot.ballhandling.dumperLift.set(true);
		    		Robot.ballhandling.pickUpFlap.set(true);
		    		Robot.ballhandling.setElevator(-1);		//add pref for speed?
			    	Robot.ballhandling.setOutsideRoller(1);	//add pref for speed?
		
			    	Robot.ballhandling.setState(BallHandlingState.pickingUp);
					cState = Robot.ballhandling.getState();
					
					break;
			}
    	}
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return done;
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.ballhandling.setState(cState);
    	timer.stop();
    }
    
    protected void interrupted() {
    	Robot.ballhandling.setState(cState);
    	timer.stop();
    }
    
    protected boolean intermediantStepDone() {
    	return timer.get() > t;
    }
}
