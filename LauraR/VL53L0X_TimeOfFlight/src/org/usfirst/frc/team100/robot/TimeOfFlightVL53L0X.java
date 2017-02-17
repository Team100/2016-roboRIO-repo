package org.usfirst.frc.team100.robot;

import java.nio.ByteBuffer;
import java.util.TimerTask;

import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.SensorBase;
import edu.wpi.first.wpilibj.livewindow.LiveWindowSendable;
import edu.wpi.first.wpilibj.tables.ITable;

public class TimeOfFlightVL53L0X extends SensorBase implements LiveWindowSendable{
	
	public static final double kDefaultPeriod = .05;
	private static int instances = 0;
	private static final byte kAddress = 0x29;
	private static final byte VL53L0x_FAILURE_RESET = 0;
	protected I2C m_i2c;
	private ITable m_table;
	private byte data;
	private boolean isInit = false;
	private double m_period;
	private java.util.Timer m_pollLoop;
	private int m_deviceAddress;
	private static int ScalerValues[] = {0, 253, 127, 84};
	private int scaling = 0;
	private int ptp_offset = 0;
	
	public class VL53L0xMeasurement {
		public double m_distance;
		public int m_errCode;
		
		VL53L0xMeasurement () {
			m_distance = -1;
			m_errCode = 15;
		}
		
		VL53L0xMeasurement(VL53L0xMeasurement sensorMeasurement) {
			m_distance = sensorMeasurement.m_distance;
			m_errCode = sensorMeasurement.m_errCode;
		}
	}
	private VL53L0xMeasurement m_CurrentMeasurement;
	
	public TimeOfFlightVL53L0X(I2C.Port port) {
		this(port, kAddress, kDefaultPeriod);
	}
	
	public TimeOfFlightVL53L0X(I2C.Port port, int deviceAddress) {
		this(port, deviceAddress, kDefaultPeriod);
	}
	
	public TimeOfFlightVL53L0X(I2C.Port port, double period) {
		this(port, kAddress, period);
	}
	public TimeOfFlightVL53L0X(I2C.Port port, int deviceAddress, double period) {
		if (instances < 1) {
			m_i2c = new I2C(port, deviceAddress);
			// verify sensor is there
			byte id = getRegister(VL53L0xRegister.IDENTIFICATION_MODEL_ID);
			if (id == (byte)0xB4) {
				instances ++;
				m_deviceAddress = deviceAddress;
				m_CurrentMeasurement = new VL53L0xMeasurement();
				VL53L0xInit();
				VL6180xDefaultSettings();
				m_period = period;

				m_pollLoop = new java.util.Timer();
				m_pollLoop.schedule(new PollVL53L0xTask(this), 0L, (long) (m_period * 1000));
			} else {
				System.out.println("Can't Find the VL6180X");
			}
		} else {
			System.out.println("Can't have multiple VL6180X at the same address");
		}

	}
	
	/**
	 * Free the TimeOfFlightVL6180x object.
	 */
	public void free() {
		m_pollLoop.cancel();
		synchronized (this) {
			m_pollLoop = null;
			m_i2c = null;
		}
		if (m_table != null) {
			//m_table.removeTableListener(m_listener);
		}
	}	
	
	private void start_ranging(VL53L0XMode mode) {
	}
	
	private void stop_ranging() {
		
	}
	
	private VL53L0xMeasurement get_distance() {
		return new VL53L0XMeasurement();
	}
	
	@Override
	public void initTable(ITable subtable) {
		m_table = subtable;
		updateTable();
	}

	@Override
	public ITable getTable() {
		return m_table;
	}

	@Override
	public String getSmartDashboardType() {
		return "TimeOfFightSensor";
	}

	@Override
	public void updateTable() {
		if (m_table != null) {
			m_table.putNumber("Dist", readDistance());
		}
	}
	
	@Override
	public void startLiveWindowMode() {
		
	}

	@Override
	public void stopLiveWindowMode() {
		
	}
	
	public static String[] VL53L0xErrors = {
		"No Error",
		"VCSEL Continuity Test",
		"VCSEL Watchdog Test",
		"VCSEL Watchdog",
		"PLL1 Lock",
		"PLL2 Lock",
		"Early Convergence Estimate",
		"Max Convergence",
		"No Target Ignore",
		"not used",
		"not used",
		"Max Signal to Noise Ratio",
		"Raw Ranging Algo Underflow",
		"Raw Ranging Algo Overflow",
		"Ranging Algo Underflow",
		"Ranging Algo Overflow"
	};
	
	public enum VL53L0xMode {
		VL53L0X_GOOD_ACCURACY_MODE      ((int) 0), // Good Accuracy mode
		VL53L0X_BETTER_ACCURACY_MODE    ((int) 1), // Better Accuracy mode
		VL53L0X_BEST_ACCURACY_MODE      ((int) 2), // Best Accuracy mode
		VL53L0X_LONG_RANGE_MODE         ((int) 3), // Longe Range mode
		VL53L0X_HIGH_SPEED_MODE         ((int) 4);  // High Speed mode
		public final int value;

		private VL53L0xMode(int value) {
			this.value = value;
		}	}
	
	public enum VL53L0xRegister{
		SYSRANGE_START                              ((byte) 0x00),

		SYSTEM_THRESH_HIGH                          ((byte) 0x0C),
		SYSTEM_THRESH_LOW                           ((byte) 0x0E),

		SYSTEM_SEQUENCE_CONFIG                      ((byte) 0x01),
		SYSTEM_RANGE_CONFIG                         ((byte) 0x09),
		SYSTEM_INTERMEASUREMENT_PERIOD              ((byte) 0x04),

		SYSTEM_INTERRUPT_CONFIG_GPIO                ((byte) 0x0A),

		GPIO_HV_MUX_ACTIVE_HIGH                     ((byte) 0x84),

		SYSTEM_INTERRUPT_CLEAR                      ((byte) 0x0B),

		RESULT_INTERRUPT_STATUS                     ((byte) 0x13),
		RESULT_RANGE_STATUS                         ((byte) 0x14),

		RESULT_CORE_AMBIENT_WINDOW_EVENTS_RTN       ((byte) 0xBC),
		RESULT_CORE_RANGING_TOTAL_EVENTS_RTN        ((byte) 0xC0),
		RESULT_CORE_AMBIENT_WINDOW_EVENTS_REF       ((byte) 0xD0),
		RESULT_CORE_RANGING_TOTAL_EVENTS_REF        ((byte) 0xD4),
		RESULT_PEAK_SIGNAL_RATE_REF                 ((byte) 0xB6),

		ALGO_PART_TO_PART_RANGE_OFFSET_MM           ((byte) 0x28),

		I2C_SLAVE_DEVICE_ADDRESS                    ((byte) 0x8A),

		MSRC_CONFIG_CONTROL                         ((byte) 0x60),

		PRE_RANGE_CONFIG_MIN_SNR                    ((byte) 0x27),
		PRE_RANGE_CONFIG_VALID_PHASE_LOW            ((byte) 0x56),
		PRE_RANGE_CONFIG_VALID_PHASE_HIGH           ((byte) 0x57),
		PRE_RANGE_MIN_COUNT_RATE_RTN_LIMIT          ((byte) 0x64),

		FINAL_RANGE_CONFIG_MIN_SNR                  ((byte) 0x67),
		FINAL_RANGE_CONFIG_VALID_PHASE_LOW          ((byte) 0x47),
		FINAL_RANGE_CONFIG_VALID_PHASE_HIGH         ((byte) 0x48),
		FINAL_RANGE_CONFIG_MIN_COUNT_RATE_RTN_LIMIT ((byte) 0x44),

		PRE_RANGE_CONFIG_SIGMA_THRESH_HI            ((byte) 0x61),
		PRE_RANGE_CONFIG_SIGMA_THRESH_LO            ((byte) 0x62),

		PRE_RANGE_CONFIG_VCSEL_PERIOD               ((byte) 0x50),
		PRE_RANGE_CONFIG_TIMEOUT_MACROP_HI          ((byte) 0x51),
		PRE_RANGE_CONFIG_TIMEOUT_MACROP_LO          ((byte) 0x52),

		SYSTEM_HISTOGRAM_BIN                        ((byte) 0x81),
		HISTOGRAM_CONFIG_INITIAL_PHASE_SELECT       ((byte) 0x33),
		HISTOGRAM_CONFIG_READOUT_CTRL               ((byte) 0x55),

		FINAL_RANGE_CONFIG_VCSEL_PERIOD             ((byte) 0x70),
		FINAL_RANGE_CONFIG_TIMEOUT_MACROP_HI        ((byte) 0x71),
		FINAL_RANGE_CONFIG_TIMEOUT_MACROP_LO        ((byte) 0x72),
		CROSSTALK_COMPENSATION_PEAK_RATE_MCPS       ((byte) 0x20),

		MSRC_CONFIG_TIMEOUT_MACROP                  ((byte) 0x46),

		SOFT_RESET_GO2_SOFT_RESET_N                 ((byte) 0xBF),
		IDENTIFICATION_MODEL_ID                     ((byte) 0xC0),
		IDENTIFICATION_REVISION_ID                  ((byte) 0xC2),

		OSC_CALIBRATE_VAL                           ((byte) 0xF8),

		GLOBAL_CONFIG_VCSEL_WIDTH                   ((byte) 0x32),
		GLOBAL_CONFIG_SPAD_ENABLES_REF_0            ((byte) 0xB0),
		GLOBAL_CONFIG_SPAD_ENABLES_REF_1            ((byte) 0xB1),
		GLOBAL_CONFIG_SPAD_ENABLES_REF_2            ((byte) 0xB2),
		GLOBAL_CONFIG_SPAD_ENABLES_REF_3            ((byte) 0xB3),
		GLOBAL_CONFIG_SPAD_ENABLES_REF_4            ((byte) 0xB4),
		GLOBAL_CONFIG_SPAD_ENABLES_REF_5            ((byte) 0xB5),

		GLOBAL_CONFIG_REF_EN_START_SELECT           ((byte) 0xB6),
		DYNAMIC_SPAD_NUM_REQUESTED_REF_SPAD         ((byte) 0x4E),
		DYNAMIC_SPAD_REF_EN_START_OFFSET            ((byte) 0x4F),
		POWER_MANAGEMENT_GO1_POWER_FORCE            ((byte) 0x80),

		VHV_CONFIG_PAD_SCL_SDA__EXTSUP_HV           ((byte) 0x89),

		ALGO_PHASECAL_LIM                           ((byte) 0x30),
		ALGO_PHASECAL_CONFIG_TIMEOUT                ((byte) 0x30);

		public final byte value;
		
		private VL53L0xRegister(byte value) {
		      this.value = value;
		}
	}
	
	public boolean isInitialized(){
		return isInit;
	}
	
	private void VL53L0xInit(){
		
		
		
		int data; //for temp data storage

		data = getRegister(VL53L0xRegister.SYSTEM_FRESH_OUT_OF_RESET);
		
		if(data == 1){
			
			scaling = 1;
			
			setRegister(0x0207, 0x01);
			setRegister(0x0208, 0x01);
			setRegister(0x0096, 0x00);
			setRegister(0x0097, 0xfd);
			setRegister(0x00e3, 0x00);
			setRegister(0x00e4, 0x04);
			setRegister(0x00e5, 0x02);
			setRegister(0x00e6, 0x01);
			setRegister(0x00e7, 0x03);
			setRegister(0x00f5, 0x02);
			setRegister(0x00d9, 0x05);
			setRegister(0x00db, 0xce);
			setRegister(0x00dc, 0x03);
			setRegister(0x00dd, 0xf8);
			setRegister(0x009f, 0x00);
			setRegister(0x00a3, 0x3c);
			setRegister(0x00b7, 0x00);
			setRegister(0x00bb, 0x3c);
			setRegister(0x00b2, 0x09);
			setRegister(0x00ca, 0x09);  
			setRegister(0x0198, 0x01);
			setRegister(0x01b0, 0x17);
			setRegister(0x01ad, 0x00);
			setRegister(0x00ff, 0x05);
			setRegister(0x0100, 0x05);
			setRegister(0x0199, 0x05);
			setRegister(0x01a6, 0x1b);
			setRegister(0x01ac, 0x3e);
			setRegister(0x01a7, 0x1f);
			setRegister(0x0030, 0x00);
			
			setRegister(0x016,0x00);
			
		    int s = getRegister16bit(VL53L0xRegister.RANGE_SCALER);
		    ptp_offset = getRegister(VL53L0xRegister.SYSRANGE_PART_TO_PART_RANGE_OFFSET);

		    if      (s == ScalerValues[3]) { scaling = 3; }
		    else if (s == ScalerValues[2]) { scaling = 2; }
		    else                           { scaling = 1; }

		    // Adjust the part-to-part range offset value read earlier to account for
		    // existing scaling. If the sensor was already in 2x or 3x scaling mode,
		    // precision will be lost calculating the original (1x) offset, but this can
		    // be resolved by resetting the sensor and Arduino again.
		    ptp_offset *= scaling;
		}
		
		

	}


	
	private void VL6180xDefaultSettings(){

/*	  setRegister(VL53L0xRegister.SYSTEM_INTERRUPT_CONFIG_GPIO, (4 << 3)|(4) ); // Set GPIO1 high when sample complete


	  setRegister(VL53L0xRegister.SYSTEM_MODE_GPIO1, 0x10); // Set GPIO1 high when sample complete
	  setRegister(VL53L0xRegister.READOUT_AVERAGING_SAMPLE_PERIOD, 0x30); //Set Avg sample period
	  setRegister(VL53L0xRegister.SYSALS_ANALOGUE_GAIN, 0x46); // Set the ALS gain
	  setRegister(VL53L0xRegister.SYSRANGE_VHV_REPEAT_RATE, 0xFF); // Set auto calibration period (Max = 255)/(OFF = 0)
	  setRegister(VL53L0xRegister.SYSALS_INTEGRATION_PERIOD, 0x63); // Set ALS integration time to 100ms
	  setRegister(VL53L0xRegister.SYSRANGE_VHV_RECALIBRATE, 0x01); // perform a single temperature calibration

	  setRegister(VL53L0xRegister.SYSRANGE_INTERMEASUREMENT_PERIOD, 0x09); // Set default ranging inter-measurement period to 100ms
	  setRegister(VL53L0xRegister.SYSALS_INTERMEASUREMENT_PERIOD, 0x0A); // Set default ALS inter-measurement period to 100ms
	  setRegister(VL53L0xRegister.SYSTEM_INTERRUPT_CONFIG_GPIO, 0x24); // Configures interrupt on 'New Sample Ready threshold event' 

	  setRegister(VL53L0xRegister.SYSRANGE_MAX_CONVERGENCE_TIME, 0x32);
	  setRegister(VL53L0xRegister.SYSRANGE_RANGE_CHECK_ENABLES, 0x10 | 0x01);
	  setRegister16bit(VL53L0xRegister.SYSRANGE_EARLY_CONVERGENCE_ESTIMATE, 0x7B );
	  setRegister16bit(VL53L0xRegister.SYSALS_INTEGRATION_PERIOD, 0x64);

	  setRegister(VL53L0xRegister.READOUT_AVERAGING_SAMPLE_PERIOD,0x30);
	  setRegister(VL53L0xRegister.SYSALS_ANALOGUE_GAIN,0x40);
	  setRegister(VL53L0xRegister.FIRMWARE_RESULT_SCALER,0x01);
	  
	  setScaling((byte)(3));*/
	}

	public void setRegister(VL53L0xRegister reg, int data){
	  setRegister((int)reg.value,data);
	}
	
	public void setRegister(int reg, int data){
	  ByteBuffer temp = ByteBuffer.allocateDirect(2);
	  temp.put((byte) (reg & 0xFF));
	  temp.put((byte) (data & 0xFF));
/*	  System.out.println("setRegister: reg: 0x"+Integer.toHexString(reg)+", "
	  		+ "data: 0x"+Integer.toHexString(data));*/
	  m_i2c.writeBulk(temp, 2);
	}

	public void setRegister16bit(VL53L0xRegister reg, int data){
		setRegister16bit(reg.value, data);
	}
	
	public void setRegister16bit(int reg, int data){
		ByteBuffer raw = ByteBuffer.allocateDirect(3);
		raw.put((byte) ((reg & 0xFF)));
		raw.put((byte) ((data >> 8) & 0xFF));
		raw.put(((byte) ((data & 0xFF))));
		m_i2c.writeBulk(raw, 3);	
	
	}
	
	private byte getRegister(VL53L0xRegister reg) {
		return getRegister((int)reg.value);
	}
	
	private byte getRegister(int registerAddr){
		ByteBuffer index = ByteBuffer.allocateDirect(1);
		ByteBuffer rawData = ByteBuffer.allocateDirect(1);
		index.put((byte) (registerAddr & 0xFF));
		boolean status = m_i2c.transaction(index, 1, rawData, 1);
		data = rawData.get();
/*		System.out.println("getRegister:  status: " + status + 
				" address: 0x" + Integer.toHexString(registerAddr) +
				" rawData: 0x"+ Integer.toHexString((int)data & 0xFF));*/
		
	  return data;
	}
	
	private int getRegister16bit(VL53L0xRegister registerAddr){
		return getRegister16bit((int)registerAddr.value);
	}

	private int getRegister16bit(int registerAddr)
	{

		ByteBuffer rawData = ByteBuffer.allocateDirect(1);
		ByteBuffer index = ByteBuffer.allocateDirect(2);
		index.put((byte) (registerAddr & 0xFF));
		boolean status = m_i2c.transaction(index, 2, rawData, 2);
		int hi = (int) rawData.get() & 0xFF;
		int lo = (int) rawData.get() & 0xFF;
		int temp = hi << 8 + lo;
/*		System.out.println("getRegister16bit:  status: " + status + 
				" address: 0x" + Integer.toHexString(registerAddr) +
				" rawData: 0x"+ Integer.toHexString(temp));*/
		return temp;

	}
	
	private void startDistance(){
		setRegister(VL53L0xRegister.SYSRANGE_START, 0x01);
		isInit = true;
	}
	
	private boolean isFinishedMeasure(){
		byte status;
/*		status = getRegister(VL53L0xRegister.RESULT_INTERRUPT_STATUS_GPIO);*/
		if((status & 0x07)==0x04){
			return true;
		}
		return false;
	}
	
	private double readDistance(){
/*		// read result
		int val = (int) getRegister(VL53L0xRegister.RESULT_RANGE_VAL) & 0xFF; 
		// read RESULT_RANGE_STATUS register to get error code (bits 7:4)
		int err = ((int) getRegister(VL53L0xRegister.RESULT_RANGE_STATUS) >> 4) & 0xF;
		// clear interrupt
		setRegister(VL53L0xRegister.SYSTEM_INTERRUPT_CLEAR, 0x05);*/
/*		System.out.println("readDistance  raw: 0x" + Integer.toHexString(val) +
				"Converted : " + (double) val + "Error Code: " + VL6180xErrors[err]);*/
		synchronized (m_CurrentMeasurement) {
			if(err != 0){
				m_CurrentMeasurement.m_distance = (double) -1.0;
				m_CurrentMeasurement.m_errCode = err;
			}else{
				m_CurrentMeasurement.m_distance = (double) val/3.0;
				m_CurrentMeasurement.m_errCode = err;
			}
		}
		return ((double) val);
	}
	
	public VL53L0xMeasurement getMeasurement() {
		VL53L0xMeasurement temp;
		synchronized (m_CurrentMeasurement) {
			temp = new VL53L0xMeasurement(m_CurrentMeasurement);		
		}
		return temp;
	}
	
	private class PollVL53L0xTask extends TimerTask {
		private TimeOfFlightVL53L0X m_sensor;

		public PollVL53L0xTask(TimeOfFlightVL53L0X sensor) {
			if (sensor == null) {
				throw new NullPointerException("Given TimeOfFlightVL6180x was null");
			}
			m_sensor = sensor;
		}

		@Override
		public void run() {
			if(m_sensor.isInitialized()){
				if(m_sensor.isFinishedMeasure()){
					m_sensor.readDistance();
					m_sensor.updateTable();
					m_sensor.startDistance();
				}
			}else{
				m_sensor.startDistance();
			}
		}
	}
	
	public void setScaling(byte new_scaling){
/*	  int DefaultCrosstalkValidHeight = 20; // default value of SYSRANGE__CROSSTALK_VALID_HEIGHT

	  // do nothing if scaling value is invalid
	  if (new_scaling < 0 || new_scaling > 4) { return; }

	  scaling = new_scaling;
	  setRegister16bit(VL53L0xRegister.RANGE_SCALER, ScalerValues[scaling]);

	  // apply scaling on part-to-part offset
	  setRegister(VL53L0xRegister.SYSRANGE_PART_TO_PART_RANGE_OFFSET, ptp_offset / scaling);

	  // apply scaling on CrossTalkValidHeight
	  setRegister(VL53L0xRegister.SYSRANGE_CROSSTALK_VALID_HEIGHT, DefaultCrosstalkValidHeight / scaling);

	  // This function does not apply scaling to RANGE_IGNORE_VALID_HEIGHT.

	  // enable early convergence estimate only at 1x scaling
	  byte rce = getRegister(VL53L0xRegister.SYSRANGE_RANGE_CHECK_ENABLES);
	  setRegister(VL53L0xRegister.SYSRANGE_RANGE_CHECK_ENABLES, (rce & 0xFE) | scaling);*/
	}
}
