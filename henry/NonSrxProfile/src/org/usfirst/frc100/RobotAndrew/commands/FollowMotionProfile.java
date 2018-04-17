package org.usfirst.frc100.RobotAndrew.commands;


import java.util.concurrent.TimeUnit;


import org.usfirst.frc100.RobotAndrew.Robot;
import org.usfirst.frc100.RobotAndrew.RobotMap;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class FollowMotionProfile extends Command{
	int count = 0;
	int changevalue = 0;
	double setP;
	double setPP;
	private static final String SmartDashoard = null;

	public FollowMotionProfile() {
		requires(Robot.driveTrain);
		
	}
	
	public void initialize() {
		count = 0;
		RobotMap.encoderLeft.reset();
		RobotMap.encoderRight.reset();
	//	//Robot.driveTrain.pidVel.setAbsoluteTolerance(0.05);
		Robot.driveTrain.pidPosRight.setAbsoluteTolerance(0.1);
		Robot.driveTrain.pidPosLeft.setAbsoluteTolerance(0.1);
		
		//Robot.driveTrain.pidVel.enable();
		Robot.driveTrain.pidPosRight.enable();
		Robot.driveTrain.pidPosLeft.enable();
		
	}
	
	public void execute() {
		if(count< MotionProfile.Points.length){
			Robot.driveTrain.pidPosLeft.setSetpoint(MotionProfile.Points[count][0]);
			Robot.driveTrain.pidPosRight.setSetpoint(MotionProfile.Points[count][0]);
			count++;
		}
		/*
		if(count == 0){
			Robot.driveTrain.pidPosLeft.setSetpoint(MotionProfile.Points[count][0]);
			Robot.driveTrain.pidPosRight.setSetpoint(MotionProfile.Points[count][0]);
			count++;
		}
		if(count < MotionProfile.Points.length && count > 0){
			if(changevalue == 0 ){//&& changevalue == 0) {
				setP = ((MotionProfile.Points[count-1][0]) + (MotionProfile.Points[count][0]))/2;
				//setPP =( MotionProfile.Points[count][0]) - setP;
				Robot.driveTrain.pidPosRight.setSetpoint(setP);
				Robot.driveTrain.pidPosLeft.setSetpoint(setP);
				changevalue++; 
			} else {
				Robot.driveTrain.pidPosRight.setSetpoint(MotionProfile.Points[count][0]);
				Robot.driveTrain.pidPosLeft.setSetpoint(MotionProfile.Points[count][0]);
				count++;
				changevalue = 0;
				
			}
		}
		*/
		SmartDashboard.putNumber("setpoint", Robot.driveTrain.pidPosLeft.getSetpoint());
		
		SmartDashboard.putNumber("change", changevalue);
	//	SmartDashboard.putNumber("error", Robot.driveTrain.pidPos.getError());
	}

	protected boolean isFinished() {
		
		return Robot.driveTrain.pidPosRight.onTarget();
	}
	protected void end(){
		
		Robot.driveTrain.pidPosLeft.disable();
		Robot.driveTrain.pidPosRight.disable();
		Robot.driveTrain.stop();
	}

}
