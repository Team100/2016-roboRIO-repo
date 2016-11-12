package org.usfirst.frc0.Robot2.commands;

import edu.wpi.first.wpilibj.command.Command;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import org.usfirst.frc0.Robot2.Robot;
import org.usfirst.frc0.Robot2.RobotMap;
import org.usfirst.frc0.Robot2.subsystems.DriveTrain;

/**
 * Causes the PID code for a specific subsystem to attempt to reach a value
 * specified on the SmartDashboard.
 */
public class TestPID extends Command {

	private final System system;
	private double target = 0;
	private boolean armDone = false;

	// The various PID loops of the robot
	public enum System {
		DRIVEDISTANCE, DRIVEANGLE, DRIVESLIDE, ROBOTARM, ARM;
	}

	/**
	 * @param system - The PID system to be tested
	 */
	public TestPID(System system) {
		this.system = system;
		
			requires(Robot.driveTrain);
		
	}

	// Called just before this Command runs the first time
	protected void initialize() {
	
			target = SmartDashboard.getNumber("PID/DriveAngle TestTarget");
			Robot.driveTrain.updateSetpoint(target);
		
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		
			//Robot.driveTrain.stop();
		
	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
					return Robot.driveTrain.onTarget();
		
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
