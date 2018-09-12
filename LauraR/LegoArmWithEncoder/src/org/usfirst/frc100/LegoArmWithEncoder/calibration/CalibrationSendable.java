package org.usfirst.frc100.LegoArmWithEncoder.calibration;
// Modeled after WPILIB Sendable interface

public interface CalibrationSendable<T> {
	String getName();
	void setName(String name);
	
	default void setName(String subsystem, String name){
		setSubsystem(subsystem);
		setName(name);
	}
	
	String getSubsystem();
	void setSubsystem(String subsystem);
	
	void writeCalibrationData(CalibrationBuilder builder);
	//void readCalibrationData(CalibrationBuilder builder);
}
