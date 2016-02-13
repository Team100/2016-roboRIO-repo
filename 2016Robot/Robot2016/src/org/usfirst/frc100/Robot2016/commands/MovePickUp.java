// RobotBuilder Version: 2.0
//
// This file was generated by RobotBuilder. It contains sections of
// code that are automatically generated and assigned by robotbuilder.
// These sections will be updated in the future when you export to
// Java from RobotBuilder. Do not put any code or make any change in
// the blocks indicating autogenerated code or it will be lost on an
// update. Deleting the comments indicating the section will prevent
// it from being updated in the future.


package org.usfirst.frc100.Robot2016.commands;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import org.usfirst.frc100.Robot2016.Robot;
import org.usfirst.frc100.Robot2016.RobotMap;

/**
 *
 */
public class MovePickUp extends Command {

    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=VARIABLE_DECLARATIONS

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=VARIABLE_DECLARATIONS

    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTRUCTOR
    public MovePickUp() {

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTRUCTOR
        // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=VARIABLE_SETTING

        // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=VARIABLE_SETTING
        // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=REQUIRES
        requires(Robot.pickUp);

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=REQUIRES
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	if(Robot.pickUp.hitUpper()){
    		if(Robot.oi.operator.getRawAxis(1) > 0){
    			Robot.pickUp.takeJoystickInputs(Robot.oi.operator.getRawAxis(1));
    		}else{
    			Robot.pickUp.stop();
    		}
    	}else if(Robot.pickUp.hitLower()){
    		if(Robot.oi.operator.getRawAxis(1) < 0){
    			Robot.pickUp.takeJoystickInputs(Robot.oi.operator.getRawAxis(1));
    		}else{
    			Robot.pickUp.stop();
    		}
    	}else{
    		Robot.pickUp.takeJoystickInputs(Robot.oi.operator.getRawAxis(1));
    	}
    	//while(RobotMap.pickUpLowerLimit.get() == true && RobotMap.pickUpUpperLimit.get() == true);
    	/*
    	while(RobotMap.pickUpLowerLimit.get() == true && RobotMap.pickUpUpperLimit.get() == false)
    	{
    		Robot.pickUp.takeJoystickInputs(((Robot.oi.operator.getRawAxis(1))*.5));
    	}
    	while(RobotMap.pickUpLowerLimit.get() == false && RobotMap.pickUpUpperLimit.get() == true)
    	{
    		Robot.pickUp.takeJoystickInputs(((Robot.oi.operator.getRawAxis(1))/.5));
    	}
    	*/

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

//	public static void moveArm(double rawAxis, double d) {
	//	Robot.pickUp.takeJoystickInputs(Robot.oi.getDriverController1(), Robot.oi.getDriverController2());
	//}
}
