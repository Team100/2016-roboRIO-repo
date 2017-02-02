package org.usfirst.frc.team100.robot;

import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.SensorBase;
import edu.wpi.first.wpilibj.livewindow.LiveWindowSendable;
import edu.wpi.first.wpilibj.tables.ITable;

public class TimeOfFlightVL6180x extends SensorBase implements LiveWindowSendable{

	private static final byte kAddress = 0x1D;
	private static final byte VL6180x_FAILURE_RESET = 0;
	protected I2C m_i2c;
	
	public TimeOfFlightVL6180x(I2C.Port port) {
		this(port, kAddress);
	}

	public TimeOfFlightVL6180x(I2C.Port port, int deviceAddress) {
		m_i2c = new I2C(port, deviceAddress);
	}
	
	
	@Override
	public void initTable(ITable subtable) {
		// TODO Auto-generated method stub
		m_table = subtable;
		updateTable();
	}

	@Override
	public ITable getTable() {
		// TODO Auto-generated method stub
		return m_table;
	}

	@Override
	public String getSmartDashboardType() {
		// TODO Auto-generated method stub
		return "TimeOfFightSensor";
	}
	private ITable m_table;

	@Override
	public void updateTable() {
		// TODO Auto-generated method stub
		if (m_table != null) {
			m_table.putNumber("Dist", getDistance());
		}
	}

	private double getDistance() {
		return 0;
	}
	
	

//	@Override
//	public void startLiveWindowMode() {
//		
//	}
//
//	@Override
//	public void stopLiveWindowMode() {
//	}
	
	public enum VL6180xRegister{
		VL6180X_IDENTIFICATION_MODEL_ID				((int)0x0000),
		VL6180X_IDENTIFICATION_MODEL_REV_MAJOR		((int)0x0001),
		VL6180X_IDENTIFICATION_MODEL_REV_MINOR		((int)0x0002),
		VL6180X_IDENTIFICATION_MODULE_REV_MAJOR		((int)0x0003),
		VL6180X_IDENTIFICATION_MODULE_REV_MINOR		((int)0x0004),
		VL6180X_IDENTIFICATION_DATE					((int)0x0006),
		VL6180X_IDENTIFICATION_TIME					((int)0x0008),
		
		VL6180X_SYSTEM_MODE_GPIO0					((int)0x0010),
		VL6180X_SYSTEM_MODE_GPIO1					((int)0x0011),
		VL6180X_SYSTEM_HISTORY_CTRL					((int)0x0012),
		VL6180X_SYSTEM_INTERRUPT_CONFIG_GPIO		((int)0x0014),
		VL6180X_SYSTEM_INTERRUPT_CLEAR				((int)0x0015),
		VL6180X_SYSTEM_FRESH_OUT_OF_RESET			((int)0x0016),
		VL6180X_SYSTEM_GROUPED_PARAMETER_HOLD		((int)0x0017),
		
		VL6180X_SYSRANGE_START						((int)0x0018),
		VL6180X_SYSRANGE_THRESH_HIGH				((int)0x0019),
		VL6180X_SYSRANGE_THRESH_LOW					((int)0x001A),
		VL6180X_SYSRANGE_INTERMEASUREMENT_PERIOD	((int)0x001B),
		VL6180X_SYSRANGE_MAX_CONVERGENCE_TIME		((int)0x001C),
		VL6180X_SYSRANGE_CROSSTALK_COMPENSATION_RATE((int)0x001E),
		VL6180X_SYSRANGE_CROSSTALK_VALID_HEIGHT		((int)0x0021),
		VL6180X_SYSRANGE_EARLY_CONVERGENCE_ESTIMATE	((int)0x0022),
		VL6180X_SYSRANGE_PART_TO_PART_RANGE_OFFSET	((int)0x0024),
		VL6180X_SYSRANGE_RANGE_IGNORE_VALID_HEIGHT	((int)0x0025),
		VL6180X_SYSRANGE_RANGE_IGNORE_THRESHOLD		((int)0x0026),
		VL6180X_SYSRANGE_MAX_AMBIENT_LEVEL_MULT		((int)0x002C),
		VL6180X_SYSRANGE_RANGE_CHECK_ENABLES		((int)0x002D),
		VL6180X_SYSRANGE_VHV_RECALIBRATE			((int)0x002E),
		VL6180X_SYSRANGE_VHV_REPEAT_RATE			((int)0x0031),
		
		VL6180X_SYSALS_START						((int)0x0038),
		VL6180X_SYSALS_THRESH_HIGH					((int)0x003A),
		VL6180X_SYSALS_THRESH_LOW					((int)0x003C),
		VL6180X_SYSALS_INTERMEASUREMENT_PERIOD		((int)0x003E),
		VL6180X_SYSALS_ANALOGUE_GAIN				((int)0x003F),
		VL6180X_SYSALS_INTEGRATION_PERIOD			((int)0x0040),
		
		VL6180X_RESULT_RANGE_STATUS                 ((int)0x004D),
		VL6180X_RESULT_ALS_STATUS                   ((int)0x004E),
		VL6180X_RESULT_INTERRUPT_STATUS_GPIO        ((int)0x004F),
		VL6180X_RESULT_ALS_VAL                      ((int)0x0050),
		VL6180X_RESULT_HISTORY_BUFFER               ((int)0x0052),
		VL6180X_RESULT_RANGE_VAL                    ((int)0x0062),
		VL6180X_RESULT_RANGE_RAW                    ((int)0x0064),
		VL6180X_RESULT_RANGE_RETURN_RATE            ((int)0x0066),
		VL6180X_RESULT_RANGE_REFERENCE_RATE         ((int)0x0068),
		VL6180X_RESULT_RANGE_RETURN_SIGNAL_COUNT    ((int)0x006C),
		VL6180X_RESULT_RANGE_REFERENCE_SIGNAL_COUNT ((int)0x0070),
		VL6180X_RESULT_RANGE_RETURN_AMB_COUNT       ((int)0x0074),
		VL6180X_RESULT_RANGE_REFERENCE_AMB_COUNT    ((int)0x0078),
		VL6180X_RESULT_RANGE_RETURN_CONV_TIME       ((int)0x007C),
		VL6180X_RESULT_RANGE_REFERENCE_CONV_TIME    ((int)0x0080),

		VL6180X_READOUT_AVERAGING_SAMPLE_PERIOD     ((int)0x010A),
		VL6180X_FIRMWARE_BOOTUP                     ((int)0x0119),
		VL6180X_FIRMWARE_RESULT_SCALER              ((int)0x0120),
		VL6180X_I2C_SLAVE_DEVICE_ADDRESS            ((int)0x0212),
		VL6180X_INTERLEAVED_MODE_ENABLE             ((int)0x02A3);
		
		public final int value;
		
		private VL6180xRegister(int value) {
		      this.value = value;
		}
	}
	public byte VL6180xInit(){
		  byte data; //for temp data storage

		  data = VL6180x_getRegister(VL6180xRegister.VL6180X_SYSTEM_FRESH_OUT_OF_RESET);

		  if(data != 1) return VL6180x_FAILURE_RESET;
		  
		  VL6180x_setRegister(0x0207, 0x01);
		  VL6180x_setRegister(0x0208, 0x01);
		  VL6180x_setRegister(0x0096, 0x00);
		  VL6180x_setRegister(0x0097, 0xfd);
		  VL6180x_setRegister(0x00e3, 0x00);
		  VL6180x_setRegister(0x00e4, 0x04);
		  VL6180x_setRegister(0x00e5, 0x02);
		  VL6180x_setRegister(0x00e6, 0x01);
		  VL6180x_setRegister(0x00e7, 0x03);
		  VL6180x_setRegister(0x00f5, 0x02);
		  VL6180x_setRegister(0x00d9, 0x05);
		  VL6180x_setRegister(0x00db, 0xce);
		  VL6180x_setRegister(0x00dc, 0x03);
		  VL6180x_setRegister(0x00dd, 0xf8);
		  VL6180x_setRegister(0x009f, 0x00);
		  VL6180x_setRegister(0x00a3, 0x3c);
		  VL6180x_setRegister(0x00b7, 0x00);
		  VL6180x_setRegister(0x00bb, 0x3c);
		  VL6180x_setRegister(0x00b2, 0x09);
		  VL6180x_setRegister(0x00ca, 0x09);  
		  VL6180x_setRegister(0x0198, 0x01);
		  VL6180x_setRegister(0x01b0, 0x17);
		  VL6180x_setRegister(0x01ad, 0x00);
		  VL6180x_setRegister(0x00ff, 0x05);
		  VL6180x_setRegister(0x0100, 0x05);
		  VL6180x_setRegister(0x0199, 0x05);
		  VL6180x_setRegister(0x01a6, 0x1b);
		  VL6180x_setRegister(0x01ac, 0x3e);
		  VL6180x_setRegister(0x01a7, 0x1f);
		  VL6180x_setRegister(0x0030, 0x00);

		  return 0;
		}

		private byte VL6180x_getRegister(VL6180xRegister vl6180xSystemFreshOutOfReset) {
		return 0;
	}
		private void VL6180xDefautSettings(){

		  VL6180x_setRegister(VL6180xRegister.VL6180X_SYSTEM_INTERRUPT_CONFIG_GPIO, (4 << 3)|(4) ); // Set GPIO1 high when sample complete


		  VL6180x_setRegister(VL6180xRegister.VL6180X_SYSTEM_MODE_GPIO1, 0x10); // Set GPIO1 high when sample complete
		  VL6180x_setRegister(VL6180xRegister.VL6180X_READOUT_AVERAGING_SAMPLE_PERIOD, 0x30); //Set Avg sample period
		  VL6180x_setRegister(VL6180xRegister.VL6180X_SYSALS_ANALOGUE_GAIN, 0x46); // Set the ALS gain
		  VL6180x_setRegister(VL6180xRegister.VL6180X_SYSRANGE_VHV_REPEAT_RATE, 0xFF); // Set auto calibration period (Max = 255)/(OFF = 0)
		  VL6180x_setRegister(VL6180xRegister.VL6180X_SYSALS_INTEGRATION_PERIOD, 0x63); // Set ALS integration time to 100ms
		  VL6180x_setRegister(VL6180xRegister.VL6180X_SYSRANGE_VHV_RECALIBRATE, 0x01); // perform a single temperature calibration

		  VL6180x_setRegister(VL6180xRegister.VL6180X_SYSRANGE_INTERMEASUREMENT_PERIOD, 0x09); // Set default ranging inter-measurement period to 100ms
		  VL6180x_setRegister(VL6180xRegister.VL6180X_SYSALS_INTERMEASUREMENT_PERIOD, 0x0A); // Set default ALS inter-measurement period to 100ms
		  VL6180x_setRegister(VL6180xRegister.VL6180X_SYSTEM_INTERRUPT_CONFIG_GPIO, 0x24); // Configures interrupt on ‘New Sample Ready threshold event’ 

		  VL6180x_setRegister(VL6180xRegister.VL6180X_SYSRANGE_MAX_CONVERGENCE_TIME, 0x32);
		  VL6180x_setRegister(VL6180xRegister.VL6180X_SYSRANGE_RANGE_CHECK_ENABLES, 0x10 | 0x01);
		  VL6180x_setRegister16bit(VL6180xRegister.VL6180X_SYSRANGE_EARLY_CONVERGENCE_ESTIMATE, 0x7B );
		  VL6180x_setRegister16bit(VL6180xRegister.VL6180X_SYSALS_INTEGRATION_PERIOD, 0x64);

		  VL6180x_setRegister(VL6180xRegister.VL6180X_READOUT_AVERAGING_SAMPLE_PERIOD,0x30);
		  VL6180x_setRegister(VL6180xRegister.VL6180X_SYSALS_ANALOGUE_GAIN,0x40);
		  VL6180x_setRegister(VL6180xRegister.VL6180X_FIRMWARE_RESULT_SCALER,0x01);
		}
	
	public void VL6180x_setRegister(VL6180xRegister vl6180xSystemInterruptConfigGpio, int data){
		/*
	  write( _i2caddress ); // Address set on class instantiation
	  Wire.write((registerAddr >> 8) & 0xFF); //MSB of register address
	  Wire.write(registerAddr & 0xFF); //LSB of register address
	  Wire.write(data); // Data/setting to be sent to device.
	  Wire.endTransmission(); //Send address and register address bytes
	  */
	}
	public void VL6180x_setRegister(int reg, int data){
		/*
	  write( _i2caddress ); // Address set on class instantiation
	  Wire.write((registerAddr >> 8) & 0xFF); //MSB of register address
	  Wire.write(registerAddr & 0xFF); //LSB of register address
	  Wire.write(data); // Data/setting to be sent to device.
	  Wire.endTransmission(); //Send address and register address bytes
	  */
	}

	public void VL6180x_setRegister16bit(VL6180xRegister vl6180xSysrangeEarlyConvergenceEstimate, int data){
	  /*
	  Wire.beginTransmission( _i2caddress ); // Address set on class instantiation
	  Wire.write((registerAddr >> 8) & 0xFF); //MSB of register address
	  Wire.write(registerAddr & 0xFF); //LSB of register address
	  byte temp;
	  temp = (byte) ((data >> 8) & 0xff);
	  Wire.write(temp); // Data/setting to be sent to device
	  temp = (byte) (data & 0xff);
	  Wire.write(temp); // Data/setting to be sent to device
	  Wire.endTransmission(); //Send address and register address bytes
	  */
	}

	@Override
	public void startLiveWindowMode() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void stopLiveWindowMode() {
		// TODO Auto-generated method stub
		
	}
}
