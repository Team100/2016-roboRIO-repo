package org.usfirst.frc100.Robot2018.commands;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;


import edu.wpi.first.wpilibj.command.Command;

//import org.json.simple.*;
//import org.json.simple.parser.JSONParser;
//import org.json.simple.parser.ParseException;



/**
 * !!! IF YOU GET AN ERROR FROM THIS DOCUMENT
 */
public class ParseJSONFile extends Command {

    public ParseJSONFile() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	String myJSONData = "{\"name\":\"test\",\"value\":\"ABC\"}";// Put the JSON data into this string
    	JSONParser parser = new JSONParser();
        JSONObject myParsedData = new JSONObject();
        
        /**
         * Tests if data is valid otherwise throws nil object
         */
        /*
        try {
        	
        	myParsedData = (JSONObject) parser.parse(myJSONData);}
        catch(ParseException e){
        	e.printStackTrace(); myParsedData = null;
        }
        */
       
        try {myParsedData = (JSONObject) parser.parse(myJSONData);}catch(ParseException e){ e.printStackTrace(); myParsedData = null;}
        

        /**
         * Gets value for index "name"
         */
        System.out.println(myParsedData.get("name")); //  Change 'name' to ideal 
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
