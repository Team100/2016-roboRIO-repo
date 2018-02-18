// RobotBuilder Version: 2.0
//
// This file was generated by RobotBuilder. It contains sections of
// code that are automatically generated and assigned by robotbuilder.
// These sections will be updated in the future when you export to
// Java from RobotBuilder. Do not put any code or make any change in
// the blocks indicating autogenerated code or it will be lost on an
// update. Deleting the comments indicating the section will prevent
// it from being updated in the future.


package org.usfirst.frc100.RandomTest;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
// BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=IMPORTS
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;
import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.SerialPort;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
// END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=IMPORTS
import edu.wpi.first.wpilibj.livewindow.LiveWindow;

/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class RobotMap {
    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS
    public static WPI_TalonSRX driveTrainTalonSRX1;
    public static WPI_TalonSRX driveTrainTalonSRX2;
    public static WPI_VictorSPX driveTrainTalonSRX3;
    public static WPI_VictorSPX driveTrainTalonSRX4;
    public static DifferentialDrive driveTrainRobotDriveTrain;
    public static ADXRS450_Gyro gyro; 
  //  public static AHRS ahrs;

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS

    public static void init() {
    	gyro = new ADXRS450_Gyro();
    	//ahrs = new AHRS(SerialPort.Port.kUSB); //ahrs = new AHRS(SerialPort.Port.kMXP); /* Alternatives:  SPI.Port.kMXP, I2C.Port.kMXP or SerialPort.Port.kUSB */
        // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTRUCTORS
        driveTrainTalonSRX1 = new WPI_TalonSRX(1); //5
        driveTrainTalonSRX2 = new WPI_TalonSRX(2); //3
        driveTrainTalonSRX3 = new  WPI_VictorSPX(3); //4
        driveTrainTalonSRX4 = new  WPI_VictorSPX(4); //2
        
        
        driveTrainTalonSRX1.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, 0);
        driveTrainTalonSRX1.setInverted(true);
        driveTrainTalonSRX3.setInverted(true);
        driveTrainTalonSRX2.setInverted(true);
        driveTrainTalonSRX4.setInverted(true);
        driveTrainTalonSRX1.setSensorPhase(false);
        driveTrainTalonSRX2.setSensorPhase(true);
        driveTrainTalonSRX2.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, 0);
        
       // driveTrainTalonSRX3.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, Constants.kPIDLoopIdx, Constants.kTimeoutMs);
          
        driveTrainTalonSRX3.set(ControlMode.Follower, 1);
       // driveTrainTalonSRX2.setInverted(true);
        driveTrainTalonSRX4.set(ControlMode.Follower, 2);
       // driveTrainTalonSRX4.setInverted(true);
//      //driveTrainTalonSRX4.setInverted(true);
        driveTrainRobotDriveTrain = new DifferentialDrive(driveTrainTalonSRX1, driveTrainTalonSRX2);
        driveTrainRobotDriveTrain.setSafetyEnabled(false);
        
       // driveTrainRobotDriveTrain.setSafetyEnabled(true);
        driveTrainRobotDriveTrain.setExpiration(0.1);
        driveTrainRobotDriveTrain.setMaxOutput(1.0);
      //  driveTrainRobotDriveTrain.setInvertedMotor(DifferentialDrive.MotorType.kRearLeft, true);

        // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTRUCTORS
    }
}
