
/**
 * Write a description of class Target here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Target
{
    private double Angle;
    private double Distance;
    private double Timestamp;
    private double[] CenterPixel;
    private double[] BboxCoordinates;
    
    @Override
    public String toString() {
        String s = "Target:\n";
        s += "\tAngle: " + Angle + "\n";
        s += "\tDistance: " + Distance + "\n";
        s += "\tTimestamp: " + Timestamp + "\n";
        s += "\tCenter Pixel: (" + CenterPixel[0] + ", " + CenterPixel[1] + ")\n";
        s += "\tBbox Coordinates: (" + BboxCoordinates[0] + ", " + BboxCoordinates[1] + ", " + BboxCoordinates[2] + ", " + BboxCoordinates[3] + ")";
        return s;
    }
}
