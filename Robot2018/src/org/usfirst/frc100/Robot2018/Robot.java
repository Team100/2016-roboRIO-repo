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

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import org.usfirst.frc100.Robot2018.commands.*;
import org.usfirst.frc100.Robot2018.subsystems.*;


import java.util.*;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;



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

	public static Boolean DriverStation;
	public static Boolean TankDrive;

    public static OI oi;

    
    //System.out.println("INITALIZED =======================================================================================");
    public static Preferences prefs = Preferences.getInstance(); //Creates preferences object

    double P; 
    double I; 
    double D; 
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
    public static DoubleSolen DoubleSolen;
    public static String gameData;
    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS

    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    @Override
    public void robotInit() {
    	SmartDashboard.putBoolean("EnteredTestPathFinding", false);
    	SmartDashboard.putBoolean("PathFindingParsing", false);
    	SmartDashboard.putBoolean("SetControlMode", false);
        RobotMap.init();
        // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTRUCTORS
        driveTrain = new DriveTrain();
        elevator = new Elevator();
        elevatorArm = new ElevatorArm();
        intake = new Intake();
        climbingArm = new ClimbingArm();
        winch = new Winch();
        misc = new Misc();
        DoubleSolen = new DoubleSolen();


        // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTRUCTORS
        
        
        // OI must be constructed after subsystems. If the OI creates Commands
        //(which it very likely will), subsystems are not guaranteed to be
        // constructed yet. Thus, their requires() statements may grab null
        // pointers. Bad news. Don't move it.
        oi = new OI();

        // Add commands to Autonomous Sendable Chooser
        // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=AUTONOMOUS

        chooser.addDefault("Autonomous Command", new AutonomousCommand());

        // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=AUTONOMOUS

        //SmartDashboard.putData("Auto mode", chooser);
        
        new UpdateSmartDashboard();

        SmartDashboard.putData("Auto mode", chooser);
        SmartDashboard.putData("TestPath", new PathFindingLogicCode());

        DriverStation = false;
        TankDrive = false;
        prefs = Preferences.getInstance();
        prefs.putBoolean("TankDrive", false);
		prefs.putBoolean("DriverStation", false);
        if (!prefs.containsKey("P")) {
        	prefs.putDouble("P", 0.01);
        }
        if (!prefs.containsKey("I")) {
        	prefs.putDouble("I", 0);
        }
        if (!prefs.containsKey("D")) {
        	prefs.putDouble("D", 0);
        }
        
        if (!prefs.containsKey("PL")) {
        	prefs.putDouble("PL", 0.01);
        }
        if (!prefs.containsKey("IL")) {
        	prefs.putDouble("IL", 0);
        }
        if (!prefs.containsKey("DL")) {
        	prefs.putDouble("DL", 0);
        }
        if (!prefs.containsKey("F")) {
        	prefs.putDouble("F", 0.3);
        }
        if (!prefs.containsKey("FL")) {

        	prefs.putDouble("FL", 0.3);
        }

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
    	gameData = DriverStation.getInstance().getGameSpecificMessage();
    	if(gameData.charAt(0) == 'L'){
    		new LeftSwitch().start();
    	}
    	if(gameData.charAt(0) == 'R'){
    		new RightSwitch().start();
    	}
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
    }

    @Override
    public void teleopInit() {
        // This makes sure that the autonomous stops running when
        // teleop starts running. If you want the autonomous to
        // continue until interrupted by another command, remove
        // this line or comment it out.
        if (autonomousCommand != null) autonomousCommand.cancel();
    }

    /**
     * This function is called periodically during operator control
     */
    @Override
    public void teleopPeriodic() {
        SmartDashboard.putData("TestPath", new PathFindingLogicCode());
        SmartDashboard.putData("Henry test path", new PathFinding());
        DriverStation = prefs.getBoolean("DriverStation", false);
        TankDrive = prefs.getBoolean("TankDrive", false);
        SmartDashboard.putBoolean("TankDrive On", TankDrive);
        SmartDashboard.putBoolean("DriverStation On", DriverStation);
        SmartDashboard.putBoolean("solenoid On", RobotMap.driveTrainShiftingSolenoid.get());
        Scheduler.getInstance().run();
        /*if(OI.leftController.getRawButton(1)){
        	RobotMap.driveTrainShiftingSolenoid.set(true);
        }else if(OI.rightStick.getRawButton(1)) {
        	
        	RobotMap.driveTrainShiftingSolenoid.set(false);
        }else if(OI.operator.getRawButtonPressed(1)) {
        	RobotMap.driveTrainShiftingSolenoid.set(true);
        }else if(OI.operator.getRawButtonPressed(2)) {
        	RobotMap.driveTrainShiftingSolenoid.set(false);
        }*/
        if(DriverStation){
        	SmartDashboard.putNumber("Left Stick", OI.leftController.getY());
        	SmartDashboard.putNumber("Right Stick", -OI.rightStick.getY());
        	if(!TankDrive){
        		RobotMap.driveTrainLeftMaster.set(ControlMode.PercentOutput, OI.leftController.getY()+OI.rightStick.getX());
        		RobotMap.driveTrainRightMaster.set(ControlMode.PercentOutput,-OI.leftController.getY()+OI.rightStick.getX());
        	}else{
        		RobotMap.driveTrainLeftMaster.set(ControlMode.PercentOutput, OI.leftController.getY());
        		RobotMap.driveTrainRightMaster.set(ControlMode.PercentOutput, OI.rightStick.getY());
        	}
        }else{
        	SmartDashboard.putNumber("Logitech", OI.operator.getY());
        	if(!TankDrive){
        		RobotMap.driveTrainLeftMaster.set(ControlMode.PercentOutput,OI.operator.getY()+OI.operator.getRawAxis(4));
        		RobotMap.driveTrainRightMaster.set(ControlMode.PercentOutput,-OI.operator.getY()+OI.operator.getRawAxis(4));
        		//RobotMap.driveTrainDifferentialDrive1.tankDrive(-OI.operator.getRawAxis(1), -OI.operator.getRawAxis(5));
        	}else{
        		RobotMap.driveTrainLeftMaster.set(ControlMode.PercentOutput, OI.operator.getY());
        		RobotMap.driveTrainRightMaster.set(ControlMode.PercentOutput, -OI.operator.getRawAxis(5));
        	}
        }

        Scheduler.getInstance().run();

    }
}
