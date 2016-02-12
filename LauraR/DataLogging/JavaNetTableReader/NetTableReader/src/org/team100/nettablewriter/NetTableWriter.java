package org.team100.nettablewriter;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.team100.nettablereader.NetTableReader;

import edu.wpi.first.wpilibj.networktables.NetworkTable;
import edu.wpi.first.wpilibj.tables.IRemote;
import edu.wpi.first.wpilibj.tables.IRemoteConnectionListener;
import edu.wpi.first.wpilibj.tables.ITable;
import edu.wpi.first.wpilibj.tables.ITableListener;

public class NetTableWriter {
	String mIpAddress = "10.1.0.2";
	String mTableName = "SmartDashboard";
	NetworkTable mTable;
	boolean mIsConnected = false;
	ITableListener mTableListener;
	ITableListener mSubTableListener;
	IRemoteConnectionListener mConnectionListener;
	FakePotentiometer mArmPotentiometer = new FakePotentiometer();

	/**
	 * @param pIpAddress
	 * @param pTableName
	 */
	public NetTableWriter(String pIpAddress, String pTableName) {
		this.mIpAddress = pIpAddress;
		this.mTableName = pTableName;
	}

	/**
	 * 
	 */
	public NetTableWriter() {
	}

	public static void main(String[] args) {
		System.out.println("I will populate the world!");
		new NetTableWriter("192.168.1.111", "SmartDashboard").run();
		System.exit(0);

	}
	
	private class FakePotentiometer {
		double mValue = 0;
		double mIncrement = 0.05;
		boolean mDirectionPositive = true;
		final double mMax;
		final double mMin;
		
		
		/**
		 * 
		 */
		public FakePotentiometer() {
			mMax = 1.0;
			mMin = 0.0;
		}
		/**
		 * @param pValue
		 * @param pIncrement
		 * @param pDirectionPositive
		 * @param pMax
		 * @param pMin
		 */
		public FakePotentiometer(double pValue, double pIncrement,
				boolean pDirectionPositive, double pMax, double pMin) {
			this.mValue = pValue;
			this.mIncrement = Math.abs(pIncrement);
			this.mDirectionPositive = pDirectionPositive;
			this.mMax = pMax;
			this.mMin = pMin;
		}
		
		public void update() {
			if (mDirectionPositive) {
				if (mValue <= (mMax - mIncrement)) {
					mValue += mIncrement;
				} else {
					mValue = mValue - mIncrement;
					mDirectionPositive = false;
				}
			} else {
				if (mValue >= (mMin + mIncrement)) {
					mValue -= mIncrement;
				} else {
					mValue = mValue + mIncrement;
					mDirectionPositive = true;
				}			
			}
		}
		/**
		 * @return the value
		 */
		public double getValue() {
			return this.mValue;
		}
		/**
		 * @param pValue the value to set
		 */
		public void setValue(double pValue) {
			this.mValue = pValue;
		}
		/**
		 * @return the increment
		 */
		public double getIncrement() {
			return this.mIncrement;
		}
		/**
		 * @param pIncrement the increment to set
		 */
		public void setIncrement(double pIncrement) {
			this.mIncrement = pIncrement;
		}
		/**
		 * @return the directionPositive
		 */
		public boolean isDirectionPositive() {
			return this.mDirectionPositive;
		}
		/**
		 * @param pDirectionPositive the directionPositive to set
		 */
		public void setDirectionPositive(boolean pDirectionPositive) {
			this.mDirectionPositive = pDirectionPositive;
		}
		/**
		 * @return the max
		 */
		public double getMax() {
			return this.mMax;
		}
		/**
		 * @return the min
		 */
		public double getMin() {
			return this.mMin;
		}
		
	}

	private class NetSubTableListener implements ITableListener {

		@Override
		public void valueChanged(ITable pSource, String pKey, Object pValue,
				boolean pIsNew) {
			System.out.println("SUBTABLE LISTENER: " + pSource + " " + pKey
					+ " " + pValue + " " + pValue.getClass() + " " + pIsNew);
		}

	}

	private void setupTable() {
		//NetworkTable.setClientMode();
		NetworkTable.setServerMode();
		NetworkTable.setIPAddress(mIpAddress);
		mTable = NetworkTable.getTable(mTableName);
		System.out.println(mTable.toString());
		mTableListener = new NetTableListener();
		mTable.addTableListener(mTableListener, true); // immediate notification
														// // of all values
		mConnectionListener = new NetConnectionListener();
		mTable.addConnectionListener(mConnectionListener, true);
		mSubTableListener = new NetSubTableListener();
		mTable.addSubTableListener(mSubTableListener);

	}

	private void run() {
		setupTable();
		populateTable();
		while (true) {
			try {
				Thread.sleep(500);
				mArmPotentiometer.update();
				mTable.putNumber("armPot", mArmPotentiometer.getValue());			
			} catch (InterruptedException ex) {
				Logger.getLogger(NetTableReader.class.getName()).log(Level.SEVERE,
						null, ex);
	
				break;
			} 
		}
		mTable.removeConnectionListener(mConnectionListener);
		mTable.removeTableListener(mTableListener);
		mTable.removeTableListener(mSubTableListener);
		mConnectionListener = null;
		mTableListener = null;
		mSubTableListener = null;
		mTable = null;
		System.out.println("Still Connected?: " + mIsConnected);
	}

	private void populateTable() {
		mTable.putString("hello", "Hello World!");
		mTable.putString("HoldIt/name", "HoldIt");
		mTable.putBoolean("HoldIt/running", false);
		mTable.putBoolean("HoldIt/isParented", false);
		mTable.putString("HoldIt/~TYPE~", "Command");
		mTable.putBoolean("HoldIt/subtab/myVar", true);
		mTable.putNumber("armPot", mArmPotentiometer.getValue());

	}

	private class NetTableListener implements ITableListener {

		@Override
		public void valueChanged(ITable pSource, String pKey, Object pValue,
				boolean pIsNew) {
			System.out.println("TABLE LISTENER: " + pSource + " " + pKey + " "
					+ pValue + " " + pValue.getClass() + " " + pIsNew);
		}
	}

	private class NetConnectionListener implements IRemoteConnectionListener {

		@Override
		public void connected(IRemote pRemote) {
			System.out.println("connected");
			mIsConnected = true;
			populateTable();

		}

		@Override
		public void disconnected(IRemote pRemote) {
			System.out.println("disconnected");
			mIsConnected = false;

		}
	}
}
