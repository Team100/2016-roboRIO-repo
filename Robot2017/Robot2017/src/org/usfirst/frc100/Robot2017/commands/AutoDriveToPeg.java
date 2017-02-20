package org.usfirst.frc100.Robot2017.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;


public class AutoDriveToPeg extends CommandGroup {

    public AutoDriveToPeg() {
        addSequential(new GenerateSPath());
    	addSequential(new TurnToAngle("vision"));
    	addSequential(new OpenGear(.75));
    	addSequential(new FollowMotionProfile());
    }
}
