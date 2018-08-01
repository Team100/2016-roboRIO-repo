package org.usfirst.frc100.LegoArmWithEncoder.util;

public class SingleAxisPathPlanner {
	public class PathPoint {
		public double m_position = Double.NaN;
		public double m_speed = Double.NaN;
		public boolean m_isComplete = false;

		@Override
		public String toString() {
			return this.m_position + ", " + this.m_speed + ", "	+ this.m_isComplete;
		}
		
	}
	public static final double s_defaultMoveDistance = 1000; // encoder ticks
	public static final double s_defaultSlewVelocity = 1000; //encoder ticks per second
	public static final double s_defaultAccel = 2000; //encoder ticks per second per second
	public static final double s_defaultDecel = 1000; //encoder ticks per second per second
	public static final double s_defaultInitVelocity = 0;
	public static final double s_defaultFinalVelocity = 0;
	public static final double s_defaultStartTime = 0;
	
	
	private double m_acceleration; //encoder ticks per second per second
	private double m_deceleration; //encoder ticks per second per second
	private double m_slewVelocity; // encoder ticks per second
	private double m_moveDistance; // encoder ticks
	private double m_initVelocity; // encoder ticks per second
	private double m_finalVelocity; // encoder ticks per second
	
	private double m_accelDistance; // encoder ticks
	private double m_decelDistance; // encoder ticks
	private double m_slewDistance; // encoder ticks
	
	private double m_startTime; // seconds
	private double m_slewStartTime; // seconds
	private double m_decelStartTime; // seconds
	private double m_endTime; // seconds 
	
	private double m_maxVelocity; // ticks per second
	private boolean m_isTrapezoidal = true;
	private boolean m_isPathComputed = false;

	public SingleAxisPathPlanner(double pMoveDist, double pSlewVel, double pAccel, double pDecel, double pInitVel, double pFinalVel, double pStartTime) throws IllegalArgumentException {
		m_moveDistance = pMoveDist; 
		m_slewVelocity = pSlewVel; 
		m_acceleration = pAccel; 
		m_deceleration = pDecel; 
		m_initVelocity = pInitVel; 
		m_finalVelocity = pFinalVel;
		m_startTime = pStartTime;
		if (m_acceleration <= 0.0 ) {
			throw new IllegalArgumentException("Acceleration must be >= 0.0");
		} else if (m_deceleration <= 0.0){
			throw new IllegalArgumentException("Deceleration must be >= 0.0");
		}
		computePathVariables();
	}
	
	public SingleAxisPathPlanner () {
		this(s_defaultMoveDistance, s_defaultSlewVelocity, s_defaultAccel, s_defaultDecel, s_defaultInitVelocity, s_defaultFinalVelocity, s_defaultStartTime) ;
	}
	

	public SingleAxisPathPlanner(double pMoveDist, double pSlewVel, double pAccel, double pDecel) {
		this(pMoveDist, pSlewVel, pAccel, pDecel, s_defaultInitVelocity, s_defaultFinalVelocity, s_defaultStartTime) ;
	}
	
	
	public PathPoint getPathPoint(double time) {
		if (!m_isPathComputed) {
			computePathVariables();
		}
		PathPoint pathPoint = new PathPoint();
		if (time < m_startTime) {
			// should never happen
			System.out.println ("Invalid time.");	
			pathPoint.m_isComplete = true;
		} else {
			if (m_isTrapezoidal) {
				// trapezoidal motion profile with constant velocity slew period
				if (time < m_slewStartTime) {
					// accelerating
					double dt = time - m_startTime;
					pathPoint.m_speed = m_initVelocity + m_acceleration * dt;
					pathPoint.m_position = (m_initVelocity * dt) + (m_acceleration * dt * dt / 2.0);
				} else if (time < m_decelStartTime) {
					// slewing at constant speed
					double dt = time - m_slewStartTime;
					pathPoint.m_speed = m_slewVelocity;
					pathPoint.m_position = m_accelDistance + m_slewVelocity * dt;
				} else if (time < m_endTime) {
					double dt = time - m_decelStartTime;
					double speed = m_slewVelocity - m_deceleration * dt;
					pathPoint.m_speed = speed;
					pathPoint.m_position = m_accelDistance + m_slewDistance + ((m_slewVelocity - speed) * dt / 2.0) + speed * dt;		
				} else {
					// We've reached the end of the path
					pathPoint.m_isComplete = true;
				}
				
			} else {
				// triangular motion profile
				if (time < m_decelStartTime) {
					// accelerating
					double dt = time - m_startTime;
					pathPoint.m_speed = m_initVelocity + m_acceleration * dt;
					pathPoint.m_position = (m_initVelocity * dt) + (m_acceleration * dt * dt / 2.0);
				} else if (time < m_endTime) {
					double dt = time - m_decelStartTime;
					double speed = m_maxVelocity - m_deceleration * dt;
					pathPoint.m_speed = speed;
					pathPoint.m_position = m_accelDistance + m_slewDistance + ((m_maxVelocity - speed) * dt / 2.0) + speed * dt;			
				} else {
					// We've reached the end of the path
					pathPoint.m_isComplete = true;
				}
			}
		}
		return (pathPoint);
	}
	
	public void computePathVariables() {
		m_accelDistance = (m_slewVelocity * m_slewVelocity - m_initVelocity * m_initVelocity) / (2 * m_acceleration);
		m_decelDistance = (m_slewVelocity * m_slewVelocity - m_finalVelocity * m_finalVelocity) / (2 * m_deceleration);
		if ((m_accelDistance + m_decelDistance) < m_moveDistance) {
			// Given the distance to be moved and the accel and decel values, we are able to achieve the slew velocity
			m_isTrapezoidal = true;
			m_maxVelocity = m_slewVelocity;
			m_slewStartTime = m_startTime + ((m_slewVelocity - m_initVelocity) / m_acceleration);
			m_decelStartTime = m_slewStartTime + ((m_moveDistance - m_accelDistance - m_decelDistance) / m_slewVelocity);
			m_slewDistance = (m_decelStartTime - m_slewStartTime) * m_slewVelocity;
			m_endTime = m_decelStartTime + ((m_slewVelocity - m_finalVelocity) / m_deceleration);						
		} else {
			// Given the distance to be moved and the accel and decel values, we cannot achieve the slew velocity
			m_isTrapezoidal = false;
			double term1 = 2.0 * m_moveDistance * m_acceleration * m_deceleration ;
			double term2 = m_initVelocity * m_initVelocity * m_deceleration; // portion related to initial velocity
			double term3 = m_finalVelocity * m_finalVelocity * m_acceleration; // portion related to final velocity
			m_maxVelocity = Math.sqrt((term1 + term2 + term3) / (m_acceleration + m_deceleration));
			m_accelDistance = (m_maxVelocity * m_maxVelocity - m_initVelocity * m_initVelocity) / (2 * m_acceleration);
			m_decelDistance = (m_maxVelocity * m_maxVelocity - m_finalVelocity * m_finalVelocity) / (2 * m_deceleration);
			m_decelStartTime = ((m_maxVelocity - m_initVelocity) / m_acceleration);
			m_endTime = m_decelStartTime + ((m_maxVelocity - m_finalVelocity) / m_deceleration);
			m_slewDistance = 0.0;
			m_slewStartTime = m_decelStartTime;
		}
			
		m_isPathComputed = true;
		
	}
	
	/**
	 * @return the m_acceleration
	 */
	public double get_acceleration() {
		return this.m_acceleration;
	}

	/**
	 * @param p_acceleration the m_acceleration to set
	 */
	public void set_acceleration(double p_acceleration) {
		this.m_acceleration = p_acceleration;
		m_isPathComputed = false;
	}

	/**
	 * @return the m_deceleration
	 */
	public double get_deceleration() {
		return this.m_deceleration;
	}

	/**
	 * @param p_deceleration the m_deceleration to set
	 */
	public void set_deceleration(double p_deceleration) {
		this.m_deceleration = p_deceleration;
		m_isPathComputed = false;
	}

	/**
	 * @return the m_slewVelocity
	 */
	public double get_slewVelocity() {
		return this.m_slewVelocity;
	}

	/**
	 * @param p_slewVelocity the m_slewVelocity to set
	 */
	public void set_slewVelocity(double p_slewVelocity) {
		this.m_slewVelocity = p_slewVelocity;
		m_isPathComputed = false;
	}

	/**
	 * @return the m_moveDistance
	 */
	public double get_moveDistance() {
		return this.m_moveDistance;
	}

	/**
	 * @param p_moveDistance the m_moveDistance to set
	 */
	public void set_moveDistance(double p_moveDistance) {
		this.m_moveDistance = p_moveDistance;
		m_isPathComputed = false;
	}

	/**
	 * @return the m_initVelocity
	 */
	public double get_initVelocity() {
		return this.m_initVelocity;
	}

	/**
	 * @param p_initVelocity the m_initVelocity to set
	 */
	public void set_initVelocity(double p_initVelocity) {
		this.m_initVelocity = p_initVelocity;
		m_isPathComputed = false;
	}

	/**
	 * @return the m_finalVelocity
	 */
	public double get_finalVelocity() {
		return this.m_finalVelocity;
	}

	/**
	 * @param p_finalVelocity the m_finalVelocity to set
	 */
	public void set_finalVelocity(double p_finalVelocity) {
		this.m_finalVelocity = p_finalVelocity;
		m_isPathComputed = false;
	}

	/**
	 * @return the m_startTime
	 */
	public double get_startTime() {
		return this.m_startTime;
	}

	/**
	 * @param p_startTime the m_startTime to set
	 */
	public void set_startTime(double p_startTime) {
		this.m_startTime = p_startTime;
		m_isPathComputed = false;
	}

	/**
	 * @return the m_decelStartTime
	 */
	public double get_decelStartTime() {
		return this.m_decelStartTime;
	}

	/**
	 * @param p_decelStartTime the m_decelStartTime to set
	 */
	public void set_decelStartTime(double p_decelStartTime) {
		this.m_decelStartTime = p_decelStartTime;
		m_isPathComputed = false;
	}

	/**
	 * @return the m_accelDistance
	 */
	public double get_accelDistance() {
		return this.m_accelDistance;
	}

	/**
	 * @return the m_decelDistance
	 */
	public double get_decelDistance() {
		return this.m_decelDistance;
	}

	/**
	 * @return the m_slewDistance
	 */
	public double get_slewDistance() {
		return this.m_slewDistance;
	}

	/**
	 * @return the m_slewStartTime
	 */
	public double get_slewStartTime() {
		return this.m_slewStartTime;
	}

	/**
	 * @return the m_endTime
	 */
	public double get_endTime() {
		return this.m_endTime;
	}

	/**
	 * @return the m_maxVelocity
	 */
	public double get_maxVelocity() {
		return this.m_maxVelocity;
	}

	/**
	 * @return the m_isTrapezoidal
	 */
	public boolean is_isTrapezoidal() {
		return this.m_isTrapezoidal;
	}

	@Override
	public String toString() {
		return "SingleAxisPathPlanner [m_acceleration=" + this.m_acceleration + ", m_deceleration="
				+ this.m_deceleration + ", m_slewVelocity=" + this.m_slewVelocity + ", m_moveDistance="
				+ this.m_moveDistance + ", m_initVelocity=" + this.m_initVelocity + ", m_finalVelocity="
				+ this.m_finalVelocity + ", m_accelDistance=" + this.m_accelDistance + ", m_decelDistance="
				+ this.m_decelDistance + ", m_slewDistance=" + this.m_slewDistance + ", m_startTime=" + this.m_startTime
				+ ", m_slewStartTime=" + this.m_slewStartTime + ", m_decelStartTime=" + this.m_decelStartTime
				+ ", m_endTime=" + this.m_endTime + ", m_maxVelocity=" + this.m_maxVelocity + ", m_isTrapezoidal="
				+ this.m_isTrapezoidal + ", m_isPathComputed=" + this.m_isPathComputed + "]";
	}

	
	

}
