package org.usfirst.frc100.RobotAndrew.commands;


import java.util.ArrayList;

import java.util.Timer;
import java.util.TimerTask;
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
	double dist;
	public GetVisionData vision;
	public static ArrayList<Double> position; //= new ArrayList<Double>();
	public static ArrayList<Double> velocity; //= new ArrayList<Double>();
	public AutoGenerate profile; 

	static Timer timer = new Timer();
	private static final String SmartDashoard = null;

	public FollowMotionProfile() {
		
		requires(Robot.driveTrain);
	}
	public FollowMotionProfile(int dista) {
		dist = dista;
		requires(Robot.driveTrain);
	}
	
	public void initialize() {
		
		vision = new GetVisionData();
		dist = (vision.calculateDistance()-20)/12;
		profile = new AutoGenerate(dist, 3.5); //3.5 dist
		profile.generateProfile();
		position = profile.returnPos();
		velocity = profile.returnVel();
		count = 0;
		
	
		RobotMap.encoderLeft.reset();
		RobotMap.encoderRight.reset();
		Robot.driveTrain.pidPosRight.setAbsoluteTolerance(0.1);
		Robot.driveTrain.pidPosLeft.setAbsoluteTolerance(0.1);
		Robot.driveTrain.pidVelRight.setAbsoluteTolerance(0.01);
		Robot.driveTrain.pidVelLeft.setAbsoluteTolerance(0.01);
		Robot.driveTrain.pidPosRight.enable();
		Robot.driveTrain.pidPosLeft.enable();
		//Robot.driveTrain.pidVelLeft.enable();
		//Robot.driveTrain.pidVelRight.enable();
		
		System.out.println(dist);
		
	}
	
	
	public void execute() {
		
		
		if(count < position.size()){
			//System.out.println(position.get(count));
			Robot.driveTrain.pidPosLeft.setSetpoint(position.get(count));
	    	Robot.driveTrain.pidPosRight.setSetpoint(position.get(count));
    		count++;
		}
		//SmartDashboard.putNumber("setpoint", Robot.driveTrain.pidPosLeft.getSetpoint());
		
	}

	protected boolean isFinished() {
		//return false;
		if(count == position.size()){ return true;} else { return false;}
		
	}
	protected void end(){
		Robot.driveTrain.pidPosLeft.disable();
		Robot.driveTrain.pidPosRight.disable();
		Robot.driveTrain.stop();
	}
}
