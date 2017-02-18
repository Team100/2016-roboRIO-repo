package org.usfirst.frc100.Robot2017.commands;

import org.usfirst.frc100.Robot2017.Robot;
import org.usfirst.frc100.Robot2017.subsystems.BallHandling.BallHandlingState;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class StopMotors extends Command {
	
	private BallHandlingState iState;
	private BallHandlingState eState;

    public StopMotors() {
    	requires(Robot.ballhandling);
    	iState = Robot.ballhandling.getState();
    	switch(iState){
			case shooting: 
			case readyToShoot:
				Robot.ballhandling.setState(BallHandlingState.readyToShoot);
				eState = Robot.ballhandling.getState();
				break;
			case pickingUp:
			case dumping:
			case readyToPickupOrDump: 
				Robot.ballhandling.setState(BallHandlingState.readyToPickupOrDump);
				eState = Robot.ballhandling.getState();
				break;
			case clearElevator:
			case clearPickUp:
				System.out.println("Cant Stop Motors in intermidte step");
				eState = Robot.ballhandling.getState();
				break;
    	}
    }

    protected void initialize() {
    }

    protected void execute() {
    	switch(iState){
			case shooting: 
			case readyToShoot:
				Robot.ballhandling.setOutsideRoller(0);
				Robot.ballhandling.setElevator(0);
				break;
			case pickingUp:
			case dumping:
			case readyToPickupOrDump: 
				Robot.ballhandling.setOutsideRoller(0);
				Robot.ballhandling.setElevator(0);
				break;
			case clearElevator:
			case clearPickUp:
				System.out.println("Cant Stop Motors in intermidte step");
				eState = Robot.ballhandling.getState();
				break;
    	}
    }

    protected boolean isFinished() {
        return false;
    }

    protected void end() {
    	
    }

    protected void interrupted() {
    	end();
    }
}
