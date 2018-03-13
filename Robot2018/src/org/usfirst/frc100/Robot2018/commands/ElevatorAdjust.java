// RobotBuilder Version: 2.0
//
// This file was generated by RobotBuilder. It contains sections of
// code that are automatically generated and assigned by robotbuilder.
// These sections will be updated in the future when you export to
// Java from RobotBuilder. Do not put any code or make any change in
// the blocks indicating autogenerated code or it will be lost on an
// update. Deleting the comments indicating the section will prevent
// it from being updated in the future.


package org.usfirst.frc100.Robot2018.commands;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import org.usfirst.frc100.Robot2018.OI;
import org.usfirst.frc100.Robot2018.Robot;
import org.usfirst.frc100.Robot2018.RobotMap;

import com.ctre.phoenix.motorcontrol.ControlMode;

/**
 *
 */
public class ElevatorAdjust extends Command {

    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=VARIABLE_DECLARATIONS
 
    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=VARIABLE_DECLARATIONS

    private int positionTalon;
    private double modifier;
	private double stickValue;
	private int prevdir = 0; // -1 = down, 0 = neutral, 1 = up

	// BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTRUCTOR
    public ElevatorAdjust() {

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTRUCTOR
        // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=VARIABLE_SETTING

        // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=VARIABLE_SETTING
        // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=REQUIRES
        requires(Robot.elevator);

        // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=REQUIRES
    }

    // Called just before this Command runs the first time
    @Override
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    @Override
    protected void execute() {
    	//if(OI.operator.getRawAxis(3)  >= -0.1) {
    		//modifier = -0.15;
    		//System.out.println("UP");
    		//System.out.println(OI.operator.getRawAxis(3));
    		RobotMap.elevatorElevatorTalon.set(ControlMode.PercentOutput, OI.operator.getRawAxis(3)-0.085);
    	//}
    	//else if(OI.operator.getRawAxis(3) >= 0.1) {
    		//modifier = +0.2;
    		//System.out.println("DOWN");
    		//System.out.println(OI.operator.getRawAxis(3));
    		//RobotMap.elevatorElevatorTalon.set(ControlMode.PercentOutput, OI.operator.getRawAxis(3));
    	//}
    	//else {
    	//	modifier = -0.03;
    		//RobotMap.elevatorElevatorTalon.set(ControlMode.PercentOutput, OI.operator.getRawAxis(3));
    	//}
    		if(prevdir == -1 && OI.operator.getRawAxis(3) >-0.000025) {
    			RobotMap.elevatorElevatorTalon.set(ControlMode.PercentOutput, -0.1);
    			
    		}
    		prevdir = (int)Math.signum(OI.operator.getRawAxis(3));
    	System.out.println("");
    	//RobotMap.elevatorElevatorTalon.set(ControlMode.PercentOutput, OI.operator.getRawAxis(3)+modifier);
    	//RobotMap.elevatorElevatorTalon.
    	SmartDashboard.putNumber("ElevatorMaster",RobotMap.elevatorElevatorTalon.getMotorOutputPercent());
    	SmartDashboard.putNumber("ElevatorSlave1",RobotMap.elevatorElevatorVictor.getMotorOutputPercent());
    	SmartDashboard.putNumber("ElevatorSlave2", RobotMap.elevatorElevatorVictor2.getMotorOutputPercent());
    	SmartDashboard.putNumber("JoyPercent", OI.operator.getRawAxis(3));
    	stickValue=OI.operator.getRawAxis(3);

    }
    // Make this return true when this Command no longer needs to run execute()
    
    // Called once after isFinished returns true
    @Override
    protected void end() {
    	RobotMap.elevatorElevatorTalon.disable();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    @Override
    protected void interrupted() {
    	end();
    }

	@Override
	protected boolean isFinished() {
		// TODO Auto-generated method stub
		return false;
	}
}