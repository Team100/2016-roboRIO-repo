package org.usfirst.frc100.Robot2018.commands;

import org.usfirst.frc100.Robot2018.Robot;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class ParserCommandVer extends Command {
	private boolean done;
    public ParserCommandVer() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	done =false;
    	 DataGetter get = new DataGetter("Camera", "localhost");
         String json = get.getCube();
         
         Gson g = new GsonBuilder().create();
         Target[] ts = g.fromJson(json, Target[].class);
         Robot.cubes = ts;
         for (Target t: ts) {
             System.out.println(t);
         }
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	done = true;
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return done;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
