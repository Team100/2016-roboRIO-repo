package edu.wpi.first.shuffleboard.sbplugin;

import java.util.List;
import java.util.Map;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;

import edu.wpi.first.shuffleboard.api.data.DataType;
import edu.wpi.first.shuffleboard.api.plugin.Description;
import edu.wpi.first.shuffleboard.api.plugin.Plugin;
import edu.wpi.first.shuffleboard.api.plugin.Requires;
import edu.wpi.first.shuffleboard.api.widget.ComponentType;
import edu.wpi.first.shuffleboard.api.widget.WidgetType;

@Description(
	    group = "edu.wpi.first.shuffleboard",
	    name = "2018Widgets",
	    version = "1.0.0",
	    summary = "Defines all Team 100 specific data types and widgets"
	)
@Requires(group = "edu.wpi.first.shuffleboard", name = "Base", minVersion = "1.0.0")
public class MyPlugIn extends Plugin {


	@Override
	public List<ComponentType> getComponents() {
		System.out.println ("in MyPlugIn.getComponents");
		 return ImmutableList.of(
			        WidgetType.forAnnotatedWidget(Team100Point2DWidget.class)
			);
	}

	@Override
	public List<DataType> getDataTypes() {
		System.out.println ("in MyPlugIn.getDataTypes");
		return ImmutableList.of(
		        new Team100Point2DType()
		);
	}

	@Override
	public Map<DataType, ComponentType> getDefaultComponents() {
		System.out.println ("in MyPlugIn.getDefaultComponents");
		return ImmutableMap.<DataType, ComponentType>builder()
		        .put(new Team100Point2DType(), WidgetType.forAnnotatedWidget(Team100Point2DWidget.class))
		        .build();
	}



}
