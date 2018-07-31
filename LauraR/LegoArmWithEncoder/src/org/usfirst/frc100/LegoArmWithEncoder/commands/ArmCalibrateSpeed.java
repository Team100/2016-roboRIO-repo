package org.usfirst.frc100.LegoArmWithEncoder.commands;

import org.usfirst.frc100.LegoArmWithEncoder.Robot;
import org.usfirst.frc100.LegoArmWithEncoder.RobotMap;
import org.usfirst.frc100.LegoArmWithEncoder.commands.ArmHome.HomingState;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class ArmCalibrateSpeed extends Command {

    public ArmCalibrateSpeed() {
        // Use requires() here to declare subsystem dependencies
        requires(Robot.robotArm);
    }
    public enum SpeedCalibrationState {
	    INIT,
	    RAISE_GET_UP_TO_SPEED,
	    RAISE_AT_SPEED,
	    REPORT_RAISE_SPEED,
	    LOWER_GET_UP_TO_SPEED,
	    LOWER_AT_SPEED,
	    REPORT_LOWER_SPEED,
	    SPEED_CALIBRATION_DONE,
	    SPEED_CALIBRATION_ERROR
	}
	
	
	private SpeedCalibrationState m_speedCalState = SpeedCalibrationState.INIT;
	private final double m_maxHomeTime = 45.0; // seconds
	private Timer m_speedCalTimer = new Timer();
	private boolean m_done = false;
    static final double s_speedMax = 1.0;
    static final double s_speedMin = 0.001;
    static final double s_speedDelta = 0.025;
    static final double s_speedRatio = 0.75;
    static final double s_accelerationTime = 1.0; // seconds
    static final double s_runTime = 1.5; // seconds
    private double m_curSpeed = 0.0;
    private double m_minSpeed = Double.NaN;
    private double m_maxSpeed = Double.NaN;
    private double m_avgSpeed = 0.0;
    private int m_numpts = 0;
    

    // Called just before this Command runs the first time
    protected void initialize() {
    	m_speedCalState = SpeedCalibrationState.INIT;
    	m_speedCalTimer.stop();
    	m_curSpeed = s_speedMax;
    	Robot.robotArm.resetSpeedCalibration();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	double speed;
    	switch (m_speedCalState) {
    	case INIT:
    		m_done = false;
    		if(Robot.robotArm.isAtLowLimit() || Robot.robotArm.isAtHighLimit()) {
    			Robot.robotArm.stop();
    			m_speedCalState = SpeedCalibrationState.SPEED_CALIBRATION_ERROR;
    		} else { 			
    		    m_minSpeed = Double.NaN;
    		    m_maxSpeed = Double.NaN;
    		    m_avgSpeed = 0.0;
    		    m_numpts = 0;
    		    Robot.robotArm.raiseAtSpeed(m_curSpeed);
    		    m_speedCalState = SpeedCalibrationState.RAISE_GET_UP_TO_SPEED;
    		    m_speedCalTimer.start();
    		}
    		break;
    	
    	case RAISE_GET_UP_TO_SPEED:
    		Robot.robotArm.raiseAtSpeed(m_curSpeed);
    		if (m_speedCalTimer.hasPeriodPassed(s_accelerationTime)) {
    			speed = Robot.robotArm.getEncoderRate();
    			m_speedCalState = SpeedCalibrationState.RAISE_AT_SPEED;
    			m_minSpeed = speed;
    			m_maxSpeed = speed;
    			m_avgSpeed = speed;
    			m_numpts ++;
    			m_speedCalTimer.stop();
    			m_speedCalTimer.start();
    		}
    		break;
    	case RAISE_AT_SPEED:
    		speed = Robot.robotArm.getEncoderRate();
    		if (speed < m_minSpeed) {
    			m_minSpeed = speed;
    		}
    		if (speed > m_maxSpeed) {
    			m_maxSpeed = speed;
    		}
			m_avgSpeed += speed;
			m_numpts ++;
    		
    		if (m_speedCalTimer.hasPeriodPassed(s_runTime)) {
    			m_speedCalState = SpeedCalibrationState.REPORT_RAISE_SPEED;
    			m_speedCalTimer.stop();
    			Robot.robotArm.stop();
    		} else {
    			Robot.robotArm.raiseAtSpeed(m_curSpeed);
    		}
	    	break;
    	case REPORT_RAISE_SPEED:
    		Robot.robotArm.updateSpeedCalibrationPoint(m_curSpeed, m_minSpeed, m_maxSpeed, (m_avgSpeed/(float) m_numpts));
    		if(Robot.robotArm.isAtLowLimit() || Robot.robotArm.isAtHighLimit()) {
    			Robot.robotArm.stop();
    			m_speedCalState = SpeedCalibrationState.SPEED_CALIBRATION_ERROR;
    		} else { 			
    		    m_minSpeed = Double.NaN;
    		    m_maxSpeed = Double.NaN;
    		    m_avgSpeed = 0.0;
    		    m_numpts = 0;
    		    Robot.robotArm.lowerAtSpeed(m_curSpeed);
    		    m_speedCalState = SpeedCalibrationState.LOWER_GET_UP_TO_SPEED;
    		    m_speedCalTimer.start();
    		}
	    	break;
    	case LOWER_GET_UP_TO_SPEED:
    		Robot.robotArm.lowerAtSpeed(m_curSpeed);
    		if (m_speedCalTimer.hasPeriodPassed(s_accelerationTime)) {
    			speed = Robot.robotArm.getEncoderRate();
    			m_speedCalState = SpeedCalibrationState.LOWER_AT_SPEED;
    			m_minSpeed = speed;
    			m_maxSpeed = speed;
    			m_avgSpeed = speed;
    			m_numpts ++;
    			m_speedCalTimer.stop();
    			m_speedCalTimer.start();
    		}
	    	break;
    	case LOWER_AT_SPEED:
    		speed = Robot.robotArm.getEncoderRate();
    		if (speed < m_minSpeed) {
    			m_minSpeed = speed;
    		}
    		if (speed > m_maxSpeed) {
    			m_maxSpeed = speed;
    		}
			m_avgSpeed += speed;
			m_numpts ++;
    		
    		if (m_speedCalTimer.hasPeriodPassed(s_runTime)) {
    			m_speedCalState = SpeedCalibrationState.REPORT_LOWER_SPEED;
    			m_speedCalTimer.stop();
    			Robot.robotArm.stop();
    		} else {
    			Robot.robotArm.lowerAtSpeed(m_curSpeed);
    		}
	    	break;
    	case REPORT_LOWER_SPEED:
    		Robot.robotArm.stop();
    		m_speedCalTimer.stop();
    		
    		if(Robot.robotArm.isAtLowLimit() || Robot.robotArm.isAtHighLimit()) {
    			m_speedCalState = SpeedCalibrationState.SPEED_CALIBRATION_ERROR;
    		} else { 			
    			Robot.robotArm.updateSpeedCalibrationPoint(-m_curSpeed, m_minSpeed, m_maxSpeed, (m_avgSpeed/(float) m_numpts));
    			if (m_curSpeed < s_speedMin) {
    				m_speedCalState = SpeedCalibrationState.SPEED_CALIBRATION_DONE;
    			} else {
    				m_curSpeed *= s_speedRatio;
    				m_minSpeed = Double.NaN;
        		    m_maxSpeed = Double.NaN;
        		    m_avgSpeed = 0.0;
        		    m_numpts = 0;
        		    Robot.robotArm.raiseAtSpeed(m_curSpeed);
        		    m_speedCalState = SpeedCalibrationState.RAISE_GET_UP_TO_SPEED;
        		    m_speedCalTimer.start();
    			}
    		}
	    	break;
    	case SPEED_CALIBRATION_DONE:
    		Robot.robotArm.printSpeedCalibration();
    		Robot.robotArm.stop();  
    		m_done = true;
	    	break;
    	case SPEED_CALIBRATION_ERROR:
    		Robot.robotArm.stop();  
    		m_done = true;
    		System.out.println("Arm Speed Calibration Command Error");
	    	break;
	    default:
	    	m_speedCalState = SpeedCalibrationState.SPEED_CALIBRATION_ERROR;
	    	break;
    	}
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return (m_done);
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.robotArm.stop();
    	m_speedCalTimer.stop();  
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
