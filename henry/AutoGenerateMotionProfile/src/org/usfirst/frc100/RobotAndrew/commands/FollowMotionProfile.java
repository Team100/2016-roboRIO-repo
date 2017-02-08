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
	public static ArrayList<Double> position; //= new ArrayList<Double>();
	public static ArrayList<Double> velocity; //= new ArrayList<Double>();
	public AutoGenerate profile; 
	static Timer timer = new Timer();
	private static final String SmartDashoard = null;

	public FollowMotionProfile() {
		requires(Robot.driveTrain);
	}
	
	public void initialize() {
		profile = new AutoGenerate(10, 3.5); //3.5
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
		//System.out.println("hi");
	}
	
	
	public void execute() {
		timer.schedule(new TimerTask() {
		    @Override
		    public void run() {
		    	if(count < position.size()){
		    		System.out.println(position.get(count) + " : " + velocity.get(count));
		    		Robot.driveTrain.pidPosLeft.setSetpoint(position.get(count));
		    		Robot.driveTrain.pidPosRight.setSetpoint(position.get(count));
		    		Robot.driveTrain.pidVelLeft.setSetpoint(velocity.get(count));
		    		Robot.driveTrain.pidVelRight.setSetpoint(velocity.get(count));
		    		count++;
		    	}
		    }
		}, 0, 2000);
		
		SmartDashboard.putNumber("setpoint", Robot.driveTrain.pidPosLeft.getSetpoint());
	}

	protected boolean isFinished() {
		if(count == position.size()){ return true;} else { return false;}
		
	}
	protected void end(){
		Robot.driveTrain.pidPosLeft.disable();
		Robot.driveTrain.pidPosRight.disable();
		Robot.driveTrain.stop();
	}

}
