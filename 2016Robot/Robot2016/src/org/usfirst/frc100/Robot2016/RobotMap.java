// RobotBuilder Version: 2.0
//
// This file was generated by RobotBuilder. It contains sections of
// code that are automatically generated and assigned by robotbuilder.
// These sections will be updated in the future when you export to
// Java from RobotBuilder. Do not put any code or make any change in
// the blocks indicating autogenerated code or it will be lost on an
// update. Deleting the comments indicating the section will prevent
// it from being updated in the future.


package org.usfirst.frc100.Robot2016;

import edu.wpi.first.wpilibj.ADXRS450_Gyro;
// BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=IMPORTS
import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.AnalogPotentiometer;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.Counter;
import edu.wpi.first.wpilibj.CounterBase.EncodingType;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.VictorSP;
// END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=IMPORTS
import edu.wpi.first.wpilibj.livewindow.LiveWindow;

/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class RobotMap {

    public static SpeedController driveTrainLeft;
    public static SpeedController driveTrainRight;
    public static RobotDrive driveTrainTwoMotorDrive;
    public static AnalogInput driveTrainIRDistanceSensor;
    public static Encoder driveTrainLeftEncoder;
    public static Encoder driveTrainRightEncoder;
    public static DigitalInput pickUpUpperLimit;
    public static DigitalInput pickUpLowerLimit;
    public static DigitalInput pickUpHomeLimit;
    public static SpeedController pickUpArmAngleMotor;
    public static AnalogPotentiometer pickUpPickUpPot;
    public static DigitalInput pickUpPortcullisSensor;
    public static SpeedController moveRollInPickUpRoller;
    public static DigitalInput loaderPinballLoaded;
    public static DoubleSolenoid loaderPinballBallHandlerSolenoid1;
    public static DoubleSolenoid loaderPinballBallHandlerSolenoid2;
    public static SpeedController shooterFlyMotor;
    public static DigitalInput shooterSpdIn;
    public static Counter shooterSpdCtr;
    public static PIDController shooterShooterSpeedControllerPID;
    public static SpeedController spinnerHorizontalPivot;
    public static DigitalInput spinnerLeftSideLimit;
    public static DigitalInput spinnerRightSideLimit;
    public static Encoder spinnerPivotEncoder;
    public static SpeedController unbeatableScalingMechanismWinchMotor;
    public static SpeedController unbeatableScalingMechanismHookExtension;
    public static DigitalInput unbeatableScalingMechanismClimberLimit;
    public static Compressor pneumaticsCompressor;
    public static DoubleSolenoid pushUpPushUpPiston;
    public static ADXRS450_Gyro internalGyro;

    public static void init() {

    	internalGyro = new ADXRS450_Gyro();
        driveTrainLeft = new VictorSP(0);
        LiveWindow.addActuator("Drive Train", "Left", (VictorSP) driveTrainLeft);

        driveTrainRight = new VictorSP(1);
        LiveWindow.addActuator("Drive Train", "Right", (VictorSP) driveTrainRight);

        driveTrainTwoMotorDrive = new RobotDrive(driveTrainLeft, driveTrainRight);

        driveTrainTwoMotorDrive.setSafetyEnabled(true);
        driveTrainTwoMotorDrive.setExpiration(0.1);
        driveTrainTwoMotorDrive.setSensitivity(0.5);
        driveTrainTwoMotorDrive.setMaxOutput(1.0);
        driveTrainTwoMotorDrive.setInvertedMotor(RobotDrive.MotorType.kRearLeft, true);

        driveTrainIRDistanceSensor = new AnalogInput(1);
        LiveWindow.addSensor("Drive Train", "IR Distance Sensor", driveTrainIRDistanceSensor);

        driveTrainLeftEncoder = new Encoder(0, 1, true, EncodingType.k4X);
        LiveWindow.addSensor("Drive Train", "Left Encoder", driveTrainLeftEncoder);
        driveTrainLeftEncoder.setDistancePerPulse(1.0);
        driveTrainLeftEncoder.setPIDSourceType(PIDSourceType.kRate);

        driveTrainRightEncoder = new Encoder(2, 3, true, EncodingType.k4X);
        LiveWindow.addSensor("Drive Train", "Right Encoder", driveTrainRightEncoder);
        driveTrainRightEncoder.setDistancePerPulse(1.0);
        driveTrainRightEncoder.setPIDSourceType(PIDSourceType.kRate);

        pickUpUpperLimit = new DigitalInput(4);
        LiveWindow.addSensor("Pick Up", "Upper Limit", pickUpUpperLimit);

        pickUpLowerLimit = new DigitalInput(5);
        LiveWindow.addSensor("Pick Up", "Lower Limit", pickUpLowerLimit);

        pickUpHomeLimit = new DigitalInput(6);
        LiveWindow.addSensor("Pick Up", "Indide Detector", pickUpHomeLimit);

        pickUpArmAngleMotor = new VictorSP(2);
        LiveWindow.addActuator("Pick Up", "Arm Angle Motor", (VictorSP) pickUpArmAngleMotor);

        pickUpPickUpPot = new AnalogPotentiometer(0, 1.0, 0.0);
        LiveWindow.addSensor("Pick Up", "PickUpPot", pickUpPickUpPot);

        pickUpPortcullisSensor = new DigitalInput(8);
        LiveWindow.addSensor("Pick Up", "Portcullis Sensor", pickUpPortcullisSensor);

        moveRollInPickUpRoller = new VictorSP(3);
        LiveWindow.addActuator("MoveRollIn", "Pick Up Roller", (VictorSP) moveRollInPickUpRoller);

        loaderPinballLoaded = new DigitalInput(13);
        LiveWindow.addSensor("Loader Pinball", "Loaded", loaderPinballLoaded);

        loaderPinballBallHandlerSolenoid1 = new DoubleSolenoid(0, 0, 1);
        LiveWindow.addActuator("Loader Pinball", "Ball Handler Solenoid 1", loaderPinballBallHandlerSolenoid1);

        loaderPinballBallHandlerSolenoid2 = new DoubleSolenoid(0, 2, 3);
        LiveWindow.addActuator("Loader Pinball", "Ball Handler Solenoid 2", loaderPinballBallHandlerSolenoid2);

        shooterFlyMotor = new VictorSP(4);
        LiveWindow.addActuator("Shooter", "Fly Motor", (VictorSP) shooterFlyMotor);

        shooterSpdIn = new DigitalInput(7);
        LiveWindow.addSensor("Shooter", "Fly Counter", shooterSpdIn);

        shooterSpdCtr = new Counter(shooterSpdIn);
        shooterSpdCtr.setUpSourceEdge(true, true);

        shooterShooterSpeedControllerPID.setContinuous(false);
        shooterShooterSpeedControllerPID.setAbsoluteTolerance(0.2);
        shooterShooterSpeedControllerPID.setOutputRange(-1.0, 1.0);
        spinnerHorizontalPivot = new VictorSP(5);
        LiveWindow.addActuator("Spinner", "Horizontal Pivot", (VictorSP) spinnerHorizontalPivot);

        //spinnerLeftSideLimit = new DigitalInput(12);
        //LiveWindow.addSensor("Spinner", "Left Side Limit", spinnerLeftSideLimit);

        //spinnerRightSideLimit = new DigitalInput(13);
        //LiveWindow.addSensor("Spinner", "Right Side Limit", spinnerRightSideLimit);

        spinnerPivotEncoder = new Encoder(11, 12, false, EncodingType.k4X);
        LiveWindow.addSensor("Spinner", "Pivot Encoder", spinnerPivotEncoder);

        spinnerPivotEncoder.setDistancePerPulse(1.0);
        spinnerPivotEncoder.setPIDSourceType(PIDSourceType.kRate);
        unbeatableScalingMechanismWinchMotor = new VictorSP(6);
        LiveWindow.addActuator("Unbeatable Scaling Mechanism ", "Winch Motor", (VictorSP) unbeatableScalingMechanismWinchMotor);

        unbeatableScalingMechanismHookExtension = new VictorSP(7);
        LiveWindow.addActuator("Unbeatable Scaling Mechanism ", "Hook Extension", (VictorSP) unbeatableScalingMechanismHookExtension);

        unbeatableScalingMechanismClimberLimit = new DigitalInput(14);
        LiveWindow.addSensor("Unbeatable Scaling Mechanism ", "Climber Limit", unbeatableScalingMechanismClimberLimit);

        pneumaticsCompressor = new Compressor(0);

    }
}
