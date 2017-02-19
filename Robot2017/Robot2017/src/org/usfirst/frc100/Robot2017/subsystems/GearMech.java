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

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Solenoid;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;


/**
 *
 */
public class GearMech extends Subsystem {
	
	public final Solenoid gearMechDrop = RobotMap.gearMechDrop;
    public final Solenoid gearMechFlap = RobotMap.gearMechFlap;

    public void initDefaultCommand() {

    }
    
    public void updateDashboard() {
    	SmartDashboard.putBoolean("GearMech/Gear Mech Drop state", gearMechDrop.get());
    	
    	SmartDashboard.putBoolean("GearMech/Gear Mech Flap state", gearMechFlap.get());
	}
    
    public boolean isGearMechDropOpen(){
    	return gearMechDrop.get();
    }
    
    public void setGearMechDrop(boolean open) {
    	if(open){
    		gearMechDrop.set(open);
		}else{
			gearMechDrop.set(!open);
		}
	}
    
    public boolean isGearMechFlapOpen(){
    	return gearMechFlap.get();
    }
    
    public void setGearMechFlap(boolean open) {
    	if(open){
    		gearMechFlap.set(open);
		}else{
			gearMechFlap.set(!open);
		}
	}
}


