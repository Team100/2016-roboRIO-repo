// RobotBuilder Version: 2.
//esse guey
//esse guey
//esse guey
//esse guey
//esse guey  
package org.usfirst.frc100.Robot2018.commands;
import edu.wpi.first.wpilibj.DriverStation;
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
import java.io
.*;/**
 *
 */
public class PathFinding extends Command {


	private boolean finish; 
	private int counter; 
	//FalconPathPlanner path;
	Timer timer; 
	//boolean finish; 
	boolean fastCalculation;
	int countZero;
	EncoderFollower left; 
	EncoderFollower right; 
	Paths paths; 
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
	Waypoint points [];
	Trajectory rightT;
	long startTime;
	long currentTime;
	long timeInt;
	int rightM = -1; 
	int leftM = 1;
	String mode;
	String fileName = "out.txt";
	double[][] path;
	//Paths p;
	
    public PathFinding() {
    	
    	requires(Robot.driveTrain);
  //  	System.out.println("hi");
  
    }
    public PathFinding(String a){
    	rightM = -1; 
    	
    	leftM = 1; 
    	requires(Robot.driveTrain);
    	mode = a;
    	if(mode == "Straight")
    		fastCalculation = false;
    	else
    		fastCalculation = true;
    }

    // Called just before this Command runs the first time
    @Override
    protected void initialize() {
    	
    	paths = new Paths();
    	timeInt = 100;
    	finish = false;
    	counter = 0;
    	//timer = new Timer();
    	startTime = System.currentTimeMillis();
    
    	if(mode == "Left") {
    		Robot.ahrs.reset();
    		path = paths.returnLeftSwitch();
    		 points = new Waypoint[]{
    			new Waypoint(0, 0, 0), 
        		new Waypoint(1.0, .9, Pathfinder.d2r(45)), //4.5 1.371    .57
        		new Waypoint(2.95, 1.3, 0), //2.4  3.05\
    		};
    	} 
    	if(mode == "Right" ){
    		Robot.ahrs.reset();
    		path = paths.returnRightSwitch();
    		 points = new Waypoint []{
    			 new Waypoint(0, 0, 0), 
    	       	 new Waypoint(1.0, -1.3, Pathfinder.d2r(-45)), //4.5 1.371    .57
    	    	 new Waypoint(2.65, -1.55, 0),
    		}; 
    	}
    	if(mode == "BackR"){
    		Robot.ahrs.reset();
    		/*
    		points = new Waypoint[]{
    				
    			new Waypoint(2.45, -1.65, 0), 
    			new Waypoint(2.0, -1.77, Pathfinder.d2r(-45)),
    			new Waypoint(1.8, -2.25, Pathfinder.d2r(-90)), 
    		}; */
    		rightM = 1; 
        	leftM = -1; 
    	}
    	if(mode == "autocube"){
    		ParseJSONFile a = new ParseJSONFile("angle");
        	String an = a.Data();
        	double length = Double.parseDouble(an);
        	//esse guey
    		//double angle = 0; 
    		double width = 0; 
    		//double length = 0;
    		Robot.ahrs.reset();
    		points = new Waypoint[] {
    				new Waypoint(0, 0, 0),
    				new Waypoint(length, 0, 0),
    		};
    		//Robot.ahrs.reset();
    		/*
    		if(angle < 0){
    			points = new Waypoint[] {
    					new Waypoint(0, 0, Pathfinder.d2r(Robot.ahrs.getAngle()*-1)),
    					new Waypoint(length, width, Pathfinder.d2r((Robot.ahrs.getAngle() + angle)*-1)),
    				};
    			}
    		else {
    			points = new Waypoint [] {
    					new Waypoint(0, 0, Pathfinder.d2r(Robot.ahrs.getAngle()*-1)),
    					new Waypoint(length, -width, Pathfinder.d2r((Robot.ahrs.getAngle() - angle)*-1)),
    			};
    		} */
    	}
    		    
    	if(mode == "BS"){
    		/*
    		points = new Waypoint[]{
    			
    			new Waypoint(0, 0, Pathfinder.d2r(-90)), 
    			new Waypoint(2.1, -1.3, Pathfinder.d2r(0)),
    			new Waypoint(2.7, -1.3, Pathfinder.d2r(20)),
    		}; */
	
    	}
    	
    	if (mode == "ScaleSR"){
    		
    		Robot.ahrs.reset();
    		
    		points = new Waypoint[]{
    			new Waypoint(0, 0, 0), 
    			new Waypoint(5.8, 0, 0),
    			new Waypoint(7.49, -.15, 0),
    		}; 
    	}
    	
    	if(mode == "ScaleSL"){
    		Robot.ahrs.reset();
    		points = new Waypoint[]{
    				new Waypoint(0, 0, 0), 
    				new Waypoint(5.8, 0, 0),
        			new Waypoint(7.49, .15, 0),	
    		};
    	}
    	
    	if (mode == "ScaleTurnLeft") {
    		Robot.ahrs.reset();
    		path = paths.returnTurnLeftSclae();
    		 points = new Waypoint[]{
    			new Waypoint(0, 0, 0), 
    			//new Waypoint(3.556, 0, 0), 
    			new Waypoint(4.5, 0, 0),//Pathfinder.d2r(0)), 
    			new Waypoint(5.3, .914, Pathfinder.d2r(-90)),
    			new Waypoint(5.2, 2.4, Pathfinder.d2r(-90)),
    		//	new Waypoint(5.5, 3.4, Pathfinder.d2r(45)),
    			new Waypoint(5.8, 4.26,0),
    			new Waypoint(7.0, 4.26, 0),
    		}; 
    	}
    	if(mode =="ScaleTurnRight"){
    		Robot.ahrs.reset();
    		path = paths.returnTurnRightScale();
    		
    		points = new Waypoint[]{
    			new Waypoint(0, 0, 0), 
    			//new Waypoint(3.556, 0, 0), 
    			new Waypoint(4.5, 0, 0),//Pathfinder.d2r(0)), 
    			new Waypoint(5.3, -.914, Pathfinder.d2r(90)),
    			new Waypoint(5.2, -2.4, Pathfinder.d2r(90)),
    		//	new Waypoint(5.5, 3.4, Pathfinder.d2r(45)),
    			new Waypoint(5.8, -4.26,0),
    			new Waypoint(7.0, -4.26, 0),
    		}; 
    	}
    	
    	if(mode == "Straight"){
    		points = new Waypoint[]{
        			new Waypoint(0, 0, 0), 
        			//new Waypoint(3.556, 0, 0), 
        			new Waypoint(2.7, 0, 0),//Pathfinder.d2r(0)), 
        			
        		}; 
    	}
    	
    	//When making waypoints (how far you wanna go, how far you wanna go left or right(left is positinve, right is negative, and exit angle);
    	//Everything needs to be in meters
    	//Keep in mind that computing paths takes a long time 
    	//because the roborio isnt really that powerful
    	//Once you have a path, it makes sense to load all the data you want to use into an array

    	
    	p = Robot.prefs.getDouble("P",
				0);
    	i = Robot.prefs.getDouble("I",
				0);
    	d = Robot.prefs.getDouble("D",
				0);
    	a = Robot.prefs.getDouble("F",             //.45
				0);
    	
    	p2 = Robot.prefs.getDouble("PL",
				0);
    	i2 = Robot.prefs.getDouble("IL",
				0);
    	d2 = Robot.prefs.getDouble("DL",
				0);
    	a2 = Robot.prefs.getDouble("FL",          //.45
				0);
    	//when tuning, use feedforward gain first, then tweak a little p
    	//dont need to really touch the i or d gain
    	
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

    	//ArrayList<Integer> y = //new ArrayList();//10.1, 16.7,  3.07 5.1  
    	//change this to 20 ms                                  1.7 1.7   2.5 2.5
    	if(!fastCalculation){
    	Trajectory.Config config = new Trajectory.Config(Trajectory.FitMethod.HERMITE_CUBIC, Trajectory.Config.SAMPLES_HIGH, 0.02, 3.07/2.2, 5.1/2.2, 20);//17.08);
    	//Keep first and second arguement the same, the refresh rate in seconds, max vel, max acc, max jerk);
    	trajectory = Pathfinder.generate(points, config);
    	TankModifier modifier = new TankModifier(trajectory).modify(.67); //modify the width between wheels
    	leftT = modifier.getLeftTrajectory();
    	rightT = modifier.getRightTrajectory();
    	
    		
    	/*
    	for (int i = 0; i < trajectory.length(); i++) {
    		Trajectory.Segment segL = leftT.get(i); 
    		Trajectory.Segment segR = (rightT.get(i));
    		System.out.println("{" +segL.velocity + ", " + segR.velocity + ", " + segR.heading +"},");
    	  
    	}  */
    	
    	}
    	
    	timer = new Timer();
    	timer.schedule(new TimerTask() {
    	    @Override
    	    public void run() {
    	    	parseArray();
    	    }
    	  }, 0, 20); //this number must match refresh rate
      
    } 

    // Called repeatedly when this Command is scheduled to run
    @Override
    protected void execute() {
    	//System.out.println("hi");
    	//parseArray();
    	long ellapsedTime = System.currentTimeMillis();
    	
    	
    	
    }
    
    public void parseArray(){
    	//SmartDashboard.putNumber("SRX1 ENC POS", ((RobotMap.driveTrainTalonSRX1.getSelectedSensorVelocity(0)*10*1.04667)/8192));
	    //SmartDashboard.putNumber("SRX2 ENC POS", ((RobotMap.driveTrainTalonSRX2.getSelectedSensorVelocity(0)*10*1.04667)/8192));
    		double setR = 0; 
    		double setL = 0;
    		SmartDashboard.putNumber("Pp", p);
    		SmartDashboard.putNumber("Pi", i);
    		SmartDashboard.putNumber("Pd", d);
    		SmartDashboard.putNumber("Pf", a);
    		SmartDashboard.putNumber("Ppl", p2);
    		SmartDashboard.putNumber("Pil", i2);
    		SmartDashboard.putNumber("Pdl", d2);
    		SmartDashboard.putNumber("Pfl", a2);
    		if(!fastCalculation){
    			Trajectory.Segment segL = leftT.get(counter); //get left and right profile data
    			Trajectory.Segment segR = (rightT.get(counter)); //access each point and count every iterations
    	/*
    		double leftV = path[counter][0];
    		double rightV = path[counter][1]; 
    		double angle = path[counter][2];
    	*/
	    
    	//double gyro_heading = RobotMap.gyro.getAngle();//... your gyro code here ...    // Assuming the gyro is giving a value in degrees
        		double desired_heading = Pathfinder.r2d(segR.heading); //angle // Should also be in degrees
    	
    	 		double angleDifference = Pathfinder.boundHalfDegrees(desired_heading - (Robot.ahrs.getAngle()*-1));
    	 		double turn = .87* (-1.0/80.0) * angleDifference; //.80 tweek the first value for how the robot tracks angle
				if(mode == "BackR" ){
				 setR = segR.velocity; //rightV
		    	 setL = segL.velocity; //leftV
				} else {
				setR = segR.velocity-turn; //rightV - turn;
	    	 	setL = segL.velocity+turn; //leftV + turn;
				} 
    			double leftV = path[counter][0];
        		double rightV = path[counter][1]; 
        		double angle = path[counter][2];
        		double desired_heading1 = Pathfinder.r2d(angle); //angle // Should also be in degrees
            	
    	 		double angleDifference1 = Pathfinder.boundHalfDegrees(desired_heading1 - (Robot.ahrs.getAngle()*-1));
    	 		double turn1 = .87* (-1.0/80.0) * angleDifference1;
        		setR = rightV - turn;
        		setL = leftV + turn;
        		
    		}
			//this corrects the robots heading
			//you can access a lot of data at each segment index like heading, acc, velocity etc
    	//double setR = segR.velocity;
    	//double setL = segL.velocity;
    	
    	SmartDashboard.putNumber("leftS", (setL*1508.965)); //this multiplier is a combination of gearing, how often encoder updates, and wheel diameter
    	SmartDashboard.putNumber("RightS", (setR*1508.965));
    	
    	RobotMap.driveTrainRightMaster.set(ControlMode.Velocity, (setR*rightM)*1508.965); 
    	RobotMap.driveTrainLeftMaster.set(ControlMode.Velocity, (setL*leftM)*1508.965);
		
    	//path.length
    	if(counter < leftT.length()){             
    		counter++; 
    	} 
    	
    	if(counter >= leftT.length()){
    		finish = true;
    	}
    
    	SmartDashboard.putBoolean("finish", finish);
    }

    // Make this return true when this Command no longer needs to run execute()
    @Override
    protected boolean isFinished() {
        return finish; //end command when the array is fully parsed
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
