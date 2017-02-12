
package org.usfirst.frc.team100.robot;

import edu.wpi.first.wpilibj.Counter;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Encoder;


import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;

import org.usfirst.frc.team100.robot.commands.Drive;
//import org.usfirst.frc.team100.robot.subsystems.EncoderMotor;
//import org.usfirst.frc.team100.robot.subsystems.Elevator;
import org.usfirst.frc.team100.robot.subsystems.SimpleMotor;

import com.ctre.CANTalon;
import com.ctre.CANTalon.TalonControlMode;

import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot {
    
	//public static final SimpleMotor exampleSubsystem = new SimpleMotor();
	
	
	public static Preferences prefs;
    Command autonomousCommand;
    SendableChooser chooser;
    public static Counter shooterSpdCtr;
    public static DigitalInput shooterSpdIn;
    public static SimpleMotor drive;
    public static Encoder encoderRight; 
    public static OI oi;
    public static CANTalon rightMaster;
    public CANTalon rightFollwer;
  //  public static EncoderMotor encoders;
   
    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    public void robotInit() {
    	rightMaster = new CANTalon(3);
    	rightMaster.changeControlMode(TalonControlMode.PercentVbus);
    	rightFollwer = new CANTalon(2);
    	rightFollwer.changeControlMode(TalonControlMode.Follower);
    	rightFollwer.set(3);
    	encoderRight = new Encoder(0,1);
    	encoderRight.setDistancePerPulse(1.0/1937.2032);
    	prefs = Preferences.getInstance();
    	drive = new SimpleMotor();
		oi = new OI();
		shooterSpdIn = new DigitalInput(4);
		shooterSpdCtr = new Counter(shooterSpdIn);
	    shooterSpdCtr.setDistancePerPulse(1.0);
	    shooterSpdCtr.setUpSource(shooterSpdIn);
	    
		//encoders = new EncoderMotor();
		
		    }
	
	/**
     * This function is called once each time the robot enters Disabled mode.
     * You can use it to reset any subsystem information you want to clear when
	 * the robot is disabled.
     */
    public void disabledInit(){

    }
	
	public void disabledPeriodic() {
		Scheduler.getInstance().run();
	}

	/**
	 * This autonomous (along with the chooser code above) shows how to select between different autonomous modes
	 * using the dashboard. The sendable chooser code works with the Java SmartDashboard. If you prefer the LabVIEW
	 * Dashboard, remove all of the chooser code and uncomment the getString code to get the auto name from the text box
	 * below the Gyro
	 *
	 * You can add additional auto modes by adding additional commands to the chooser code above (like the commented example)
	 * or additional comparisons to the switch structure below with additional strings & commands.
	 */
    public void autonomousInit() {
        autonomousCommand = (Command) chooser.getSelected();
        
		/* String autoSelected = SmartDashboard.getString("Auto Selector", "Default");
		switch(autoSelected) {
		case "My Auto":
			autonomousCommand = new MyAutoCommand();
			break;
		case "Default Auto":
		default:
			autonomousCommand = new ExampleCommand();
			break;
		} */
    	
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
        
    }
   

    /**
     * This function is called periodically during operator control
     */
    public void teleopPeriodic() {
    	
        Scheduler.getInstance().run();
        
      	
       // SmartDashboard.putBoolean("ontarget",  Robot.elevator.onTarget());
     //  SmartDashboard.putNumber("Input Value", elevator.returnDValue());
     //  SmartDashboard.putNumber("gyroValue", drive.potValue());
      SmartDashboard.putNumber("encoderRate", Robot.encoderRight.getRate());
      	
    }
    
    /**
     * This function is called periodically during test mode
     */
    public void testPeriodic() {
        LiveWindow.run();
    }
}
