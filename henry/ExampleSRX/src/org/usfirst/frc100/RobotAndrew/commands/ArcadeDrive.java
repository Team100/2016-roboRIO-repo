// RobotBuilder Version: 2.0
//
// This file was generated by RobotBuilder. It contains sections of
// code that are automatically generated and assigned by robotbuilder.
// These sections will be updated in the future when you export to
// Java from RobotBuilder. Do not put any code or make any change in
// the blocks indicating autogenerated code or it will be lost on an
// update. Deleting the comments indicating the section will prevent
// it from being updated in the future.


package org.usfirst.frc100.RobotAndrew.commands;

import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import jaci.pathfinder.Pathfinder;
import jaci.pathfinder.Trajectory;
import jaci.pathfinder.Waypoint;
import jaci.pathfinder.followers.EncoderFollower;
import jaci.pathfinder.modifiers.TankModifier;
//import jaci.pathfinder;

import java.io.File;
import org.usfirst.frc100.RobotAndrew.RobotMap;
import org.usfirst.frc100.RobotAndrew.Robot;

/**
 *
 */
public class ArcadeDrive extends Command {
	
	
    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=VARIABLE_DECLARATIONS
 
    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=VARIABLE_DECLARATIONS

    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTRUCTOR
	Waypoint[] points = new Waypoint[] {
		    new Waypoint(-4, -1, Pathfinder.d2r(-45)),      // Waypoint @ x=-4, y=-1, exit angle=-45 degrees
		    new Waypoint(-2, -2, 0),                        // Waypoint @ x=-2, y=-2, exit angle=0 radians
		    new Waypoint(0, 0, 0)                           // Waypoint @ x=0, y=0,   exit angle=0 radians
		};
		Trajectory.Config config;// = new Trajectory.Config(Trajectory.FitMethod.HERMITE_CUBIC, Trajectory.Config.SAMPLES_HIGH, 0.05, 1.7, 2.0, 60.0);
		Trajectory trajectory; 
		Trajectory left;
		Trajectory right;// = modifier.getRightTrajectory();
		TankModifier modifier;
		EncoderFollower leftE;
		EncoderFollower rightE;
	   
    public ArcadeDrive() {
    	

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTRUCTOR
        // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=VARIABLE_SETTING

        // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=VARIABLE_SETTING
        // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=REQUIRES
        requires(Robot.driveTrain);

        // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=REQUIRES
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	
    	config = new Trajectory.Config(Trajectory.FitMethod.HERMITE_CUBIC, Trajectory.Config.SAMPLES_HIGH, 0.05, 1.7, 2.0, 60.0);
    	trajectory = Pathfinder.generate(points, config);
    	//left = modifier.getLeftTrajectory();
    	//right = modifier.getRightTrajectory();
    	modifier = new TankModifier(trajectory).modify(0.5);
    	leftE = new EncoderFollower(modifier.getLeftTrajectory());
        rightE = new EncoderFollower(modifier.getRightTrajectory());
        leftE.configureEncoder((int) RobotMap.encoderLeft.getDistance(), 1000, 5);
        rightE.configureEncoder((int) RobotMap.encoderRight.getDistance(), 1000, 5);
        leftE.configurePIDVA(1, 0.0, 0.0, 1 / 10, 0);
        rightE.configurePIDVA(1, 0.0, 0.0, 1 / 10, 0);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	double l = leftE.calculate((int) RobotMap.encoderLeft.getDistance());
    	double r = rightE.calculate((int) RobotMap.encoderRight.getDistance());

    	double gyro_heading = org.usfirst.frc100.RobotAndrew.RobotMap.internalGyro.getAngle();    // Assuming the gyro is giving a value in degrees
    	double desired_heading = Pathfinder.r2d(leftE.getHeading());  // Should also be in degrees

    	double angleDifference = Pathfinder.boundHalfDegrees(desired_heading - gyro_heading);
    	double turn = 0.8 * (-1.0/80.0) * angleDifference;

    	RobotMap.driveTrainright.set(l + turn);
    	RobotMap.driveTrainleft.set(r - turn);
    	//Robot.driveTrain.driveRobot(Robot.oi.joystick1);
    	
    	
    	
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
