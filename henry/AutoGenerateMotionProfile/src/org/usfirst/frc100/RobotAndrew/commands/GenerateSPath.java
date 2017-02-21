package org.usfirst.frc100.RobotAndrew.commands;

import org.usfirst.frc100.RobotAndrew.Robot;
import org.usfirst.frc100.RobotAndrew.RobotMap;

import edu.wpi.first.wpilibj.command.Command;

public class GenerateSPath extends Command{
	int count;
	FalconPathPlanner path;
	
	public GenerateSPath(){
		requires(Robot.driveTrain);
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
		
		path = new FalconPathPlanner(waypoints);
		path.calculate(totalTime, timeStep, robotTrackWidth);
		
		RobotMap.encoderLeft.reset();
		RobotMap.encoderRight.reset();
		
		Robot.driveTrain.pidVelRight.setAbsoluteTolerance(0.01);
		Robot.driveTrain.pidVelLeft.setAbsoluteTolerance(0.01);
		
		Robot.driveTrain.pidVelLeft.enable();
		Robot.driveTrain.pidVelRight.enable();
		
	}
	
	public void execute(){
		
		if(count < path.smoothLeftVelocity.length){
			Robot.driveTrain.pidVelLeft.setSetpoint(path.smoothLeftVelocity[count][1] );
    		Robot.driveTrain.pidVelRight.setSetpoint(path.smoothRightVelocity[count][1] );
    		System.out.println(path.smoothLeftVelocity[count][1] );
    		count++;
		}
		
	}
	protected boolean isFinished() {
		if(count >= path.smoothLeftVelocity.length)
			return true;
		else 
			return false; 
	}
	
	public void end(){
		Robot.driveTrain.pidVelLeft.disable();
	
		Robot.driveTrain.pidVelRight.disable();
		Robot.driveTrain.pidVelLeft.reset();
		Robot.driveTrain.pidVelRight.reset();
		Robot.driveTrain.stop();
	
	}
}
