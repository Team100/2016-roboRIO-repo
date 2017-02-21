package org.usfirst.frc100.RobotAndrew.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;


public class AutoDriveToPeg extends CommandGroup {

    public AutoDriveToPeg() {
        addSequential(new GenerateSPath());
    	addSequential(new TurnToAngle("vision"));
    	addSequential(new FollowMotionProfile());
    	addSequential(new FollowMotionProfile(-3.0));
    }
}
