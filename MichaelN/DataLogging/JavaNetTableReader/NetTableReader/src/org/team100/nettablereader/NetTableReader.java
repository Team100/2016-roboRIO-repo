package org.team100.nettablereader;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import edu.wpi.first.wpilibj.networktables.NetworkTable;
import edu.wpi.first.wpilibj.networktables.NetworkTablesJNI;
import edu.wpi.first.wpilibj.tables.ITable;




public class NetTableReader implements NetworkTablesJNI.EntryListenerFunction, Runnable{
	public static final char PATH_SEPARATOR = '/';
	private static final String sDefaultIpAddress = "roborio-100-frc.local";
	private static final String sDefaultTableName = "SmartDashboard";
	private static final String sDefaultFileName = "fileCapture.txt";
	
	private String mIpAddress;
	private String mTableName;	
	private String mFileName;
	private String mPath;
	private int mPathLen;
	private volatile boolean threadRunning = false;
	
	long mStartTime = System.currentTimeMillis();
	private int mUid;
	
	BufferedWriter output = null;

	public NetTableReader(String pIpAddress, String pTableName, String pFileName) {
		mIpAddress = pIpAddress;
		mTableName = pTableName;
		mFileName = pFileName;
		if (mTableName.length() == 0) {
			mPath = Character.toString(PATH_SEPARATOR);
		} else {
			mPath = PATH_SEPARATOR + mTableName + PATH_SEPARATOR;
		}
		mPathLen = mPath.length();

	}

	public NetTableReader() {
		this(sDefaultIpAddress, sDefaultTableName, sDefaultFileName);
	}


	@Override
	public void apply(int pUid, String pKey, Object pValue, int pFlags) {
		String line = "";
		pKey = pKey.substring(mPathLen);
		long now = System.currentTimeMillis();
		
		if ((pValue instanceof Boolean) || (pValue instanceof Double)) {
			line = "\"" + (now - mStartTime) + "\",\"" + pKey + "\",\"" + pValue.toString() + "\"\n";	 
		} 
		else if (pValue instanceof String) {
			//
		}
		else  if (pValue instanceof byte[]) {
			//
		}
		else if (pValue instanceof boolean[]) {
			boolean[] a = (boolean[]) pValue;
			for (int i = 0; i < a.length; i ++) {
				Boolean val = new Boolean(a[i]);
				line += "\"" + (now - mStartTime) + "\",\"" + pKey + "[" + i + "]" + "\",\"" + val.toString() + "\"\n";
			}
			
		}
		else if (pValue instanceof double[]) {
			double[] a = (double[]) pValue;
			for (int i = 0; i < a.length; i ++) {
				Double val = new Double(a[i]);
				line += "\"" + (now - mStartTime) + "\",\"" + pKey + "[" + i + "]" + "\",\"" + val.toString() + "\"\n";
			}
			
		}
		else if (pValue instanceof String[]) {
//			String[] a = (String[]) pValue;
//			for (int i = 0; i < a.length; i ++) {
//				String val = a[i];
//				System.out.println("\"" + (now - mStartTime) + "\",\"" + pKey + "[" + i + "]" + "\",\"" + val.toString() + "\"");	
//			}			
		}
		
		if (line.length() != 0) {
			if (output != null) {
				try {
					output.write(line);
				} catch (IOException ex) {
					//
				}
			}
			System.out.print(line);
		}
	}
	
	private void setupTable() {		
		NetworkTable.setIPAddress(mIpAddress); 
		NetworkTable.setClientMode();
		NetworkTable.initialize();
		mUid = NetworkTablesJNI.addEntryListener(mPath, this, ITable.NOTIFY_NEW | ITable.NOTIFY_UPDATE| ITable.NOTIFY_IMMEDIATE);
	}


	
	@Override
	public void run() {
		threadRunning = true;
		setupTable();
		if (mFileName.length() != 0) {
			try {
				File file = new File(mFileName);
				output = new BufferedWriter(new FileWriter(file));
				output.write("Time (ms),Name,Value\n");
			} catch (IOException ex) {
				output = null;
			}
		}		
		while(threadRunning) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException ex) {
				threadRunning = false;
			}
		}
		if (output != null) {
			try {
				output.close();
			} catch (IOException ex) {
				
			}
		}
	}
	


	
	public static void main(String[] args) {
		int numargs = args.length;
		String ip = sDefaultIpAddress;
		String tname = sDefaultTableName;
		String fname = sDefaultFileName;
		if (numargs > 0) {
			ip = args[0];
		}
		if (numargs > 1) {
			tname = args [1];
		}
		if (numargs > 2) {
			fname = args [2];
		}
 
		
		NetTableReader reader = new NetTableReader(ip, tname, fname);
		Thread t = new Thread(reader);
		t.start();
		System.out.println("Press the Enter Key to exit");
		try {
			char tmp = (char) System.in.read();
		} catch (IOException ex) {
		} finally {
			reader.threadRunning = false;
		}
		
		try {
			t.join();
		} catch (InterruptedException ex) {
			
		}
		
		NetworkTable.shutdown();
		System.exit(0);
	}
}

