package org.usfirst.frc100.RobotAndrew.commands;

import org.usfirst.frc100.RobotAndrew.Robot;
import org.usfirst.frc100.RobotAndrew.RobotMap;

import edu.wpi.first.wpilibj.command.Command;

public class TurnToAngle extends Command{
	
	double desiredAngle;
	double currentAngle;
	int counter; 
	
	public TurnToAngle(int desiredAngle){
		this.desiredAngle = desiredAngle;
		counter = desiredAngle;
	}
	
	public void initialize(){
		currentAngle = RobotMap.internalGyro.getAngle();
		Robot.driveTrain.pidAngle.setAbsoluteTolerance(0.1);
		Robot.driveTrain.pidAngle.setSetpoint(this.desiredAngle);
		Robot.driveTrain.pidAngle.enable();
	}
	
	public void execute(){
		if(counter > 0){
			//Robot.driveTrain.pidAngle.setSetpoint(currentAngle - counter);
			System.out.println(30 - counter);
			counter --;
		}
	
	}
	@Override
	protected boolean isFinished() {

		return false;
	}

}
