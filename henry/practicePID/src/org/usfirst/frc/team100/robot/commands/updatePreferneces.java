
package org.usfirst.frc.team100.robot.commands;



import org.usfirst.frc.team100.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
public class updatePreferneces extends Command {

	//@Override
	public double p;
	public double i;
	public double d;
	//public double d;
	public double f;
	protected void initialize() {
		p = Robot.prefs.getDouble("driveTrain_kP",0);
		i = Robot.prefs.getDouble("driveTrain_kI",0);
		d = Robot.prefs.getDouble("driveTrain_kD",0);
		f = Robot.prefs.getDouble("driveTrain_kF",0);
		Robot.drive.pid.setPID(p, i, d, f);
		
	}

	@Override
	protected void execute() {
		SmartDashboard.putNumber("p", Robot.drive.pid.getP());
		SmartDashboard.putNumber("i", Robot.drive.pid.getI());
		SmartDashboard.putNumber("d", Robot.drive.pid.getD());
		SmartDashboard.putNumber("f", Robot.drive.pid.getF());
		
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
