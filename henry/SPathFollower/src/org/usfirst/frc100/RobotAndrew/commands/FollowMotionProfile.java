package org.usfirst.frc100.RobotAndrew.commands;


import java.awt.Color;
import java.awt.GraphicsEnvironment;
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
	double totalTime = 8; //max seconds we want to drive the path
	double timeStep = 0.1; //period of control loop on Rio, seconds
	double robotTrackWidth = 2;
	FalconLinePlot fig1;
	FalconPathPlanner path;
	public static ArrayList<Double> position; //= new ArrayList<Double>();
	public static ArrayList<Double> velocity; //= new ArrayList<Double>();
	public AutoGenerate profile; 
	static Timer timer = new Timer();
	private static final String SmartDashoard = null;

	public FollowMotionProfile() {
		requires(Robot.driveTrain);
	}
	
	public void initialize() {
		//profile = new AutoGenerate(10, 3.5); //3.5
		//profile.generateProfile();
		//position = profile.returnPos();
		//velocity = profile.returnVel();
		count = 0;
		double[][] waypoints = new double[][]{
		    {1, 1}, //1,1
		    {1, 1}, //5,1
		    {1, 1}, // 9,12
		    {1, 1}, //12,9
		    {1, 1}, //15,6
		    {1, 1} //19,12
		}; 

		double totalTime = 8; //max seconds we want to drive the path
		double timeStep = 0.1; //period of control loop on Rio, seconds
		double robotTrackWidth = 2.25; //distance between left and right wheels, feet

	    path = new FalconPathPlanner(waypoints);
		path.calculate(totalTime, timeStep, robotTrackWidth);
		
		RobotMap.encoderLeft.reset();
		RobotMap.encoderRight.reset();
		Robot.driveTrain.pidPosRight.setAbsoluteTolerance(0.1);
		Robot.driveTrain.pidPosLeft.setAbsoluteTolerance(0.1);
		Robot.driveTrain.pidVelRight.setAbsoluteTolerance(0.01);
		Robot.driveTrain.pidVelLeft.setAbsoluteTolerance(0.01);
		
			fig1 = new FalconLinePlot();//path.nodeOnlyPath,Color.blue,Color.green);}
		
		
		/*if(!GraphicsEnvironment.isHeadless()){
		
		fig1.yGridOn();
		fig1.xGridOn();
		fig1.setYLabel("Y (feet)");
		fig1.setXLabel("X (feet)");
		fig1.setTitle("Top Down View of FRC Field (24ft x 27ft) \n shows global position of robot path, along with left and right wheel trajectories");

		//force graph to show 1/2 field dimensions of 24ft x 27 feet
		fig1.setXTic(0, 27, 1);
		fig1.setYTic(0, 24, 1);
		} */
	//	Robot.driveTrain.pidPosLeft.set
	}
	
	
	public void execute() {
		/*
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
		}, 0, 2000); */
		if(count < path.smoothLeftVelocity.length){
    		System.out.println("left: " + path.smoothLeftVelocity[count][1] + "  " + "right: " + path.smoothRightVelocity[count][1]);
			count++;
		}
		
	//	SmartDashboard.putNumber("setpoint", Robot.driveTrain.pidPosLeft.getSetpoint());
	}

	protected boolean isFinished() {
		//if(count == position.size()){ return true;} else { return false;}
		return false;
		
	}
	protected void end(){
		//Robot.driveTrain.pidPosLeft.disable();
		//Robot.driveTrain.pidPosRight.disable();
		Robot.driveTrain.stop();
	}

}
