package org.usfirst.frc100.Lego_Arm_PID.commands;







import org.usfirst.frc100.Lego_Arm_PID.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class SetArmPosition extends Command {
	
	private double position;
	private boolean presentPosition;

  
    public SetArmPosition(int position) {
    	this.position = position;
    	this.presentPosition = true;
    	requires(Robot.robotArm);
		// TODO Auto-generated constructor stub
	}
    public SetArmPosition(double position, boolean presetPosition) {
		this.position = position;
		this.presentPosition = presetPosition;
        requires(Robot.robotArm);
	}

	// Called just before this Command runs the first time
    protected void initialize() {
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
    	Robot.robotArm.updatePID();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
