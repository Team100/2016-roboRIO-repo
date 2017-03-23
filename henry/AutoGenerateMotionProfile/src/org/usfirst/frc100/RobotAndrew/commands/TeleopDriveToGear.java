package org.usfirst.frc100.RobotAndrew.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class TeleopDriveToGear extends CommandGroup {

    public TeleopDriveToGear() {
    	
    	addSequential(new TurnToAngle("vision"));
    	addSequential(new FollowMotionProfile("go to 2 feet"));
    	addSequential(new TurnToAngle("vision"));
    	addSequential(new FollowMotionProfile());
       
    }
}
