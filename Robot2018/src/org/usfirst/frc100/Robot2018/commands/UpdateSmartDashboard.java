package org.usfirst.frc100.Robot2018.commands;

import org.usfirst.frc100.Robot2018.Robot;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Hi. This is where we are updating all of the SmartDashboard stuff
 * Make sure that you update your SmartDashboard stuff here
 * Create a new instance, as everything is in initialize
 * 
 * @author Alex Beaver
 */
public class UpdateSmartDashboard extends Command {

    public UpdateSmartDashboard() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	/**
    	 * Here is where we are publishing everything to {@code SmartDashboard}
    	 * Make sure that all of your updates are put here. It will make everything easier in the long run!
    	 * Make sure to use {@code SmartDashboard.putXyz("key",value);}
    	 */
    	// This is a test to make sure that this command is run.
    	SmartDashboard.putBoolean("UpdateSmartDashbaordRun", true);
    	/*
    	 * Put all SmartDashboard stuff below the following line
    	 */
    	////////////////////////////////BRAKE////////////////////////////////
    	
    	SmartDashboard.putData("Auto mode", Robot.chooser);
    	
    	
    	SmartDashboard.putNumber("posDeadzone",CalculateDeadzone.posDeadzone);
		SmartDashboard.putNumber("negDeadzone", CalculateDeadzone.negDeadzone);
		
		
	
		SmartDashboard.putData("WinchWind", new WinchWind());
		SmartDashboard.putData("Autonomous Command", new AutonomousCommand());
        SmartDashboard.putData("Drive", new Drive());
        SmartDashboard.putData("ElevatorUp", new ElevatorUp());
        SmartDashboard.putData("ElevatorDown", new ElevatorDown());
        SmartDashboard.putData("ElevatorArmUp", new ElevatorArmUp());
        SmartDashboard.putData("ElevatorArmDown", new ElevatorArmDown());
        SmartDashboard.putData("ElevatorAdjust", new ElevatorAdjust());
        SmartDashboard.putData("IntakeIn", new IntakeIn());
        SmartDashboard.putData("IntakeOut", new IntakeOut());
        SmartDashboard.putData("ClimbUp", new ClimbUp());
        SmartDashboard.putData("ClimbDown", new ClimbDown());
        SmartDashboard.putData("ClimbAdjust", new ClimbAdjust());
        SmartDashboard.putData("WinchWind", new WinchWind());
        SmartDashboard.putData("Test Pathfinding Param. stuff", new AlexTestPathFinding());
        
       
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
