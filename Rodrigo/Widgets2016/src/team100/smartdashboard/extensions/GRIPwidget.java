package team100.smartdashboard.extensions;

import java.awt.*;

import javax.swing.JPanel;
import javax.swing.JTextPane;

import edu.wpi.first.smartdashboard.gui.StaticWidget;
import edu.wpi.first.smartdashboard.properties.Property;
import edu.wpi.first.smartdashboard.types.DataType;
import edu.wpi.first.wpilibj.networktables.NetworkTable;
import edu.wpi.first.wpilibj.tables.ITable;
import edu.wpi.first.wpilibj.tables.ITableListener;

public class GRIPwidget extends StaticWidget {
	/**
	 * Widget for GRIP
	 */
	private static final long serialVersionUID = 1L;
	public static final String NAME = "GRIP Widget";
	public static final DataType[] TYPES = { DataType.NUMBER };
	private final NetworkTable mCR = NetworkTable.getTable("GRIP/myContoursReport");
	private final JPanel p = new JPanel();
    private final Color team100orange = new Color(0xF4, 0x92, 0x07);
    private final JTextPane x = new JTextPane();
    private GridLayout g;

	public void propertyChanged(Property arg0) {
		//Empty
	}
	
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(team100orange);
        g.fillRect(0, 0, getWidth(), getHeight());
    }
	
	public void init() {
		setPreferredSize(new Dimension(500,  500));
		setLayout(new FlowLayout());
        g = new GridLayout(3, 5);
        g.setHgap(5);
        g.setVgap(5);
        x.setText("GRIP Widget");
		p.setSize(500, 500);
        p.setLayout(g);
        p.setBackground(team100orange);
        add(p);
        add(x);
        
		mCR.addTableListener(new ITableListener(){
			
			public void valueChanged(ITable source, String key, Object value, boolean isNew) {
				if(value instanceof double[]){
					double[] a = (double[]) value;
				for(int i = 0; i < a.length; i++){
					Double val = new Double(a[i]);
				System.out.println(key  + "[" + i + "]" + val.toString());
				}
				}
			}
		});
	}
}