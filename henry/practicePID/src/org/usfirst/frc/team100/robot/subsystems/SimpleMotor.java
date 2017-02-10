
package org.usfirst.frc.team100.robot.subsystems;

import org.usfirst.frc.team100.robot.Robot;

import com.ctre.CANTalon;

import edu.wpi.first.wpilibj.AnalogGyro;
import edu.wpi.first.wpilibj.AnalogPotentiometer;
import edu.wpi.first.wpilibj.Counter;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;
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
public class SimpleMotor extends Subsystem  {
//	public Servo motor;
	public double dValue;
	RobotDrive drive;
	public PIDController pid;
  //  private Potentiometer pot;
   // private Potentiometer pots;
   // private AnalogGyro gyro;
    //public SpeedController rightSide, shooter;
    public CANTalon rightSide = new CANTalon(4);
    private static final double kP_real = 4, kI_real = 0.07,
            kP_simulation = 18, kI_simulation = 0.2;
	private static final double DEFAULT_DRIVE_TRAIN_KP = 1; //.004
	private static final double DEFAULT_DRIVE_TRAIN_KI = 0.00;
	private static final double DEFAULT_DRIVE_TRAIN_KD = 0.0;

	public double driveTrain_kP;
	public double driveTrain_kI;
	public double driveTrain_kD;
    
    public SimpleMotor() {
    	// rightSide = new Victor(1);
         //shooter = new Victor(0);
        
 		if (!Robot.prefs.containsKey("driveTrain_kP")) {
			Robot.prefs.putDouble("driveTrain_kP", DEFAULT_DRIVE_TRAIN_KP);
		}
		if (!Robot.prefs.containsKey("driveTrain_kI")) {
			Robot.prefs.putDouble("driveTrain_kI", DEFAULT_DRIVE_TRAIN_KI);
		}
		if (!Robot.prefs.containsKey("driveTrain_kD")) {
			Robot.prefs.putDouble("driveTrain_kD", DEFAULT_DRIVE_TRAIN_KD);
		}

		driveTrain_kP = Robot.prefs.getDouble("driveTrain_kP",
				DEFAULT_DRIVE_TRAIN_KP);
		driveTrain_kI = Robot.prefs.getDouble("driveTrain_kI",
				DEFAULT_DRIVE_TRAIN_KI);
		driveTrain_kD = Robot.prefs.getDouble("driveTrain_kD",
				DEFAULT_DRIVE_TRAIN_KD);
		
    	pid = new PIDController(driveTrain_kP, driveTrain_kI,  driveTrain_kD, 0.0, new PIDSource() { // .04 0 0 for 180
			PIDSourceType m_sourceType = PIDSourceType.kRate;

			public double pidGet() {
				return Robot.encoderRight.getRate();
			}

			@Override
			public void setPIDSourceType(PIDSourceType pidSource) {
				m_sourceType = pidSource;
			}

			@Override
			public PIDSourceType getPIDSourceType() {
				return m_sourceType;
			}
		}, new PIDOutput() {
			public void pidWrite(double d) {
				rightSide.pidWrite(-d); // /2
				//left.pidWrite(-d/2); // /2
			}
		});
       
   
    }

    public void initDefaultCommand() {}

	/**
	 * The log method puts interesting information to the SmartDashboard.
	 */
    public void log() {
      //  SmartDashboard.putData("Wrist Pot", (AnalogPotentiometer) pot);
    }

    /**
     * Use the potentiometer as the PID sensor. This method is automatically
     * called by the subsystem.
     */
  


    /**
     * Use the motor as the PID output. This method is automatically called by
     * the subsystem.
     */
    protected void usePIDOutput(double d) {
    	//d=-d/4;
    	//d +=.5;
    //	dValue = d;
    //	drive.drive((d+.5), (-d-.5));
       // one.set(d);
        
        //SmartDashboard.putNumber("motor speed", motor.getAngle());
    }
    public double returnDValue()
    {
    	return dValue;
    }
   
    
}
