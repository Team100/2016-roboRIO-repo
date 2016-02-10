// RobotBuilder Version: 1.5
//
// This file was generated by RobotBuilder. It contains sections of
// code that are automatically generated and assigned by robotbuilder.
// These sections will be updated in the future when you export to
// Java from RobotBuilder. Do not put any code or make any change in
// the blocks indicating autogenerated code or it will be lost on an
// update. Deleting the comments indicating the section will prevent
// it from being updated in the future.


package org.usfirst.frc100.Lego_Arm_PID;
    
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
    public static Servo robotArmArmContinuousRotationServo;
    public static DigitalInput robotArmArmUpperLimit;
    public static DigitalInput robotArmArmLowerLimit;
    public static AnalogPotentiometer robotArmArmPositionPot;
    public static SpeedController driveTrainLeft;
    public static SpeedController driveTrainRight;
    public static RobotDrive driveTrainTwoMotorDrive;
    public static AnalogInput driveTrainIRDistanceSensor;
    public static Relay clawClawActuatorRelay;

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS

    public static void init() {
        // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTRUCTORS
        robotArmArmContinuousRotationServo = new Servo(0);
        LiveWindow.addActuator("RobotArm", "ArmContinuousRotationServo", robotArmArmContinuousRotationServo);
        
        robotArmArmUpperLimit = new DigitalInput(1);
        LiveWindow.addSensor("RobotArm", "Arm Upper Limit", robotArmArmUpperLimit);
        
        robotArmArmLowerLimit = new DigitalInput(0);
        LiveWindow.addSensor("RobotArm", "Arm Lower Limit", robotArmArmLowerLimit);
        
        robotArmArmPositionPot = new AnalogPotentiometer(0, 1.0, 0.0);
        LiveWindow.addSensor("RobotArm", "Arm Position Pot", robotArmArmPositionPot);
        
        driveTrainLeft = new Victor(3);
        LiveWindow.addActuator("Drive Train", "Left", (Victor) driveTrainLeft);
        
        driveTrainRight = new Victor(2);
        LiveWindow.addActuator("Drive Train", "Right", (Victor) driveTrainRight);
        
        driveTrainTwoMotorDrive = new RobotDrive(driveTrainLeft, driveTrainRight);
        
        driveTrainTwoMotorDrive.setSafetyEnabled(false);
        driveTrainTwoMotorDrive.setExpiration(0.1);
        driveTrainTwoMotorDrive.setSensitivity(0.5);
        driveTrainTwoMotorDrive.setMaxOutput(1.0);
        driveTrainTwoMotorDrive.setInvertedMotor(RobotDrive.MotorType.kRearLeft, true);        

        driveTrainIRDistanceSensor = new AnalogInput(1);
        LiveWindow.addSensor("Drive Train", "IR Distance Sensor", driveTrainIRDistanceSensor);
        
        clawClawActuatorRelay = new Relay(0);
        LiveWindow.addActuator("Claw", "Claw Actuator Relay", clawClawActuatorRelay);
        

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTRUCTORS
    }
}
