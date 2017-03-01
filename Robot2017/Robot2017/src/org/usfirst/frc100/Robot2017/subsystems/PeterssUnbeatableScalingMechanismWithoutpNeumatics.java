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
import org.usfirst.frc100.Robot2017.commands.*;
import edu.wpi.first.wpilibj.CounterBase.EncodingType;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.VictorSP;

import edu.wpi.first.wpilibj.command.Subsystem;


/**
 *
 */
public class PeterssUnbeatableScalingMechanismWithoutpNeumatics extends Subsystem {
	
    //private final SpeedController winchMotor = RobotMap.peterssUnbeatableScalingMechanismWithoutpNeumaticswinchMotor;
    //private final Encoder winchEncoder = RobotMap.peterssUnbeatableScalingMechanismWithoutpNeumaticswinchEncoder;

	public final Encoder climberEncoder = RobotMap.climberEncoder;
	public final VictorSP climberWinch = RobotMap.climberWinch;

    public void initDefaultCommand() {
    	setDefaultCommand(new ClimbJoysticks()); 
    }
    
    public PeterssUnbeatableScalingMechanismWithoutpNeumatics(){
    }
    
    public void climbJoysticks(Joystick joy){
    	RobotMap.climberWinch.set(joy.getRawAxis(3));
    }
    
    public void climbNudge(double value){
    	RobotMap.climberWinch.set(value);
    }
}

