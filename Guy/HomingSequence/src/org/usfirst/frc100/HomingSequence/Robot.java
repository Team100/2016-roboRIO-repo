// RobotBuilder Version: 2.0
//
// This file was generated by RobotBuilder. It contains sections of
// code that are automatically generated and assigned by robotbuilder.
// These sections will be updated in the future when you export to
// Java from RobotBuilder. Do not put any code or make any change in
// the blocks indicating autogenerated code or it will be lost on an
// update. Deleting the comments indicating the section will prevent
// it from being updated in the future.


package org.usfirst.frc100.HomingSequence;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.usfirst.frc100.HomingSequence.commands.*;
import org.usfirst.frc100.HomingSequence.subsystems.*;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.properties file in 
 * the project.
 */
public class Robot extends TimedRobot {

    Command autonomousCommand;
    SendableChooser<Command> chooser = new SendableChooser<>();

    public static OI oi;
    private static final int kJoystickPort = 0;
	static double positionSetpoint = 50;
	public static Preferences prefs;
	public static double P;
	public static double F;
	public static double I;
	public static double D;
	public static int Accel;
	public static int DesiredVelocity;
	//public static DigitalInput RobotMap.elevatorLimitSwitch1;
	//public static AnalogInput pot;
    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS
    public static Elevator elevator;

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS

    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    @Override
    public void robotInit() {
    	
        RobotMap.init();
        prefs = Preferences.getInstance();
		prefs.putDouble("P", 5);
		prefs.putDouble("I", 0.01);
		prefs.putDouble("D", 1);
		prefs.putDouble("F", 0.5);
		prefs.putDouble("Setpoint", 4000);
		//RobotMap.RobotMap.elevatorLimitSwitch1 = new DigitalInput(0);
		//pot = new AnalogInput(0);

        RobotMap.elevatorMotorElevator.configClosedloopRamp(0, 0);
        RobotMap.elevatorMotorElevator.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, 0);
        RobotMap.elevatorMotorElevator.configOpenloopRamp(0, 0);
        RobotMap.elevatorMotorElevator.configMotionCruiseVelocity(150, 0);
        RobotMap.elevatorMotorElevator.configMotionAcceleration(100, 0);
        RobotMap.elevatorMotorElevator.selectProfileSlot(0, 0);
        RobotMap.elevatorMotorElevator.config_kP(0, 5.0, 0);
        RobotMap.elevatorMotorElevator.config_kI(0, 0.01, 0);
        RobotMap.elevatorMotorElevator.config_kD(0, 1, 0);
        RobotMap.elevatorMotorElevator.config_kF(0, 0.5, 0);
        RobotMap.elevatorMotorElevator.setInverted(true);
        RobotMap.elevatorMotorElevator.setSensorPhase(true);
        RobotMap.elevatorMotorElevator.configNominalOutputForward(0.0f, 0);
        RobotMap.elevatorMotorElevator.configNominalOutputReverse(0.0f, 0);
        // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTRUCTORS
        elevator = new Elevator();

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
        SmartDashboard.putData("Auto mode", chooser);
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
        autonomousCommand = chooser.getSelected();
        // schedule the autonomous command (example)
        if (autonomousCommand != null) autonomousCommand.start();
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
    	/*Accel = prefs.getInt("Accel",100);
		DesiredVelocity = prefs.getInt("DesiredVelocity",200);
		P = prefs.getDouble("P", 0);
		I = prefs.getDouble("I", 0);
		D = prefs.getDouble("D", 0);
		F = prefs.getDouble("F", 0);
        RobotMap.elevatorMotorElevator.config_kP(0, P, 0);
        RobotMap.elevatorMotorElevator.config_kI(0, I, 0);
        RobotMap.elevatorMotorElevator.config_kD(0, D, 0);
        RobotMap.elevatorMotorElevator.config_kF(0, F, 0);*/
       // RobotMap.elevatorMotorElevator.configMotionCruiseVelocity(DesiredVelocity, 0);
       // RobotMap.elevatorMotorElevator.configMotionAcceleration(Accel, 0);
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
        Scheduler.getInstance().run();
        positionSetpoint = prefs.getDouble("Setpoint",0);

		SmartDashboard.putBoolean("limitSwitch", RobotMap.elevatorLimitSwitch1.get());
		SmartDashboard.putNumber("TalonSRXVoltage", RobotMap.elevatorMotorElevator.getMotorOutputVoltage());
		SmartDashboard.putNumber("TalonSRXCurrent", RobotMap.elevatorMotorElevator.getOutputCurrent());

		SmartDashboard.putNumber("TalonSRXError", RobotMap.elevatorMotorElevator.getClosedLoopError(0));
		SmartDashboard.putNumber("EncoderValueForTalonSRX1", RobotMap.elevatorMotorElevator.getSelectedSensorPosition(0));
		SmartDashboard.putNumber("TalonSRX1Velocity", RobotMap.elevatorMotorElevator.getSelectedSensorVelocity(0));
		SmartDashboard.putNumber("Desiried SetPoint", positionSetpoint);
		if (goUp.going) {
			RobotMap.elevatorMotorElevator.set(ControlMode.MotionMagic, 25000);
		}
		/*if (goDown2.going2) {
			RobotMap.elevatorMotorElevator.set(ControlMode.MotionMagic, 0);
			if(RobotMap.elevatorMotorElevator.getSelectedSensorPosition(0) > -5 && RobotMap.elevatorMotorElevator.getSelectedSensorPosition(0) < 5){
	    		goDown2.done2 = true;
				RobotMap.elevatorMotorElevator.set(ControlMode.PercentOutput, 0);

	    	}*/
		
		/*} else if(RobotMap.elevatorLimitSwitch1.get()){
	        RobotMap.elevatorMotorElevator.setSelectedSensorPosition(0, 0, 0);
			RobotMap.elevatorMotorElevator.set(ControlMode.Velocity, 0);*/

    }
}
