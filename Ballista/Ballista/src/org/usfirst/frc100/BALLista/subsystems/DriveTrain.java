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

public class DriveTrain extends Subsystem {

	private final SpeedController left = RobotMap.driveTrainLeft;
	private final SpeedController right = RobotMap.driveTrainRight;
	private final RobotDrive twoMotorDrive = RobotMap.driveTrainTwoMotorDrive;
	private final AnalogInput iRDistanceSensor = RobotMap.driveTrainIRDistanceSensor;
	private final Encoder leftEncoder = RobotMap.driveTrainLeftEncoder;
	private final Encoder rightEncoder = RobotMap.driveTrainRightEncoder;
	public PIDController pid;

	private static final double DEFAULT_DRIVE_TRAIN_KP = 0.004;
	private static final double DEFAULT_DRIVE_TRAIN_KI = 0.0;
	private static final double DEFAULT_DRIVE_TRAIN_KD = 0.0;

	public double driveTrain_kP;
	public double driveTrain_kI;
	public double driveTrain_kD;

	public DriveTrain() {

		if (!Robot.prefs.containsKey("driveTrain_kP")) {
			Robot.prefs.putDouble("driveTrain_kP", DEFAULT_DRIVE_TRAIN_KP);
		}
		if (!Robot.prefs.containsKey("driveTrain_kI")) {
			Robot.prefs.putDouble("driveTrain_kI", DEFAULT_DRIVE_TRAIN_KI);
		}
		if (!Robot.prefs.containsKey("driveTrain_kD")) {
			Robot.prefs.putDouble("driveTrain_kD", DEFAULT_DRIVE_TRAIN_KD);
		}

		driveTrain_kP = Robot.prefs.getDouble("driveTrain_kP",
				DEFAULT_DRIVE_TRAIN_KP);
		driveTrain_kI = Robot.prefs.getDouble("driveTrain_kI",
				DEFAULT_DRIVE_TRAIN_KI);
		driveTrain_kD = Robot.prefs.getDouble("driveTrain_kD",
				DEFAULT_DRIVE_TRAIN_KD);

		pid = new PIDController(Robot.prefs.getDouble("driveTrain_kP",
				DEFAULT_DRIVE_TRAIN_KP), Robot.prefs.getDouble("driveTrain_kD",
				DEFAULT_DRIVE_TRAIN_KD), 0, new PIDSource() { // .04 0 0 for 180
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

	public void stop() {
		twoMotorDrive.drive(0, 0);
	}

	public double getAngles() {

		return RobotMap.internalGyro.getAngle(); // add the gyro

	}

	public void drives() {
		twoMotorDrive.drive(.15, -RobotMap.internalGyro.getAngle() * .03);// .getAngleOfGyro());
		SmartDashboard.putNumber("heading",
				RobotMap.internalGyro.getAngle() * 0.03);

	}

	public void updateDashboard() {

	}

}
