package org.usfirst.frc100.Robot2017.subsystems;

import org.usfirst.frc100.Robot2017.RobotMap;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class BallHandling extends Subsystem {

	public final VictorSP outsideRoller = RobotMap.outsideRoller;
	public final VictorSP elevator = RobotMap.elevator;
	public final Solenoid dumperLift = RobotMap.dumperLift;
	public final Solenoid pickUpFlap = RobotMap.pickUpFlap;
	public final Encoder elevatorEncoder = RobotMap.elevatorEncoder;
	
	private BallHandlingState mState = BallHandlingState.readyToPickupOrDump;
	
	public void updateDashboard() {
		SmartDashboard.putNumber("BallHandling/Elevator Encoder Raw", elevatorEncoder.getRaw());
		SmartDashboard.putNumber("BallHandling/Elevator Encoder Count", elevatorEncoder.get());
		SmartDashboard.putNumber("BallHandling/Elevator Encoder Distance", elevatorEncoder.getDistance());
    	SmartDashboard.putNumber("BallHandling/Elevator Encoder Rate", elevatorEncoder.getRate());

    	SmartDashboard.putNumber("BallHandling/Outside Roller Rate", outsideRoller.get());
    	
    	SmartDashboard.putNumber("BallHandling/Elevator Rate", elevator.get());
    	
    	SmartDashboard.putBoolean("BallHandling/Dumper Lift state", dumperLift.get());
    	
    	SmartDashboard.putBoolean("BallHandling/Pickup Flap state", pickUpFlap.get());
	}
	
	public enum BallHandlingState{
    	
    	shooting 			(1),
    	readyToShoot 		(2),
    	pickingUp 			(3),
    	dumping				(4),
    	readyToPickupOrDump (5),
    	clearElevator 		(6),
    	clearPickUp 		(7);
    	
    	public final int number;
    	
    	private BallHandlingState(int number){
    		this.number = number;
    	}
    }
	
	
	public BallHandlingState getState(){
		return mState;
	}

	public void setState(BallHandlingState state){
		mState = state;
	}
	
    public void initDefaultCommand() {
    	
    }
    
    public void setOutsideRoller(double value){
    	outsideRoller.set(value);
    }
    
    public void setElevator(double value){
    	elevator.set(value);
    }
    
    public boolean isDumperLiftOpen(){
    	return dumperLift.get();
    }
    
    public void setDumperLift(boolean open){
    	if(open){
    		dumperLift.set(open);
    	}else{
    		dumperLift.set(!open);
    	}
    }
    
    public boolean isPickUpFlapClosed(){
    	return pickUpFlap.get();
    }
    
    public void setPickUpFlap(boolean open){
    	if(open){
    		pickUpFlap.set(open);
    	}else{
    		pickUpFlap.set(!open);
    	}
    }
}

