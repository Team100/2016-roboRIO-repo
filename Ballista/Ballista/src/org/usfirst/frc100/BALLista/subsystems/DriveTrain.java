package org.usfirst.frc100.BALLista.subsystems;

import org.usfirst.frc100.BALLista.Robot;
import org.usfirst.frc100.BALLista.RobotMap;
import org.usfirst.frc100.BALLista.commands.TankDrive;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class DriveTrain extends Subsystem {

	private final SpeedController left = RobotMap.driveTrainLeft;
	private final SpeedController right = RobotMap.driveTrainRight;
	private final RobotDrive twoMotorDrive = RobotMap.driveTrainTwoMotorDrive;
	private final AnalogInput iRDistanceSensor = RobotMap.driveTrainIRDistanceSensor;
	private final Encoder leftEncoder = RobotMap.driveTrainLeftEncoder;
	private final Encoder rightEncoder = RobotMap.driveTrainRightEncoder;
	public PIDController pid;
	private boolean driveDirection = true;
	private int distances;

	public void updateDashboard() {
		SmartDashboard.putNumber("Drivetrain/LeftEncoder Raw", leftEncoder.getRaw());
		SmartDashboard.putNumber("Drivetrain/RightEncoder Raw", rightEncoder.getRaw());
		SmartDashboard.putNumber("Drivetrain/LeftEncoder", leftEncoder.getDistance());
		SmartDashboard.putNumber("Drivetrain/RightEncoder", rightEncoder.getDistance());
		SmartDashboard.putNumber("Drivetrain/Gyro", RobotMap.internalGyro.getAngle());
		SmartDashboard.putNumber("Drivetrain/Heading", RobotMap.internalGyro.getAngle() * 0.03);
		SmartDashboard.putNumber("Drivetrain/HoldItValue", Robot.driveTrain.pid.getSetpoint());
		SmartDashboard.putNumber("Drivetrain/RateOfRight", RobotMap.driveTrainRightEncoder.getRate());
    	SmartDashboard.putNumber("Drivetrain/RateOfLeft", RobotMap.driveTrainLeftEncoder.getRate());
    	SmartDashboard.putNumber("Drivetrain/DistOfRight", RobotMap.driveTrainRightEncoder.getDistance());
    	SmartDashboard.putNumber("Drivetrain/DistOfLeft", RobotMap.driveTrainLeftEncoder.getDistance());
    	SmartDashboard.putBoolean("orientation", driveDirection);
    	SmartDashboard.putNumber("Difference of encoders:", Math.abs(RobotMap.driveTrainRightEncoder.getDistance() - RobotMap.driveTrainLeftEncoder.getDistance()));
		SmartDashboard.putNumber("Difference of encoders Rate:", Math.abs(RobotMap.driveTrainRightEncoder.getRate() - RobotMap.driveTrainLeftEncoder.getRate()));

		/*
		// Acceleration code
		SmartDashboard.putNumber("DriveTrain/Acceleration Limit", driveLimit);
		SmartDashboard.putNumber("DriveTrain/Interval", accelerationLoopInterval);

		// only applies to non-slide
		SmartDashboard.putNumber("DriveTrain/Velocity", velocity);
		SmartDashboard.putNumber("DriveTrain/Acceleration", trueAcceleration);
		*/
	}

	public DriveTrain() {
		pid = new PIDController(.04, 0, 0, new PIDSource() { // .04 0 0 for 180
																// // .04 .02 0
																// for like 1
																// degree
					PIDSourceType m_sourceType = PIDSourceType.kDisplacement;

					public double pidGet() {
						return RobotMap.internalGyro.getAngle();
					}

					@Override
					public void setPIDSourceType(PIDSourceType pidSource) {
						m_sourceType = pidSource;
					}

					@Override
					public PIDSourceType getPIDSourceType() {
						return m_sourceType;
					}
				}, new PIDOutput() {
					public void pidWrite(double d) {
						right.pidWrite(d); // /2
						left.pidWrite(-d); // /2
					}
				});
	}

	// Put methods for controlling this subsystem
	// here. Call these from Commands.

	public void initDefaultCommand() {
		setDefaultCommand(new TankDrive());
		// Set the default command for a subsystem here.
		// setDefaultCommand(new MySpecialCommand());
	}

	public void takeJoystickInputs(double x, double y) {
		// twoMotorDrive.tankDrive(left, right);
		twoMotorDrive.arcadeDrive(-x, y);
	}

	public void takeJoystickInputsReverse(double x, double y) {
		twoMotorDrive.arcadeDrive(-x, y);
	}

	public boolean getDriveDirection(){
		return driveDirection;
	}

	public void setDriveDirection(boolean input){
		driveDirection = input;
	}

	public int getDistances(){
		return distances;
	}

	public void setDistances(int input){
		distances = input;
	}

	public void stop() {
		twoMotorDrive.drive(0, 0);
	}

	public double getAngles() {
		return RobotMap.internalGyro.getAngle(); // add the gyro
	}

	public void drives() {
		twoMotorDrive.drive(.15, -RobotMap.internalGyro.getAngle() * .03);// .getAngleOfGyro());
	}
}
