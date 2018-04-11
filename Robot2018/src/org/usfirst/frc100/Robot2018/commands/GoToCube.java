package org.usfirst.frc100.Robot2018.commands;

import edu.wpi.first.wpilibj.command.Command;

import org.usfirst.frc100.Robot2018.Robot;
import org.usfirst.frc100.Robot2018.RobotMap;
import org.usfirst.frc100.Robot2018.commands.DataGetter;

import com.ctre.phoenix.motorcontrol.ControlMode;

/**
 *
 */
public class GoToCube extends Command {
	private boolean done = false;
	private float angle; // Angle in Networktables
	private float distance; // Distance in networktables
	private boolean turnMode = true; // true = turn; false = go forward
	private float kTurnDeadband = 1; // Distance that is allowable for turning offset
	private int kForwardSegment1Subtractor = 150; // Distance from the end of the last segment of motor speed
	private int kForwardSegment2Subtractor = 200; // Second to last segment
	private int kForwardSegment3Subtractor = 400; // Third to last segment
	private float originalHeading; // Starting heading of the NavX
	private int finalEncPos; // End encoder position for the LEFT MASTER TALON SRX
	
	
    public GoToCube() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.ahrs.reset();
    	new ParserCommandVer();
    	angle = (float) Robot.cubes[0].getAngle(); // Gets data from the NetworkTables JSON
    	distance=(float) ((float) Robot.cubes[0].getDistance() * 150.8965); //Number = cm->encrev conversion I think
    	originalHeading = (float)Robot.ahrs.getFusedHeading(); // Sets original heading
    	turnMode = true;
    	done = false;
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	System.out.println(distance);
    	System.out.println("EXECUTE");
    	if(turnMode == true) {
    		System.out.println("turnMode");
    		if (Robot.ahrs.getFusedHeading() > angle + kTurnDeadband) {
    			RobotMap.driveTrainLeftMaster.set(ControlMode.PercentOutput, -0.5); //TODO changed left
    			RobotMap.driveTrainRightMaster.set(ControlMode.PercentOutput, -0.5);
    		}
    		else if (Robot.ahrs.getFusedHeading() < angle - kTurnDeadband) {
    			RobotMap.driveTrainLeftMaster.set(ControlMode.PercentOutput, +0.5);
    			RobotMap.driveTrainRightMaster.set(ControlMode.PercentOutput, +0.5);
    		}
    		else {
    			turnMode = false;
    	    	finalEncPos = (int) (distance + RobotMap.driveTrainLeftMaster.getSelectedSensorPosition(0)); // Target encoder position at the end of the drive forward segment
    	    	System.out.println("Final ENC Pos");
    	    	System.out.println(finalEncPos);
    	    	System.out.println("Current ENC Pos");
    	    	System.out.println(RobotMap.driveTrainLeftMaster.getSelectedSensorPosition(0));
    		}
    		System.out.println(angle - Robot.ahrs.getFusedHeading());
    	}
    	else if(turnMode == false && !done) {
    		System.out.println("Drive Forward");
    		double robotDriveForwardPO = 0; // % Output for both motors
    		int currentEncPos = RobotMap.driveTrainLeftMaster.getSelectedSensorPosition(0);
    		int distanceLeft = finalEncPos - currentEncPos;
    		System.out.println(distanceLeft);
    		
    		if(currentEncPos < finalEncPos - kForwardSegment3Subtractor) {
    			robotDriveForwardPO = 0.8;
    		}
    		else if(currentEncPos < finalEncPos - kForwardSegment2Subtractor) {
    			robotDriveForwardPO = 0.6;
    		}
    		else if (currentEncPos < finalEncPos - kForwardSegment1Subtractor) {
    			robotDriveForwardPO = 0.5;
    		}
    		else if(currentEncPos < finalEncPos) {
    			robotDriveForwardPO = 0.4;
    		}
    		else {
    			robotDriveForwardPO = 0;
    			done = true;
    		}
    		
    		RobotMap.driveTrainLeftMaster.set(ControlMode.PercentOutput, robotDriveForwardPO);
    		RobotMap.driveTrainRightMaster.set(ControlMode.PercentOutput, -1 * robotDriveForwardPO);
    	}
    	else {
    		end();
    	}
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return done;
    }

    // Called once after isFinished returns true
    protected void end() {
    	System.out.println("ENDED");
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
