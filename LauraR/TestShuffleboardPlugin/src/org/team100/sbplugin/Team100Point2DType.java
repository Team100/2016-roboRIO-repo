package org.team100.sbplugin;

import java.util.Map;
import java.util.function.Function;

import edu.wpi.first.shuffleboard.api.data.ComplexDataType;

public class Team100Point2DType extends ComplexDataType<Team100Point2DData> {

	public Team100Point2DType() {
		
	    super("Team100Point2D", Team100Point2DData.class);
	  }

	  @Override
	  public Function<Map<String, Object>, Team100Point2DData> fromMap() {
	    return Team100Point2DData::new;
	  }

	  @Override
	  public Team100Point2DData getDefaultValue() {
	    return new Team100Point2DData("example", 0.0, 0.0);
	}

}
