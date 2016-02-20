// RobotBuilder Version: 1.5
//
// This file was generated by RobotBuilder. It contains sections of
// code that are automatically generated and assigned by robotbuilder.
// These sections will be updated in the future when you export to
// Java from RobotBuilder. Do not put any code or make any change in
// the blocks indicating autogenerated code or it will be lost on an
// update. Deleting the comments indicating the section will prevent
// it from being updated in the future.


package org.usfirst.frc0.Robot2;
    
import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;

import java.util.Vector;

/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class RobotMap {
    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS
    public static SpeedController driveTrainSpeedController4;
    public static SpeedController driveTrainSpeedController1;
    public static SpeedController driveTrainSpeedController2;
    public static SpeedController driveTrainSpeedController3;
    public static RobotDrive driveTrainRobotDrive41;
    public static  AnalogInput lineFollower;
    public static Servo exampleServo;
    public static AnalogInput lineFollowerLeft;
    public static AnalogInput lineFollowerRight;
    public static AnalogInput rangeFinder;
    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS

    public static void init() {
        // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTRUCTORS
        driveTrainSpeedController4 = new Victor(10);
        LiveWindow.addActuator("Drive Train", "Speed Controller 4", (Victor) driveTrainSpeedController4);
        
        driveTrainSpeedController1 = new Victor(1);
        LiveWindow.addActuator("Drive Train", "Speed Controller 1", (Victor) driveTrainSpeedController1);
        
        driveTrainSpeedController2 = new Victor(0);
        LiveWindow.addActuator("Drive Train", "Speed Controller 2", (Victor) driveTrainSpeedController2);
        
        driveTrainSpeedController3 = new Victor(5);
        LiveWindow.addActuator("Drive Train", "Speed Controller 3", (Victor) driveTrainSpeedController3);
        
        driveTrainRobotDrive41 = new RobotDrive(driveTrainSpeedController1, driveTrainSpeedController3,
              driveTrainSpeedController2, driveTrainSpeedController4);
        lineFollower = new AnalogInput(3);
        lineFollowerLeft = new AnalogInput(1);
        lineFollowerRight = new AnalogInput(2);
        rangeFinder = new AnalogInput(0);
        exampleServo = new Servo(9);
        driveTrainRobotDrive41.setSafetyEnabled(true);
        driveTrainRobotDrive41.setExpiration(0.1);
        driveTrainRobotDrive41.setSensitivity(0.5);
        driveTrainRobotDrive41.setMaxOutput(1.0);


    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTRUCTORS
    }
}
