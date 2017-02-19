package org.usfirst.frc100.Robot2017.commands;

import org.usfirst.frc100.Robot2017.Robot;
import org.usfirst.frc100.Robot2017.subsystems.BallHandling.BallHandlingState;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class StopMotors extends Command {
	
	private BallHandlingState iState;
	private BallHandlingState cState;

    public StopMotors() {
    	requires(Robot.ballhandling);
    	
    }

    protected void initialize() {
    	iState = Robot.ballhandling.getState();
    	cState = iState;
    }

    protected void execute() {
    	switch(cState){
			case shooting: 
			case readyToShoot:
				Robot.ballhandling.dumperLift.set(false);
				Robot.ballhandling.pickUpFlap.set(false);
				Robot.ballhandling.setOutsideRoller(0);
				Robot.ballhandling.setElevator(0);
				
				Robot.ballhandling.setState(BallHandlingState.readyToShoot);
				cState = Robot.ballhandling.getState();
				break;
			case pickingUp:
			case dumping:
			case readyToPickupOrDump: 
				Robot.ballhandling.dumperLift.set(true);
				Robot.ballhandling.pickUpFlap.set(true);
				Robot.ballhandling.setOutsideRoller(0);
				Robot.ballhandling.setElevator(0);
				
				Robot.ballhandling.setState(BallHandlingState.readyToPickupOrDump);
				cState = Robot.ballhandling.getState();
				break;
			case clearElevator:
			case clearPickUp:
				System.out.println("Cant Stop Motors in intermidte step");
				cState = Robot.ballhandling.getState();
				break;
    	}
    }

    protected boolean isFinished() {
        return false;
    }

    protected void end() {
    	
    }

    protected void interrupted() {
		Robot.ballhandling.setState(cState);
    }
}
