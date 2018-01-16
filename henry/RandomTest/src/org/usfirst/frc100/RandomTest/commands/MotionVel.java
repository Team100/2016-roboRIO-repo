// RobotBuilder Version: 2.0
//
// This file was generated by RobotBuilder. It contains sections of
// code that are automatically generated and assigned by robotbuilder.
// These sections will be updated in the future when you export to
// Java from RobotBuilder. Do not put any code or make any change in
// the blocks indicating autogenerated code or it will be lost on an
// update. Deleting the comments indicating the section will prevent
// it from being updated in the future.


package org.usfirst.frc100.RandomTest.commands;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import org.usfirst.frc100.RandomTest.Robot;
import org.usfirst.frc100.RandomTest.RobotMap;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.StatusFrameEnhanced;

/**
 *
 */
public class MotionVel extends Command {

	private double P; 
	private double I; 
	private double D;
	private double F;
	private double PL; 
	private double IL; 
	private double DL;
	private double FL;
	private boolean finish; 
	private int counter; 
	FalconPathPlanner path;
    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=VARIABLE_DECLARATIONS
 
    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=VARIABLE_DECLARATIONS

    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTRUCTOR
    public MotionVel() {
    	requires(Robot.driveTrain);

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTRUCTOR
        // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=VARIABLE_SETTING

        // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=VARIABLE_SETTING
        // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=REQUIRES

        // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=REQUIRES
    }

    // Called just before this Command runs the first time
    @Override
    protected void initialize() {
    	finish = false;
    	counter = 0;
    	P = Robot.prefs.getDouble("P",
				0);
    	I = Robot.prefs.getDouble("I",
				0);
    	D = Robot.prefs.getDouble("D",
				0);
    	F = Robot.prefs.getDouble("F",
				0);
    	PL = Robot.prefs.getDouble("PL",
				0);
    	IL = Robot.prefs.getDouble("IL",
				0);
    	DL = Robot.prefs.getDouble("DL",
				0);
    	FL = Robot.prefs.getDouble("FL",
				0);
    	RobotMap.driveTrainTalonSRX1.config_kF(0, F, 10); //.123
    	RobotMap.driveTrainTalonSRX1.config_kP(0, P, 10); //.2
        RobotMap.driveTrainTalonSRX1.config_kI(0, I, 10);
    	RobotMap.driveTrainTalonSRX1.config_kD(0, D, 10);
    
    	RobotMap.driveTrainTalonSRX2.config_kF(0, FL, 10); //.34 //.22
    	RobotMap.driveTrainTalonSRX2.config_kP(0, PL, 10); //.189
    	RobotMap.driveTrainTalonSRX2.config_kI(0, IL, 10); //2.0E-4
    	RobotMap.driveTrainTalonSRX2.config_kD(0, DL, 10); //0
    	
    	double[][] waypoints = new double[][]{
    		{1, 0},
    		{1, 4}
    		
    	}; 

    	double totalTime = 2; //max seconds we want to drive the path
    	double timeStep = .1; //period of control loop on Rio, seconds
    	double robotTrackWidth = 2.33; //distance between left and right wheels, feet

        path = new FalconPathPlanner(waypoints);
    	path.calculate(totalTime, timeStep, robotTrackWidth);
    	
    	//System.out.println("hi");

    }

    // Called repeatedly when this Command is scheduled to run
    @Override
    protected void execute() {
    	 SmartDashboard.putNumber("SRX1 ENC POS", (RobotMap.driveTrainTalonSRX1.getSelectedSensorVelocity(0)));
	     SmartDashboard.putNumber("SRX2 ENC POS", (RobotMap.driveTrainTalonSRX2.getSelectedSensorVelocity(0)));
	     
    	SmartDashboard.putNumber("P", P);
    	SmartDashboard.putNumber("I", I);
    	SmartDashboard.putNumber("D", D);
    //	SmartDashboard.putNumber("Error", RobotMap.driveTrainTalonSRX1.getClosedLoopError(0));
    	SmartDashboard.putNumber("F", F);
    	
    	SmartDashboard.putNumber("PL", PL);
    	SmartDashboard.putNumber("IL", IL);
    	SmartDashboard.putNumber("DL", DL);
    //	SmartDashboard.putNumber("Error", RobotMap.driveTrainTalonSRX1.getClosedLoopError(0));
    	SmartDashboard.putNumber("FL", FL);
    	
    	SmartDashboard.putNumber("L", path.smoothRightVelocity.length);
    	SmartDashboard.putNumber("LL", path.smoothRightVelocity[1].length);
    	if(counter < path.smoothRightVelocity.length){
    		SmartDashboard.putNumber("actual setpoint", ((path.smoothRightVelocity[counter][1]/1.04667)/10)*8192);
    	//	System.out.println("time step "+ path.smoothRightVelocity[counter][0]);
    		//SmartDashboard.putNumber("setpoint", path.smoothRightVelocity[counter][1]);
    		RobotMap.driveTrainTalonSRX1.set(ControlMode.Velocity, -((path.smoothRightVelocity[counter][1]/1.04667)/10)*8192);
    		RobotMap.driveTrainTalonSRX2.set(ControlMode.Velocity, ((path.smoothLeftVelocity[counter][1]/1.04667)/10)*8192);
    		
    	} else {
    		finish = true; 
    	}
    	
    	//RobotMap.driveTrainTalonSRX1.set(ControlMode.Velocity, 1100);
    	
    	counter++; 
    	try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

    // Make this return true when this Command no longer needs to run execute()
    @Override
    protected boolean isFinished() {
        return finish;
    }

    // Called once after isFinished returns true
    @Override
    protected void end() {
    	Robot.driveTrain.stop();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    @Override
    protected void interrupted() {
    }
}
