package org.usfirst.frc100.LegoArmWithEncoder.commands;

import org.usfirst.frc100.LegoArmWithEncoder.Robot;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class UpdateDashboard extends Command {

    public UpdateDashboard() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	SmartDashboard.putString("RobotArm/.type", "PlotSubSystem");
    	SmartDashboard.putString("DriveTrain/.type", "SubSystem");
    	SmartDashboard.putString("Claw/.type", "SubSystem");
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	Robot.robotArm.updateDashboard();
    	Robot.driveTrain.updateDashboard();
    	Robot.claw.updateDashboard();
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false; // NEVER FINISH!
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
