package org.usfirst.frc100.LegoArmWithEncoder.commands;

import org.usfirst.frc100.LegoArmWithEncoder.Robot;
import org.usfirst.frc100.LegoArmWithEncoder.RobotMap;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.InterruptableSensorBase.WaitResult;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class ArmCalibrateIndices extends Command {
    public ArmCalibrateIndices() {
        // Use requires() here to declare subsystem dependencies
        requires(Robot.robotArm);
    }
    
	public class IndexInterruptTask implements Runnable {
		public void run() {
			
			Robot.robotArm.resetIndexCalibration();
			m_encIdx.requestInterrupts();
			while (!Thread.interrupted()) {
				WaitResult result = m_encIdx.waitForInterrupt(0.5, false);
				if (result == WaitResult.kRisingEdge){
					Robot.robotArm.updateIndexCalibrationPoint();
				} 			
			}
	    	m_encIdx.cancelInterrupts();
		}
	}

	private DigitalInput m_encIdx = RobotMap.robotArmEncoderIndex;
	private boolean m_done = false;
	private Thread m_task;

    // Called just before this Command runs the first time
    protected void initialize() {  	
    	m_task = new Thread(new IndexInterruptTask(), "IndexWatcher");
    	m_task.start(); // begin looking for index pulses and take data
    	m_done = false;
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	if (Robot.robotArm.isAtHighLimit()) {
    		Robot.robotArm.stop();
    		Robot.robotArm.printIndexCalibration();
    		m_done = true;
    	} else {
    		Robot.robotArm.raise();
    	}   	
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return m_done;
    }

    // Called once after isFinished returns true
    protected void end() {
    	if (m_task.isAlive()) {
    		m_task.interrupt();
    	}
    	Robot.robotArm.stop();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
