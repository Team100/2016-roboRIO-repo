package org.usfirst.frc100.Robot2018.commands;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import jaci.pathfinder.Pathfinder;
import jaci.pathfinder.Waypoint;

/**
 * @author Alex Beaver
 * This is the example code that should exist for EACH Auton path
 * 
 * This may be refered to as the initializing controller
 * 
 */
public class PathFindingLogicCode extends Command {
	
    int index;
    double point;

	public PathFindingLogicCode() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	SmartDashboard.putBoolean("EnteredTestPathFinding", true);
    	
    	/**
    	 * Waypoint array that gets pushed in to the logic controller
    	 */
    	Waypoint [] points = new Waypoint[]{
        		//right
        			new Waypoint(0.0,0.0,0.0),
        			new Waypoint(1.0, -1.2, Pathfinder.d2r(-45)), //4.5 1.371    .57
        			new Waypoint(2.3, -1.75, 0), //2.4  3.05
        		
        		
        	};
    	/*System.out.println("TO STRING OF POINTS========================================");
    	System.out.println(points.toString());
    	System.out.println("END");*/
    	
    	SmartDashboard.putBoolean("EnteredTestPathFinding", false);
    	
    	/**
    	 * This is how we set the PathFinding logic with the points
    	 */
    	PathFindingLogicProcessor logicProcessor = new PathFindingLogicProcessor(points);
    	logicProcessor.start();
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
