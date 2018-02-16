package org.team100.sbplugin;

import java.util.Map;

import edu.wpi.first.shuffleboard.api.data.ComplexData;
import edu.wpi.first.shuffleboard.api.util.Maps;

/**
 * @author Team100
 * @version 1.0.0
 *
 * Creates Point2DData
 */
public class Team100Point2DData extends ComplexData<Team100Point2DData> {

	/**
	 * Variables for Point2DData
	 */
	
	/** Internal name */
	  private final String name;
	  /** Internal kP*/
	  private final double kP;
	  /**Internal kI*/
	  private final double kI;
	  /**Internal kD*/
	  private final double kD;
	  /**Internal kF*/
	  private final double kF;

	/**
	 *
	 * @param name
	 * 
	 * @param kP
	 * Proportional
	 * @param kI
	 * Integral
	 * @param kD
	 * Derivative
	 * @param kF
	 * Feedforward
	 *
	 */
	 
	  public Team100Point2DData(String name, double kP, double kI, double kD, double kF) {
		  /**
			 * Constructor for data
			 */
	  	this.name = name;

	    this.kP = kP;
	    this.kI = kI;
	    this.kD = kD;
	    this.kF = kF;
	  }


	  public Team100Point2DData(Map<String, Object> map) {
		  /**
		   * Generates map
		   */
	     name = (String) map.getOrDefault("Name", "");

	    kP = (double) map.getOrDefault("kP", 0.0);
	    kI = (double) map.getOrDefault("kI",0.0);
	    kD = (double) map.getOrDefault("kD",0.0);
	    kF = (double) map.getOrDefault("kF",0.0);
	  }

	  @Override
	  public Map<String, Object> asMap() {
		  /**
		   * @return Maps.<String, Object>
 		   */
	    return Maps.<String, Object>builder()
	        .put("Name", name)
			.put("kP",kP)
			.put("kI",kI)
			.put("kD",kD)
			.put("kF",kF)
	        .build();
	  }

	
	public String getName() {
		/**
		 *
	 	 * @return name
		 */
	    return name;
	  }

	
	public double getkP() {
		/**
		 *
		 * @return kP
		 */
		return kP;
		
	}

	
	public double getkI() {
		/**
		 *
		 * @return kI
		 */
		return kI;
	}

	
	public double getkD() {
		/**
		 *
		 * @return kD
		 *
		return kD;
	}

	
	public double getkF() {
		/**
		 *
		 * @return kF
		 */
		return kF;
	}
}
