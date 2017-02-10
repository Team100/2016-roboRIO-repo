package org.usfirst.frc.team100.TOFRangeFinder;

import java.nio.ByteBuffer;
import java.util.TimerTask;

import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.SensorBase;
import edu.wpi.first.wpilibj.livewindow.LiveWindowSendable;
import edu.wpi.first.wpilibj.tables.ITable;

public class TimeOfFlightVL6180x extends SensorBase implements LiveWindowSendable{
	public static final double kDefaultPeriod = .05;
	private static int instances = 0;
	private static final byte kAddress = 0x29;
	private static final byte VL6180x_FAILURE_RESET = 0;
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
	
	public class VL6180xMeasurement {
		public double m_distance;
		public int m_errCode;
		
		VL6180xMeasurement () {
			m_distance = 0xFF;
			m_errCode = 15;
		}
		
		VL6180xMeasurement(VL6180xMeasurement sensorMeasurement) {
			m_distance = sensorMeasurement.m_distance;
			m_errCode = sensorMeasurement.m_errCode;
		}
	}
	private VL6180xMeasurement m_CurrentMeasurement;
	
	public TimeOfFlightVL6180x(I2C.Port port) {
		this(port, kAddress, kDefaultPeriod);
	}
	
	public TimeOfFlightVL6180x(I2C.Port port, int deviceAddress) {
		this(port, deviceAddress, kDefaultPeriod);
	}
	
	public TimeOfFlightVL6180x(I2C.Port port, double period) {
		this(port, kAddress, period);
	}
	public TimeOfFlightVL6180x(I2C.Port port, int deviceAddress, double period) {
		if (instances < 1) {
			m_i2c = new I2C(port, deviceAddress);
			// verify sensor is there
			byte id = getRegister(VL6180xRegister.IDENTIFICATION_MODEL_ID);
			if (id == (byte)0xB4) {
				instances ++;
				m_deviceAddress = deviceAddress;
				m_CurrentMeasurement = new VL6180xMeasurement();
				VL6180xInit();
				VL6180xDefaultSettings();
				m_period = period;

				m_pollLoop = new java.util.Timer();
				m_pollLoop.schedule(new PollVL6180xTask(this), 0L, (long) (m_period * 1000));
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
	
	public static String[] VL6180xErrors = {
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
	
	public enum VL6180xRegister{
		IDENTIFICATION_MODEL_ID				((int)0x0000),
		IDENTIFICATION_MODEL_REV_MAJOR		((int)0x0001),
		IDENTIFICATION_MODEL_REV_MINOR		((int)0x0002),
		IDENTIFICATION_MODULE_REV_MAJOR		((int)0x0003),
		IDENTIFICATION_MODULE_REV_MINOR		((int)0x0004),
		IDENTIFICATION_DATE					((int)0x0006),
		IDENTIFICATION_TIME					((int)0x0008),
		
		SYSTEM_MODE_GPIO0					((int)0x0010),
		SYSTEM_MODE_GPIO1					((int)0x0011),
		SYSTEM_HISTORY_CTRL					((int)0x0012),
		SYSTEM_INTERRUPT_CONFIG_GPIO		((int)0x0014),
		SYSTEM_INTERRUPT_CLEAR				((int)0x0015),
		SYSTEM_FRESH_OUT_OF_RESET			((int)0x0016),
		SYSTEM_GROUPED_PARAMETER_HOLD		((int)0x0017),
		
		SYSRANGE_START						((int)0x0018),
		SYSRANGE_THRESH_HIGH				((int)0x0019),
		SYSRANGE_THRESH_LOW					((int)0x001A),
		SYSRANGE_INTERMEASUREMENT_PERIOD	((int)0x001B),
		SYSRANGE_MAX_CONVERGENCE_TIME		((int)0x001C),
		SYSRANGE_CROSSTALK_COMPENSATION_RATE((int)0x001E),
		SYSRANGE_CROSSTALK_VALID_HEIGHT		((int)0x0021),
		SYSRANGE_EARLY_CONVERGENCE_ESTIMATE	((int)0x0022),
		SYSRANGE_PART_TO_PART_RANGE_OFFSET	((int)0x0024),
		SYSRANGE_RANGE_IGNORE_VALID_HEIGHT	((int)0x0025),
		SYSRANGE_RANGE_IGNORE_THRESHOLD		((int)0x0026),
		SYSRANGE_MAX_AMBIENT_LEVEL_MULT		((int)0x002C),
		SYSRANGE_RANGE_CHECK_ENABLES		((int)0x002D),
		SYSRANGE_VHV_RECALIBRATE			((int)0x002E),
		SYSRANGE_VHV_REPEAT_RATE			((int)0x0031),
		
		SYSALS_START						((int)0x0038),
		SYSALS_THRESH_HIGH					((int)0x003A),
		SYSALS_THRESH_LOW					((int)0x003C),
		SYSALS_INTERMEASUREMENT_PERIOD		((int)0x003E),
		SYSALS_ANALOGUE_GAIN				((int)0x003F),
		SYSALS_INTEGRATION_PERIOD			((int)0x0040),
		
		RESULT_RANGE_STATUS                 ((int)0x004D),
		RESULT_ALS_STATUS                   ((int)0x004E),
		RESULT_INTERRUPT_STATUS_GPIO        ((int)0x004F),
		RESULT_ALS_VAL                      ((int)0x0050),
		RESULT_HISTORY_BUFFER               ((int)0x0052),
		RESULT_RANGE_VAL                    ((int)0x0062),
		RESULT_RANGE_RAW                    ((int)0x0064),
		RESULT_RANGE_RETURN_RATE            ((int)0x0066),
		RESULT_RANGE_REFERENCE_RATE         ((int)0x0068),
		RESULT_RANGE_RETURN_SIGNAL_COUNT    ((int)0x006C),
		RESULT_RANGE_REFERENCE_SIGNAL_COUNT ((int)0x0070),
		RESULT_RANGE_RETURN_AMB_COUNT       ((int)0x0074),
		RESULT_RANGE_REFERENCE_AMB_COUNT    ((int)0x0078),
		RESULT_RANGE_RETURN_CONV_TIME       ((int)0x007C),
		RESULT_RANGE_REFERENCE_CONV_TIME    ((int)0x0080),
		
		RANGE_SCALER						((int)0x0096),

		READOUT_AVERAGING_SAMPLE_PERIOD     ((int)0x010A),
		FIRMWARE_BOOTUP                     ((int)0x0119),
		FIRMWARE_RESULT_SCALER              ((int)0x0120),
		I2C_SLAVE_DEVICE_ADDRESS            ((int)0x0212),
		INTERLEAVED_MODE_ENABLE             ((int)0x02A3);
		
		public final int value;
		
		private VL6180xRegister(int value) {
		      this.value = value;
		}
	}
	
	public boolean isInitialized(){
		return isInit;
	}
	
	private void VL6180xInit(){
		
		
		
		int data; //for temp data storage

		data = getRegister(VL6180xRegister.SYSTEM_FRESH_OUT_OF_RESET);
		
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
			
		    int s = getRegister16bit(VL6180xRegister.RANGE_SCALER);
		    ptp_offset = getRegister(VL6180xRegister.SYSRANGE_PART_TO_PART_RANGE_OFFSET);

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

	  setRegister(VL6180xRegister.SYSTEM_INTERRUPT_CONFIG_GPIO, (4 << 3)|(4) ); // Set GPIO1 high when sample complete


	  setRegister(VL6180xRegister.SYSTEM_MODE_GPIO1, 0x10); // Set GPIO1 high when sample complete
	  setRegister(VL6180xRegister.READOUT_AVERAGING_SAMPLE_PERIOD, 0x30); //Set Avg sample period
	  setRegister(VL6180xRegister.SYSALS_ANALOGUE_GAIN, 0x46); // Set the ALS gain
	  setRegister(VL6180xRegister.SYSRANGE_VHV_REPEAT_RATE, 0xFF); // Set auto calibration period (Max = 255)/(OFF = 0)
	  setRegister(VL6180xRegister.SYSALS_INTEGRATION_PERIOD, 0x63); // Set ALS integration time to 100ms
	  setRegister(VL6180xRegister.SYSRANGE_VHV_RECALIBRATE, 0x01); // perform a single temperature calibration

	  setRegister(VL6180xRegister.SYSRANGE_INTERMEASUREMENT_PERIOD, 0x09); // Set default ranging inter-measurement period to 100ms
	  setRegister(VL6180xRegister.SYSALS_INTERMEASUREMENT_PERIOD, 0x0A); // Set default ALS inter-measurement period to 100ms
	  setRegister(VL6180xRegister.SYSTEM_INTERRUPT_CONFIG_GPIO, 0x24); // Configures interrupt on 'New Sample Ready threshold event' 

	  setRegister(VL6180xRegister.SYSRANGE_MAX_CONVERGENCE_TIME, 0x32);
	  setRegister(VL6180xRegister.SYSRANGE_RANGE_CHECK_ENABLES, 0x10 | 0x01);
	  setRegister16bit(VL6180xRegister.SYSRANGE_EARLY_CONVERGENCE_ESTIMATE, 0x7B );
	  setRegister16bit(VL6180xRegister.SYSALS_INTEGRATION_PERIOD, 0x64);

	  setRegister(VL6180xRegister.READOUT_AVERAGING_SAMPLE_PERIOD,0x30);
	  setRegister(VL6180xRegister.SYSALS_ANALOGUE_GAIN,0x40);
	  setRegister(VL6180xRegister.FIRMWARE_RESULT_SCALER,0x01);
	  
	  setScaling((byte)(3));
	}

	public void setRegister(VL6180xRegister reg, int data){
	  setRegister(reg.value,data);
	}
	
	public void setRegister(int reg, int data){
	  ByteBuffer temp = ByteBuffer.allocateDirect(3);
	  temp.put((byte) ((reg >> 8) & 0xFF));
	  temp.put((byte) (reg & 0xFF));
	  temp.put((byte) (data & 0xFF));
/*	  System.out.println("setRegister: reg: 0x"+Integer.toHexString(reg)+", "
	  		+ "data: 0x"+Integer.toHexString(data));*/
	  m_i2c.writeBulk(temp, 3);
	}

	public void setRegister16bit(VL6180xRegister reg, int data){
		setRegister16bit(reg.value, data);
	}
	
	public void setRegister16bit(int reg, int data){
		ByteBuffer raw = ByteBuffer.allocateDirect(4);
		raw.put((byte) ((reg >> 8) & 0xFF));
		raw.put((byte) ((reg & 0xFF)));
		raw.put((byte) ((data >> 8) & 0xFF));
		raw.put(((byte) ((data & 0xFF))));
		m_i2c.writeBulk(raw, 4);	
	
	}
	
	private byte getRegister(VL6180xRegister reg) {
		return getRegister(reg.value);
	}
	
	private byte getRegister(int registerAddr){
		ByteBuffer index = ByteBuffer.allocateDirect(2);
		ByteBuffer rawData = ByteBuffer.allocateDirect(1);
		index.put((byte) ((registerAddr >> 8) & 0xFF));
		index.put((byte) (registerAddr & 0xFF));
		boolean status = m_i2c.transaction(index, 2, rawData, 1);
		data = rawData.get();
/*		System.out.println("getRegister:  status: " + status + 
				" address: 0x" + Integer.toHexString(registerAddr) +
				" rawData: 0x"+ Integer.toHexString((int)data & 0xFF));*/
		
	  return data;
	}
	
	private int getRegister16bit(VL6180xRegister registerAddr){
		return getRegister16bit(registerAddr.value);
	}

	private int getRegister16bit(int registerAddr)
	{

		ByteBuffer rawData = ByteBuffer.allocateDirect(2);
		ByteBuffer index = ByteBuffer.allocateDirect(2);
		index.put((byte)((registerAddr >> 8) & 0xFF));
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
		setRegister(VL6180xRegister.SYSRANGE_START, 0x01);
		isInit = true;
	}
	
	private boolean isFinishedMeasure(){
		byte status;
		status = getRegister(VL6180xRegister.RESULT_INTERRUPT_STATUS_GPIO);
		if((status & 0x07)==0x04){
			return true;
		}
		return false;
	}
	
	private double readDistance(){
		// read result
		int val = (int) getRegister(VL6180xRegister.RESULT_RANGE_VAL) & 0xFF; 
		// read RESULT_RANGE_STATUS register to get error code (bits 7:4)
		int err = ((int) getRegister(VL6180xRegister.RESULT_RANGE_STATUS) >> 4) & 0xF;
		// clear interrupt
		setRegister(VL6180xRegister.SYSTEM_INTERRUPT_CLEAR, 0x05);
/*		System.out.println("readDistance  raw: 0x" + Integer.toHexString(val) +
				"Converted : " + (double) val + "Error Code: " + VL6180xErrors[err]);*/
		synchronized (m_CurrentMeasurement) {
			m_CurrentMeasurement.m_distance = (double) val;
			m_CurrentMeasurement.m_errCode = err;
		}
		return ((double) val);
	}
	
	public VL6180xMeasurement getMeasurement() {
		VL6180xMeasurement temp;
		synchronized (m_CurrentMeasurement) {
			temp = new VL6180xMeasurement(m_CurrentMeasurement);		
		}
		return temp;
	}
	
	private class PollVL6180xTask extends TimerTask {
		private TimeOfFlightVL6180x m_sensor;

		public PollVL6180xTask(TimeOfFlightVL6180x sensor) {
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
	  int DefaultCrosstalkValidHeight = 20; // default value of SYSRANGE__CROSSTALK_VALID_HEIGHT

	  // do nothing if scaling value is invalid
	  if (new_scaling < 0 || new_scaling > 4) { return; }

	  scaling = new_scaling;
	  setRegister16bit(VL6180xRegister.RANGE_SCALER, ScalerValues[scaling]);

	  // apply scaling on part-to-part offset
	  setRegister(VL6180xRegister.SYSRANGE_PART_TO_PART_RANGE_OFFSET, ptp_offset / scaling);

	  // apply scaling on CrossTalkValidHeight
	  setRegister(VL6180xRegister.SYSRANGE_CROSSTALK_VALID_HEIGHT, DefaultCrosstalkValidHeight / scaling);

	  // This function does not apply scaling to RANGE_IGNORE_VALID_HEIGHT.

	  // enable early convergence estimate only at 1x scaling
	  byte rce = getRegister(VL6180xRegister.SYSRANGE_RANGE_CHECK_ENABLES);
	  setRegister(VL6180xRegister.SYSRANGE_RANGE_CHECK_ENABLES, (rce & 0xFE) | scaling);
	}
}
