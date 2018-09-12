package org.usfirst.frc100.LegoArmWithEncoder.calibration;

import org.usfirst.frc100.LegoArmWithEncoder.Robot;


public class IndexCalibrationData implements CalibrationSendable <IndexCalibrationData>{
	
	public class IndexCalibrationPoint {
		double m_potPosition;
		double m_encoderPosition;
		
		/**
		 * @return the m_potPosition
		 */
		public double get_potPosition() {
			return this.m_potPosition;
		}

		/**
		 * @return the m_encoderPosition
		 */
		public double get_encoderPosition() {
			return this.m_encoderPosition;
		}

		@Override
		public String toString() {
			
			return (m_encoderPosition + ", " + m_potPosition);
		}
		
	}
	
	private final static String s_name = "Arm";
	public IndexCalibrationPoint[] m_calibrationData;
	public int m_curPtr = 0;
	private final static String s_key = "IndexCalibration";
	public IndexCalibrationData (int numPoints) {
		m_calibrationData = new IndexCalibrationPoint[numPoints];
	}
	
	public void resetCurPtr () {
		m_curPtr = 0;
	}
	
	public boolean addCurData (double p_encPos, double p_potPos) {
		if (m_curPtr < m_calibrationData.length - 1) {
			m_calibrationData[m_curPtr] = new IndexCalibrationPoint();
			m_calibrationData [m_curPtr].m_encoderPosition = p_encPos;
			m_calibrationData[m_curPtr].m_potPosition = p_potPos;
			m_curPtr ++;
			return true;
		} else {
			return false;
		}			
	}

	@Override
	public String getName() {
		return s_key;
	}

	@Override
	public void setName(String pName) {
		//don't allow to change		
	}

	@Override
	public String getSubsystem() {
		return s_name;
	}

	@Override
	public void setSubsystem(String pSubsystem) {
		// don't allow to change			
	}

	@Override
	public void writeCalibrationData(CalibrationBuilder pBuilder) {
		double[] encLocations = new double[m_curPtr];
		double[] potPositions = new double[m_curPtr];
		for (int i=0; i < m_curPtr; i ++) {
			encLocations[i] = m_calibrationData[i].m_encoderPosition;
			potPositions[i] = m_calibrationData[i].m_potPosition;
		}
		//pBuilder.putDouble("TheAnswer", 42.0);	
		pBuilder.putDoubleArray("Encoder", encLocations);
		pBuilder.putDoubleArray("Potentiometer", potPositions);
	}


	public synchronized void readCalibrationData() {
		CalibrationBuilderImpl builder = Robot.calibration.getCalibrationBuilder(s_key);
		double[] encLocations = builder.getDoubleArray("Encoder", new double [0]);
		double[] potPositions = builder.getDoubleArray("Potentiometer", new double [0]);
		if (encLocations.length == potPositions.length) {
			m_curPtr = encLocations.length;
			
			for (int i = 0; i < m_curPtr; i++){
				IndexCalibrationPoint calibrationPoint = new IndexCalibrationPoint();
				m_calibrationData[i] = calibrationPoint;
				m_calibrationData[i].m_encoderPosition = encLocations[i];
				m_calibrationData[i].m_potPosition = potPositions[i];
			}	
		} 			
	}		
		
	
}
