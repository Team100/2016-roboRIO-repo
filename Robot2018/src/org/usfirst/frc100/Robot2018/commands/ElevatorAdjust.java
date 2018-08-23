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
    private int count;
	private double stickValue;
	//private double limitMultiplier;
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
    	count = 0; 
    }

    // Called repeatedly when this Command is scheduled to run
    @Override
    protected void execute() {
    	if(RobotMap.elevatorArmEleArmLim1.get()&& count == 0) {
    		count = 1;
    	} 
   
    		RobotMap.elevatorElevatorTalon.configPeakOutputForward(0.75, 0);
    		RobotMap.elevatorElevatorVictor.configPeakOutputForward(0.75, 0);
    		RobotMap.elevatorElevatorVictor2.configPeakOutputForward(0.75, 0);
    		RobotMap.elevatorElevatorTalon.configPeakOutputReverse(-0.75, 0);
    		RobotMap.elevatorElevatorVictor.configPeakOutputReverse(-0.75, 0);
    		RobotMap.elevatorElevatorVictor2.configPeakOutputReverse(-0.75, 0);
    	if(OI.operator.getRawAxis(3)  >= 0.05 ) {//&& RobotMap.limitSwitches.get() < 1) {
    		System.out.println("POSITIVE JOYSTICK");
    		//modifier = -000.045;
    		//System.out.println("UP");
    		if((RobotMap.elevatorElevatorTalon.getSelectedSensorPosition(0))>= 200) {
        		RobotMap.elevatorElevatorTalon.set(ControlMode.PercentOutput, (OI.operator.getRawAxis(3))-000.095);//Change here

    		}else{//System.out.println(OI.operator.getRawAxis(3));
    		RobotMap.elevatorElevatorTalon.set(ControlMode.PercentOutput, (OI.operator.getRawAxis(3)*0.2)-000.065);//Change here
    		RobotMap.limitSwitches.reset();
    		}
    	}else if(OI.operator.getRawAxis(3) <= -0.05 ) {//&& RobotMap.limitSwitches.get() > -1) {
    		System.out.println("NEGATIVE JOYSTICK");
    		//modifier = +0.2;
    		//System.out.println("DOWN");
    		//System.out.println(OI.operator.getRawAxis(3));
    		//RobotMap.elevatorElevatorTalon.set(ControlMode.PercentOutput, OI.operator.getRawAxis(3)-000.065);
    		//RobotMap.limitSwitches.reset();
    		System.out.println("A");
    		if((RobotMap.elevatorElevatorTalon.getSelectedSensorPosition(0))<= 1500) {
        		RobotMap.elevatorElevatorTalon.set(ControlMode.PercentOutput, (OI.operator.getRawAxis(3))-000.085);
        		System.out.println("DEBUG GOING DOWN 2");

    		}else{//System.out.println(OI.operator.getRawAxis(3));
    		RobotMap.elevatorElevatorTalon.set(ControlMode.PercentOutput, (OI.operator.getRawAxis(3))*0.2-000.065);
    		System.out.println("DEBUG: GOING DOWN");
    		RobotMap.limitSwitches.reset();
    		}
    	}else {
    	//	modifier = -0.03;
    		//System.out.println("null");
//TODO make the joystick value positive and the bias negative 0.045
    		RobotMap.elevatorElevatorTalon.set(ControlMode.PercentOutput, -000.075);
    	} /**///TODO make the encoder value negative
  
    	
    		//if(prevdir == -1 && OI.operator.getRawAxis(3) >-0.000025) {
    			//RobotMap.elevatorElevatorTalon.set(ControlMode.PercentOutput, -0.1);
    			
    		//}
    		//prevdir = (int)Math.signum(OI.operator.getRawAxis(3));
    	//System.out.println("");
    	//RobotMap.elevatorElevatorTalon.set(ControlMode.PercentOutput, OI.operator.getRawAxis(3)+modifier);
    	//RobotMap.elevatorElevatorTalon.
    	SmartDashboard.putNumber("ElevatorMaster",RobotMap.elevatorElevatorTalon.getMotorOutputPercent());
    	SmartDashboard.putNumber("ElevatorVel", RobotMap.elevatorElevatorTalon.getSelectedSensorVelocity(0));
    	SmartDashboard.putNumber("ElevatorSlave1",RobotMap.elevatorElevatorVictor.getMotorOutputPercent());
    	SmartDashboard.putNumber("ElevatorSlave2", RobotMap.elevatorElevatorVictor2.getMotorOutputPercent());
    	SmartDashboard.putNumber("JoyPercent", -OI.operator.getRawAxis(3));
    	stickValue=OI.operator.getRawAxis(3);
    	//System.out.println(count);

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