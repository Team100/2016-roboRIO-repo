package org.usfirst.frc.team100.robot;



import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.I2C;

/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class RobotMap {
	    // For example to map the left and right motors, you could define the
	// following variables to use with your drivetrain subsystem.
	// public static int leftMotor = 1;
	// public static int rightMotor = 2;

	// If you are using multiple modules, make sure to define both the port
	// number and the module. For example you with a rangefinder:
	// public static int rangefinderPort = 1;
	// public static int rangefinderModule = 1;
	public static TimeOfFlightVL53L0X tof_sensor;
	public static AnalogInput maxBotix;
	
	public static void init() {
		tof_sensor = new TimeOfFlightVL53L0X(I2C.Port.kOnboard);
		maxBotix = new AnalogInput(0);
	}
}
