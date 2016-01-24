package org.usfirst.frc100.Lego_Arm_PID.commands;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import org.usfirst.frc100.Lego_Arm_PID.Robot;

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
		if (system == System.ROBOTARM) {
			requires(Robot.robotArm);
//		} else if (system == System.ARM) {
//			requires(SlideWinder.arm);
		} else {
			requires(Robot.driveTrain);
		}
	}

	// Called just before this Command runs the first time
	protected void initialize() {
		if (system == System.DRIVEDISTANCE) {
			target = SmartDashboard.getNumber("PID/DriveDistance TestTarget");
			Robot.driveTrain.setAutoTarget(target, 0, 0);
		} else if (system == System.DRIVESLIDE) {
			target = SmartDashboard.getNumber("PID/DriveSlide TestTarget");
			Robot.driveTrain.setAutoTarget(0, target, 0);
		} else if (system == System.DRIVEANGLE) {
			target = SmartDashboard.getNumber("PID/DriveAngle TestTarget");
			Robot.driveTrain.setAutoTarget(0, 0, target);
		} else if (system == System.ROBOTARM) {
			target = SmartDashboard.getNumber("PID/RobotArm TestTarget");
			Robot.robotArm.setAutoTarget(target);
//		} else if (system == System.ARM) {
//			target = SmartDashboard.getNumber("PID/Arm TestTarget");
//			SlideWinder.arm.setArmHeight(target);
		}
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		if (system == System.ROBOTARM) {
			Robot.robotArm.updatePID();
//		} else if (system == System.ARM) {
//			armDone = SlideWinder.arm.updateArm();
		} else {
			Robot.driveTrain.updateAuto(false);
		}
	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		if (system == System.ROBOTARM) {
			return Robot.robotArm.isInPosition();
		} else if (system == System.ARM) {
			return armDone;
		} else {
			return Robot.driveTrain.autoReachedTarget();
		}
	}

	// Called once after isFinished returns true
	protected void end() {
		if (system == System.ROBOTARM) {
			Robot.robotArm.activateBrake();
//		} else if (system == System.ARM) {
//			SlideWinder.arm.manualControl(0.0);
		} else {
			Robot.robotArm.drive(0, 0, 0);
		}
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
		end();
	}
}
