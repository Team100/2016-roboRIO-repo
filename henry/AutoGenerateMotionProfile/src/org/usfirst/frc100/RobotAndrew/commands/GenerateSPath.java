package org.usfirst.frc100.RobotAndrew.commands;

import java.util.ArrayList;

import org.usfirst.frc100.RobotAndrew.Robot;
import org.usfirst.frc100.RobotAndrew.RobotMap;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class GenerateSPath extends Command{
	int count;
	int count2;
	public AutoGenerate profileR;
	public AutoGenerate profileL;
	public static ArrayList<Double> positionR;
	public static ArrayList<Double> positionL;
	double distanceRight;
	double distanceLeft;
	FalconPathPlanner path;
	
	public GenerateSPath(double distR, double distL){
		requires(Robot.driveTrain);
		distanceRight = distR;
		distanceLeft = distL;
		count2 = 0;
		count = 0;
		
	}
	public void initialize(){
		count = 0;
		double[][] waypoints = new double[][]{
			{1, 1},
		    {5, 1},
		    {6, 1},
		    {7, 1},
		    {8, 1}, 
		    {9, 1}, 
		    {9.5,1.5}, 
		}; 
		
		double totalTime = 8;
		double timeStep = .1;
		double robotTrackWidth = 2.25;
		/*
		path = new FalconPathPlanner(waypoints);
		path.calculate(totalTime, timeStep, robotTrackWidth);
		*/
		count2 = 0;
		count = 0;
		profileR = new AutoGenerate(distanceRight, 2.5);
		profileL = new AutoGenerate(distanceLeft, 2.5);
		profileR.generateProfile();
		profileL.generateProfile();
		positionR = profileR.returnPos();
		positionL = profileL.returnPos();
		RobotMap.encoderLeft.reset();
		RobotMap.encoderRight.reset();
		
		Robot.driveTrain.pidPosRight.setAbsoluteTolerance(0.01);
		Robot.driveTrain.pidPosLeft.setAbsoluteTolerance(0.01);
		
		Robot.driveTrain.pidPosRight.enable();
		Robot.driveTrain.pidPosLeft.enable();
		System.out.println("hi");
		
	}
	
	public void execute(){
		
		if(count < positionR.size()){
			//Robot.driveTrain.pidVelLeft.setSetpoint(path.smoothLeftVelocity[count][1] );
    		//Robot.driveTrain.pidVelRight.setSetpoint(path.smoothRightVelocity[count][1] );
			Robot.driveTrain.pidPosRight.setSetpoint(positionR.get(count) );
			Robot.driveTrain.pidPosLeft.setSetpoint(positionL.get(count));
    		//Robot.driveTrain.pidPosLeft.setSetpoint(positionL.get(count));
			
			
    		
    		count++;
    		count2++;
		} 
		
		if(count2 < positionL.size() && count == positionR.size()){
			//Robot.driveTrain.pidPosRight.setSetpoint(positionL.get(count2));
			count2++;
		}
		//System.out.println(positionL.size());
		System.out.println("r" +count2);
		System.out.println("ok " 	+ positionL.size());
		
	}
	protected boolean isFinished() {
		if(count2 >= positionL.size())// && count2 >= positionR.size() )
			return true;
		else 
			return false; 
	}
	
	public void end(){
	//	Robot.driveTrain.pidPosLeft.disable();
		//Robot.driveTrain.pidPosRight.disable();
		//Robot.driveTrain.pidVelLeft.reset();
	//	Robot.driveTrain.pidVelRight.reset();
		Robot.driveTrain.stop();
	
	}
}
