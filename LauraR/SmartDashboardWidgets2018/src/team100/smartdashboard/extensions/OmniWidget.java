package team100.smartdashboard.extensions;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JLabel;

import edu.wpi.first.smartdashboard.gui.elements.bindings.AbstractTableWidget;
import edu.wpi.first.smartdashboard.gui.elements.bindings.BooleanBindable;
import edu.wpi.first.smartdashboard.gui.elements.bindings.NumberBindable;
import edu.wpi.first.smartdashboard.gui.elements.bindings.StringBindable;
import edu.wpi.first.smartdashboard.livewindow.elements.NameTag;
import edu.wpi.first.smartdashboard.properties.Property;
import edu.wpi.first.smartdashboard.robot.Robot;
import edu.wpi.first.smartdashboard.types.DataType;
import edu.wpi.first.wpilibj.tables.ITable;
import edu.wpi.first.wpilibj.tables.ITableListener;

/**
 * One widget to rule them all.
 * 
 * @author Team100
 */
public class OmniWidget extends AbstractTableWidget {
	private static final long serialVersionUID = -4456904180457735348L;
	public static final DataType[] TYPES = { SubsystemType.get() };
	public static final String NAME = "OmniWidget";
	GridLayout layout;

	private Map<String, EditorTextField> fieldMap = new HashMap<String, EditorTextField>();

	

	@Override
	public void valueChanged(ITable itable, String key, Object value, boolean isNew) {	
		if (isNew && !key.equals(".type")) {
			// We've got a new field in our table, so clear out all the widgets and recreate the display
			removeAll(); // calls the ancestor class JPanel.removeAll() to clear any previously generated elements
			EditorTextField field = null;
			if (value instanceof Double) {
				// field = new NumberTableField(key);
				field = new UneditableNumberField();
				setNumberBinding(key, (NumberBindable) field);
			} else if (value instanceof String) {
				// field = new StringTableField(key);
				field = new UneditableStringField();
				setStringBinding(key, (StringBindable) field, "");
			} else if (value instanceof Boolean) {
				// field = new BooleanTableField(key);
				field = new UneditableBooleanField();
				setBooleanBinding(key, (BooleanBindable) field);
			}
			if (field != null) {
				field.setText(value + "");
				fieldMap.put(key, field);
			}

			Object[] keys = fieldMap.keySet().toArray();
			Arrays.sort(keys);
			add(new JLabel("Subsystem "));
			add(new NameTag(getFieldName()));
			for (int i = 0; i < keys.length; i++) {
				String name = (String) keys[i];
				// add(new JLabel(name+""));
				add(new NameTag(name + ""));
				add(fieldMap.get(name));
			}
			revalidate();
			repaint();
		}
		else {
			// Otherwise let the parent (AbstractTableWidget) take care of updating fields
			super.valueChanged(itable, key, value, isNew);
		}
	}


	@Override
	public void init() {
		layout = new GridLayout(0, 2);
		this.setLayout(layout);
		setMaximumSize(new Dimension(Integer.MAX_VALUE, getPreferredSize().height));
		// set up the table listener in the parent, AbstractTableWidget
		setValue(Robot.getTable("SmartDashboard/" + getFieldName())); 
	}

	@Override
	public void propertyChanged(Property prprt) {
	}
}