package org.usfirst.frc100.LegoArmWithEncoder.calibration;
//modeled after the WPILIB SendableBuilder
import edu.wpi.first.networktables.NetworkTableEntry;

public interface CalibrationBuilder {
	void setCalibrationDataType(String type);
	NetworkTableEntry getEntry(String key);
	
	void putBoolean(String key, boolean data);
	void putDouble(String key, double data);
	void putString(String key, String data);
	void putBooleanArray(String key, boolean[] data);
	void putDoubleArray(String key, double[] data);
	void putStringArray(String key, String[] data);
	void putRaw(String key, byte[] data);
	
	boolean getBoolean(String key, boolean data);
	double getDouble(String key, double data);
	String getString(String key, String data);
	boolean[] getBooleanArray(String key, boolean[] data);
	double[] getDoubleArray(String key, double[] data);
	String[] getStringArray(String key, String[] data);
	byte[] getRaw(String key, byte[] data);
	
	

}
