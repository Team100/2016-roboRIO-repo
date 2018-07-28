package org.usfirst.frc100.LegoArmWithEncoder.calibration;

import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.Sendable;
import edu.wpi.first.wpilibj.smartdashboard.SendableBuilderImpl;

public class Calibration {
	/**
	 * Calibration Table Name
	 */
	private static final String TABLE_NAME = "Calibration";
	
	/**
	 * The singleton instance
	 */
	private static Calibration instance;
	/**
	 * The network table
	 */
	private final NetworkTable m_table;
	
	/**
	 * CalibrationData
	 */
	private static class CalibrationData <T>{
		CalibrationData(CalibrationSendable<T> sendable) {
			m_sendable = sendable;
		}
		final CalibrationSendable<T> m_sendable;
		final CalibrationBuilderImpl m_builder = new CalibrationBuilderImpl();
	}
//	private static final Map<String, CalibrationData> tablesToCalibrationData = new HashMap<>();

	/**
	 * Returns the calibration instance.
	 * 
	 * @return the calibration instance
	 */
	public static synchronized Calibration getInstance() {
		if (instance == null) {
			instance = new Calibration();
		}
		return instance;
	}
	
	/**
	 * Creates a Calibration class
	 */
	private Calibration() {
		m_table = NetworkTableInstance.getDefault().getTable(TABLE_NAME);
		m_table.getEntry(".type").setString("RobotCalibration");
	}
	
	/**
	 * Gets the vector of keys.
	 */
	public Vector<String> getKeys() {
		return new Vector<>(m_table.getKeys());
	}
	
	  /**
	   * Maps the specified key to the specified value in this table. The key can not be null. The value
	   * can be retrieved by calling the get method with a key that is equal to the original key.
	   *
	   * @param key  the key
	   * @param data the value
	   * @throws IllegalArgumentException If key is null
	   */
	  public synchronized <T> void putCalibrationData (String key, CalibrationSendable<T> data) {
	      CalibrationData<T> caldata = new CalibrationData<T>(data);
	      NetworkTable dataTable = m_table.getSubTable(key);
	      caldata.m_builder.setTable(dataTable);
	      data.writeCalibrationData(caldata.m_builder);
	      dataTable.getEntry(".name").setString(key);
	  }

	  /**
	   * Maps the specified key (where the key is the name of the {@link NamedSendable}
	   * to the specified value in this table. The value can be retrieved by
	   * calling the get method with a key that is equal to the original key.
	   *
	   * @param value the value
	   * @throws IllegalArgumentException If key is null
	   */
	  public <T> void putCalibrationData (CalibrationSendable<T>  value) {
	    putCalibrationData(value.getName(), value);
	  }

	  public synchronized CalibrationBuilderImpl getCalibrationBuilder(String key) {
		  CalibrationBuilderImpl builder = new CalibrationBuilderImpl();
		  NetworkTable dataTable = m_table.getSubTable(key);
		  builder.setTable(dataTable);
		  return builder;		  
	  }
	  

}
