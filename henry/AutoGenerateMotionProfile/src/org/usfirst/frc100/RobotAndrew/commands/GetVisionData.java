package org.usfirst.frc100.RobotAndrew.commands;

import edu.wpi.first.wpilibj.networktables.NetworkTable;
import edu.wpi.first.wpilibj.tables.ITable;

public class GetVisionData{
	private static double distance;
	private double angle;
	double empty;
	public NetworkTable table = NetworkTable.getTable("GRIP/myContoursReport");
	public GetVisionData(){
		
		distance = table.getNumber("distance", empty);
		angle = table.getNumber("degreeOffset", empty);
	}
	
	public  double calculateDistance(){
		return distance;
	}
	
	public double calculateAngle(){
		return angle;
	}

	public void valueChanged(ITable source, String key, Object value,
			boolean isNew) {
		
    	 distance = table.getNumber("Distance", empty);
    	 angle = table.getNumber("Angle", empty);
	}
}
	
