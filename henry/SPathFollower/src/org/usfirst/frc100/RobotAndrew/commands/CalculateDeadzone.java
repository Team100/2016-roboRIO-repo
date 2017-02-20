package org.usfirst.frc100.RobotAndrew.commands;

import org.usfirst.frc100.RobotAndrew.RobotMap;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class CalculateDeadzone extends Command{
	public double posDeadzone;
	public double negDeadzone;
	public boolean calculate = true;
	public boolean calculateback = true;
	public CalculateDeadzone(){
		posDeadzone = 0;
		negDeadzone = 0;
	}

	public void initizlize(){
		
	}
	
	public void execute() {
		
		if(calculate && RobotMap.encoderRight.getRate() > 0) {
			posDeadzone = RobotMap.driveTrainright.get();
			calculate = false;
		}
		
		if(calculateback && RobotMap.encoderRight.getRate() < 0) {
			negDeadzone = RobotMap.driveTrainright.get();
			calculateback = false;
		}
		SmartDashboard.putNumber("posDeadzone", posDeadzone);
		SmartDashboard.putNumber("negDeadzone", negDeadzone);
	}
	protected boolean isFinished() {
		// TODO Auto-generated method stub
		return false;
	}
	
	
}
