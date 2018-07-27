package org.usfirst.frc100.LegoArmWithEncoder.commands;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

import org.usfirst.frc100.LegoArmWithEncoder.Robot;
import org.usfirst.frc100.LegoArmWithEncoder.RobotMap;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.InterruptHandlerFunction;
import edu.wpi.first.wpilibj.InterruptableSensorBase.WaitResult;
import edu.wpi.first.wpilibj.RobotController;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class ArmCalibrateIndices extends Command {
	private class InterruptCounter {
	    private final AtomicInteger m_count = new AtomicInteger();

	    void increment() {
	      m_count.addAndGet(1);
	    }
	
	    int getCount() {
	      return m_count.get();
	    }
  
	}
	private class IndexInterruptHandlerFunction extends InterruptHandlerFunction<InterruptCounter> {
		final AtomicBoolean m_interruptComplete = new AtomicBoolean(false);
		final InterruptCounter m_counter;
		
		IndexInterruptHandlerFunction(InterruptCounter counter) {
			m_counter = counter;
		}

		@Override
		public void interruptFired(int pInterruptAssertedMask, InterruptCounter pParam) {
			m_counter.increment();
			m_interruptComplete.set(true);		
		}

		@Override
		public InterruptCounter overridableParameter() {
			return m_counter;
		}
		
	}
	
	public class IndexInterruptTask implements Runnable {
		public void run() {
			encIdx.requestInterrupts();
			//encIdx.enableInterrupts();
			Robot.robotArm.resetIndexCalibration();
			while (!Thread.interrupted()) {
				WaitResult result = encIdx.waitForInterrupt(.1, false);
				if (result == WaitResult.kRisingEdge){
					Robot.robotArm.updateIndexCalibrationPoint();
				}				
			}
	    	//encIdx.disableInterrupts();
	    	encIdx.cancelInterrupts();
		}
	}
	final InterruptCounter counter = new InterruptCounter();
	IndexInterruptHandlerFunction function = new IndexInterruptHandlerFunction(counter);
	DigitalInput encIdx = RobotMap.robotArmEncoderIndex;
	boolean done = false;
	Thread task;
	
    public ArmCalibrateIndices() {
        // Use requires() here to declare subsystem dependencies
        requires(Robot.robotArm);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
//    	function.m_interruptComplete.set(false);
//    	encIdx.requestInterrupts(function);
//    	encIdx.enableInterrupts();
    	task = new Thread(new IndexInterruptTask(), "IndexWatcher");
    	task.start(); // begin looking for index pulses and take data
    	done = false;
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
//    	if (function.m_interruptComplete.get()){
//    		System.out.println("count =" + counter.getCount());
//    		System.out.println("GOT INTERRUPT!" );  		
//    		done = true;
//    	} else {
    	if (Robot.robotArm.isAtHighLimit()) {
    		Robot.robotArm.stop();
    		Robot.robotArm.printIndexCalibration();
    		done = true;
    	} else {
    		Robot.robotArm.raise();
    	}   	
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return done;
    }

    // Called once after isFinished returns true
    protected void end() {
    	task.interrupt();
    	Robot.robotArm.stop();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
