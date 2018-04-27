package org.usfirst.frc100.RandomTest.commands;
import org.usfirst.frc100.RandomTest.Robot;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class updatePreferneces extends Command {

	//@Override

	public double anP;
	public double anI;
	public double anD;
	protected void initialize() {

		/////////////////////////////////////////////////////
		anP = Robot.prefs.getDouble("angleP", 0);
		anI = Robot.prefs.getDouble("angleI", 0);
		anD = Robot.prefs.getDouble("andleD", 0);
		//Robot.driveTrain.pidAngle.setPID(anP, anI, anD);
		Robot.driveTrain.pidAngle.setPID(anP, anI, anD);
		
		
	}

	@Override
	protected void execute() {

		//---------------------------------------------
		SmartDashboard.putNumber("pA", anP);
		SmartDashboard.putNumber("pI", anI);
		SmartDashboard.putNumber("pD", anD);
		
		
	}

	@Override
	protected boolean isFinished() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected void end() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void interrupted() {
		// TODO Auto-generated method stub
		
	}

}
