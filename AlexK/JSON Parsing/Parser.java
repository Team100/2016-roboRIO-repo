import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * Write a description of class Parser here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Parser
{
    public static void main() {
        String json = "[{'Angle': 0, 'Distance': 0, 'Timestamp': 2, 'CenterPixel': [0, 0], 'BboxCoordinates': [0, 1, 2, 3]}, {'Angle': 0, 'Distance': 0, 'Timestamp': 2, 'CenterPixel': [0, 0], 'BboxCoordinates': [0, 1, 2, 3]}]";
        Gson g = new GsonBuilder().create();
        Target[] ts = g.fromJson(json, Target[].class);
        for (Target t: ts) {
            System.out.println(t);
        }
    }
}
