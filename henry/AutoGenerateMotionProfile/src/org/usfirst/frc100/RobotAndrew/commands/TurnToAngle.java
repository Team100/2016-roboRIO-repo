package org.usfirst.frc100.RobotAndrew.commands;

import org.usfirst.frc100.RobotAndrew.Robot;
import org.usfirst.frc100.RobotAndrew.RobotMap;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class TurnToAngle extends Command{
	
	boolean cancelPID;
	double desiredAngle;
	double currentAngle;
	public GetVisionData v;
	double visionData;
	int counter; 
	String state;
	
	public TurnToAngle(){
		cancelPID = true;
		state = "stop";
	}
	
	public TurnToAngle(String vision){
		state = "vision";
	}
	
	public TurnToAngle(double angle){
		//state = "button";
		cancelPID = false;
		desiredAngle = angle ;//+ RobotMap.internalGyro.getAngle();
		//counter = desiredAngle;
	}
	
	public void initialize(){
		RobotMap.internalGyro.reset();
		Robot.driveTrain.pidAngle.setAbsoluteTolerance(0.1);
		Robot.driveTrain.pidAngle.setSetpoint(desiredAngle);
		Robot.driveTrain.pidAngle.enable();
		
		
		if(state == "vision"){
			v = new GetVisionData();
			visionData = v.calculateAngle();
			Robot.driveTrain.pidAngle.setSetpoint(visionData);
			Robot.driveTrain.pidAngle.enable();
			System.out.println("im in!");
			System.out.println(visionData);
		} 
		//currentAngle = RobotMap.internalGyro.getAngle();
		
		//RobotMap.internalGyro.reset();
		/*
		if(!cancelPID ){
			Robot.driveTrain.pidAngle.enable();
			Robot.driveTrain.pidAngle.setSetpoint(desiredAngle);
			
		} else {
			//Robot.driveTrain.pidAngle.reset();
		} */
	}
	
	public void execute(){
		
		SmartDashboard.putBoolean("target", Robot.driveTrain.pidAngle.onTarget());
		SmartDashboard.putBoolean("pidState", cancelPID);
		SmartDashboard.putNumber("angleerrors", Robot.driveTrain.pidAngle.getError());
	}
	protected boolean isFinished() {
		//if(Robot.driveTrain.pidAngle.onTarget() || cancelPID == true) return true;
		if(Robot.driveTrain.pidAngle.onTarget() && Math.abs(RobotMap.leftMaster.get()) < .1)
		return true;
		
		return false;//false;
	}
	public void end(){
		System.out.println("ended");
		//Robot.driveTrain.pidAngle.reset();
		Robot.driveTrain.pidAngle.disable();
	}
	


}
