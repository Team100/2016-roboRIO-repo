package org.usfirst.frc100.BALLista.commands;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import org.usfirst.frc100.BALLista.Robot;
import org.usfirst.frc100.BALLista.RobotMap;

/**
 * This is actually arcade drive, the name was never changed.
 */

public class TankDrive extends Command {

	boolean driveDirection = true;

    public TankDrive() {

        requires(Robot.driveTrain);

    }
    public TankDrive(boolean direction){
    	driveDirection = direction;
    	requires(Robot.driveTrain);
    }

    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {

    	if(driveDirection){
    	Robot.driveTrain.takeJoystickInputs(Robot.oi.getDriverController1().getX(), -Robot.oi.getDriverController2().getY());
    	}
    	else{
    	Robot.driveTrain.takeJoystickInputsReverse(Robot.oi.getDriverController1().getX(), Robot.oi.getDriverController2().getY());
    	}
    	SmartDashboard.putNumber("angle", RobotMap.internalGyro.getAngle());
    	SmartDashboard.putBoolean("value1", RobotMap.pickUpUpperLimit.get());
    	SmartDashboard.putBoolean("value2", RobotMap.pickUpLowerLimit.get());
    	//SmartDashboard.putNumber("tester", Robot.testValue);

    	SmartDashboard.putBoolean("valuess", RobotMap.pickUpHomeLimit.get());
    	if(driveDirection){
    	Robot.driveTrain.takeJoystickInputs(Robot.oi.getDriverController1().getX(), -Robot.oi.getDriverController2().getY());
    	}else{
    	Robot.driveTrain.takeJoystickInputsReverse(Robot.oi.getDriverController1().getX(), Robot.oi.getDriverController2().getY());
    	}

    	SmartDashboard.putBoolean("orientation", driveDirection);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.driveTrain.stop();

    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
