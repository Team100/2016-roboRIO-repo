package org.usfirst.frc100.Robot2017.commands;



import edu.wpi.first.wpilibj.command.CommandGroup;


public class AutoDriveToPeg extends CommandGroup {

    public AutoDriveToPeg() {
    	addSequential(new GenerateSPath(4.5, 7.8));
        addSequential(new TurnToAngle("vision"));
    	addSequential(new FollowMotionProfile("go to 2 feet"));
    	addSequential(new TurnToAngle("vision"));
    	addSequential(new FollowMotionProfile());
    	addSequential(new OpenGear(.25));
    	addSequential(new FollowMotionProfile(-3.0));
    }
}
