// RobotBuilder Version: 2.0
//
// This file was generated by RobotBuilder. It contains sections of
// code that are automatically generated and assigned by robotbuilder.
// These sections will be updated in the future when you export to
// Java from RobotBuilder. Do not put any code or make any change in
// the blocks indicating autogenerated code or it will be lost on an
// update. Deleting the comments indicating the section will prevent
// it from being updated in the future.


package org.usfirst.frc100.Robot2018.subsystems;

import org.usfirst.frc100.Robot2018.OI;
import org.usfirst.frc100.Robot2018.Robot;
import org.usfirst.frc100.Robot2018.RobotMap;
import org.usfirst.frc100.Robot2018.commands.*;

import edu.wpi.first.wpilibj.command.Subsystem;
// BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=IMPORTS
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

// END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=IMPORTS
import com.kauailabs.navx.frc.AHRS;
import com.kauailabs.navx.frc.AHRS.SerialDataType;

/**
 *
 */
public class DriveTrain extends Subsystem {

    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTANTS

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTANTS

    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS
    private final WPI_TalonSRX rightMaster = RobotMap.driveTrainRightMaster;
    private final WPI_TalonSRX leftMaster = RobotMap.driveTrainLeftMaster;
    private final DifferentialDrive differentialDrive1 = RobotMap.driveTrainDifferentialDrive1;
    private final WPI_VictorSPX rightFollower = RobotMap.driveTrainRightFollower;
    private final WPI_VictorSPX leftFollower = RobotMap.driveTrainLeftFollower;
    private final Solenoid shiftingSolenoid = RobotMap.driveTrainShiftingSolenoid;

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS
    /**
     * Public variables for NavX IMU Sensor
     * 
     */
    public static boolean navxIsConnected;
    public static boolean navxIsCalibrating;
    public static float navxPitch;
    public static float navxYaw;
    public static float navxRoll;
    public static float navxCompassHeading;
    public static float navxFusedHeading;
    public static double navxAngle;
    public static double navxYawRate;
    public static float navxAccelX;
    public static float navxAccelY;
    public static boolean navxIsMoving;
    public static boolean navxIsRotating;
    public static float navxVelocityX;
    public static float navxVelocityY;
    public static float navxDisplacementX;
    public static float navxDisplacementY;
    public static float navxRawGyroX;
    public static float navxRawGyroY;
    public static float navxQuaternionW;
    public static float navxQuaternionX;
    public static float navxQuaternionY;
    public static float navxQuaternionZ;
    public double angleP; 
    public double angleI; 
    public double angleD;
    public PIDController pidAngle;
    
    
    /**
     * Variable for the NAVX IMU
     */
    public DriveTrain(){
		if (!Robot.prefs.containsKey("angleP")) {
			Robot.prefs.putDouble("angleP", 1);
		}
		if (!Robot.prefs.containsKey("angleI")) {
			Robot.prefs.putDouble("angleI", 0);
		}
		if (!Robot.prefs.containsKey("angleD")) {
			Robot.prefs.putDouble("angleD", 0);
		}

		angleP = Robot.prefs.getDouble("angleP",
				.01);
		angleI = Robot.prefs.getDouble("angleI",
				0);
		angleD = Robot.prefs.getDouble("angleD",
				0);
    	pidAngle = new PIDController(angleP,  angleI, angleD, new PIDSource() { // .58823
			PIDSourceType m_sourceType = PIDSourceType.kDisplacement;

			public double pidGet() {
				return Robot.ahrs.getAngle();
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
				
				RobotMap.driveTrainRightFollower.pidWrite(d);///2);
				RobotMap.driveTrainLeftMaster.pidWrite(d);///2);
				
			}
		});
    }
    
    @Override
    public void initDefaultCommand() {
        // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DEFAULT_COMMAND
    	
        setDefaultCommand(new Drive());
        

        // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DEFAULT_COMMAND

        // Set the default command for a subsystem here.
        // setDefaultCommand(new MySpecialCommand());

    }

    @Override
    public void periodic() {
        // Put code here to be run every loop
    //	differentialDrive1.arcadeDrive(Robot.oi.leftController.getRawAxis(1), Robot.oi.rightStick.getRawAxis(0));

    	updateNavxIMU();
    	

    }
    public void getJoy(){
    	if(OI.leftController.getRawButton(1)){
        	RobotMap.driveTrainShiftingSolenoid.set(true);
        }else if(OI.rightStick.getRawButton(1)) {
        	
        	RobotMap.driveTrainShiftingSolenoid.set(false);
        }else if(OI.operator.getRawButtonPressed(1)) {
        	RobotMap.driveTrainShiftingSolenoid.set(true);
        }else if(OI.operator.getRawButtonPressed(2)) {
        	RobotMap.driveTrainShiftingSolenoid.set(false);
        }
        if(!Robot.Logitech){
        	SmartDashboard.putNumber("Left Stick", OI.leftController.getY());
        	SmartDashboard.putNumber("Right Stick", OI.rightStick.getY());
        	if(Robot.ArcadeDrive){
        		RobotMap.driveTrainDifferentialDrive1.arcadeDrive(OI.leftController.getY(), OI.rightStick.getX());
        	}else{
        		RobotMap.driveTrainDifferentialDrive1.tankDrive(OI.leftController.getY(), OI.rightStick.getY());
        	}
        }else{
        	SmartDashboard.putNumber("Logitech", OI.operator.getY());
        	if(Robot.ArcadeDrive){
        		differentialDrive1.arcadeDrive(OI.operator.getRawAxis(1), OI.operator.getRawAxis(4));
        	}else{
        		RobotMap.driveTrainDifferentialDrive1.tankDrive(-OI.operator.getRawAxis(1), -OI.operator.getRawAxis(5));
        	}
        	
        }
     //   System.out.println("running");
    }
    


    // Put methods for controlling this subsystem
    // here. Call these from Commands.
    private void updateNavxIMU() {
    	/**
    	 * Updates all of the data from the Navx IMU Sensor
    	 */
    	
    	navxIsConnected = Robot.ahrs.isConnected();
    	navxIsCalibrating = Robot.ahrs.isCalibrating();
    	navxPitch = Robot.ahrs.getPitch();
    	navxYaw = Robot.ahrs.getYaw();
    	navxRoll = Robot.ahrs.getRoll();
    	navxCompassHeading = Robot.ahrs.getCompassHeading();
    	navxFusedHeading = Robot.ahrs.getFusedHeading();
    	navxAngle = Robot.ahrs.getAngle();
    	navxYawRate = Robot.ahrs.getRate();
    	navxAccelX = Robot.ahrs.getWorldLinearAccelX();
    	navxAccelY = Robot.ahrs.getWorldLinearAccelY();
    	navxIsMoving = Robot.ahrs.isMoving();
    	navxIsRotating = Robot.ahrs.isRotating();
    	navxVelocityX = Robot.ahrs.getVelocityX();
    	navxVelocityY = Robot.ahrs.getVelocityY();
    	navxDisplacementX = Robot.ahrs.getDisplacementX();
    	navxDisplacementY = Robot.ahrs.getDisplacementY();
    	/**
    	 * Below are variables that you should not use unless critical. Use the condensed versions above for better data
    	 */
    	
    	navxRawGyroX = Robot.ahrs.getRawGyroX();
    	navxRawGyroY = Robot.ahrs.getRawGyroY();
    	
    	
    	navxQuaternionW = Robot.ahrs.getQuaternionW();
    	navxQuaternionX = Robot.ahrs.getQuaternionX();
    	navxQuaternionY = Robot.ahrs.getQuaternionY();
    	navxQuaternionZ = Robot.ahrs.getQuaternionZ();
    	
    	
    	new UpdateSmartDashboard();
    	
    }
    public static void resetNavXYaw() {
    	Robot.ahrs.zeroYaw();
    }
}

