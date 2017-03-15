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
	//public static double timeToSwitch = 2;
	//public static double totalLoopChanges = timeToSwitch/0.002;
	//public static double defultRamp = totalLoopChanges/timeToSwitch;
	public static double ramp = 0.1;
	
	private BallHandlingState mState = BallHandlingState.readyToPickupOrDump;
	
	public BallHandling(){
		
	}
	
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
    	if(value == 0){
    		outsideRoller.set(0);
		}else if(Math.abs(value*ramp + outsideRoller.get()) <= 1){
    		outsideRoller.set(value*ramp + outsideRoller.get());
    	}else if(value*ramp + outsideRoller.get() > 1){
    		outsideRoller.set(1);
    	}else if(value*ramp + outsideRoller.get() < -1){
    		outsideRoller.set(-1);
    	}else{
    		outsideRoller.set(0);
    	}
    }
    
    public void setElevator(double value){
    	if(value == 0){
    		elevator.set(0);
    	}else if(Math.abs(value*ramp + elevator.get()) <= 1){
    		elevator.set(value*ramp + elevator.get());
    	}else if(value*ramp + elevator.get() > 1){
    		elevator.set(1);
    	}else if(value*ramp + elevator.get() < -1){
    		elevator.set(-1);
    	}else {
    		elevator.set(0);
    	}
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

