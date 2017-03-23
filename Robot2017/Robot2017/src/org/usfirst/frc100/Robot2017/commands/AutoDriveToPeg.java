package org.usfirst.frc100.Robot2017.commands;



import org.usfirst.frc100.Robot2017.Robot;

import edu.wpi.first.wpilibj.command.CommandGroup;


public class AutoDriveToPeg extends CommandGroup {

    public AutoDriveToPeg() {
    	 addSequential(new GenerateSPath(7.8, 4.5)); //4.5 7.8
         addSequential(new TurnToAngle("vision"));
     	addSequential(new FollowMotionProfile("go to 2 feet"));
     	addSequential(new TurnToAngle("vision"));
     	addSequential(new FollowMotionProfile());
     	//addSequential(new TurnToAngle("vision"));
     	//addSequential(new FollowMotionProfile());
     	double DEFAULT_SOLINOIDWAIT = .25;
     	addSequential(new OpenGear(Robot.prefs.getDouble("robot_solinoidWait", DEFAULT_SOLINOIDWAIT)));
     	addSequential(new FollowMotionProfile(-3.0));
    }
}
