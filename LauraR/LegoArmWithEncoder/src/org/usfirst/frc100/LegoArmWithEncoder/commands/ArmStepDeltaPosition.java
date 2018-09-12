package org.usfirst.frc100.LegoArmWithEncoder.commands;

import org.usfirst.frc100.LegoArmWithEncoder.Robot;
import org.usfirst.frc100.LegoArmWithEncoder.subsystems.RobotArm;
import org.usfirst.frc100.LegoArmWithEncoder.util.MotionPreferences;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class ArmStepDeltaPosition extends Command {
	final RobotArm.Direction direction;
	private boolean m_isDone = true;

    public ArmStepDeltaPosition(RobotArm.Direction dir) {
        // Use requires() here to declare subsystem dependencies
    	requires(Robot.robotArm);
    	direction = dir;
    	
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	System.out.println("ArmStepDeltaPosition" + (direction == RobotArm.Direction.kUp ? "Up" : "Down"));
    	m_isDone = false;
    	Robot.robotArm.m_pidController.setSetpointProvider(null);
    	double init_position = Robot.robotArm.getEncoderPosition();
    	double step_size = Robot.robotArm.m_motionPreferences.get_moveDistance();
    	if (direction == RobotArm.Direction.kUp) {
    		Robot.robotArm.m_pidController.setSetpoint(init_position + step_size);
    	} else {
    		Robot.robotArm.m_pidController.setSetpoint(init_position - step_size);
    	}
    	Robot.robotArm.setPIDforPositionControl();
    	Robot.robotArm.m_pidController.enable();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	//Just let the PID controller do it's thing while running
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return m_isDone;
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.robotArm.m_pidController.disable();	
    	System.out.println("ArmStepDeltaPosition complete");
    	Robot.robotArm.stop();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
