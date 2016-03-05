// RobotBuilder Version: 2.0
//
// This file was generated by RobotBuilder. It contains sections of
// code that are automatically generated and assigned by robotbuilder.
// These sections will be updated in the future when you export to
// Java from RobotBuilder. Do not put any code or make any change in
// the blocks indicating autogenerated code or it will be lost on an
// update. Deleting the comments indicating the section will prevent
// it from being updated in the future.


package org.usfirst.frc100.BALLista.subsystems;

import org.usfirst.frc100.BALLista.RobotMap;
import org.usfirst.frc100.BALLista.commands.*;

import edu.wpi.first.wpilibj.CounterBase.EncodingType;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.command.Subsystem;


/**
 *
 */
public class Spinner extends Subsystem {

    private final SpeedController horizontalPivot = RobotMap.spinnerHorizontalPivot;
   // private final DigitalInput leftSideLimit = RobotMap.spinnerLeftSideLimit;
   // private final DigitalInput rightSideLimit = RobotMap.spinnerRightSideLimit;
    private final Encoder pivotEncoder = RobotMap.spinnerPivotEncoder;

    // Put methods for controlling this subsystem
    // here. Call these from Commands.

    public void initDefaultCommand() {

        setDefaultCommand(new AdjustSpinner());

        // Set the default command for a subsystem here.
        // setDefaultCommand(new MySpecialCommand());

       }

    public void takeJoystickInputs(Joystick op){

    	horizontalPivot.set(op.getRawAxis(3));

    }

    public void stop(){

    	horizontalPivot.set(0);

    }

}

