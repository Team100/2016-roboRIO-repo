package org.usfirst.frc100.Rigo.commands;

import org.usfirst.frc100.Rigo.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class turnToAngle extends Command{

	//@Override
	
boolean cancelPID; 
	public double angles;
	public turnToAngle(double angle){
		requires(Robot.driveTrain);
		angles = angle;
	}
	protected void initialize() {
		// TODO Auto-generated method stub
		Robot.driveTrain.pid.setAbsoluteTolerance(0.3);
		Robot.driveTrain.pid.setSetpoint(angles); // Robot.driveTrain.getAngles+1
		//Robot.driveTrain.pid.reset();
		Robot.driveTrain.pid.enable();
	}

	@Override
	protected void execute() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected boolean isFinished() {
		if(Robot.driveTrain.pid.onTarget()){ //|| cancelPID == true) return true;
		return true;
		}
		return false;
	}

	@Override
	protected void end() {
		
		Robot.driveTrain.pid.disable();
		Robot.driveTrain.stop();
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void interrupted() {
		// TODO Auto-generated method stub
		
	}

}
