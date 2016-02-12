package team100.smartdashboard.extensions;

import edu.wpi.first.smartdashboard.gui.Widget;
import edu.wpi.first.smartdashboard.properties.Property;
import edu.wpi.first.smartdashboard.types.DataType;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.KeyAdapter;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * Widget to graphically display the elevator height.
 */
public class ElevatorWidget extends Widget{

    public static final String NAME = "Elevator";
    public static final DataType[] TYPES = {DataType.NUMBER};
    private double height = 0;
    private final JPanel graphics = new JPanel(){
        @Override
        public void paint(Graphics g) {
            updateGraphics(g);
        }
    };
    private Image robot, elevator, tote;
    private static final double PIXEL_TO_INCH_RATIO = 2.5;//pixels per inch
    private static final double TOTE_HEIGHT = 12;//inches
    private static final double MIN_HEIGHT = 4;//inches
    private static final double ELEVATOR_HEIGHT = 6;//inches
    
    @Override
    public void setValue(Object o) {
        if(o instanceof Double){
            height = (Double)o;
        }
        repaint();
    }

    @Override
    public void init() {
        setPreferredSize(new Dimension(300, 200));
        setLayout(new BorderLayout());
        add(graphics, BorderLayout.CENTER);
        try {
            robot = ImageIO.read(getClass().getResourceAsStream("robot.png"));
            tote = ImageIO.read(getClass().getResourceAsStream("tote.png"));
            elevator = ImageIO.read(getClass().getResourceAsStream("elevator.png"));
        } catch (IOException ex) {
        }
    }

    @Override
    public void propertyChanged(Property prprt) {
    }
    
    public void updateGraphics(Graphics g) {
        g.drawImage(robot, 0, 0, 100, 200, this);
        for(int i=1; i<7; i++){
            double h = TOTE_HEIGHT*PIXEL_TO_INCH_RATIO;
            g.drawImage(tote, 225, (int)(getHeight()-i*h), 75, (int)h, this);
        }
        g.drawImage(elevator, 100, (int)(getHeight()-(MIN_HEIGHT+height+ELEVATOR_HEIGHT)*PIXEL_TO_INCH_RATIO), 100, (int)(ELEVATOR_HEIGHT*PIXEL_TO_INCH_RATIO), this);
    }
    
    public static void main(String[] args){
        JFrame f = new JFrame();
        f.setSize(300, 300);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setVisible(true);
        final ElevatorWidget e = new ElevatorWidget();
        f.setLayout(new BorderLayout());
        f.add(e, BorderLayout.CENTER);
        e.init();
        final JTextField a = new JTextField("0");
        e.setSize(e.getPreferredSize());
        a.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent ae) {
                try {
                    double d = Double.parseDouble(a.getText());
                    e.setValue(d);
                    System.out.println(d);
                } catch(NumberFormatException e){
                    System.out.println("Not a double");
                }
            }
        });
        f.add(a, BorderLayout.SOUTH);
        f.pack();
    }
}
