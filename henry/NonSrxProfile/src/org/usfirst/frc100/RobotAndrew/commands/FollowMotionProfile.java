package org.usfirst.frc100.RobotAndrew.commands;


import java.util.concurrent.TimeUnit;

import org.usfirst.frc100.RobotAndrew.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class FollowMotionProfile extends Command{
	
	public FollowMotionProfile() {
		requires(Robot.driveTrain);
	}
	
	public void initialize() {
		Robot.driveTrain.pidVel.setAbsoluteTolerance(0.3);
		Robot.driveTrain.pidPos.setAbsoluteTolerance(0.3);
		
		Robot.driveTrain.pidVel.enable();
		Robot.driveTrain.pidPos.enable();
		
	}
	
	public void execute() {
		
		for(int i = 0; i < MotionProfile.Points.length; i++){
			
			Robot.driveTrain.pidVel.setSetpoint(MotionProfile.Points[i][1]);
			Robot.driveTrain.pidPos.setSetpoint(MotionProfile.Points[i][0]);
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	protected boolean isFinished() {
		
		return Robot.driveTrain.pidPos.onTarget();
	}

}
