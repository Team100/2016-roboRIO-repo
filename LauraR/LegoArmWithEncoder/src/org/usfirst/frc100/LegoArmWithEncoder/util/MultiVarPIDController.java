package org.usfirst.frc100.LegoArmWithEncoder.util;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.Timer;


public class MultiVarPIDController extends PIDController {
	public interface SetpointProvider {
		double[] getSetpoints(double ptime);	
		void setSetpointStartTime(double ptime);
		
		default int getNumSP(){
			return 2;
		}
	}
	
	private double[] m_multiVarSetPoint;
	private SetpointProvider m_setpointProvider;
	private Timer m_setpointTimer = new Timer();
	
	
	public MultiVarPIDController (double Kp, double Ki, double Kd, double Kf, PIDSource pSource, PIDOutput pOutput,
			double pPeriod, SetpointProvider pSPProvider) {
		
		super(Kp, Ki, Kd, Kf, pSource, pOutput, pPeriod);
		setSetpointProvider(pSPProvider);
		m_setpointTimer.stop();
		m_setpointTimer.reset();
	}
	
	public void setSetpointProvider(SetpointProvider pSPProvider) {
		disable();
		m_setpointProvider = pSPProvider;
		if (m_setpointProvider == null){
			m_multiVarSetPoint = new double[1];
		} else {
			m_multiVarSetPoint = new double[m_setpointProvider.getNumSP()];
		}
		m_multiVarSetPoint[0] = getSetpoint();
	}
	
	public void startSetpointProvider() {
		m_setpointTimer.stop();
		m_setpointTimer.reset();
		m_setpointTimer.start();
		if (m_setpointProvider == null){
			m_setpointProvider.setSetpointStartTime(m_setpointTimer.get());			
		}	
	}
	

	/* (non-Javadoc)
	 * @see edu.wpi.first.wpilibj.PIDController#calculate()
	 */
	@Override
	protected void calculate() {
		if (m_setpointProvider != null) {
			m_multiVarSetPoint = m_setpointProvider.getSetpoints(m_setpointTimer.get());
			if (m_multiVarSetPoint[0] != Double.NaN) {
				setSetpoint(m_multiVarSetPoint[0]);
			}
		}		
		super.calculate();
	}


	/* (non-Javadoc)
	 * @see edu.wpi.first.wpilibj.PIDController#cal{culateFeedForward()
	 */
	@Override
	protected double calculateFeedForward() {
		if ((m_setpointProvider != null) && 
				(m_multiVarSetPoint.length > 1) &&
				(m_multiVarSetPoint[1] != Double.NaN)){
			return m_multiVarSetPoint[1] * getF();
		} else {
			return super.calculateFeedForward();
		}
	}

}
