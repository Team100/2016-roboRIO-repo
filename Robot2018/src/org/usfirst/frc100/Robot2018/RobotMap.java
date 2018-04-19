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
import edu.wpi.first.wpilibj.Counter;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DoubleSolenoid;
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
	@SuppressWarnings(value = { "CTRE CAN Recieve Timeout" })
	//defining all the motors
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
    public static WPI_VictorSPX intakeIntakeFollower; //TODO CHANGE TO VICTOR SPX
    public static DigitalInput intakeIntakeDigSensorb;
    public static WPI_TalonSRX climbingArmClimbingTalon;
    public static DigitalInput climbingArmClimbLim1;
    public static DigitalInput climbingArmClimbLim2;
    public static WPI_TalonSRX winchWinchTalon;
    public static WPI_VictorSPX winchWinchVictor1;
    public static Counter limitSwitches;
    
    public static Compressor miscCompressor;

	public static DoubleSolenoid DuoSol;

	
    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS

    public static void init() {
        // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTRUCTORS
   
        driveTrainRightMaster = new WPI_TalonSRX(1);
        //telling the rio which can tallons are on which CANID
        //The CANID is a number set in the webdash. dont put them on zero(that is the the default).
        driveTrainLeftMaster = new WPI_TalonSRX(2);
        //Defining the Diff Drive(the differential drive is a way of defining the DriveTrain
        driveTrainDifferentialDrive1 = new DifferentialDrive(driveTrainRightMaster, driveTrainLeftMaster);
        //parameters for the drivetrain
        driveTrainDifferentialDrive1.setSafetyEnabled(false);
        driveTrainDifferentialDrive1.setExpiration(0.1);
        driveTrainDifferentialDrive1.setMaxOutput(1.0);
        //defining the Right follower which will follow the left master
        driveTrainRightFollower = new WPI_VictorSPX(3);
        //setting the closed loop feedback to a quad encoder
        driveTrainRightMaster.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, 0);
        driveTrainLeftMaster.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, 0);
        driveTrainRightMaster.setSelectedSensorPosition(0, 0, 0);
        driveTrainLeftMaster.setSelectedSensorPosition(0, 0, 0);

        //the left follower which will follow the left master
        driveTrainLeftFollower = new WPI_VictorSPX(4);
        driveTrainRightMaster.setInverted(false); //make sure these are correct on the actual drive train. 
        driveTrainLeftMaster.setInverted(false); 
        driveTrainLeftFollower.setInverted(false); 
        driveTrainRightFollower.setInverted(false); 
        driveTrainRightMaster.setSensorPhase(false); 
        driveTrainLeftMaster.setSensorPhase(false); 
        
        driveTrainLeftMaster.configOpenloopRamp(0.45, 0);//configs the ramp rate(# of seconds from 0 - max speed)
        driveTrainRightMaster.configOpenloopRamp(0.45, 0);
        
        driveTrainLeftMaster.configPeakCurrentLimit(45, 0);//the maximum current that can applied on the drivetrain(in amps)
        driveTrainRightMaster.configPeakCurrentLimit(45, 0);

        driveTrainLeftFollower.follow(driveTrainLeftMaster); //the command that actually makes the sollower follow tha master(what we definded earlier is just a name that means nothing)  
        driveTrainRightFollower.follow(driveTrainRightMaster);
        // driveTrainTalonSRX2.setInverted(true);
        

        driveTrainShiftingSolenoid = new Solenoid(0,0);//the first param is the value of the PCM(on webdash) and the second is the value on the PCM(on the hardware)
        //if the second value is the same on two solenoid you wil get a fatal error

        
        elevatorElevatorTalon = new WPI_TalonSRX(5);//deifing the first elevator(master)
        elevatorElevatorTalon.setSensorPhase(true);
        elevatorElevatorTalon.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, 0);//setting the feedback sensor of the eleator master
        elevatorElevatorTalon.configPeakCurrentLimit(40, 0);//the maximum amount of current allowed on the elevator talon
        elevatorElevatorVictor = new WPI_VictorSPX(6);  //the first follower of the elevator talon(this is victor which is a simpler talon)(talons can have two followers)
        elevatorElevatorVictor.follow(elevatorElevatorTalon);//follows the master talon
        elevatorElevatorVictor.setInverted(false);
        elevatorElevatorVictor2 = new WPI_VictorSPX(7);//the second of the elevator follower
        elevatorElevatorVictor2.setInverted(false);
        elevatorElevatorVictor2.follow(elevatorElevatorTalon);
        
        elevatorElevatorLim1 = new DigitalInput(0);//the limit switch on the elevator(only on comp bot)        
        elevatorElevatorLim2 = new DigitalInput(1);//same(do not use either for stoping the elevator)

        elevatorArmSolenoid = new Solenoid(0, 1);  //defining the arm solenoid(keep in mind no other solenoid can have the second value)       

        elevatorArmEleArmLim1 = new DigitalInput(2);//do not use or change(does not acutally exist
        
        elevatorArmEleArmLim2 = new DigitalInput(3);//do not use or change(does not actually exist)
        
        intakeIntakeMaster = new WPI_TalonSRX(8);//Intake master(Talon)
        intakeIntakeMaster.setInverted(true);//sets the motor inverted
        intakeIntakeMaster.configPeakOutputForward(1, 0);//allows maximum motor output(was set down for previous testing)
        intakeIntakeMaster.configPeakOutputReverse(-1, 0);//same but backwards
        intakeIntakeMaster.configOpenloopRamp(0.2, 0);//ramp so it does not pull too much current
        //TODO check with 2383 on closed loop elevator
        intakeIntakeFollower = new WPI_VictorSPX(9); //WARNING: THIS IS NOT A FOLLOWER; IT GOES AS THE SAME POWER AS THE TALON; 
        //this happened b/c there was a light delay that we did not want with a follower on this talon        
        intakeIntakeFollower.setInverted(true);//sets motor inverted
        intakeIntakeFollower.configPeakOutputForward(1, 0);//set maximum outout forward
        intakeIntakeFollower.configPeakOutputReverse(-1, 0);//sets maximum output reverse
        intakeIntakeFollower.configOpenloopRamp(0.2, 0);//ramp like on other intake motor
        
        intakeIntakeDigSensorb = new DigitalInput(4);//is not on the actual robot
        
        climbingArmClimbingTalon = new WPI_TalonSRX(10);//this is one singular motor for the arm that hooks on to the scale bar(should fall with gravity)
        climbingArmClimbingTalon.setInverted(false);//doesnt set inverted
        climbingArmClimbingTalon.configPeakOutputForward(0.25, 0);//will go up too fast otherwise
        climbingArmClimbingTalon.configPeakOutputReverse(-0.25, 0);//same with down
        
        climbingArmClimbLim1 = new DigitalInput(5);//doesnt exist       
        climbingArmClimbLim2 = new DigitalInput(6);//also doesnt exist 
        
        winchWinchTalon = new WPI_TalonSRX(11);//winch pull on rope attached to hook to pull the robot up(ID11)
        winchWinchTalon.configPeakOutputForward(0.8, 0); //TODO Tune properly
        winchWinchTalon.configPeakOutputReverse(0, 0);
        
        winchWinchVictor1 = new WPI_VictorSPX(12);//This is a follower! they must be running the same direction of it WILL BREAK         //TODO CHANGE TO VICTOR SPX(done?)
        winchWinchVictor1.setInverted(true);//Inversion
        winchWinchVictor1.follow(winchWinchTalon);//to Follow the master

        limitSwitches = new Counter();//is not used
        limitSwitches.setUpDownCounterMode();//is not used
        limitSwitches.setDownSource(elevatorElevatorLim2);//is not used
        limitSwitches.setUpSource(elevatorElevatorLim1);//is not used

        miscCompressor = new Compressor(0);//needs to be defined to work
        
        DuoSol = new DoubleSolenoid(2,3); //the param is (solen one on the PCM, solenoid two on the PCM)
		
        // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTRUCTORS
    }
}
