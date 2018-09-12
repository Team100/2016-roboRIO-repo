package org.usfirst.frc100.LegoArmWithEncoder.calibration;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;

public class CalibrationBuilderImpl implements CalibrationBuilder{
	private NetworkTable m_table;
	
	public void setTable(NetworkTable table) {
		m_table = table;
	}
	
	public NetworkTable getTable() {
		return m_table;
	}

	@Override
	public void setCalibrationDataType(String pType) {
		m_table.getEntry(".type").setString(pType);		
	}

	@Override
	public NetworkTableEntry getEntry(String pKey) {
		return m_table.getEntry(pKey);
	}

	@Override
	public void putBoolean(String pKey, boolean pData) {
		NetworkTableEntry entry = getEntry(pKey);
		entry.setBoolean(pData);
		entry.setPersistent();
		
	}

	@Override
	public void putDouble(String pKey, double pData) {
		NetworkTableEntry entry = getEntry(pKey);
		entry.setDouble(pData);
		entry.setPersistent();				
	}

	@Override
	public void putString(String pKey, String pData) {
		NetworkTableEntry entry = getEntry(pKey);
		entry.setString(pData);
		entry.setPersistent();				
	}

	@Override
	public void putBooleanArray(String pKey, boolean[] pData) {
		NetworkTableEntry entry = getEntry(pKey);
		entry.setBooleanArray(pData);
		entry.setPersistent();			
	}

	@Override
	public void putDoubleArray(String pKey, double[] pData) {
		NetworkTableEntry entry = getEntry(pKey);
		entry.setDoubleArray(pData);
		entry.setPersistent();			
	}

	@Override
	public void putStringArray(String pKey, String[] pData) {		
		NetworkTableEntry entry = getEntry(pKey);
		entry.setStringArray(pData);
		entry.setPersistent();			
	}

	@Override
	public void putRaw(String pKey, byte[] pData) {	
		NetworkTableEntry entry = getEntry(pKey);
		entry.setRaw(pData);
		entry.setPersistent();			
	}

	@Override
	public boolean getBoolean(String pKey, boolean pData) {
		NetworkTableEntry entry = getEntry(pKey);
		return entry.getBoolean(pData);	
	}

	@Override
	public double getDouble(String pKey, double pData) {
		NetworkTableEntry entry = getEntry(pKey);
		return entry.getDouble(pData);		}

	@Override
	public String getString(String pKey, String pData) {
		NetworkTableEntry entry = getEntry(pKey);
		return entry.getString(pData);		}

	@Override
	public boolean[] getBooleanArray(String pKey, boolean[] pData) {
		NetworkTableEntry entry = getEntry(pKey);
		return entry.getBooleanArray(pData);		}

	@Override
	public double[] getDoubleArray(String pKey, double[] pData) {
		NetworkTableEntry entry = getEntry(pKey);
		return entry.getDoubleArray(pData);		}

	@Override
	public String[] getStringArray(String pKey, String[] pData) {
		NetworkTableEntry entry = getEntry(pKey);
		return entry.getStringArray(pData);		}
	 
	@Override
	public byte[] getRaw(String pKey, byte[] pData) {
		NetworkTableEntry entry = getEntry(pKey);
		return entry.getRaw(pData);		}

}
