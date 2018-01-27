package org.usfirst.frc100.Robot2018.commands;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import org.usfirst.frc100.Robot2018.Robot;

/**
 * 
 * @author Team100
 *
 *<h1> Update Preferences </h1>
 *<p> Updates the preferences </p>
 * 
 */
public class updatePreferences extends Command {
	/**
	 * DEFINE YOUR PREFERENCES HERE
	 * Preference Format: {@code FRC<Season><Subsystem><Group><name> ===> FRC2018LiftPIDFkP}
	 * Variable Format: {@code PREF<Season><Subsystem><Group><name>  ===> PREF2018LiftPIDFkP}
	 */
	
	public double PREF2018TestPreferencesOne;
	
	/*
	 * END OF Variable creation
	 */
	
	
	
    public updatePreferences() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	
    	//CONSTRCTOR NOT USED
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	/**
    	 * Creates a new variable to get the data from preferences
    	 * Example: PREF2018TestPreferencesOne = Robot.prefs.getDouble("2018-Test-Preferences-One", 0.0);
    	 * Duplicate the line below and change the appropriate values for your new variable
    	 */
    	
    	SmartDashboard.putBoolean("InUP", true);
    	System.out.println("Started =======================================================================================");
    	Robot.prefs.putDouble("FRC2018TestPreferencesOne", 5);
    	double PREF2018TestPreferencesOne = Robot.prefs.getDouble("FRC2018TestPreferencesOne", 0); // <===== Goes Wrong Here
    	SmartDashboard.putBoolean("LOADED", false);
    	SmartDashboard.putString("TestPref", Double.toString(PREF2018TestPreferencesOne));
    	SmartDashboard.putString("MReachedValue", "VALID");
    	System.out.println(PREF2018TestPreferencesOne);
    	SmartDashboard.putBoolean("InUP", false);
    
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	//SmartDashboard.putString("TestPref", Double.toString(PREF2018TestPreferencesOne));
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
        // isFinished NOT USED
    }

    // Called once after isFinished returns true
    protected void end() {
    	// end NOT USED
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	//Interrupted Not Used 
    }
}
