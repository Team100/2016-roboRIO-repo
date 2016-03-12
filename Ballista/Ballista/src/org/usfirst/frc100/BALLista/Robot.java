package org.usfirst.frc100.BALLista;

import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.usfirst.frc100.BALLista.commands.*;
import org.usfirst.frc100.BALLista.subsystems.*;

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
	public static Shooter shooter;
	public static Preferences prefs;
	
	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */

	public void robotInit() {

		RobotMap.init();
		prefs = Preferences.getInstance();
		driveTrain = new DriveTrain();
		pickUp = new PickUp();
		shooter = new Shooter();
		moveRollIn = new MoveRollIn();
		
//		prefs.putDouble("pValue", .04);
//		prefs.putDouble("iValue", 0);
//		prefs.putDouble("dValue", 0);


		// int testValue = 5;

		// OI must be constructed after subsystems. If the OI creates Commands
		// (which it very likely will), subsystems are not guaranteed to be
		// constructed yet. Thus, their requires() statements may grab null
		// pointers. Bad news. Don't move it.

		oi = new OI();

		autonomousCommand = new AutonomousCommand();
		
	   CameraServer.getInstance().startAutomaticCapture("cam0");
	}

	/**
	 * This function is called when the disabled button is hit. You can use it
	 * to reset subsystems before shutting down.
	 */

	public void disabledInit() {

	}

	public void disabledPeriodic() {
		Scheduler.getInstance().run();
	}

	public void autonomousInit() {
		// schedule the autonomous command (example)
	//	if (autonomousCommand != null)
		//	autonomousCommand.start();

		int modeSelect = oi.selector();
		switch (modeSelect) {
		case 1:  new DoNothing(1).start();
			break;
		case 2:  new DoNothing(2).start();
			break;
		case 3: new DoNothing(3).start();
			break;
		case 4: new DoNothing(4).start();
			break;
		default: new DoNothing(0).start();
			break;
		}
		new UpdateDashboard().start();
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
		if (autonomousCommand != null)
			autonomousCommand.cancel();
		// RobotMap.internalGyro.reset();
		Scheduler.getInstance().removeAll();
		new UpdateDashboard().start();
	}

	/**
	 * This function is called periodically during operator control
	 */

	public void teleopPeriodic() {

		Scheduler.getInstance().run();

//	    double p = prefs.getDouble("pValue", .04);
//		double i = prefs.getDouble("iValue", 0);
//		double d = prefs.getDouble("dValue", 0);
//		double aP = prefs.getDouble("armP", 4.00);
//		double aI = prefs.getDouble("armI", .4);
//		double aD = prefs.getDouble("armD", .0);
//		SmartDashboard.putNumber("p", p);
//		SmartDashboard.putNumber("i", i);
//		SmartDashboard.putNumber("d", d);
//
//		SmartDashboard.putNumber("armP", aP);
//		SmartDashboard.putNumber("armI", aI);
//		SmartDashboard.putNumber("armD", aD); 
		/*

		SmartDashboard.putNumber("dist of counter",
				RobotMap.shooterSpdCtr.getDistance());
		SmartDashboard.putNumber("rate of counter",
				RobotMap.shooterSpdCtr.getRate());

		SmartDashboard.putNumber("rate of right",
				RobotMap.driveTrainRightEncoder.getRate());
		SmartDashboard.putNumber("rate of left",
				RobotMap.driveTrainLeftEncoder.getRate());

		SmartDashboard.putNumber(
				"Difference of encoders:",
				Math.abs(RobotMap.driveTrainRightEncoder.getDistance()
						- RobotMap.driveTrainLeftEncoder.getDistance()));
		SmartDashboard.putNumber(
				"Difference of encoders Rate:",
				Math.abs(RobotMap.driveTrainRightEncoder.getRate()
						- RobotMap.driveTrainLeftEncoder.getRate()));

		SmartDashboard.putNumber("dist of right",
				RobotMap.driveTrainRightEncoder.getDistance());
		SmartDashboard.putNumber("dist of left",
				RobotMap.driveTrainLeftEncoder.getDistance());

		SmartDashboard.putNumber("PosIsOn", Robot.pickUp.getArmPosVal());
		SmartDashboard.putNumber("rate of right",
				RobotMap.driveTrainRightEncoder.getRate());
		SmartDashboard
				.putBoolean("shooter sensor", RobotMap.shooterSpdIn.get());
		SmartDashboard.putBoolean("top", RobotMap.pickUpUpperLimit.get());
		SmartDashboard
				.putBoolean("low sensor", RobotMap.pickUpLowerLimit.get());
		SmartDashboard.putNumber("setPoint", Robot.pickUp.getSetpoint());
		SmartDashboard.putBoolean("home value", RobotMap.pickUpHomeLimit.get());
		*/
	}

	/**
	 * This function is called periodically during test mode
	 */

	public void testPeriodic() {
		LiveWindow.run();
	}

}
