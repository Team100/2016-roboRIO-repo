// RobotBuilder Version: 2.0
//
// This file was generated by RobotBuilder. It contains sections of
// code that are automatically generated and assigned by robotbuilder.
// These sections will be updated in the future when you export to
// Java from RobotBuilder. Do not put any code or make any change in
// the blocks indicating autogenerated code or it will be lost on an
// update. Deleting the comments indicating the section will prevent
// it from being updated in the future.


package org.usfirst.frc100.SehtTestProject;


// BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=IMPORTS
import com.ctre.CANTalon;
import com.ctre.CANTalon.TalonControlMode;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.RobotDrive;

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
    public static CANTalon driveTrainLeftMaster;
    public static CANTalon driveTrainLeftFollower;
    public static CANTalon driveTrainRightMaster;
    public static CANTalon driveTrainRightFollower;
    public static RobotDrive driveTrainDriveyThingy;
    public static Encoder encRight;
    public static Encoder encLeft;
    public static AnalogInput irSen;
    public static AnalogInput pen;

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS

    public static void init() {
        // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTRUCTORS
        driveTrainLeftMaster = new CANTalon(4);
        LiveWindow.addActuator("DriveTrain", "LeftMaster", driveTrainLeftMaster);
        driveTrainLeftMaster.changeControlMode(TalonControlMode.PercentVbus);
        driveTrainLeftFollower = new CANTalon(5);
        LiveWindow.addActuator("DriveTrain", "LeftFollower", driveTrainLeftFollower);
        driveTrainLeftFollower.changeControlMode(TalonControlMode.Follower);
        driveTrainLeftFollower.set(4);
        driveTrainLeftFollower.setSafetyEnabled(false);
        driveTrainRightMaster = new CANTalon(2);
        LiveWindow.addActuator("DriveTrain", "RightMaster", driveTrainRightMaster);
        driveTrainRightMaster.changeControlMode(TalonControlMode.PercentVbus);
        driveTrainRightFollower = new CANTalon(3);
        LiveWindow.addActuator("DriveTrain", "RightFollower", driveTrainRightFollower);
        driveTrainRightFollower.changeControlMode(TalonControlMode.Follower);
        driveTrainRightFollower.set(2);
        driveTrainRightFollower.setSafetyEnabled(false);
        driveTrainDriveyThingy = new RobotDrive(driveTrainLeftMaster,
              driveTrainRightMaster);
        encRight = new Encoder (0, 1);
        encRight.setDistancePerPulse(-1.0/1937.2032);
        encLeft = new Encoder (2, 3);
        encLeft.setDistancePerPulse(1.0/1937.2032);
        
        driveTrainDriveyThingy.setSafetyEnabled(true);
        driveTrainDriveyThingy.setExpiration(0.1);
        driveTrainDriveyThingy.setSensitivity(0.5);
        driveTrainDriveyThingy.setMaxOutput(1.0);

        irSen = new AnalogInput(0);
        pen = new AnalogInput(1);
        //driveTrainDriveyThingy.setInvertedMotor(RobotDrive.MotorType.kFrontRight, true);
        driveTrainDriveyThingy.setInvertedMotor(RobotDrive.MotorType.kRearRight, true);

        // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTRUCTORS
    }
}