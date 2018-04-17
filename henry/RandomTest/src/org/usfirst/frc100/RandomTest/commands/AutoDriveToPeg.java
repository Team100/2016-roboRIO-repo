package org.usfirst.frc100.RandomTest.commands;



import edu.wpi.first.wpilibj.command.CommandGroup;


public class AutoDriveToPeg extends CommandGroup {

    public AutoDriveToPeg() {
    	addSequential(new MotionVel());
    	addSequential(new TurnToAngle(0));
    }
}
