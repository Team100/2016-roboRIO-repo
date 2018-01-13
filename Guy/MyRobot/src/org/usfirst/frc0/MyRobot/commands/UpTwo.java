package org.usfirst.frc0.MyRobot.commands;




import org.usfirst.frc0.MyRobot.Robot;
import org.usfirst.frc0.MyRobot.RobotMap;

import com.ctre.phoenix.motorcontrol.ControlMode;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class UpTwo extends Command {
	public static boolean upOne;
    public UpTwo() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    }

    // Called just before this Command runs the first time
    protected void initialize() {  
    	
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	
    	//RobotMap.pIDDriveTrainTalonSRX1.configForwardSoftLimitThreshold(1000, 0);
    	//RobotMap.pIDDriveTrainTalonSRX1.configForwardSoftLimitEnable(true, 0);
    	upOne = true;
    	//RobotMap.pIDDriveTrainTalonSRX1.configClosedloopRamp(0.9, 0);

    	RobotMap.pIDDriveTrainTalonSRX1.set(ControlMode.MotionMagic, 100);
    	//RobotMap.pIDDriveTrainTalonSRX2.set(ControlMode.Position, 420);


    	
    	
    	
    	
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    	
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}


