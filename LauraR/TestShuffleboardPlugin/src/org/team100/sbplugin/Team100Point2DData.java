package org.team100.sbplugin;

import java.util.Map;

import edu.wpi.first.shuffleboard.api.data.ComplexData;
import edu.wpi.first.shuffleboard.api.util.Maps;

public class Team100Point2DData extends ComplexData<Team100Point2DData> {
	

	  private final String name;
	  private final double x;
	  private final double y;
	  
	  
	  public Team100Point2DData(String name, double x, double y) {
	    this.name = name;
	    this.x = x;
	    this.y = y;
	  }

	  
	  public Team100Point2DData(Map<String, Object> map) {
	    name = (String) map.getOrDefault("Name", "");
	    x = (double) map.getOrDefault("X", 0.0);
	    y = (double) map.getOrDefault("Y", 0.0);
	  }

	  @Override
	  public Map<String, Object> asMap() {
	    return Maps.<String, Object>builder()
	        .put("Name", name)
	        .put("X", x)
	        .put("Y", y)
	        .build();
	  }

	  public String getName() {
	    return name;
	  }

		public double getX() {
			return x;
		}

		public double getY() {
			return y;
		}

		  
}
