package org.usfirst.frc100.LegoArmWithEncoder.calibration;

import org.usfirst.frc100.LegoArmWithEncoder.Robot;

public class SpeedCalibrationData implements CalibrationSendable <SpeedCalibrationData>{
	public class SpeedCalibrationPoint {
		double m_speedSP;
		double m_maxEncRate;
		double m_minEncRate;
		double m_avgEncRate;
		
		/**
		 * @return the m_speedSP
		 */
		public double get_speedSP() {
			return this.m_speedSP;
		}

		/**
		 * @return the m_maxEncRate
		 */
		public double get_maxEncRate() {
			return this.m_maxEncRate;
		}

		/**
		 * @return the m_minEncRate
		 */
		public double get_minEncRate() {
			return this.m_minEncRate;
		}

		/**
		 * @return the m_avgEncRate
		 */
		public double get_avgEncRate() {
			return this.m_avgEncRate;
		}

		@Override
		public String toString() {	
			return (m_speedSP + ", " + m_avgEncRate + ", " + m_minEncRate + ", " + m_maxEncRate);
		}
		
	}
	
	private final static String s_name = "Arm";
	public SpeedCalibrationPoint[] m_calibrationData;
	public int m_curPtr = 0;
	private final static String s_key = "SpeedCalibration";
	public SpeedCalibrationData (int numPoints) {
		m_calibrationData = new SpeedCalibrationPoint[numPoints];
	}
	
	public void resetCurPtr () {
		m_curPtr = 0;
	}
	
	public boolean addCurData (double p_speedSP, double p_minSpeed, double p_maxSpeed, double p_avgSpeed) {
		if (m_curPtr < m_calibrationData.length - 1) {
			m_calibrationData[m_curPtr] = new SpeedCalibrationPoint();
			m_calibrationData [m_curPtr].m_avgEncRate = p_avgSpeed;
			m_calibrationData [m_curPtr].m_minEncRate = p_minSpeed;
			m_calibrationData [m_curPtr].m_maxEncRate = p_maxSpeed;
			m_calibrationData[m_curPtr].m_speedSP = p_speedSP;
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
		double[] speedSP = new double[m_curPtr];
		double[] avgRate = new double[m_curPtr];
		for (int i=0; i < m_curPtr; i ++) {
			speedSP[i] = m_calibrationData[i].get_speedSP();
			avgRate[i] = m_calibrationData[i].get_avgEncRate();
		}
			
		pBuilder.putDoubleArray("SpeedSP", speedSP);
		pBuilder.putDoubleArray("AvgEncRate", avgRate);
	}


	public synchronized void readCalibrationData() {
		CalibrationBuilderImpl builder = Robot.calibration.getCalibrationBuilder(s_key);
		double[] speedSP = builder.getDoubleArray("SpeedSP", new double [0]);
		double[] avgRate = builder.getDoubleArray("AvgEncRate", new double [0]);
		if (speedSP.length == avgRate.length) {
			m_curPtr = speedSP.length;
			
			for (int i = 0; i < m_curPtr; i++){
				SpeedCalibrationPoint calibrationPoint = new SpeedCalibrationPoint();
				m_calibrationData[i] = calibrationPoint;
				m_calibrationData[i].m_speedSP = speedSP[i];
				m_calibrationData[i].m_avgEncRate = avgRate[i];
			}	
		} 			
	}		
}
