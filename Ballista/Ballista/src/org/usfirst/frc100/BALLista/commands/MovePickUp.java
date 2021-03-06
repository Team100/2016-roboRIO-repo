// RobotBuilder Version: 2.0
//
// This file was generated by RobotBuilder. It contains sections of
// code that are automatically generated and assigned by robotbuilder.
// These sections will be updated in the future when you export to
// Java from RobotBuilder. Do not put any code or make any change in
// the blocks indicating autogenerated code or it will be lost on an
// update. Deleting the comments indicating the section will prevent
// it from being updated in the future.


package org.usfirst.frc100.BALLista.commands;

import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import org.usfirst.frc100.BALLista.Robot;
import org.usfirst.frc100.BALLista.RobotMap;

/**
 *
 */
public class MovePickUp extends Command {
	int angles; 
	boolean goPastLowerLimit = false;
	boolean check = true;

    public MovePickUp() {
 
        requires(Robot.pickUp);

    }
    public MovePickUp(int  angle){
    	angles = angle;
    }
    public MovePickUp(boolean obey){
    	goPastLowerLimit = obey;
    	requires(Robot.pickUp);
    }

    // Called just before this Command runs the first time
    protected void initialize() {

    	/*
    	Robot.pickUp.pid.enable();
    	Robot.pickUp.pid.setPID(Robot.prefs.getDouble("armP", .04), Robot.prefs.getDouble("armI", .00), Robot.prefs.getDouble("armD", .00), 0);
    	Robot.pickUp.pid.setAbsoluteTolerance(.001);
    	Robot.pickUp.pid.setSetpoint(angles);
    	*/
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {

    	//	if(!goPastLowerLimit)
    	  ///  	Robot.pickUp.manualControl(-Robot.oi.operator.getRawAxis(3)/14, true, 3);
    		//else
    	    	Robot.pickUp.manualControl(-Robot.oi.operator.getRawAxis(3)/3, false, 1);
    		
    	//RobotMap.shooterFlyMotor.set(Robot.oi.operator.getRawAxis(1));
    		SmartDashboard.putBoolean("lower Limit obey", goPastLowerLimit);




    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
       return false;

    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.pickUp.stop();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }

    //public static void moveArm(double rawAxis, double d) {
	//	  Robot.pickUp.takeJoystickInputs(Robot.oi.getDriverController1(), Robot.oi.getDriverController2());
	//}
}
