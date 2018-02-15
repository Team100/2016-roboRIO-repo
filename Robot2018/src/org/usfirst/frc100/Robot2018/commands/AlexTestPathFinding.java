package org.usfirst.frc100.Robot2018.commands;

import edu.wpi.first.wpilibj.command.Command;
import jaci.pathfinder.Pathfinder;
import jaci.pathfinder.Waypoint;

/**
 * @author Alex Beaver
 * This is the test file for the autonomous Path Finding stuff. IT HAS NOT BEEN TESTED YET
 */
public class AlexTestPathFinding extends Command {

    public AlexTestPathFinding() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Waypoint [] points = new Waypoint[]{
        		//right
        			new Waypoint(0, 0, 0), 
        			new Waypoint(1.0, -1.2, Pathfinder.d2r(-45)), //4.5 1.371    .57
        			new Waypoint(2.3, -1.75, 0), //2.4  3.05
        			
        			
        			
        		
        	};
    	
    	/**
    	 * This is how we set the PathFinding logic with the points
    	 */
    	new AlexPathFinding(points);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
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
