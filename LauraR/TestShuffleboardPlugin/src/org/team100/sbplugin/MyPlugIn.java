package org.team100.sbplugin;

import java.util.List;
import java.util.Map;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;

import edu.wpi.first.shuffleboard.api.data.DataType;
import edu.wpi.first.shuffleboard.api.plugin.Description;
import edu.wpi.first.shuffleboard.api.plugin.Plugin;
import edu.wpi.first.shuffleboard.api.widget.ComponentType;
import edu.wpi.first.shuffleboard.api.widget.WidgetType;

@Description(
	    group = "org.team100.sbplugin",
	    name = "2018Widgets",
	    version = "1.0.0",
	    summary = "Defines all Team 100 specific data types and widgets"
	)
public class MyPlugIn extends Plugin {


	@SuppressWarnings("rawtypes")
	@Override
	public List<ComponentType> getComponents() {
		 return ImmutableList.of(
			        WidgetType.forAnnotatedWidget(Team100Point2DWidget.class)
			);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public List<DataType> getDataTypes() {
		return ImmutableList.of(
		        new Team100Point2DType()
		);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Map<DataType, ComponentType> getDefaultComponents() {
		return ImmutableMap.<DataType, ComponentType>builder()
		        .put(new Team100Point2DType(), WidgetType.forAnnotatedWidget(Team100Point2DWidget.class))
		        .build();
	}
}
