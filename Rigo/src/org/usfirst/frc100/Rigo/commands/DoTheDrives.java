package org.usfirst.frc100.Rigo.commands;

import org.omg.PortableInterceptor.ObjectIdHelper;
import org.usfirst.frc100.Rigo.OI;
import org.usfirst.frc100.Rigo.Robot;
import org.usfirst.frc100.Rigo.RobotMap;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class DoTheDrives extends Command {

    public DoTheDrives() {
    	requires(Robot.driveTrain);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	Robot.driveTrain.tankIt(Robot.oi.stick);
    	System.out.println("Gyro Value: " + Robot.driveTrain.gyro.getValue());
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
