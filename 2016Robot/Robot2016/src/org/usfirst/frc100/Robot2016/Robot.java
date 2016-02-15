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

import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import org.usfirst.frc100.Robot2016.commands.*;
import org.usfirst.frc100.Robot2016.subsystems.*;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot {

    Command autonomousCommand;

    public static OI oi;
    public static DriveTrain driveTrain;
    public static PickUp pickUp;
    public static MoveRollIn moveRollIn;
    public static LoaderPinball loaderPinball;
    public static Shooter shooter;
    public static Spinner spinner;
    public static UnbeatableScalingMechanism unbeatableScalingMechanism;
    public static Pneumatics pneumatics;
    public static Vision vision;
    //public static holdPosition pos;
    public static Preferences prefs;


    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS


    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    public void robotInit() {

    	RobotMap.init();

        driveTrain = new DriveTrain();
        pickUp = new PickUp();
        //pos = new holdPosition();
        moveRollIn = new MoveRollIn();
        loaderPinball = new LoaderPinball();
        shooter = new Shooter();
        spinner = new Spinner();
        unbeatableScalingMechanism = new UnbeatableScalingMechanism();
        pneumatics = new Pneumatics();
        vision = new Vision();

        prefs = Preferences.getInstance();
        prefs.putDouble("pValue", .04);
        prefs.putDouble("iValue", 0);
        prefs.putDouble("dValue", 0);
       // int testValue = 5;
    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTRUCTORS
        // OI must be constructed after subsystems. If the OI creates Commands
        //(which it very likely will), subsystems are not guaranteed to be
        // constructed yet. Thus, their requires() statements may grab null
        // pointers. Bad news. Don't move it.

        oi = new OI();

        autonomousCommand = new AutonomousCommand();

    }

    /**
     * This function is called when the disabled button is hit.
     * You can use it to reset subsystems before shutting down.
     */
    public void disabledInit(){

    }

    public void disabledPeriodic() {
        Scheduler.getInstance().run();
    }

    public void autonomousInit() {
        // schedule the autonomous command (example)
        if (autonomousCommand != null) autonomousCommand.start();
    }

    /**
     * This function is called periodically during autonomous
     */
    public void autonomousPeriodic() {
        Scheduler.getInstance().run();
    }

    public void teleopInit() {
        // This makes sure that the autonomous stops running when
        // teleop starts running. If you want the autonomous to
        // continue until interrupted by another command, remove
        // this line or comment it out.
        if (autonomousCommand != null) autonomousCommand.cancel();
        //RobotMap.internalGyro.reset();
        CameraServer.getInstance().startAutomaticCapture("cam0");


    }

    /**
     * This function is called periodically during operator control
     */
    public void teleopPeriodic() {
        Scheduler.getInstance().run();

        double p = prefs.getDouble("pValue", .04);
        double i = prefs.getDouble("iValue", 0);
        double d = prefs.getDouble("dValue", 0);
        SmartDashboard.putNumber("p", p);
    	SmartDashboard.putNumber("i", i);
    	SmartDashboard.putNumber("d", d);


        SmartDashboard.putNumber("Posison",Robot.pickUp.getArmPosVal());


    }

    /**
     * This function is called periodically during test mode
     */
    public void testPeriodic() {
        LiveWindow.run();
    }
    /*
    public static double getP(){
    	return  p = prefs.getDouble("pValue", .04);
    }
    public static double getI(){
    	return i = prefs.getDouble("iValue", 0);
    }
    public static double getD(){
    	return  d = prefs.getDouble("dValue", 0);
    }
    */
}
