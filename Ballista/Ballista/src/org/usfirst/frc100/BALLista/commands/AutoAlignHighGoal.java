/**
 * This method 
 **/

package org.usfirst.frc100.BALLista.commands;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.networktables.NetworkTable;
import edu.wpi.first.wpilibj.Preferences;

import org.usfirst.frc100.BALLista.Robot;

/**
 *
 */


public class AutoAlignHighGoal extends Command {
	
	NetworkTable visionTable;
	Preferences prefs;
	boolean skipTurn = false;

    public AutoAlignHighGoal() {

        requires(Robot.driveTrain);

    }

        protected void initialize() {
        	
    	// Get x position of target(s) and corresponding area(s) from GRIP running on RPi.  Look 
        // in Network Tables for these values.  This implementation assumes that the largest 
        // target found is the one we wish to shoot at.  
        // This can be modified to shoot at other target (for example, the leftmost target, 
        // or one selected by driver).  Convert the x-coordinate
    	// of the target to degrees that the robot needs to rotate.
        	
        double[] xTargets;  // x coordinates of potential targets (pixel locations)
        double[] areas;     // areas of potential targets
        double[] defaultValue = new double[0];
        double xTarget, maxArea;
        int i, max_i;
        final double CAMERA_FOV = 56.0;  // Guess at Lifecam horizontal Field of View
        final double CAMERA_HORZ_PIXELS = 320.0;
        final double MIN_TURN_RES = 0.25;  // Maybe too stringent?
        double pixelAimingPt, xAngleToTurn;
        int angleToTurn;
        
        	
        // Get target information from Network Tables
        visionTable = NetworkTable.getTable("GRIP/myContoursReport"); 	
        
        xTargets = visionTable.getNumberArray("centerX", defaultValue);
        areas = visionTable.getNumberArray("area", defaultValue);
        
        // Find x-coordinate of target with largest area
        i = 0;
        max_i = 0;
        maxArea = 0;
        for (double area : areas) {
        	if (area > maxArea) {
        		maxArea = area;
        		max_i = i;	
        	}
        i++;
        }
        xTarget = xTargets[max_i]; // This should be x-coordinate of largest target.
        
        // Convert target pixel coordinate to 'degrees from AimingPoint'
        pixelAimingPt = prefs.getDouble("pixelAimingPoint", CAMERA_HORZ_PIXELS/2.0); // default: aim at middle 
        xAngleToTurn = (pixelAimingPt - xTarget) / (CAMERA_HORZ_PIXELS/CAMERA_FOV);
        angleToTurn = (int) Math.round(xAngleToTurn);
        
        // Check if we need to turn robot
        // If we are already pointing at target, then we're done
        
        if (xAngleToTurn < MIN_TURN_RES) {
        	skipTurn = true;
        	return;
        }
         	
        // Turn Robot to point to target    	
    	Robot.driveTrain.pid.setPID(Robot.prefs.getDouble("pValue", .04), Robot.prefs.getDouble("iValue", .00), Robot.prefs.getDouble("dValue", .00), 0);
   	 	Robot.driveTrain.pid.setAbsoluteTolerance(0.2);
 //  	Robot.driveTrain.pid.setSetpoint((Robot.driveTrain.getAngles()+distances));  //Robot.driveTrain.getAngles+1
   	 	Robot.driveTrain.pid.reset();
   	 	Robot.driveTrain.pid.enable();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	if (skipTurn || Robot.driveTrain.pid.onTarget()) {
    		return true;
    	}
    	else {
    		return false;
    	}
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
