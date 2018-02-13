package edu.wpi.first.shuffleboard.sbplugin;

import java.util.Map;
import java.util.function.Function;

import edu.wpi.first.shuffleboard.api.data.ComplexDataType;

public class Team100Point2DType extends ComplexDataType<Team100Point2DData> {

	public Team100Point2DType() {
		
	    super("Team100Point2D", Team100Point2DData.class);
	    System.out.println("in Point2DType constructor");
	  }

	  @Override
	  public Function<Map<String, Object>, Team100Point2DData> fromMap() {
		  System.out.println ("in Point2DType.fromMap");
	    return Team100Point2DData::new;
	  }

	  @Override
	  public Team100Point2DData getDefaultValue() {
		  System.out.println ("in Point2DType.getDefaultValue");
	    return new Team100Point2DData("example", 0.0, 0.0);
	}

}
