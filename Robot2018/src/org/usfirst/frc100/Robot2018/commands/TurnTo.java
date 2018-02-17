package org.usfirst.frc100.Robot2018.commands;

import org.usfirst.frc100.Robot2018.subsystems.DriveTrain;

import edu.wpi.first.wpilibj.command.Command;
import jaci.pathfinder.Pathfinder;
import jaci.pathfinder.Waypoint;

/**
 *
 */
public class TurnTo extends Command {
	private static float degreeOffset;
	private static float currentHead;
	private static String direction;
    public TurnTo(float degreesToTurn, String directionToTurn) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	degreeOffset = degreesToTurn;
    	direction = directionToTurn;
    	
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Waypoint[] points = new Waypoint[] {
    			new Waypoint(0,0,Pathfinder.d2r(degreeOffset)),
    	};
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	currentHead = DriveTrain.navxCompassHeading;
    	
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