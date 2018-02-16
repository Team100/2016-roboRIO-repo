// RobotBuilder Version: 2.0
//
// This file was generated by RobotBuilder. It contains sections of
// code that are automatically generated and assigned by robotbuilder.
// These sections will be updated in the future when you export to
// Java from RobotBuilder. Do not put any code or make any change in
// the blocks indicating autogenerated code or it will be lost on an
// update. Deleting the comments indicating the section will prevent
// it from being updated in the future.


package org.usfirst.frc100.Robot2018;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
// BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=IMPORTS
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Solenoid;
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
    public static WPI_TalonSRX driveTrainRightMaster;
    public static WPI_TalonSRX driveTrainLeftMaster;
    public static DifferentialDrive driveTrainDifferentialDrive1;
    public static WPI_VictorSPX driveTrainRightFollower;
    public static WPI_VictorSPX driveTrainLeftFollower;
    public static Solenoid driveTrainShiftingSolenoid;
    public static WPI_TalonSRX elevatorElevatorTalon;
    public static WPI_VictorSPX elevatorElevatorVictor;
    public static WPI_VictorSPX elevatorElevatorVictor2;
    public static DigitalInput elevatorElevatorLim1;
    public static DigitalInput elevatorElevatorLim2;
    public static Solenoid elevatorArmSolenoid;
    public static DigitalInput elevatorArmEleArmLim1;
    public static DigitalInput elevatorArmEleArmLim2;
    public static WPI_TalonSRX intakeIntakeMaster;
    public static WPI_VictorSPX intakeIntakeFollower;
    public static DigitalInput intakeIntakeDigSensorb;
    public static WPI_TalonSRX climbingArmClimbingTalon;
    public static DigitalInput climbingArmClimbLim1;
    public static DigitalInput climbingArmClimbLim2;
    public static WPI_TalonSRX winchWinchTalon;
    public static WPI_VictorSPX winchWinchVictor1;
    public static WPI_VictorSPX winchWinchVictor2;
    public static Compressor miscCompressor;

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS

    public static void init() {
        // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTRUCTORS
        driveTrainRightMaster = new WPI_TalonSRX(1);
        driveTrainRightMaster.set(ControlMode.PercentOutput, 0);
        
        
        driveTrainLeftMaster = new WPI_TalonSRX(2);
        driveTrainLeftMaster.set(ControlMode.PercentOutput,0);
        
        
        driveTrainLeftMaster = new WPI_TalonSRX(2);

        
        
        driveTrainDifferentialDrive1 = new DifferentialDrive(driveTrainRightMaster, driveTrainLeftMaster);
        LiveWindow.addActuator("DriveTrain", "DifferentialDrive1", driveTrainDifferentialDrive1);
        driveTrainDifferentialDrive1.setSafetyEnabled(true);
        driveTrainDifferentialDrive1.setExpiration(0.1);
        driveTrainDifferentialDrive1.setMaxOutput(1.0);

        driveTrainRightFollower = new WPI_VictorSPX(3);
        
        driveTrainRightMaster.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, 0);
        driveTrainLeftMaster.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, 0);

        driveTrainLeftFollower = new WPI_VictorSPX(4);
        driveTrainRightMaster.setInverted(true);
        driveTrainLeftMaster.setInverted(true);
        driveTrainLeftFollower.setInverted(true);
        driveTrainRightFollower.setInverted(true);
        driveTrainRightMaster.setSensorPhase(true);
        
        driveTrainLeftFollower.set(ControlMode.Follower, 2);
        // driveTrainTalonSRX2.setInverted(true);
        driveTrainRightFollower.set(ControlMode.Follower, 1);
        
        driveTrainShiftingSolenoid = new Solenoid(0,0);
        driveTrainShiftingSolenoid = new Solenoid(0, 1);
        LiveWindow.addActuator("DriveTrain", "ShiftingSolenoid", driveTrainShiftingSolenoid);
        
        elevatorElevatorTalon = new WPI_TalonSRX(4);
        
        
        elevatorElevatorVictor = new WPI_VictorSPX(5);
        
        
        elevatorElevatorVictor2 = new WPI_VictorSPX(6);
        
        
        
        elevatorElevatorLim1 = new DigitalInput(0);
        LiveWindow.addSensor("Elevator", "ElevatorLim1", elevatorElevatorLim1);
        
        elevatorElevatorLim2 = new DigitalInput(1);
        LiveWindow.addSensor("Elevator", "ElevatorLim2", elevatorElevatorLim2);
        
      //  elevatorArmSolenoid = new Solenoid(0, 1);
      //  LiveWindow.addActuator("ElevatorArm", "Solenoid", elevatorArmSolenoid);
       // 
        elevatorArmSolenoid = new Solenoid(0, 0);
        LiveWindow.addActuator("ElevatorArm", "Solenoid", elevatorArmSolenoid);
        
        elevatorArmEleArmLim1 = new DigitalInput(2);
        LiveWindow.addSensor("ElevatorArm", "EleArmLim1", elevatorArmEleArmLim1);
        
        elevatorArmEleArmLim2 = new DigitalInput(3);
        LiveWindow.addSensor("ElevatorArm", "EleArmLim2", elevatorArmEleArmLim2);
        
        intakeIntakeMaster = new WPI_TalonSRX(7);
        
        
        intakeIntakeFollower = new WPI_VictorSPX(8);
        
        
        intakeIntakeDigSensorb = new DigitalInput(4);
        LiveWindow.addSensor("Intake", "IntakeDigSensor b", intakeIntakeDigSensorb);
        
        climbingArmClimbingTalon = new WPI_TalonSRX(9);
        
        
        climbingArmClimbLim1 = new DigitalInput(5);
        LiveWindow.addSensor("ClimbingArm", "ClimbLim1", climbingArmClimbLim1);
        
        climbingArmClimbLim2 = new DigitalInput(6);
        LiveWindow.addSensor("ClimbingArm", "ClimbLim2", climbingArmClimbLim2);
        
        winchWinchTalon = new WPI_TalonSRX(10);
        
        
        winchWinchVictor1 = new WPI_VictorSPX(11);
        
        
        winchWinchVictor2 = new WPI_VictorSPX(12);
        
        

        
        
        miscCompressor = new Compressor(0);
        
        miscCompressor = new Compressor(0);
        LiveWindow.addActuator("Misc", "Compressor", miscCompressor);
        

        // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTRUCTORS
    }
}
