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

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.CameraServer;

//import org.usfirst.frc100.RandomTest.RobotMap;
import org.usfirst.frc100.Robot2018.commands.*;
import org.usfirst.frc100.Robot2018.subsystems.*;
import org.usfirst.frc100.Robot2018.RobotMap;


import java.util.*;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.kauailabs.navx.frc.AHRS;

import java.util.*;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.properties file in 
 * the project.
 */
public class Robot extends TimedRobot {

    Command autonomousCommand;

    public static SendableChooser<Command> chooser = new SendableChooser<>();

	public static Boolean Logitech;
	public static Boolean ArcadeDrive;

    public static OI oi;

    
    //System.out.println("INITALIZED =======================================================================================");
    public static Preferences prefs = Preferences.getInstance(); //Creates preferences object
    boolean run;
    double P; 
    double I; 
    double D; 
    double F;
    double PE; 
    double IE; 
    double DE; 
    double FE;
    double time;
    double PL; 
    double IL; 
    double DL;
    double FL;


    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS
    public static DriveTrain driveTrain;
    public static Elevator elevator;
    public static ElevatorArm elevatorArm;
    public static Intake intake;
    public static ClimbingArm climbingArm;
    public static Winch winch;
    public static Misc misc;
    public static String gameData;
    public static AHRS ahrs; 

    public static Wrist wrist;
  

    public static double RequestedTime;
    public static boolean MaunalDuo;
    public static double EP;
    public static double EI;
    public static double ED;
    public static double EF;
    public static NetworkTable cubeInfo;
    
    
    //Config for Elevator Min and Max
    public static double elevatorMultiplier;
    public static final double kElevatorMultiplierMin = 0.65;
    public static final double kElevatorMultiplierMax = 1;

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS

    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    @Override
    public void robotInit() {
    	RobotMap.init();
    	
    	NetworkTableInstance nt = NetworkTableInstance.create();
    	nt.startClient();
    	cubeInfo = nt.getTable("Camera");
    	
    	
    	//USBCamera camera = new USBCamera("Camera");
    	CameraServer.getInstance().startAutomaticCapture(0);
    	
    	elevatorMultiplier = kElevatorMultiplierMax;
    	RobotMap.elevatorElevatorTalon.config_kP(0, 8, 0);
     	RobotMap.elevatorElevatorTalon.config_kI(0, 0, 0);
     	RobotMap.elevatorElevatorTalon.config_kD(0, 0, 0);
     	RobotMap.elevatorElevatorTalon.config_kF(0, 24, 0);
        RobotMap.elevatorElevatorTalon.selectProfileSlot(0, 0);


        RobotMap.elevatorElevatorTalon.setSensorPhase(false);
        RobotMap.elevatorElevatorTalon.configNominalOutputForward(0.0f, 0);
        RobotMap.elevatorElevatorTalon.configNominalOutputReverse(0.0f, 0);
        RobotMap.elevatorElevatorTalon.configMotionAcceleration(10, 0);
        RobotMap.elevatorElevatorTalon.configMotionCruiseVelocity(15, 0);
        RobotMap.elevatorElevatorTalon.configPeakOutputForward(.75, 0);
        RobotMap.elevatorElevatorTalon.configPeakOutputReverse(-0.75, 0);
        RobotMap.elevatorElevatorTalon.configClosedLoopPeakOutput(0, 0.2, 10);
        
       
        RobotMap.elevatorElevatorTalon.setInverted(true);
    	if (!prefs.containsKey("PE")) {
        	prefs.putDouble("PE", 0.01);
        }
        if (!prefs.containsKey("IE")) {
        	prefs.putDouble("IE", 0);
        }
        if (!prefs.containsKey("DE")) {
        	prefs.putDouble("DE", 0);
        }
        if (!prefs.containsKey("FE")) {
        	prefs.putDouble("FE", 3.1);
        }
        /*
    	RobotMap.elevatorElevatorTalon.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, 0);
        RobotMap.elevatorElevatorTalon.selectProfileSlot(0, 0);
        RobotMap.elevatorElevatorTalon.setSensorPhase(false);
        RobotMap.elevatorElevatorTalon.configNominalOutputForward(0.0f, 0);
        RobotMap.elevatorElevatorTalon.configNominalOutputReverse(0.0f, 0);
        RobotMap.elevatorElevatorTalon.configMotionAcceleration(60, 0);
        RobotMap.elevatorElevatorTalon.configMotionCruiseVelocity(30, 0);
        //RobotMap.elevatorElevatorTalon.configPeakOutputForward(.4, 0);
        //RobotMap.elevatorElevatorTalon.configPeakOutputReverse(-.4, 0);
         * */
         
    	/*SmartDashboard.putBoolean("EnteredTestPathFinding", false);
    	SmartDashboard.putBoolean("PathFindingParsing", false);
    	SmartDashboard.putBoolean("SetControlMode", false);*/
        
        // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTRUCTORS

        driveTrain = new DriveTrain();
        elevator = new Elevator();
        elevatorArm = new ElevatorArm();
        intake = new Intake();
        climbingArm = new ClimbingArm();
        winch = new Winch();
        wrist = new Wrist();
        misc = new Misc();
  
        try {
			/***********************************************************************
			 * navX-MXP:
			 * - Communication via RoboRIO MXP (SPI, I2C, TTL UART) and USB.            
			 * - See http://navx-mxp.kauailabs.com/guidance/selecting-an-interface.
			 * 
			 * navX-Micro:
			 * - Communication via I2C (RoboRIO MXP or Onboard) and USB.
			 * - See http://navx-micro.kauailabs.com/guidance/selecting-an-interface.
			 * 
			 * Multiple navX-model devices on a single robot are supported.
			 ************************************************************************/
            //ahrs = new AHRS(SerialPort.Port.kUSB1);
            //ahrs = new AHRS(SerialPort.Port.kMXP, SerialDataType.kProcessedData, (byte)200);
            //ahrs = new AHRS(SPI.Port.kMXP);
            ahrs = new AHRS(I2C.Port.kOnboard);
        //	ahrs.reset();
// Uses onboard I2C port
        	//ahrs.enableLogging(true); // Sends debugging logging
        } catch (RuntimeException ex ) {
            DriverStation.reportError("Error instantiating navX  MXP:  " + ex.getMessage(), true);
        }
        
       
        // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTRUCTORS
        
        
        // OI must be constructed after subsystems. If the OI creates Commands
        //(which it very likely will), subsystems are not guaranteed to be
        // constructed yet. Thus, their requires() statements may grab null
        // pointers. Bad news. Don't move it.
        oi = new OI();
        RequestedTime = prefs.getDouble("Number of Seconds to Wait", 10);
        // Add commands to Autonomous Sendable Chooser
        // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=AUTONOMOUS

        chooser.addDefault("Autonomous Command", new AutonomousCommand());

        // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=AUTONOMOUS
        RobotMap.driveTrainLeftMaster.setSelectedSensorPosition(0, 0, 0);
        RobotMap.driveTrainRightMaster.setSelectedSensorPosition(0, 0, 0);

        new UpdateSmartDashboard();
        //SmartDashboard.putData("Auto mode", chooser);
        
        new UpdateSmartDashboard();

        SmartDashboard.putData("Auto mode", chooser);
       // SmartDashboard.putData("TestPath", new PathFindingLogicCode());
        
        //SmartDashboard.putData("JSON", new ParseJSONFile());
    	RobotMap.driveTrainShiftingSolenoid.set(true);

        MaunalDuo = prefs.getBoolean("Solenoid Maunal", false);
        Logitech = false;
        ArcadeDrive = true;
        prefs = Preferences.getInstance();
        prefs.putBoolean("ArcadeDrive", true);
		prefs.putBoolean("Logitech", false);

        if (!prefs.containsKey("P")) {
        	prefs.putDouble("P", 0.009);
        }
        if (!prefs.containsKey("I")) {
        	prefs.putDouble("I", 0);
        }
        if (!prefs.containsKey("D")) {
        	prefs.putDouble("D", 0);
        }
        if (!prefs.containsKey("F")) {
        	prefs.putDouble("F", 0);
        }
        
        if (!prefs.containsKey("PL")) {
        	prefs.putDouble("PL", 0.009);
        }
        if (!prefs.containsKey("IL")) {
        	prefs.putDouble("IL", 0);
        }
        if (!prefs.containsKey("DL")) {
        	prefs.putDouble("DL", 0);
        }
        if (!prefs.containsKey("F")) {
        	prefs.putDouble("F", 0.39);
        }
        if (!prefs.containsKey("FL")) {

        	prefs.putDouble("FL", 0.39);
        }
        if (!prefs.containsKey("EP")) {
        	prefs.putDouble("EP", 0.01);
        }
        if (!prefs.containsKey("EI")) {
        	prefs.putDouble("EI", 0);
        }
        if (!prefs.containsKey("ED")) {
        	prefs.putDouble("ED", 0);
        }
        if (!prefs.containsKey("EF")) {
        	prefs.putDouble("EF", 0);
        }
        RobotMap.driveTrainShiftingSolenoid.set(true);
        
      //  new ParseJSONFile();
        //System.out.println(cubeInfo.getEntry("JSON").getString("Bad")); //TODO add back in (?)
    }

    /**
     * This function is called when the disabled button is hit.
     * You can use it to reset subsystems before shutting down.
     */
    @Override
    public void disabledInit(){

    }

    @Override
    public void disabledPeriodic() {
        Scheduler.getInstance().run();
    }
   
    @Override
    public void autonomousInit() {
    	SmartDashboard.putString("DT/.type", "PlotSubSystem");
    	RobotMap.elevatorElevatorTalon.setSelectedSensorPosition(0, 0, 0);
    	RobotMap.driveTrainShiftingSolenoid.set(true);
    	RobotMap.elevatorArmSolenoid.set(false);

    	run = true;
    	RobotMap.driveTrainRightMaster.setSelectedSensorPosition(0, 0, 0);
    	RobotMap.driveTrainLeftMaster.setSelectedSensorPosition(0, 0, 0);
		//new PathFinding("Straight");

    	//new StraightSwitch().start();
    //	new RightSwitch().start();
    	

        //autonomousCommand = chooser.getSelected();
        // schedule the autonomous command (example)
       // if (autonomousCommand != null) autonomousCommand.start();
    }

    /**
     * This function is called periodically during autonomous
     */
    @Override
    public void autonomousPeriodic() {
        Scheduler.getInstance().run();
        gameData = DriverStation.getInstance().getGameSpecificMessage();
    	int modeSelect = oi.selector();
        SmartDashboard.putNumber("velR", (RobotMap.driveTrainRightMaster.getSelectedSensorVelocity(0)));///4096/1.5);
        SmartDashboard.putNumber("velL", (RobotMap.driveTrainLeftMaster.getSelectedSensorVelocity(0)));///4096/1.5);
        SmartDashboard.putNumber("PosR", (RobotMap.driveTrainRightMaster.getSelectedSensorPosition(0)));
        SmartDashboard.putNumber("PosL", (RobotMap.driveTrainLeftMaster.getSelectedSensorPosition(0)));
     	
    	if(gameData.length() > 0 && run) {
         	RobotMap.driveTrainLeftMaster.setSelectedSensorPosition(0, 0, 0);
        	RobotMap.driveTrainRightMaster.setSelectedSensorPosition(0, 0, 0);
    		switch(modeSelect) {
    		case 0:
    			new StraightSwitch("CrossLine").start();
    		break;
    		case 1: 
    			if(gameData.charAt(0) == 'L') {
    				new StraightSwitch("CenterLeft").start();
        		} else if (gameData.charAt(0) == 'R') {
        			new StraightSwitch("CenterRight").start();
        		}else {
        			new StraightSwitch("CrossLine").start();
        		}
    			break;
    		case 2:
    			if(gameData.charAt(0) == 'L') {
    				new StraightSwitch("LeftLeft").start();
        		} else if (gameData.charAt(1) == 'L') {
        			new StraightSwitch("LeftLeftScale").start();
        		}else {
        			new StraightSwitch("CrossLine").start();
        		}
    			break;
    		case 3:
    			if(gameData.charAt(0) == 'R') {
    				new StraightSwitch("RightRight").start();
        		} else if (gameData.charAt(1) == 'R') {
        			new StraightSwitch("RightRightScale").start();
        		}else {
        			new StraightSwitch("CrossLine").start();
        		}
    			break;
    		
    		}
    		
    		/*case 2:
    			new TurnRightScale().start();
    		}*/
    		run  = false;
    		

    		} 
    		
    	
    	/*
    	if(gameData.length() > 0 && run)
        {
    		switch(modeSelect){
    		case 0:  //mid point switch
    			if(gameData.charAt(0) == 'L')
        		{
    				new TurnLeftScale().start();
        		} else if(gameData.charAt(0) == 'R') {
        			new RightSwitch().start();
        		}
    			break;
    		case 1: //start right side for scale
    			if(gameData.charAt(1) == 'R'){
    				new StraightScaleLeft().start();
    			} else if(gameData.charAt(1) == 'L'){
    				new TurnLeftScale().start();
    			}
    			break; //HOGARTH
    		case 2:  //start left side for scale
    			if(gameData.charAt(1) == 'L'){
    				new StraightScaleRight().start();
    			} else if(gameData.charAt(1) == 'R'){
    				new TurnRightScale().start();
    			}
    			break;
    		case 3: 
    			new StraightSwitch().start();
    		}
    		run = false;
    		
        }
    	*/
    }

    @Override
    public void teleopInit() {
    	RobotMap.elevatorElevatorTalon.setSelectedSensorPosition(0, 0, 0);
    	//new PathFinding("ScaleS").start();
 //   	 ahrs.reset();
    	 PE = Robot.prefs.getDouble("PE",
 				0);
     	IE = Robot.prefs.getDouble("IE",
 				0);
     	DE = Robot.prefs.getDouble("DE",
 				0);
     	FE = Robot.prefs.getDouble("FE",             //.45
 				0.2);
    	 //Robot.driveTrain.pidAngle.reset();
    	 EP= prefs.getDouble("EP", 0);
     	 EI= prefs.getDouble("EI", 0);
     	 ED= prefs.getDouble("ED", 0);
     	 EF= prefs.getDouble("EF", 0);
     	 /*
         RobotMap.elevatorElevatorTalon.configClosedloopRamp(1, 0);
         RobotMap.elevatorElevatorTalon.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, 0);
         RobotMap.elevatorElevatorTalon.selectProfileSlot(0, 0);
         RobotMap.elevatorElevatorTalon.config_kP(0, EP, 0);
         RobotMap.elevatorElevatorTalon.config_kI(0, EI, 0);
         RobotMap.elevatorElevatorTalon.config_kD(0, ED, 0);
         RobotMap.elevatorElevatorTalon.config_kF(0, EF, 0);
         RobotMap.elevatorElevatorTalon.configNominalOutputForward(0.0f, 0);
         RobotMap.elevatorElevatorTalon.configNominalOutputReverse(0.0f, 0);
         RobotMap.elevatorElevatorTalon.configMotionAcceleration(25, 0);
         RobotMap.elevatorElevatorTalon.configMotionCruiseVelocity(50, 0);
         RobotMap.elevatorElevatorTalon.configPeakOutputForward(25, 0);  
         //RobotMap.elevatorElevatorTalon.setSensorPhase(true);
          
          */
        // This makes sure that the autonomous stops running when
        // teleop starts running. If you want the autonomous to
        // continue until interrupted by another command, remove
        // this line or comment it out.
        if (autonomousCommand != null) autonomousCommand.cancel();
        RobotMap.elevatorElevatorTalon.config_kP(0, PE, 0);
    	RobotMap.elevatorElevatorTalon.config_kI(0, IE, 0);
    	RobotMap.elevatorElevatorTalon.config_kD(0, DE, 0);
    	RobotMap.elevatorElevatorTalon.config_kF(0, EF, 0);
    	//RobotMap.driveTrainShiftingSolenoid.set(true);
    	RobotMap.elevatorElevatorTalon.setSelectedSensorPosition(0, 0, 0);
    }

    /**
     * This function is called periodically during operator control
     */
    @Override
    public void teleopPeriodic() {
    	SmartDashboard.putNumber("ElevatorENC", RobotMap.elevatorElevatorTalon.getSelectedSensorPosition(0));

    	
    	//System.out.println("running");
        //SmartDashboard.putData("TestPath", new PathFindingLogicCode());
        //SmartDashboard.putData("Henry test path", new PathFinding());
        RequestedTime = prefs.getDouble("Number of Seconds to Wait", 10);
        //logitech = prefs.getBoolean("DriverStation", false);
       // TankDrive = prefs.getBoolean("TankDrive", false);
        MaunalDuo = prefs.getBoolean("Solenoid Maunal", false);
     //   SmartDashboard.putBoolean("TankDrive On", TankDrive);
       // SmartDashboard.putBoolean("DriverStation On", logitech);
        SmartDashboard.putBoolean("solenoid On", RobotMap.driveTrainShiftingSolenoid.get());
        Scheduler.getInstance().run();
    	//System.out.println("running");
    	//SmartDashboard.putNumber("NavX-angle", ahrs.getAngle());
        SmartDashboard.putData("Henry test path", new PathFinding());
        Logitech = prefs.getBoolean("Logitech", false);
        ArcadeDrive = prefs.getBoolean("ArcadeDrive", false);
        SmartDashboard.putBoolean("ArcadeDrive On", ArcadeDrive);
        SmartDashboard.putBoolean("Logitech On", Logitech);
        //SmartDashboard.putNumber("Cube angle difference", cubeInfo.);
        SmartDashboard.putNumber("Position", RobotMap.elevatorElevatorTalon.getSelectedSensorPosition(0));
        SmartDashboard.putNumber("velR", (RobotMap.driveTrainRightMaster.getSelectedSensorVelocity(0)));///4096/1.5);
        SmartDashboard.putNumber("velL", (RobotMap.driveTrainLeftMaster.getSelectedSensorVelocity(0)));///4096/1.5);
        SmartDashboard.putNumber("PosR", (RobotMap.driveTrainRightMaster.getSelectedSensorPosition(0)));
        

        SmartDashboard.putNumber("PosL", (RobotMap.driveTrainLeftMaster.getSelectedSensorPosition(0)));//1508.965) *3.28);
        SmartDashboard.putBoolean("solenoid On", RobotMap.driveTrainShiftingSolenoid.get());
        Scheduler.getInstance().run();
        if(OI.operator.getRawButton(1)){
        	RobotMap.driveTrainLeftMaster.setSelectedSensorPosition(0, 0, 0);
        	RobotMap.driveTrainRightMaster.setSelectedSensorPosition(0, 0, 0);

        }

        //Scheduler.getInstance().run();

    }
}
