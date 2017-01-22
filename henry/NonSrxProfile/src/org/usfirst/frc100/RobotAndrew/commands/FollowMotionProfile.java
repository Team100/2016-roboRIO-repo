package org.usfirst.frc100.RobotAndrew.commands;


import java.util.concurrent.TimeUnit;

import org.usfirst.frc100.RobotAndrew.Robot;
import org.usfirst.frc100.RobotAndrew.RobotMap;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class FollowMotionProfile extends Command{
	int count = 0;
	private static final String SmartDashoard = null;

	public FollowMotionProfile() {
		requires(Robot.driveTrain);
		
	}
	
	public void initialize() {
		count = 0;
		//Robot.driveTrain.pidVel.setAbsoluteTolerance(0.05);
		Robot.driveTrain.pidPos.setAbsoluteTolerance(0.05);
		
		//Robot.driveTrain.pidVel.enable();
		Robot.driveTrain.pidPos.enable();
		
	}
	
	public void execute() {
		 
		if(count < MotionProfile.Points.length) {
			
			//Robot.driveTrain.pidVel.setSetpoint(MotionProfile.Points[count][1]);
			Robot.driveTrain.pidPos.setSetpoint(MotionProfile.Points[count][0]);
			count++;
		} else {
			SmartDashboard.putNumber("count", count);
			//Robot.driveTrain.pidVel.disable();
			//Robot.driveTrain.pidPos.disable();
		}
		
		SmartDashboard.putNumber("setpoint", Robot.driveTrain.pidPos.getSetpoint());
		SmartDashboard.putNumber("error", Robot.driveTrain.pidPos.getError());
	}

	protected boolean isFinished() {
		
		return Robot.driveTrain.pidPos.onTarget();
	}
	protected void end(){
		
		Robot.driveTrain.pidVel.disable();
		Robot.driveTrain.pidPos.disable();
		Robot.driveTrain.stop();
	}

}
