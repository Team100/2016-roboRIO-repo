package org.usfirst.frc100.KitBotPneumatics.subsystems;

import org.usfirst.frc100.KitBotPneumatics.RobotMap;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class Gear extends Subsystem {
	
	DoubleSolenoid leftPiston = RobotMap.leftPiston;
	DoubleSolenoid rightPiston = RobotMap.rightPiston;
    
    // Put methods for controlling this subsystem
    // here. Call these from Commands.

    @Override
	public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
    
    public boolean isLeftClosed() {
		return leftPiston.get() == DoubleSolenoid.Value.kForward;
	}
    
    public boolean isRightClosed() {
		return rightPiston.get() == DoubleSolenoid.Value.kForward;
	}
    
    public void setPiston(boolean closed) {
		if (!closed) {
			leftPiston.set(DoubleSolenoid.Value.kReverse);
			rightPiston.set(DoubleSolenoid.Value.kReverse);
		} else {
			leftPiston.set(DoubleSolenoid.Value.kForward);
			rightPiston.set(DoubleSolenoid.Value.kForward);
		}
	}
}

