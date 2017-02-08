
package org.usfirst.frc100.RobotAndrew.commands;
import org.usfirst.frc100.RobotAndrew.Robot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
public class updatePreferneces extends Command {

	//@Override
	public double posP;
	public double posI;
	public double posF;
	public double velP;
	public double velI;
	public double velF;
	protected void initialize() {
		posP = Robot.prefs.getDouble("driveTrain_kP",0);
		posI = Robot.prefs.getDouble("driveTrain_kI",0);
		posF = Robot.prefs.getDouble("driveTrain_kF",0);
		Robot.driveTrain.pidPosRight.setPID(posP, posI, posF);
		Robot.driveTrain.pidPosLeft.setPID(posP, posI, posF);
		//-----------------------------------------------------
		velP = Robot.prefs.getDouble("driveVelP",0);
		velI = Robot.prefs.getDouble("driveVelI",0);
		velF = Robot.prefs.getDouble("driveVelF",0);
		Robot.driveTrain.pidPosRight.setPID(velP, velI, velF);
		Robot.driveTrain.pidPosLeft.setPID(velP, velI, velF);
		
		
	}

	@Override
	protected void execute() {
		SmartDashboard.putNumber("p", posP);
		SmartDashboard.putNumber("i", posI);
		SmartDashboard.putNumber("f", posF);
		SmartDashboard.putNumber("pV", velP);
		SmartDashboard.putNumber("iV", velI);
		SmartDashboard.putNumber("fV", velF);
		
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
