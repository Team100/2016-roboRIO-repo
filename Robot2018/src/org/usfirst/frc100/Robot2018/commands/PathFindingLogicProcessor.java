// RobotBuilder Version: 2.0
//
// This file was generated by RobotBuilder. It contains sections of
// code that are automatically generated and assigned by robotbuilder.
// These sections will be updated in the future when you export to
// Java from RobotBuilder. Do not put any code or make any change in
// the blocks indicating autogenerated code or it will be lost on an
// update. Deleting the comments indicating the section will prevent
// it from being updated in the future.


package org.usfirst.frc100.Robot2018.commands;
import edu.wpi.first.wpilibj.command.Command;




import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import jaci.pathfinder.Pathfinder;
import jaci.pathfinder.Trajectory;
import jaci.pathfinder.Waypoint;
//import jaci.pathfinder.Waypoint;
import jaci.pathfinder.followers.EncoderFollower;
import jaci.pathfinder.modifiers.TankModifier;


import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.usfirst.frc100.Robot2018.Robot;
import org.usfirst.frc100.Robot2018.RobotMap;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.StatusFrameEnhanced;

/**
 * @author Alex Beaver
 * Based on Henry's Code
 * 
 * 
 * Path Finding test code for the purpose of parameterization
 * This is the logic controller for finding the paths. Create a new instance
 * and send in a Jaci's Pathfinding array in each initializer 
 */

public class PathFindingLogicProcessor extends Command {
	/**
	 * 
	 * All required variables
	 */
	public static boolean isGoing;
	private boolean finish; 
	private int counter; 
	private Waypoint[] paramPoints;
	//FalconPathPlanner path;
	Timer timer; 
	//boolean finish; 
	int countZero;
	EncoderFollower left; 
	EncoderFollower right; 
	double p; 
	double i; 
	double d; 
	double a;
	double p2; 
	double i2; 
	double d2; 
	double a2;
	Trajectory trajectory;
	Trajectory leftT;
	
	Trajectory rightT;
	long startTime;
	long currentTime;
	long timeInt;
	/**
	 * 
	 * @param mypoints - list of waypoints to calculate as a Jaci's Waypoint Array
	 * 
	 * Waypoint [] points = new Waypoint[]{
        		//right
        			new Waypoint(0.0,0.0,0.0),
        			new Waypoint(1.0, -1.2, Pathfinder.d2r(-45)), //4.5 1.371    .57
        			new Waypoint(2.3, -1.75, 0), //2.4  3.05
        		
        		
        	};
	 * 
	 */
    public PathFindingLogicProcessor(Waypoint [] mypoints) {
    	requires(Robot.driveTrain);
    	System.out.println("hi");
    	//paramPoints = mypoints;
    	Waypoint [] paramPoints = mypoints;
    	
  
    }

    // Called just before this Command runs the first time
    @Override
    protected void initialize() {
    	/**
    	 * Logic controller from code from HENRY
    	 */
    	isGoing = false;
    	SmartDashboard.putBoolean("EnteredTestPathFinding", isGoing);
    	System.out.println("PARAMETER POINTS BELOW========================================================");
    	System.out.println(paramPoints.toString());
    	System.out.println("END");
    	timeInt = 100;
    	finish = false;
    	counter = 0;
    	//timer = new Timer();
    	startTime = System.currentTimeMillis();
    	Waypoint [] points = new Waypoint[] {};
    	
    	
    	updatePreferences preferences = new updatePreferences();
    	p = preferences.kP;
    	i = preferences.kI;
    	d = preferences.kD;
    	a = preferences.kA;
    	
    	p2 = preferences.kP;
    	i2 = preferences.kI;
    	d2 = preferences.kD;
    	a2 = preferences.kA;
    	
    	RobotMap.driveTrainRightMaster.config_kP(0, p, 10); //.123
    	RobotMap.driveTrainRightMaster.config_kI(0, i, 10); //.2
        RobotMap.driveTrainRightMaster.config_kD(0, d, 10);
    	RobotMap.driveTrainRightMaster.config_kF(0, a, 10);
    
    	RobotMap.driveTrainLeftMaster.config_kP(0, p2, 10); //.34 //.22
    	RobotMap.driveTrainLeftMaster.config_kI(0, i2, 10); //.189
    	RobotMap.driveTrainLeftMaster.config_kD(0, d2, 10); //2.0E-4
    	RobotMap.driveTrainLeftMaster.config_kF(0, a2, 10); //0
    // 	RobotMap.gyro.reset();
    	RobotMap.driveTrainLeftMaster.setSelectedSensorPosition(0, 0, 0);
    	
    	//ArrayList<Integer> y = //new ArrayList();//10.1, 16.7,  3.07 5.1                                                    //change this to 20 ms                                  1.7 1.7   2.5 2.5
    	Trajectory.Config config = new Trajectory.Config(Trajectory.FitMethod.HERMITE_CUBIC, Trajectory.Config.SAMPLES_HIGH, 0.1, 3.07/2.2, 5.1/2.2, 20);//17.08);
    	trajectory = Pathfinder.generate(paramPoints, config);
    
    	TankModifier modifier = new TankModifier(trajectory).modify(.67);
    	leftT = modifier.getLeftTrajectory();
    	rightT = modifier.getRightTrajectory();
    	for (int i = 0; i < trajectory.length(); i++) {
    	    Trajectory.Segment seg = trajectory.get(i);
    	    
    	    System.out.printf("%f,%f,\n", 
    	       seg.x, seg.y);
    	}
    	timer = new Timer();
    	timer.schedule(new TimerTask() {
    	    @Override
    	    public void run() {
    	    	System.out.println("Entered run()");
    	    	parseArray();
    	    }
    	  }, 0, 100);
      
    } 

    // Called repeatedly when this Command is scheduled to run
    @Override
    protected void execute() {
    	//System.out.println("hi");
    	
    	long ellapsedTime = System.currentTimeMillis();
    	
    	
    	
    }
    
    public void parseArray(){
    	/**
    	 * Parses the array of waypoints
    	 */
    	isGoing = true;
    	SmartDashboard.putBoolean("PathFindingParsing", isGoing);
    	//SmartDashboard.putNumber("SRX1 ENC POS", ((RobotMap.driveTrainTalonSRX1.getSelectedSensorVelocity(0)*10*1.04667)/8192));
	    //SmartDashboard.putNumber("SRX2 ENC POS", ((RobotMap.driveTrainTalonSRX2.getSelectedSensorVelocity(0)*10*1.04667)/8192));
   
    		SmartDashboard.putNumber("Pp", p);
    		SmartDashboard.putNumber("Pi", i);
    		SmartDashboard.putNumber("Pd", d);
    		SmartDashboard.putNumber("Pa", a);
    		SmartDashboard.putNumber("Ppl", p2);
    		SmartDashboard.putNumber("Pil", i2);
    		SmartDashboard.putNumber("Pdl", d2);
    		SmartDashboard.putNumber("Pal", a2);
    	
    		Trajectory.Segment segL = leftT.get(counter); 
    		Trajectory.Segment segR = (rightT.get(counter));
    		//System.out.println(segL.velocity);
    		
    		
	    
    	//double gyro_heading = RobotMap.gyro.getAngle();//... your gyro code here ...    // Assuming the gyro is giving a value in degrees
    	double desired_heading = Pathfinder.r2d(segR.heading);  // Should also be in degrees

    	//double angleDifference = Pathfinder.boundHalfDegrees(desired_heading - gyro_heading);
    //	double turn = 0.2 * (-1.0/80.0) * angleDifference;
    	
    	//SmartDashboard.putNumber("turn", turn);
    	
    	double setR = -segR.velocity;
    	double setL = -segL.velocity;//put a neg sign
    //	double setR = -(segR.velocity + turn); //-segR.velocity;// -(segR.velocity + turn); 
    //	double setL = (segL.velocity - turn);//segL.velocity;//(segL.velocity - turn);
    	
    	SmartDashboard.putNumber("leftS", (setL*3.28));
    	SmartDashboard.putNumber("RightS", -(setR*3.28));///1.04667)/10)*8192);
    	
    	RobotMap.driveTrainRightMaster.set(ControlMode.Velocity, setR*1508.965);//(((setR*3.28)/1.04667)/10)*8192);
 		RobotMap.driveTrainLeftMaster.set(ControlMode.Velocity, setL*1508.965);//(((setL*3.28)/1.04667)/10)*8192);
 		SmartDashboard.putBoolean("SetControlMode", true);
		
    		
    	if(counter < leftT.length()){
    		counter++; 
    	} 
    	
    	if(counter >= leftT.length()){
    		finish = true;
    	}
    	/*
    	try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  */
		
    	//	counter++;
    	SmartDashboard.putBoolean("finish", finish);
    	SmartDashboard.putBoolean("PathFindingParsing", isGoing);
    }

    // Make this return true when this Command no longer needs to run execute()
    @Override
    protected boolean isFinished() {
        return finish;
     }

    // Called once after isFinished returns true
    @Override
    protected void end() {
   // 	Robot.driveTrain.stop();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    @Override
    protected void interrupted() {
    }
}
