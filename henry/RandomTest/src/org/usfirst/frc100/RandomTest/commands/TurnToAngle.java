package org.usfirst.frc100.RandomTest.commands;

import java.util.ArrayList;

import org.usfirst.frc100.RandomTest.Robot;
import org.usfirst.frc100.RandomTest.RobotMap;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class TurnToAngle extends Command{
	int countOnTarget = 0;
	double initialAngle = 0;
	boolean cancelPID;
	double desiredAngle;
	double currentAngle;
	double angless;
	//public GetVisionData v;
	public AutoGenerate generateAngle;
	double visionData;
	int counter; 
	public static ArrayList<Double> angles;
 	String state;
	
	public TurnToAngle(){
		cancelPID = true;
		state = "stop";
		Robot.driveTrain.pidAngle.disable();
	}
	
	public TurnToAngle(String vision){
		state = "vision";
	}
	
	public TurnToAngle(double angle){
		cancelPID = false;
		desiredAngle = angle ;
	}
	
	public void initialize(){
		counter = 0;
		RobotMap.gyro.reset(); // this part can be changes for law of sines
		Robot.driveTrain.pidAngle.setAbsoluteTolerance(0.3);
		
			generateAngle = new AutoGenerate(desiredAngle, 5, "angle");
			generateAngle.generateProfile();
			angles = generateAngle.returnPos();
		//	Robot.driveTrain.pidAngle.setSetpoint(desiredAngle);
	//		Robot.driveTrain.pidAngle.enable();
			
			Robot.driveTrain.pidAngle.setSetpoint( 0 +(angles.get(counter)));
	
	}
	
	public void execute(){ //initialAngle + 
		if(counter < angles.size()){
			if(desiredAngle < 0){
				Robot.driveTrain.pidAngle.setSetpoint(  -(angles.get(counter))); //initialAngle - // this part can be changes for law of sines
				//System.out.println("anngle sets" + ((initialAngle -(angles.get(counter)))));
			}
			else{ 
				Robot.driveTrain.pidAngle.setSetpoint((angles.get(counter)));	//(initialAngle + // this part can be changes for law of sines
			//	System.out.println("anngle set" + initialAngle +(angles.get(counter)));  
			}
			SmartDashboard.putNumber("angle Set", angles.get(counter));
			counter++;
		}
		
		if(Robot.driveTrain.pidAngle.onTarget() && countOnTarget == 0){
			countOnTarget++;
		} else if(Robot.driveTrain.pidAngle.onTarget() && countOnTarget > 0){
			countOnTarget++;
		} else {
			countOnTarget = 0;
		}
		SmartDashboard.putNumber("countTraget", countOnTarget);
	}
	protected boolean isFinished() {

		if(counter >= angles.size() || RobotMap.gyro.getAngle() >= (Math.abs(desiredAngle) - .2) || cancelPID)//Robot.driveTrain.pidAngle.onTarget() && Math.abs(RobotMap.leftMaster.get()) < .1 && Robot.driveTrain.pidAngle.getAvgError() < .3 )//&& countOnTarget >= 3)
			return true;
			
		else
			return false;
	}
	public void end(){
		System.out.println("ended");
		Robot.driveTrain.pidAngle.disable();
		Robot.driveTrain.pidAngle.reset();
	}
}

