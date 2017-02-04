package org.usfirst.frc100.RobotAndrew.commands;

import java.util.ArrayList;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class AutoGenerate {
	
	private double maxAcc; 
	private double distance; 
	private double position; 
	private double velocity; 
	private double T;// = Math.sqrt((2*Math.PI*distance)/maxAcc);
	private double kOne;// = (2*Math.PI)/T;
	private double kTwo; //= maxAcc/kOne;
	private double kThree; //= 1/kOne;
	private double loopCount;// = 0;
	private double timeInterval = 20.0;
	private double time;
	private ArrayList<Double> positionArr = new ArrayList<Double>();
	private ArrayList<Double> velocityArr = new ArrayList<Double>();
	
	public AutoGenerate(double dist, double maxA){
		distance = dist; 
		maxAcc = maxA; 
		T = Math.sqrt((2*Math.PI*distance)/maxAcc); 
		kOne = (2*Math.PI)/T;
		kTwo = maxAcc/kOne;
		kThree = 1/kOne;
		loopCount = 0;
	}
	
	public void generateProfile(){
		while(position < distance){
			
			time = (timeInterval*loopCount)/1000;
		//	System.out.println(time);
			velocity = kTwo*(1-Math.cos(kOne*(time)));
			velocityArr.add(velocity);
			//------------------------------
			position = kTwo*((time)-(kThree*Math.sin(kOne*((time)))));   
			positionArr.add(position);
			//------------------------------
			loopCount++;
			//System.out.println(positionArr);
			SmartDashboard.putNumber("loop", loopCount);
		}
	}
	public ArrayList<Double> returnVel(){
		return velocityArr; 
	}
	
	public ArrayList<Double> returnPos(){
		return positionArr;
		
	}
}
