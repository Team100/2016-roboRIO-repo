package team100.smartdashboard.extensions;

import java.awt.FlowLayout;
import edu.wpi.first.smartdashboard.gui.StaticWidget;
import edu.wpi.first.smartdashboard.gui.Widget;
import edu.wpi.first.smartdashboard.gui.elements.LinePlot;
import edu.wpi.first.smartdashboard.properties.Property;
import edu.wpi.first.smartdashboard.properties.StringProperty;
import edu.wpi.first.smartdashboard.types.DataType;
import edu.wpi.first.wpilibj.networktables.NetworkTable;
import edu.wpi.first.wpilibj.tables.ITable;
import edu.wpi.first.wpilibj.tables.ITableListener;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * Widget that helps with PID tuning.
 * @author Paul
 */

public class PID extends StaticWidget {

    public static final String NAME = "PID Tuner";

    private final JButton reset = new JButton("Reset Graphs");
    private final JButton testPID = new JButton("Start PID");
    private LinePlot errorPlot = new LinePlot();
    private LinePlot outputPlot = new LinePlot();
    private final MyTextBox[] boxes = new MyTextBox[13];
    private final String[] boxNames = {"kP", "kI", "kD", "kF", "TestTarget", "SensorValue", "Input", "Error", "Target", "Output", "Interval", "Rate", "TotalError"};
    private JLabel j;
    
    private final JPanel p1 = new JPanel();
    private final JPanel p2 = new JPanel();
    
    private String name = "Default ";
    public final StringProperty loopName = new StringProperty(this, "LoopName", "Default");
    private final  NetworkTable sd = NetworkTable.getTable("SmartDashboard/PID");
    private final  NetworkTable prefs = NetworkTable.getTable("Preferences");
    
    private final Color team100orange = new Color(0xF4, 0x92, 0x07);

    @Override
    public void propertyChanged(Property prop) {
        if (prop == loopName) {
            name = loopName.getValue()+" ";
            j.setText(name+"PID");
            p1.removeAll();
            
            for(int i=0; i<boxes.length; i++){
                final MyTextBox box = new MyTextBox("PID/"+name);
                boxes[i] = box;
                    addWidget(box, boxNames[i], p1, DataType.NUMBER);
                if(sd.containsKey(name+boxNames[i])){
                    box.setValue(sd.getNumber(name+boxNames[i]));
                } else {
                    box.setValue(0);
                }
                final String prefKey = (name + boxNames[i]).replace(" ", "_");
                if (i>4) {
                    box.editable.setValue(false);
                } else if(i<4){
                    if(prefs.containsKey(prefKey)){
                        boxes[i].setValue(prefs.getValue(prefKey));
                    }
                    box.addSpecialListener(new FocusAdapter(){
                        @Override
                        public void focusLost(FocusEvent e) {
                            prefs.putString(prefKey, box.getText());
                        }
                    });
                }
            }
            
            p1.add(testPID);
            p1.add(reset);
            
            resetGraphs();
        }
    }

    @Override
    public void init() {
        setPreferredSize(new Dimension(825, 444));
        setLayout(new FlowLayout());
        GridLayout g = new GridLayout(3,5);
        g.setHgap(5);
        g.setVgap(5);
        p1.setLayout(g);
        p1.setBackground(team100orange);
        p2.setLayout(new GridLayout(1,2));
        p2.setBackground(team100orange);
        reset.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                resetGraphs();
            }
        });
        testPID.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(sd.containsSubTable("Test "+name+"PID")) {
                    ITable t2 = sd.getSubTable("Test "+name+"PID");
                    t2.putBoolean("running", !t2.getBoolean("running"));
                    if(t2.getBoolean("running")){
                        testPID.setText("Cancel PID");
                    } else {
                        testPID.setText("Start PID");
                    }
                }
            }
        });
        j = new JLabel(name+"PID");
        j.setFont(new Font(j.getFont().getFontName(), Font.BOLD, 30));
        add(j);
        add(p1);
        add(p2);
        propertyChanged(loopName);
        // Listen for new SD data
        sd.addTableListener(new ITableListener(){
            @Override
            public void valueChanged(ITable itable, String key, Object value, boolean bln) {
                for(int i=0; i<boxes.length; i++){
                    if(key.equals(name+boxNames[i])){
                        boxes[i].setValue(value);
                    }
                }
                if(key.equals(name+"Error")){
                    errorPlot.setValue(value);
                } else if(key.equals(name+"Output")){
                    outputPlot.setValue(value);
                }
            }
        });
        //Listen for external preference modification
        prefs.addTableListener(new ITableListener() {
            @Override
            public void valueChanged(ITable itable, String string, Object o, boolean bln) {
                for(int i=0; i<4; i++){
                    if(string.equals((name+boxNames[i]).replace(" ", "_"))){
                        boxes[i].setValue(o);
                        sd.putNumber((name+boxNames[i]), Double.parseDouble(o+""));
                    }
                }
            }
        });
    }

    @Override
    // Orange background!!!
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(team100orange);
        g.fillRect(0, 0, getWidth(), getHeight());
    }

    // Adds widget to dashboard
    private void addWidget(Widget w, String s, JComponent p, DataType d) {
        w.setFieldName(s);
        w.setType(d);
        w.init();
        p.add(w);
    }
    
    // Clears the line plots
    private void resetGraphs(){
        p2.removeAll();
        errorPlot = new LinePlot();
        outputPlot = new LinePlot();
        // The graphs don't need to set the field name to the proper SD path
        addWidget(errorPlot, "Error", p2, DataType.NUMBER);
        addWidget(outputPlot, "Output", p2, DataType.NUMBER);
        revalidate();
    }
}
