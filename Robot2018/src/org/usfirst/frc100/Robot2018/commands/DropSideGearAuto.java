package org.usfirst.frc100.Robot2018.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class DropSideGearAuto extends CommandGroup {
	
	public DropSideGearAuto(){
		addSequential(new FollowMotionProfile());
		//addSequential(new turnToAngleWithVision());
		addSequential(new FollowMotionProfile()); //get distance from vision
		//addSequential(DropGear);
	}

}
