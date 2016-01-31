package org.usfirst.frc100.Lego_Arm_PID.commands;

import org.usfirst.frc100.Lego_Arm_PID.PID;
import org.usfirst.frc100.Lego_Arm_PID.Robot;
import org.usfirst.frc100.Lego_Arm_PID.subsystems.DriveTrain;

public class SetMotorSpeed {
	
private double speed;
private boolean presentSpeed;

private PID DriveTrainPID = new PID("DriveTrain");
private PID MotorSpeedPID;
private Object driveTrain;

public void stop() {

	
}
	 
public SetMotorSpeed(double speed) {
	    	this.speed = speed;
	    	this.presentSpeed = true;
	    	requires(Robot.driveTrain);
	    	}

public SetMotorSpeed(double speed, boolean presentspeed) {
	this.speed = speed;
	this.presentSpeed = presentSpeed;
    requires(Robot.driveTrain);
}

protected void execute() {
	
	setMotorSpeed(-MotorSpeedPID.getOutput());
	
}






private void setMotorSpeed(double d) {
	
}

private Object getSpeedValue() {
	
	return null;
}

private void requires(DriveTrain driveTrain) {	
}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
