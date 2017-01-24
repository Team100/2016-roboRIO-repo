
package org.usfirst.frc.team100.robot.commands;



import org.usfirst.frc.team100.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
public class updatePreferneces extends Command {

	//@Override
	public double p;
	public double i;
	public double f;
	public double s;
	protected void initialize() {
		p = Robot.prefs.getDouble("driveTrain_kP",0);
		i = Robot.prefs.getDouble("driveTrain_kI",0);
		f = Robot.prefs.getDouble("driveTrain_kF",0);
		Robot.drive.pid.setPID(p, i, f);
		
	}

	@Override
	protected void execute() {
		SmartDashboard.putNumber("p", p);
		SmartDashboard.putNumber("i", i);
		SmartDashboard.putNumber("f", f);
		
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
