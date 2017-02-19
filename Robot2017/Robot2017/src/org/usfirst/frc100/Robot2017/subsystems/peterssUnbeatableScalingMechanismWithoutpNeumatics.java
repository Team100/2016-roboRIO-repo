// RobotBuilder Version: 2.0
//
// This file was generated by RobotBuilder. It contains sections of
// code that are automatically generated and assigned by robotbuilder.
// These sections will be updated in the future when you export to
// Java from RobotBuilder. Do not put any code or make any change in
// the blocks indicating autogenerated code or it will be lost on an
// update. Deleting the comments indicating the section will prevent
// it from being updated in the future.


package org.usfirst.frc100.Robot2017.subsystems;

import org.usfirst.frc100.Robot2017.RobotMap;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;


/**
 *
 */
public class PeterssUnbeatableScalingMechanismWithoutpNeumatics extends Subsystem {

    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTANTS

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTANTS

    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS

    //private final SpeedController winchMotor = RobotMap.peterssUnbeatableScalingMechanismWithoutpNeumaticswinchMotor;
    //private final Encoder winchEncoder = RobotMap.peterssUnbeatableScalingMechanismWithoutpNeumaticswinchEncoder;


    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS


    // Put methods for controlling this subsystem
    // here. Call these from Commands.

    public void initDefaultCommand() {
        // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DEFAULT_COMMAND


        // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DEFAULT_COMMAND

        // Set the default command for a subsystem here.
        // setDefaultCommand(new MySpecialCommand());
    }

	public void updateDashboard() {
		// TODO Auto-generated method stub
		SmartDashboard.putNumber("ScalingMech/WinchMotor: ", winchMotor.get());
		SmartDashboard.putNumber("ScalingMech/WinchEncoder Rate: ", winchEncoder.getRate());
		SmartDashboard.putNumber("ScalingMech/WinchEncoder Distance: ", winchEncoder.getDistance());
		SmartDashboard.putNumber("ScalingMech/Winchencoder Count: ", winchEncoder.get());
		
	}
}


