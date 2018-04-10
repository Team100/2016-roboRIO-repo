package org.usfirst.frc100.Robot2018.commands;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class Parser
{
    public static void main(String[] args) {
        //String json = "[{'Angle': 0, 'Distance': 0, 'Timestamp': 2, 'CenterPixel': [0, 0], 'BboxCoordinates': [0, 1, 2, 3]}, {'Angle': 0, 'Distance': 0, 'Timestamp': 2, 'CenterPixel': [0, 0], 'BboxCoordinates': [0, 1, 2, 3]}]";
        DataGetter get = new DataGetter("Camera", "localhost");
        String json = get.getCube();
        
        Gson g = new GsonBuilder().create();
        Target[] ts = g.fromJson(json, Target[].class);
        for (Target t: ts) {
            System.out.println(t);
        }
    }
}
