package org.usfirst.frc100.SethRobot.commands;

import org.usfirst.frc100.SethRobot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class GoBack extends Command{
	public double x;
	public double y;
	public GoBack(double right, double left){
		requires(Robot.driveTrain);
		x = right;
		y = left;
	}
	protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	Robot.driveTrain.Move(x, y);
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
	protected boolean isFinished() {
		// TODO Auto-generated method stub
		return false;
	}
	
}
