package org.usfirst.frc100.Robot2018.commands;

import java.util.concurrent.TimeUnit;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;

public class DataGetter {
	private NetworkTableInstance nt = NetworkTableInstance.create();
	private NetworkTable table;
	
	public DataGetter(String t, String s) {
		nt.startClient(s);
		try {
			TimeUnit.SECONDS.sleep(2);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		table = nt.getTable(t);
	}
	public DataGetter(String t, String s, int p) {
		nt.startClient(s, p);
		table = nt.getTable(t);
	}
	
	public String getCube() {
		NetworkTableEntry data = table.getEntry("Cube");
		System.out.println(data.getString("[]"));
		return data.getString("[]");
	}
	public String getCube(String key) {
		NetworkTableEntry data = table.getEntry(key);
		return data.getString("[]");
	}
	
	public String getSwitch() {
		NetworkTableEntry data = table.getEntry("Switch");
		return data.getString("[]");
	}
	public String getSwitch(String key) {
		NetworkTableEntry data = table.getEntry(key);
		return data.getString("[]");
	}
}
