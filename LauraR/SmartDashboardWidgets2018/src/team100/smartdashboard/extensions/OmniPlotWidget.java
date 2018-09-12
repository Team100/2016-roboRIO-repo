package team100.smartdashboard.extensions;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;

import edu.wpi.first.smartdashboard.gui.Widget;
import edu.wpi.first.smartdashboard.gui.elements.LinePlot;
import edu.wpi.first.smartdashboard.gui.elements.bindings.AbstractTableWidget;
import edu.wpi.first.smartdashboard.gui.elements.bindings.BooleanBindable;
import edu.wpi.first.smartdashboard.gui.elements.bindings.NumberBindable;
import edu.wpi.first.smartdashboard.gui.elements.bindings.StringBindable;
import edu.wpi.first.smartdashboard.livewindow.elements.NameTag;
import edu.wpi.first.smartdashboard.properties.BooleanProperty;
import edu.wpi.first.smartdashboard.properties.Property;
import edu.wpi.first.smartdashboard.robot.Robot;
import edu.wpi.first.smartdashboard.types.DataType;
import edu.wpi.first.wpilibj.tables.ITable;

public class OmniPlotWidget extends AbstractTableWidget{

	private static final long serialVersionUID = -7091474751856013444L;
	public static final DataType[] TYPES = { PlotSubSystemType.get() };
	public static final String NAME = "OmniPlotWidget";
	
	GridLayout layout;
	private Map<String, EditorTextField> fieldMap = new HashMap<String, EditorTextField>();
	private Map<String, LinePlot> plotMap = new HashMap<String, LinePlot>();
	private Map<String, BooleanProperty> propertyMap = new HashMap<String, BooleanProperty>();
	private final JPanel plotPanel = new JPanel();
	private final JPanel valuePanel = new JPanel();
	private final JButton reset = new JButton("Reset Graphs");
	
	@Override
	public void valueChanged(ITable itable, String key, Object value, boolean isNew) {	
		if (isNew && !key.equals(".type")) {
			// We've got a new field in our table, so clear out all the widgets and recreate the display
			valuePanel.removeAll(); // calls the ancestor class JPanel.removeAll() to clear any previously generated elements
			
			EditorTextField field = null;
			if (value instanceof Double) {
				field = new UneditableNumberField();
				setNumberBinding(key, (NumberBindable) field);
				if (!plotMap.containsKey(key)) {
					plotMap.put(key, new LinePlot());
				}
				if (!propertyMap.containsKey(key)) {
					propertyMap.put(key, new BooleanProperty(this, key, true));
				}
				
			} else if (value instanceof String) {
				field = new UneditableStringField();
				setStringBinding(key, (StringBindable) field, "");
			} else if (value instanceof Boolean) {
				field = new UneditableBooleanField();
				setBooleanBinding(key, (BooleanBindable) field);
			}
			if (field != null) {
				field.setText(value + "");
				fieldMap.put(key, field);
			}

			Object[] keys = fieldMap.keySet().toArray();
			Arrays.sort(keys);
			valuePanel.add(new JLabel("PlotSubsystem "));
			valuePanel.add(new NameTag(getFieldName()));
			for (int i = 0; i < keys.length; i++) {
				String name = (String) keys[i];
				// add(new JLabel(name+""));
				valuePanel.add(new NameTag(name + ""));
				valuePanel.add(fieldMap.get(name));
			}
			valuePanel.add (reset);
			valuePanel.add (new JLabel("")); // blank in second column
			resetGraphs();
			revalidate();
			repaint();
		}
		else {
			
			// Update plots
			if (plotMap.containsKey(key)) {
				System.out.println("Trying to update plot values: " + key);
				LinePlot plot = (LinePlot) plotMap.get(key);
				plot.setValue(value);				
			}
			// Otherwise let the parent (AbstractTableWidget) take care of updating fields
			super.valueChanged(itable, key, value, isNew);
		}
	}	

	@Override
	public void init() {
		this.setLayout(new FlowLayout());
		
		valuePanel.setLayout(new GridLayout(0, 2));
		plotPanel.setLayout(new GridLayout(0,3));
		add(valuePanel);
		add(plotPanel);
		setMaximumSize(new Dimension(Integer.MAX_VALUE, getPreferredSize().height));
		
		reset.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				resetGraphs();
			}
		});
		
		// set up the table listener in the parent, AbstractTableWidget
		setValue(Robot.getTable("SmartDashboard/" + getFieldName())); 
	}
	
	// Adds widget to dashboard
    private void addWidget(Widget w, String s, JComponent p, DataType d) {
        w.setFieldName(s);
        w.setType(d);
        w.init();
        p.add(w);
    }
    
	private void resetGraphs() {
		plotPanel.removeAll();
		// reinstantiate all line plots
		for (Object key: plotMap.keySet()){
			if (propertyMap.get(key).getValue()) {
				plotMap.put((String) key, new LinePlot());
				addWidget(plotMap.get(key), (String) key, plotPanel, DataType.NUMBER);
			}		
		}
		revalidate();
	}
	
	@Override
	public void propertyChanged(Property pProperty) {
		
		if (pProperty instanceof BooleanProperty) {
			String name = pProperty.getName();
			System.out.println("Property " + name + "is " + pProperty.getValue());
			resetGraphs();
		}		
	}
}
