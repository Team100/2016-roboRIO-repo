package org.usfirst.frc.team100.robot;

import java.nio.ByteBuffer;
import java.util.TimerTask;

import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.SensorBase;
import edu.wpi.first.wpilibj.livewindow.LiveWindowSendable;
import edu.wpi.first.wpilibj.tables.ITable;

public class TCS43725RBGSensor extends SensorBase implements LiveWindowSendable{
	
	private static final byte kAddress = 0x29;
	public static final double kDefaultPeriod = .05;
	private static int instances = 0;
	protected I2C m_i2c;
	private int m_deviceAddress;
	private byte data;
	private boolean tcs34725Initialised;
	private tcs34725Gain tcs34725Gain;
	private tcs34725IntegrationTime tcs34725IntegrationTime; 
	private ITable m_table;
	private double m_period;
	private java.util.Timer m_pollLoop;
	private TCS34725Measurement m_CurrentMeasurement;
	
	public enum TCS34725Register{
		ADDRESS    		((byte)(0x29)),
	
		COMMAND_BIT     ((byte)(0x80)),
	
		ENABLE          ((byte)(0x00)),
		ENABLE_AIEN     ((byte)(0x10)),   /* RGBC Interrupt Enable */
		ENABLE_WEN      ((byte)(0x08)),   /* Wait enable - Writing 1 activates the wait timer */
		ENABLE_AEN      ((byte)(0x02)),   /* RGBC Enable - Writing 1 actives the ADC, 0 disables it */
		ENABLE_PON      ((byte)(0x01)),   /* Power on - Writing 1 activates the internal oscillator, 0 disables it */
		ATIME           ((byte)(0x01)),   /* Integration time */
		WTIME           ((byte)(0x03)),   /* Wait time (if TCS34725_ENABLE_WEN is asserted) */
		WTIME_2_4MS     ((byte)(0xFF)),   /* WLONG0 = 2.4ms   WLONG1 = 0.029s */
		WTIME_204MS     ((byte)(0xAB)),   /* WLONG0 = 204ms   WLONG1 = 2.45s  */
		WTIME_614MS     ((byte)(0x00)),   /* WLONG0 = 614ms   WLONG1 = 7.4s   */
		AILTL           ((byte)(0x04)),   /* Clear channel lower interrupt threshold */
		AILTH           ((byte)(0x05)),
		AIHTL           ((byte)(0x06)),   /* Clear channel upper interrupt threshold */
		AIHTH           ((byte)(0x07)),
		PERS            ((byte)(0x0C)),    /* Persistence register - basic SW filtering mechanism for interrupts */
		PERS_NONE       ((byte)(0b0000)),  /* Every RGBC cycle generates an interrupt                                */
		PERS_1_CYCLE    ((byte)(0b0001)),  /* 1 clean channel value outside threshold range generates an interrupt   */
		PERS_2_CYCLE    ((byte)(0b0010)), /* 2 clean channel values outside threshold range generates an interrupt  */
		PERS_3_CYCLE    ((byte)(0b0011)), /* 3 clean channel values outside threshold range generates an interrupt  */
		PERS_5_CYCLE    ((byte)(0b0100)), /* 5 clean channel values outside threshold range generates an interrupt  */
		PERS_10_CYCLE   ((byte)(0b0101)), /* 10 clean channel values outside threshold range generates an interrupt */
		PERS_15_CYCLE   ((byte)(0b0110)), /* 15 clean channel values outside threshold range generates an interrupt */
		PERS_20_CYCLE   ((byte)(0b0111)), /* 20 clean channel values outside threshold range generates an interrupt */
		PERS_25_CYCLE   ((byte)(0b1000)), /* 25 clean channel values outside threshold range generates an interrupt */
		PERS_30_CYCLE   ((byte)(0b1001)), /* 30 clean channel values outside threshold range generates an interrupt */
		PERS_35_CYCLE   ((byte)(0b1010)), /* 35 clean channel values outside threshold range generates an interrupt */
		PERS_40_CYCLE   ((byte)(0b1011)), /* 40 clean channel values outside threshold range generates an interrupt */
		PERS_45_CYCLE   ((byte)(0b1100)), /* 45 clean channel values outside threshold range generates an interrupt */
		PERS_50_CYCLE   ((byte)(0b1101)), /* 50 clean channel values outside threshold range generates an interrupt */
		PERS_55_CYCLE   ((byte)(0b1110)), /* 55 clean channel values outside threshold range generates an interrupt */
		PERS_60_CYCLE   ((byte)(0b1111)), /* 60 clean channel values outside threshold range generates an interrupt */
		CONFIG          ((byte)(0x0D)),
		CONFIG_WLONG    ((byte)(0x02)),   /* Choose between short and long (12x) wait times via TCS34725_WTIME */
		CONTROL         ((byte)(0x0F)),   /* Set the gain level for the sensor */
		ID              ((byte)(0x12)),   /* 0x44 = TCS34721/TCS34725, 0x4D = TCS34723/TCS34727 */
		STATUS          ((byte)(0x13)),
		STATUS_AINT     ((byte)(0x10)),   /* RGBC Clean channel interrupt */
		STATUS_AVALID   ((byte)(0x01)),   /* Indicates that the RGBC channels have completed an integration cycle */
		CDATAL          ((byte)(0x14)),   /* Clear channel data */
		CDATAH          ((byte)(0x15)),
		RDATAL          ((byte)(0x16)),    /* Red channel data */
		RDATAH          ((byte)(0x17)),
		GDATAL          ((byte)(0x18)),   /* Green channel data */
		GDATAH          ((byte)(0x19)),
		BDATAL          ((byte)(0x1A)),   /* Blue channel data */
		BDATAH          ((byte)(0x1B));
		
		public final byte value;
		
		private TCS34725Register(byte value) {
		      this.value = value;
		}
	}
	
	public void clearHighLow(){
		setRegister(TCS34725Register.CDATAH, 0x00);
		setRegister(TCS34725Register.CDATAL, 0x00);
	}
	
	public enum tcs34725IntegrationTime{
		TCS34725_INTEGRATIONTIME_2_4MS  ((int)(0xFF)),   /**<  2.4ms - 1 cycle    - Max Count: 1024  */
		TCS34725_INTEGRATIONTIME_24MS   ((int)(0xF6)),   /**<  24ms  - 10 cycles  - Max Count: 10240 */
		TCS34725_INTEGRATIONTIME_50MS   ((int)(0xEB)),   /**<  50ms  - 20 cycles  - Max Count: 20480 */
		TCS34725_INTEGRATIONTIME_101MS  ((int)(0xD5)),   /**<  101ms - 42 cycles  - Max Count: 43008 */
		TCS34725_INTEGRATIONTIME_154MS  ((int)(0xC0)),   /**<  154ms - 64 cycles  - Max Count: 65535 */
		TCS34725_INTEGRATIONTIME_700MS  ((int)(0x00));   /**<  700ms - 256 cycles - Max Count: 65535 */
		
		public final int value;
		
		private tcs34725IntegrationTime(int value) {
		      this.value = value;
		}
	}
	
	public enum tcs34725Gain{
		TCS34725_GAIN_1X                ((int)(0x00)),   /**<  No gain  */
		TCS34725_GAIN_4X                ((int)(0x01)),   /**<  4x gain  */
		TCS34725_GAIN_16X               ((int)(0x02)),   /**<  16x gain */
		TCS34725_GAIN_60X               ((int)(0x03));   /**<  60x gain */
		
		public final int value;
		
		private tcs34725Gain(int value) {
		      this.value = value;
		}
	}
	
	public TCS43725RBGSensor(I2C.Port port) {
		this(port, kAddress, kDefaultPeriod);
	}
	
	public TCS43725RBGSensor(I2C.Port port, int deviceAddress) {
		this(port, deviceAddress, kDefaultPeriod);
	}
	
	public TCS43725RBGSensor(I2C.Port port, double period) {
		this(port, kAddress, period);
	}
	public TCS43725RBGSensor(I2C.Port port, int deviceAddress, double period) {
		if (instances < 1) {
			m_i2c = new I2C(port, deviceAddress);
			// verify sensor is there
			byte id = getRegister(TCS34725Register.ID);
			if (id == (byte)0xB4) {
				instances ++;
				m_deviceAddress = deviceAddress;
				m_period = period;

				m_pollLoop = new java.util.Timer();
				m_pollLoop.schedule(new PollTCS43725Task(this), 0L, (long) (m_period * 1000));
			} else {
				System.out.println("Can't Find the TCS43725RBGSensor");
			}
		} else {
			System.out.println("Can't have multiple TCS43725RBGSensor at the same address");
		}

	}
	
	public TCS43725RBGSensor(tcs34725IntegrationTime it, tcs34725Gain gain) 
	{
	  tcs34725Initialised = false;
	  tcs34725IntegrationTime = it;
	  tcs34725Gain = gain;
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
		return "TCS43725RBGSensor";
	}
	
	@Override
	public void updateTable() {

	}
	
	@Override
	public void startLiveWindowMode() {
		
	}
	
	@Override
	public void stopLiveWindowMode() {
		
	}
	
	public boolean isInitialized(){
		return tcs34725Initialised;
	}
	
	public void setRegister(TCS34725Register reg, int data){
		setRegister(reg.value,data);
	}
		
	public void setRegister(int reg, int data){
		ByteBuffer temp = ByteBuffer.allocateDirect(3);
		temp.put((byte) ((reg >> 8) & 0xFF));
		temp.put((byte) (reg & 0xFF));
		temp.put((byte) (data & 0xFF));
		/*	  
		 * System.out.println("setRegister: reg: 0x"+Integer.toHexString(reg)+", "
		 * + "data: 0x"+Integer.toHexString(data));
		 */
		m_i2c.writeBulk(temp, 3);
	}

	public void setRegister16bit(TCS34725Register reg, int data){
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
	
	private byte getRegister(TCS34725Register reg) {
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
	
	private int getRegister16bit(TCS34725Register registerAddr){
		return getRegister16bit(registerAddr.value);
	}

	private int getRegister16bit(int registerAddr){

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
	
	public boolean begin(){
		
	  /* Make sure we're actually connected */
	  byte x = getRegister(TCS34725Register.ID);
	  if ((x != 0x44) && (x != 0x10)){
	    return false;
	  }
	  tcs34725Initialised = true;

	  /* Set default integration time and gain */
	  setIntegrationTime(tcs34725IntegrationTime);
	  setGain(tcs34725Gain);

	  return true;
	}
	
	public void setIntegrationTime(tcs34725IntegrationTime it){
	  if (!tcs34725Initialised) begin();

	  /* Update the timing register */
	  setRegister(TCS34725Register.ATIME, it.value);

	  /* Update value placeholders */
	  tcs34725IntegrationTime = it;
	}
	
	public void setGain(tcs34725Gain gain){
	  if (!tcs34725Initialised) begin();

	  /* Update the timing register */
	  setRegister(TCS34725Register.CONTROL, gain.value);

	  /* Update value placeholders */
	  tcs34725Gain = gain;
	}
	
	public void getRawData (int r, int g, int b, int c){
	  if (!tcs34725Initialised) begin();

	  c = getRegister16bit(TCS34725Register.CDATAL);
	  r = getRegister16bit(TCS34725Register.RDATAL);
	  g = getRegister16bit(TCS34725Register.GDATAL);
	  b = getRegister16bit(TCS34725Register.BDATAL);
	  
	  /* Set a delay for the integration time */
	  switch (tcs34725IntegrationTime){
	    case TCS34725_INTEGRATIONTIME_2_4MS:
	      break;
	    case TCS34725_INTEGRATIONTIME_24MS:
	      break;
	    case TCS34725_INTEGRATIONTIME_50MS:
	      break;
	    case TCS34725_INTEGRATIONTIME_101MS:
	      break;
	    case TCS34725_INTEGRATIONTIME_154MS:
	      break;
	    case TCS34725_INTEGRATIONTIME_700MS:
	      break;
	  }
	}
	
	int calculateColorTemperature(int r, int g, int b){
	  double X, Y, Z;      /* RGB to XYZ correlation      */
	  double xc, yc;       /* Chromaticity co-ordinates   */
	  double n;            /* McCamy's formula            */
	  double cct;

	  /* 1. Map RGB values to their XYZ counterparts.    */
	  /* Based on 6500K fluorescent, 3000K fluorescent   */
	  /* and 60W incandescent values for a wide range.   */
	  /* Note: Y = Illuminance or lux                    */
	  X = (-0.14282F * r) + (1.54924F * g) + (-0.95641F * b);
	  Y = (-0.32466F * r) + (1.57837F * g) + (-0.73191F * b);
	  Z = (-0.68202F * r) + (0.77073F * g) + ( 0.56332F * b);

	  /* 2. Calculate the chromaticity co-ordinates      */
	  xc = (X) / (X + Y + Z);
	  yc = (Y) / (X + Y + Z);

	  /* 3. Use McCamy's formula to determine the CCT    */
	  n = (xc - 0.3320F) / (0.1858F - yc);

	  /* Calculate the final CCT */
	  cct = (449.0F * Math.pow(n, 3)) + (3525.0F * Math.pow(n, 2)) + (6823.3F * n) + 5520.33F;

	  /* Return the results in degrees Kelvin */
	  return (int)cct;
	}
	
	public int calculateLux(int r, int g, int b){
	  float illuminance;

	  /* This only uses RGB ... how can we integrate clear or calculate lux */
	  /* based exclusively on clear since this might be more reliable?      */
	  illuminance = (-0.32466F * r) + (1.57837F * g) + (-0.73191F * b);

	  return (int)illuminance;
	}


	public void setInterrupt(boolean i) {
	  byte r = getRegister(TCS34725Register.ENABLE);
	  if (i) {
	    r |= TCS34725Register.ENABLE_AIEN.value;
	  } else {
	    r &= ~TCS34725Register.ENABLE_AIEN.value;
	  }
	  setRegister(TCS34725Register.ENABLE, r);
	}

	public void clearInterrupt() {
		/*
	  Wire.beginTransmission(TCS34725_ADDRESS);
	  #if ARDUINO >= 100
	  Wire.write(TCS34725_COMMAND_BIT | 0x66);
	  #else
	  Wire.send(TCS34725_COMMAND_BIT | 0x66);
	  #endif
	  Wire.endTransmission();
	  */
	  setRegister(TCS34725Register.COMMAND_BIT, 0x66);
	}


	public void setIntLimits(int low, int high) {
	   setRegister(0x04, low & 0xFF);
	   setRegister(0x05, low >> 8);
	   setRegister(0x06, high & 0xFF);
	   setRegister(0x07, high >> 8);
	}
	
	private void startColor(){

	}
	
	private boolean isFinished(){
		if(true){
			return true;
		}
		return false;
	}
	
	private double readColor(){
		
		return 0;
	}
	
	public int readRed(){
		int hi = (int) TCS34725Register.RDATAH.value;
		int lo = (int) TCS34725Register.RDATAL.value;
		int temp = hi << 8 + lo;
		return temp;
	}
	
	public int readGreen(){
		int hi = (int) TCS34725Register.GDATAH.value;
		int lo = (int) TCS34725Register.GDATAL.value;
		int temp = hi << 8 + lo;
		return temp;
	}
	
	public int readBlue(){
		int hi = (int) TCS34725Register.BDATAH.value;
		int lo = (int) TCS34725Register.BDATAL.value;
		int temp = hi << 8 + lo;
		return temp;
	}
	
	public class TCS34725Measurement {
		public double m_distance;
		public int m_errCode;
		
		TCS34725Measurement () {
			m_distance = -1;
			m_errCode = 15;
		}
		
		TCS34725Measurement(TCS34725Measurement sensorMeasurement) {
			m_distance = sensorMeasurement.m_distance;
			m_errCode = sensorMeasurement.m_errCode;
		}
	}
	
	public TCS34725Measurement getMeasurement() {
		TCS34725Measurement temp;
		synchronized (m_CurrentMeasurement) {
			temp = new TCS34725Measurement(m_CurrentMeasurement);		
		}
		return temp;
	}
	
	private class PollTCS43725Task extends TimerTask {
		private TCS43725RBGSensor m_sensor;

		public PollTCS43725Task(TCS43725RBGSensor sensor) {
			if (sensor == null) {
				throw new NullPointerException("Given TCS43725 was null");
			}
			m_sensor = sensor;
		}

		@Override
		public void run() {
			if(m_sensor.isInitialized()){
				if(m_sensor.isFinished()){
					m_sensor.readColor();
					m_sensor.updateTable();
					m_sensor.startColor();
				}
			}else{
				m_sensor.startColor();
			}
		}
	}
}
