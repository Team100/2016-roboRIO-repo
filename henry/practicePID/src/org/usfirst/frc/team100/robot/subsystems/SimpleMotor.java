
package org.usfirst.frc.team100.robot.subsystems;

import org.usfirst.frc.team100.robot.Robot;

import edu.wpi.first.wpilibj.AnalogGyro;
import edu.wpi.first.wpilibj.AnalogPotentiometer;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.command.PIDSubsystem;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.interfaces.Gyro;
import edu.wpi.first.wpilibj.interfaces.Potentiometer;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class SimpleMotor extends PIDSubsystem  {
//	public Servo motor;
	public double dValue;
	RobotDrive drive;
    private Potentiometer pot;
    private Potentiometer pots;
    private AnalogGyro gyro;
   SpeedController rightSide, leftSide;
    private static final double kP_real = 4, kI_real = 0.07,
            kP_simulation = 18, kI_simulation = 0.2;
    
    public SimpleMotor() {
        super(kP_real, kI_real, 0);
        if (Robot.isSimulation()) { // Check for simulation and update PID values
            getPIDController().setPID(kP_simulation, kI_simulation, 0, 0);
        }
        setAbsoluteTolerance(0.005);
       
       
      //  motor = new Servo(0);
        pots = new AnalogPotentiometer(1, -2.0/5);
        rightSide = new Victor(1);
        leftSide = new Victor(0);
        gyro = new AnalogGyro(0);
        drive = new RobotDrive(rightSide, leftSide);
        // Conversion value of potentiometer varies between the real world and simulation
        if (Robot.isReal()) {
            pot = new AnalogPotentiometer(2);
        } else {
            pot = new AnalogPotentiometer(2); // Defaults to meters
        }

		// Let's show everything on the LiveWindow
      //  LiveWindow.addActuator("Elevator", "motor",  one);
       // LiveWindow.addSensor("Elevator", "Pot", (AnalogPotentiometer) pot);
       
        LiveWindow.addActuator("Elevator", "PID", getPIDController());
        LiveWindow.addSensor("skjsad", "pots", (AnalogPotentiometer) pots);
        LiveWindow.addSensor("gyro" , "gyro", gyro);
    }

    public void initDefaultCommand() {}

	/**
	 * The log method puts interesting information to the SmartDashboard.
	 */
    public void log() {
        SmartDashboard.putData("Wrist Pot", (AnalogPotentiometer) pot);
    }

    /**
     * Use the potentiometer as the PID sensor. This method is automatically
     * called by the subsystem.
     */
   protected double returnPIDInput() {
       return gyro.getAngle();
    }


    /**
     * Use the motor as the PID output. This method is automatically called by
     * the subsystem.
     */
    protected void usePIDOutput(double d) {
    	//d=-d/4;
    	//d +=.5;
    	dValue = d;
    	drive.drive((d+.5), (-d-.5));
       // one.set(d);
        
        //SmartDashboard.putNumber("motor speed", motor.getAngle());
    }
    public double returnDValue()
    {
    	return dValue;
    }
    public double potValue()
    {
    	return gyro.getAngle();
    }
    
}
