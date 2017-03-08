package org.usfirst.frc.team100.robot;

import java.nio.ByteBuffer;
import java.util.TimerTask;

import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.SensorBase;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.livewindow.LiveWindowSendable;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.tables.ITable;

/* This java implementation for the roboRIO
 * loosely follows the Pololu Arduino library C++ implementation
 * as defined in the GitHub repository located at 
 * https://github.com/pololu/vl53l0x-arduino
 */

public class TimeOfFlightVL53L0X extends SensorBase implements LiveWindowSendable{

	public static final double kDefaultPeriod = .05;
	private static int instances = 0;
	private static final byte kAddress = 0x29;
	protected I2C m_i2c;
	private ITable m_table;
	private boolean isInit = false;
	private double m_period;
	private java.util.Timer m_pollLoop;
	private int m_deviceAddress;
	private byte m_spad_count;
	private boolean m_spad_type_is_aperture;
	private byte m_stop_variable; // read by init and used when starting measurement; 
	private int m_measurement_timing_budget_us;

	private int m_io_timeout;
	private Timer m_tof_response_timer;
	private class VL53L0xTimeout extends Exception {

	    public VL53L0xTimeout(String message) {
	        super(message);
	    }

	}

	private static String[] VL53L0xErrors = {
		"No Error", 				// 0
		"VCSEL Continuity Test", 	// 1
		"VCSEL Watchdog Test",		// 2
		"No VHV Value Found",		// 3
		"MSRC No Target", 			// 4
		"SNRr Check",				// 5
		"Range Phase Check",		// 6
		"Sigma Threshold Check",	// 7
		"TCC",			 			// 8
		"Phase Consistency",		// 9
		"Min Clip",					// 10
		"Range Complete",			// 11
		"Ranging Algo Overflow",	// 12
		"Raw Ranging Algo Overflow",// 13
		"Range Ignore Threshold",	// 14
	};



	public class VL53L0xMeasurement {
		public boolean m_isValid;
		public int m_distance;
		public byte m_errCode;

		VL53L0xMeasurement () {
			m_distance = -1;
			m_errCode = 0;
			m_isValid = false;
		}

		VL53L0xMeasurement(VL53L0xMeasurement sensorMeasurement) {
			m_distance = sensorMeasurement.m_distance;
			m_errCode = sensorMeasurement.m_errCode;
			m_isValid = sensorMeasurement.m_isValid;
		}


		public String getStatusString () {
			return VL53L0xErrors[m_errCode];
		}
	}


	private enum vcselPeriodType { 
		VcselPeriodPreRange, 
		VcselPeriodFinalRange 
	};

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
			m_deviceAddress = deviceAddress;
			m_tof_response_timer = new Timer();
			m_io_timeout = 200000; // 200 milliseconds
			// verify sensor is there
			byte id = getRegister(VL53L0xRegister.IDENTIFICATION_MODEL_ID);
			if (true) { // (id != 0x00) { // WHAT IS THE CORRECT VALUE???
				instances ++;
				m_deviceAddress = deviceAddress;
				m_CurrentMeasurement = new VL53L0xMeasurement();
				try {
					VL53L0xInit();
					VL53L0xDefaultSettings();

					isInit = true;
				} catch (VL53L0xTimeout e) {
					e.printStackTrace();
					System.out.println("Can't Find the VL53L0X");
					isInit = false;
				}
				if (isInit) {
					m_period = period;
					m_pollLoop = new java.util.Timer();
					m_pollLoop.schedule(new PollVL53L0xTask(this), 0L, (long) (m_period * 1000));
					startContinuous(0);					
				}
			} else {
				System.out.println("Can't Find the VL53L0X");
			}
		} else {
			System.out.println("Can't have multiple VL53L0X at the same address");
		}

	}

	/**
	 * Free the TimeOfFlightVL6180x object.
	 */
	public void free() {
		m_pollLoop.cancel();
		synchronized (this) {
			m_pollLoop = null;
			m_tof_response_timer.stop();
			m_tof_response_timer = null;
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
		return "VL53L0XTOF";
	}

	@Override
	public void updateTable() {
		if (m_table != null) {
			final TimeOfFlightVL53L0X.VL53L0xMeasurement meas = RobotMap.tof_sensor.getMeasurement();

			m_table.putNumber("VL53L0X distance mm", meas.m_distance);
			m_table.putBoolean("VL53L0x IsValid", meas.m_isValid);
			m_table.putString("VL53L0x Status", meas.getStatusString());
		}
	}

	@Override
	public void startLiveWindowMode() {

	}

	@Override
	public void stopLiveWindowMode() {

	}




	public boolean isInitialized(){
		return isInit;
	}
	private boolean isFinishedMeasure(){
		byte status = getRegister(VL53L0xRegister.RESULT_INTERRUPT_STATUS);

		if((status & 0x07) != 0){
			return true;
		}
		return false;
	}

	private double readDistance(){
		byte status = getRegister(VL53L0xRegister.RESULT_RANGE_STATUS);
		status = (byte) ((status & 0x78) >> 3); // a value of 11 means that the range measurement is complete


		int val = getRegister16bit(VL53L0xRegister.RESULT_RANGE_STATUS.value + 10);

		setRegister(VL53L0xRegister.SYSTEM_INTERRUPT_CLEAR, 0x01);


		synchronized (m_CurrentMeasurement) {
			m_CurrentMeasurement.m_errCode = status;
			if(status == 11){ 
				// only update if valid
				m_CurrentMeasurement.m_distance = val;
				m_CurrentMeasurement.m_isValid = true;
			} else {
				m_CurrentMeasurement.m_isValid = false;
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
				throw new NullPointerException("Given TimeOfFlightVL53L0x was null");
			}
			m_sensor = sensor;
		}

		@Override
		public void run() {
			if(m_sensor.isInitialized()){
				if(m_sensor.isFinishedMeasure()){
					m_sensor.readDistance();
					m_sensor.updateTable();

				}
			}
		}

	}

	private enum VL53L0xRegister{
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

	private boolean VL53L0xInit() throws VL53L0xTimeout{
		// "Set I2C standard mode"
		setRegister(0x88, 0x00);

		setRegister(0x80, 0x01);
		setRegister(0xFF, 0x01);
		setRegister(0x00, 0x00);
		m_stop_variable = getRegister(0x91);
		setRegister(0x00, 0x01);
		setRegister(0xFF, 0x00);
		setRegister(0x80, 0x00);

		// disable SIGNAL_RATEL53L0_MSRC (bit 1) and SIGNAL_RATE_PRE_RANGE (bit 4) limit checks
		setRegister(VL53L0xRegister.MSRC_CONFIG_CONTROL, getRegister(VL53L0xRegister.MSRC_CONFIG_CONTROL) | 0x12);

		// set final range signal rate limit to 0.25 MCPS (million counts per second)
		setSignalRateLimit(0.25);

		setRegister(VL53L0xRegister.SYSTEM_SEQUENCE_CONFIG, 0xFF);

		// VL53L0X_DataInit() end

		// VL53L0X_StaticInit() begin

		if (!getSpadInfo()) { return false; }

		// The SPAD map (RefGoodSpadMap) is read by VL53L0X_get_info_from_device() in
		// the API, but the same data seems to be more easily readable from
		// GLOBAL_CONFIG_SPAD_ENABLES_REF_0 through _6, so read it from there
		byte [] ref_spad_map = new byte [6];
		readMulti(VL53L0xRegister.GLOBAL_CONFIG_SPAD_ENABLES_REF_0, ref_spad_map, 6);

		// -- VL53L0X_set_reference_spads() begin (assume NVM values are valid)

		setRegister(0xFF, 0x01);
		setRegister(VL53L0xRegister.DYNAMIC_SPAD_REF_EN_START_OFFSET, 0x00);
		setRegister(VL53L0xRegister.DYNAMIC_SPAD_NUM_REQUESTED_REF_SPAD, 0x2C);
		setRegister(0xFF, 0x00);
		setRegister(VL53L0xRegister.GLOBAL_CONFIG_REF_EN_START_SELECT, 0xB4);

		byte first_spad_to_enable = (byte) (m_spad_type_is_aperture ? 12 : 0); // 12 is the first aperture spad
		byte spads_enabled = 0;

		for (byte i = 0; i < 48; i++)
		{
			if (i < first_spad_to_enable || spads_enabled == m_spad_count)
			{
				// This bit is lower than the first one that should be enabled, or
				// (reference_spad_count) bits have already been enabled, so zero this bit
				ref_spad_map[i / 8] &= ~(1 << (i % 8));
			}
			else if (((ref_spad_map[i / 8] >> (i % 8)) & 0x1) != 0)
			{
				spads_enabled++;
			}
		}

		writeMulti(VL53L0xRegister.GLOBAL_CONFIG_SPAD_ENABLES_REF_0, ref_spad_map, 6);

		// -- VL53L0X_set_reference_spads() end

		// -- VL53L0X_load_tuning_settings() begin
		// DefaultTuningSettings from vl53l0x_tuning.h

		setRegister(0xFF, 0x01);
		setRegister(0x00, 0x00);

		setRegister(0xFF, 0x00);
		setRegister(0x09, 0x00);
		setRegister(0x10, 0x00);
		setRegister(0x11, 0x00);

		setRegister(0x24, 0x01);
		setRegister(0x25, 0xFF);
		setRegister(0x75, 0x00);

		setRegister(0xFF, 0x01);
		setRegister(0x4E, 0x2C);
		setRegister(0x48, 0x00);
		setRegister(0x30, 0x20);

		setRegister(0xFF, 0x00);
		setRegister(0x30, 0x09);
		setRegister(0x54, 0x00);
		setRegister(0x31, 0x04);
		setRegister(0x32, 0x03);
		setRegister(0x40, 0x83);
		setRegister(0x46, 0x25);
		setRegister(0x60, 0x00);
		setRegister(0x27, 0x00);
		setRegister(0x50, 0x06);
		setRegister(0x51, 0x00);
		setRegister(0x52, 0x96);
		setRegister(0x56, 0x08);
		setRegister(0x57, 0x30);
		setRegister(0x61, 0x00);
		setRegister(0x62, 0x00);
		setRegister(0x64, 0x00);
		setRegister(0x65, 0x00);
		setRegister(0x66, 0xA0);

		setRegister(0xFF, 0x01);
		setRegister(0x22, 0x32);
		setRegister(0x47, 0x14);
		setRegister(0x49, 0xFF);
		setRegister(0x4A, 0x00);

		setRegister(0xFF, 0x00);
		setRegister(0x7A, 0x0A);
		setRegister(0x7B, 0x00);
		setRegister(0x78, 0x21);

		setRegister(0xFF, 0x01);
		setRegister(0x23, 0x34);
		setRegister(0x42, 0x00);
		setRegister(0x44, 0xFF);
		setRegister(0x45, 0x26);
		setRegister(0x46, 0x05);
		setRegister(0x40, 0x40);
		setRegister(0x0E, 0x06);
		setRegister(0x20, 0x1A);
		setRegister(0x43, 0x40);

		setRegister(0xFF, 0x00);
		setRegister(0x34, 0x03);
		setRegister(0x35, 0x44);

		setRegister(0xFF, 0x01);
		setRegister(0x31, 0x04);
		setRegister(0x4B, 0x09);
		setRegister(0x4C, 0x05);
		setRegister(0x4D, 0x04);

		setRegister(0xFF, 0x00);
		setRegister(0x44, 0x00);
		setRegister(0x45, 0x20);
		setRegister(0x47, 0x08);
		setRegister(0x48, 0x28);
		setRegister(0x67, 0x00);
		setRegister(0x70, 0x04);
		setRegister(0x71, 0x01);
		setRegister(0x72, 0xFE);
		setRegister(0x76, 0x00);
		setRegister(0x77, 0x00);

		setRegister(0xFF, 0x01);
		setRegister(0x0D, 0x01);

		setRegister(0xFF, 0x00);
		setRegister(0x80, 0x01);
		setRegister(0x01, 0xF8);

		setRegister(0xFF, 0x01);
		setRegister(0x8E, 0x01);
		setRegister(0x00, 0x01);
		setRegister(0xFF, 0x00);
		setRegister(0x80, 0x00);

		// -- VL53L0X_load_tuning_settings() end

		// "Set interrupt config to new sample ready"
		// -- VL53L0X_SetGpioConfig() begin

		setRegister(VL53L0xRegister.SYSTEM_INTERRUPT_CONFIG_GPIO, 0x04);
		setRegister(VL53L0xRegister.GPIO_HV_MUX_ACTIVE_HIGH, getRegister(VL53L0xRegister.GPIO_HV_MUX_ACTIVE_HIGH) & ~0x10); // active low
		setRegister(VL53L0xRegister.SYSTEM_INTERRUPT_CLEAR, 0x01);

		// -- VL53L0X_SetGpioConfig() end

		m_measurement_timing_budget_us = getMeasurementTimingBudget();

		// "Disable MSRC and TCC by default"
		// MSRC = Minimum Signal Rate Check
		// TCC = Target CentreCheck
		// -- VL53L0X_SetSequenceStepEnable() begin

		setRegister(VL53L0xRegister.SYSTEM_SEQUENCE_CONFIG, 0xE8);

		// -- VL53L0X_SetSequenceStepEnable() end

		// "Recalculate timing budget"
		setMeasurementTimingBudget(m_measurement_timing_budget_us);

		// VL53L0X_StaticInit() end

		// VL53L0X_PerformRefCalibration() begin (VL53L0X_perform_ref_calibration())

		// -- VL53L0X_perform_vhv_calibration() begin

		setRegister(VL53L0xRegister.SYSTEM_SEQUENCE_CONFIG, 0x01);
		if (!performSingleRefCalibration((byte)0x40)) { return false; }

		// -- VL53L0X_perform_vhv_calibration() end

		// -- VL53L0X_perform_phase_calibration() begin

		setRegister(VL53L0xRegister.SYSTEM_SEQUENCE_CONFIG, 0x02);
		if (!performSingleRefCalibration((byte)0x00)) { return false; }

		// -- VL53L0X_perform_phase_calibration() end

		// "restore the previous Sequence Config"
		setRegister(VL53L0xRegister.SYSTEM_SEQUENCE_CONFIG, 0xE8);

		// VL53L0X_PerformRefCalibration() end

		return true;



	}



	private void VL53L0xDefaultSettings(){


	}

	// Start continuous ranging measurements. If period_ms (optional) is 0 or not
	// given, continuous back-to-back mode is used (the sensor takes measurements as
	// often as possible); otherwise, continuous timed mode is used, with the given
	// inter-measurement period in milliseconds determining how often the sensor
	// takes a measurement.
	// based on VL53L0X_StartMeasurement()
	private void startContinuous(int period_ms)
	{
		setRegister(0x80, 0x01);
		setRegister(0xFF, 0x01);
		setRegister(0x00, 0x00);
		setRegister(0x91, m_stop_variable);
		setRegister(0x00, 0x01);
		setRegister(0xFF, 0x00);
		setRegister(0x80, 0x00);

		if (period_ms != 0)
		{
			// continuous timed mode

			// VL53L0X_SetInterMeasurementPeriodMilliSeconds() begin

			int osc_calibrate_val = getRegister16bit(VL53L0xRegister.OSC_CALIBRATE_VAL);

			if (osc_calibrate_val != 0)
			{
				period_ms *= osc_calibrate_val;
			}

			setRegister32Bit(VL53L0xRegister.SYSTEM_INTERMEASUREMENT_PERIOD, period_ms);

			// VL53L0X_SetInterMeasurementPeriodMilliSeconds() end

			setRegister(VL53L0xRegister.SYSRANGE_START, 0x04); // VL53L0X_REG_SYSRANGE_MODE_TIMED
		}
		else
		{
			// continuous back-to-back mode
			setRegister(VL53L0xRegister.SYSRANGE_START, 0x02); // VL53L0X_REG_SYSRANGE_MODE_BACKTOBACK
		}
	}

	// Stop continuous measurements
	// based on VL53L0X_StopMeasurement()
	private void stopContinuous()
	{
		setRegister(VL53L0xRegister.SYSRANGE_START, 0x01); // VL53L0X_REG_SYSRANGE_MODE_SINGLESHOT

		setRegister(0xFF, 0x01);
		setRegister(0x00, 0x00);
		setRegister(0x91, 0x00);
		setRegister(0x00, 0x01);
		setRegister(0xFF, 0x00);
	}


	// Set the return signal rate limit check value in units of MCPS (mega counts
	// per second). "This represents the amplitude of the signal reflected from the
	// target and detected by the device"; setting this limit presumably determines
	// the minimum measurement necessary for the sensor to report a valid reading.
	// Setting a lower limit increases the potential range of the sensor but also
	// seems to increase the likelihood of getting an inaccurate reading because of
	// unwanted reflections from objects other than the intended target.
	// Defaults to 0.25 MCPS as initialized by the ST API and this library.
	boolean setSignalRateLimit(double limit_Mcps)	{
		if (limit_Mcps < 0 || limit_Mcps > 511.99) { return false; }

		// Q9.7 fixed point format (9 integer bits, 7 fractional bits)
		setRegister16bit(VL53L0xRegister.FINAL_RANGE_CONFIG_MIN_COUNT_RATE_RTN_LIMIT, (int) (limit_Mcps * 128.000));
		return true;
	}

	// Get the return signal rate limit check value in MCPS
	double getSignalRateLimit()
	{
		return ((double) getRegister16bit (VL53L0xRegister.FINAL_RANGE_CONFIG_MIN_COUNT_RATE_RTN_LIMIT)) / 128.0;
	}


	private class SequenceStepEnables
	{
		boolean tcc;
		boolean msrc;
		boolean dss;
		boolean pre_range;
		boolean final_range;
	}

	private class SequenceStepTimeouts
	{
		int pre_range_vcsel_period_pclks;
		int final_range_vcsel_period_pclks;

		int msrc_dss_tcc_mclks; 
		int pre_range_mclks;
		int final_range_mclks;
		int msrc_dss_tcc_us;   
		int pre_range_us;   
		int final_range_us;
	}
	
	// Set the measurement timing budget in microseconds, which is the time allowed
	// for one measurement; the ST API and this library take care of splitting the
	// timing budget among the sub-steps in the ranging sequence. A longer timing
	// budget allows for more accurate measurements. Increasing the budget by a
	// factor of N decreases the range measurement standard deviation by a factor of
	// sqrt(N). Defaults to about 33 milliseconds; the minimum is 20 ms.
	// based on VL53L0X_set_measurement_timing_budget_micro_seconds()
	private boolean setMeasurementTimingBudget(int budget_us)
	{
		SequenceStepEnables enables;
		SequenceStepTimeouts timeouts;

		int StartOverhead      = 1320; // note that this is different than the value in get_
		int EndOverhead        = 960;
		int MsrcOverhead       = 660;
		int TccOverhead        = 590;
		int DssOverhead        = 690;
		int PreRangeOverhead   = 660;
		int FinalRangeOverhead = 550;

		int MinTimingBudget = 20000;

		if (budget_us < MinTimingBudget) { return false; }

		int used_budget_us = StartOverhead + EndOverhead;

		enables = getSequenceStepEnables();
		timeouts = getSequenceStepTimeouts(enables);

		if (enables.tcc)
		{
			used_budget_us += (timeouts.msrc_dss_tcc_us + TccOverhead);
		}

		if (enables.dss)
		{
			used_budget_us += 2 * (timeouts.msrc_dss_tcc_us + DssOverhead);
		}
		else if (enables.msrc)
		{
			used_budget_us += (timeouts.msrc_dss_tcc_us + MsrcOverhead);
		}

		if (enables.pre_range)
		{
			used_budget_us += (timeouts.pre_range_us + PreRangeOverhead);
		}

		if (enables.final_range)
		{
			used_budget_us += FinalRangeOverhead;

			// "Note that the final range timeout is determined by the timing
			// budget and the sum of all other timeouts within the sequence.
			// If there is no room for the final range timeout, then an error
			// will be set. Otherwise the remaining time will be applied to
			// the final range."

			if (used_budget_us > budget_us)
			{
				// "Requested timeout too big."
				return false;
			}

			int final_range_timeout_us = budget_us - used_budget_us;

			// set_sequence_step_timeout() begin
			// (SequenceStepId == VL53L0X_SEQUENCESTEP_FINAL_RANGE)

			// "For the final range timeout, the pre-range timeout
			//  must be added. To do this both final and pre-range
			//  timeouts must be expressed in macro periods MClks
			//  because they have different vcsel periods."

			int final_range_timeout_mclks =
					timeoutMicrosecondsToMclks(final_range_timeout_us,
							(byte)timeouts.final_range_vcsel_period_pclks);

			if (enables.pre_range)
			{
				final_range_timeout_mclks += timeouts.pre_range_mclks;
			}

			setRegister16bit(VL53L0xRegister.FINAL_RANGE_CONFIG_TIMEOUT_MACROP_HI,
					(int)encodeTimeout(final_range_timeout_mclks));

			// set_sequence_step_timeout() end

			m_measurement_timing_budget_us = budget_us; // store for internal reuse
		}
		return true;
	}

	// Get the measurement timing budget in microseconds
	// based on VL53L0X_get_measurement_timing_budget_micro_seconds()
	// in us
	private int getMeasurementTimingBudget()
	{
		SequenceStepEnables enables;
		SequenceStepTimeouts timeouts;

		int StartOverhead     = 1910; // note that this is different than the value in set_
		int EndOverhead        = 960;
		int MsrcOverhead       = 660;
		int TccOverhead        = 590;
		int DssOverhead        = 690;
		int PreRangeOverhead   = 660;
		int FinalRangeOverhead = 550;

		// "Start and end overhead times always present"
		int budget_us = StartOverhead + EndOverhead;

		enables = getSequenceStepEnables();
		timeouts = getSequenceStepTimeouts(enables);

		if (enables.tcc)
		{
			budget_us += (timeouts.msrc_dss_tcc_us + TccOverhead);
		}

		if (enables.dss)
		{
			budget_us += 2 * (timeouts.msrc_dss_tcc_us + DssOverhead);
		}
		else if (enables.msrc)
		{
			budget_us += (timeouts.msrc_dss_tcc_us + MsrcOverhead);
		}

		if (enables.pre_range)
		{
			budget_us += (timeouts.pre_range_us + PreRangeOverhead);
		}

		if (enables.final_range)
		{
			budget_us += (timeouts.final_range_us + FinalRangeOverhead);
		}

		m_measurement_timing_budget_us = budget_us; // store for internal reuse
		return budget_us;
	}

	// Set the VCSEL (vertical cavity surface emitting laser) pulse period for the
	// given period type (pre-range or final range) to the given value in PCLKs.
	// Longer periods seem to increase the potential range of the sensor.
	// Valid values are (even numbers only):
	//  pre:  12 to 18 (initialized default: 14)
	//  final: 8 to 14 (initialized default: 10)
	// based on VL53L0X_set_vcsel_pulse_period()
	private boolean setVcselPulsePeriod(vcselPeriodType type, byte period_pclks) throws VL53L0xTimeout
	{
		int vcsel_period_reg = encodeVcselPeriod(period_pclks);

		SequenceStepEnables enables;
		SequenceStepTimeouts timeouts;

		enables = getSequenceStepEnables();
		timeouts = getSequenceStepTimeouts(enables);

		// "Apply specific settings for the requested clock period"
		// "Re-calculate and apply timeouts, in macro periods"

		// "When the VCSEL period for the pre or final range is changed,
		// the corresponding timeout must be read from the device using
		// the current VCSEL period, then the new VCSEL period can be
		// applied. The timeout then must be written back to the device
		// using the new VCSEL period.
		//
		// For the MSRC timeout, the same applies - this timeout being
		// dependant on the pre-range vcsel period."


		if (type == vcselPeriodType.VcselPeriodPreRange)
		{
			// "Set phase check limits"
			switch (period_pclks)
			{
			case 12:
				setRegister(VL53L0xRegister.PRE_RANGE_CONFIG_VALID_PHASE_HIGH, 0x18);
				break;

			case 14:
				setRegister(VL53L0xRegister.PRE_RANGE_CONFIG_VALID_PHASE_HIGH, 0x30);
				break;

			case 16:
				setRegister(VL53L0xRegister.PRE_RANGE_CONFIG_VALID_PHASE_HIGH, 0x40);
				break;

			case 18:
				setRegister(VL53L0xRegister.PRE_RANGE_CONFIG_VALID_PHASE_HIGH, 0x50);
				break;

			default:
				// invalid period
				return false;
			}
			setRegister(VL53L0xRegister.PRE_RANGE_CONFIG_VALID_PHASE_LOW, 0x08);

			// apply new VCSEL period
			setRegister(VL53L0xRegister.PRE_RANGE_CONFIG_VCSEL_PERIOD, vcsel_period_reg);

			// update timeouts

			// set_sequence_step_timeout() begin
			// (SequenceStepId == VL53L0X_SEQUENCESTEP_PRE_RANGE)

			int new_pre_range_timeout_mclks =
					timeoutMicrosecondsToMclks(timeouts.pre_range_us, period_pclks);

			setRegister16bit(VL53L0xRegister.PRE_RANGE_CONFIG_TIMEOUT_MACROP_HI,
					encodeTimeout(new_pre_range_timeout_mclks));

			// set_sequence_step_timeout() end

			// set_sequence_step_timeout() begin
			// (SequenceStepId == VL53L0X_SEQUENCESTEP_MSRC)

			int new_msrc_timeout_mclks =
					timeoutMicrosecondsToMclks(timeouts.msrc_dss_tcc_us, period_pclks);

			setRegister(VL53L0xRegister.MSRC_CONFIG_TIMEOUT_MACROP,
					(new_msrc_timeout_mclks > 256) ? 255 : (new_msrc_timeout_mclks - 1));

			// set_sequence_step_timeout() end
		}
		else if (type == vcselPeriodType.VcselPeriodFinalRange)
		{
			switch (period_pclks)
			{
			case 8:
				setRegister(VL53L0xRegister.FINAL_RANGE_CONFIG_VALID_PHASE_HIGH, 0x10);
				setRegister(VL53L0xRegister.FINAL_RANGE_CONFIG_VALID_PHASE_LOW,  0x08);
				setRegister(VL53L0xRegister.GLOBAL_CONFIG_VCSEL_WIDTH, 0x02);
				setRegister(VL53L0xRegister.ALGO_PHASECAL_CONFIG_TIMEOUT, 0x0C);
				setRegister(0xFF, 0x01);
				setRegister(VL53L0xRegister.ALGO_PHASECAL_LIM, 0x30);
				setRegister(0xFF, 0x00);
				break;

			case 10:
				setRegister(VL53L0xRegister.FINAL_RANGE_CONFIG_VALID_PHASE_HIGH, 0x28);
				setRegister(VL53L0xRegister.FINAL_RANGE_CONFIG_VALID_PHASE_LOW,  0x08);
				setRegister(VL53L0xRegister.GLOBAL_CONFIG_VCSEL_WIDTH, 0x03);
				setRegister(VL53L0xRegister.ALGO_PHASECAL_CONFIG_TIMEOUT, 0x09);
				setRegister(0xFF, 0x01);
				setRegister(VL53L0xRegister.ALGO_PHASECAL_LIM, 0x20);
				setRegister(0xFF, 0x00);
				break;

			case 12:
				setRegister(VL53L0xRegister.FINAL_RANGE_CONFIG_VALID_PHASE_HIGH, 0x38);
				setRegister(VL53L0xRegister.FINAL_RANGE_CONFIG_VALID_PHASE_LOW,  0x08);
				setRegister(VL53L0xRegister.GLOBAL_CONFIG_VCSEL_WIDTH, 0x03);
				setRegister(VL53L0xRegister.ALGO_PHASECAL_CONFIG_TIMEOUT, 0x08);
				setRegister(0xFF, 0x01);
				setRegister(VL53L0xRegister.ALGO_PHASECAL_LIM, 0x20);
				setRegister(0xFF, 0x00);
				break;

			case 14:
				setRegister(VL53L0xRegister.FINAL_RANGE_CONFIG_VALID_PHASE_HIGH, 0x48);
				setRegister(VL53L0xRegister.FINAL_RANGE_CONFIG_VALID_PHASE_LOW,  0x08);
				setRegister(VL53L0xRegister.GLOBAL_CONFIG_VCSEL_WIDTH, 0x03);
				setRegister(VL53L0xRegister.ALGO_PHASECAL_CONFIG_TIMEOUT, 0x07);
				setRegister(0xFF, 0x01);
				setRegister(VL53L0xRegister.ALGO_PHASECAL_LIM, 0x20);
				setRegister(0xFF, 0x00);
				break;

			default:
				// invalid period
				return false;
			}

			// apply new VCSEL period
			setRegister(VL53L0xRegister.FINAL_RANGE_CONFIG_VCSEL_PERIOD, vcsel_period_reg);

			// update timeouts

			// set_sequence_step_timeout() begin
			// (SequenceStepId == VL53L0X_SEQUENCESTEP_FINAL_RANGE)

			// "For the final range timeout, the pre-range timeout
			//  must be added. To do this both final and pre-range
			//  timeouts must be expressed in macro periods MClks
			//  because they have different vcsel periods."

			int new_final_range_timeout_mclks =
					timeoutMicrosecondsToMclks(timeouts.final_range_us, period_pclks);

			if (enables.pre_range)
			{
				new_final_range_timeout_mclks += timeouts.pre_range_mclks;
			}

			setRegister16bit(VL53L0xRegister.FINAL_RANGE_CONFIG_TIMEOUT_MACROP_HI,
					encodeTimeout(new_final_range_timeout_mclks));

			// set_sequence_step_timeout end
		}
		else
		{
			// invalid type
			return false;
		}

		// "Finally, the timing budget must be re-applied"

		setMeasurementTimingBudget(m_measurement_timing_budget_us);

		// "Perform the phase calibration. This is needed after changing on vcsel period."
		// VL53L0X_perform_phase_calibration() begin

		byte sequence_config = getRegister(VL53L0xRegister.SYSTEM_SEQUENCE_CONFIG);
		setRegister(VL53L0xRegister.SYSTEM_SEQUENCE_CONFIG, 0x02);
		performSingleRefCalibration((byte)0x0);
		setRegister(VL53L0xRegister.SYSTEM_SEQUENCE_CONFIG, sequence_config);

		// VL53L0X_perform_phase_calibration() end

		return true;
	}

	// Get the VCSEL pulse period in PCLKs for the given period type.
	// based on VL53L0X_get_vcsel_pulse_period()
	private byte getVcselPulsePeriod(vcselPeriodType type)
	{
		if (type == vcselPeriodType.VcselPeriodPreRange)
		{
			return decodeVcselPeriod(getRegister(VL53L0xRegister.PRE_RANGE_CONFIG_VCSEL_PERIOD));
		}
		else if (type == vcselPeriodType.VcselPeriodFinalRange)
		{
			return decodeVcselPeriod(getRegister(VL53L0xRegister.FINAL_RANGE_CONFIG_VCSEL_PERIOD));
		}
		else { return (byte)255; }
	}




	// Get reference SPAD (single photon avalanche diode) count and type
	// based on VL53L0X_get_info_from_device(),
	// but only gets reference SPAD count and type
	private boolean getSpadInfo() throws VL53L0xTimeout
	{
		byte tmp;

		setRegister(0x80, 0x01);
		setRegister(0xFF, 0x01);
		setRegister(0x00, 0x00);

		setRegister(0xFF, 0x06);
		setRegister(0x83, getRegister(0x83) | 0x04);
		setRegister(0xFF, 0x07);
		setRegister(0x81, 0x01);

		setRegister(0x80, 0x01);

		setRegister(0x94, 0x6b);
		setRegister(0x83, 0x00);
		m_tof_response_timer.reset();
		m_tof_response_timer.start();
		while (getRegister(0x83) == 0x00)
		{
			if (checkTimeoutExpired()) { return false; }
		}
		setRegister(0x83, 0x01);
		tmp = getRegister(0x92);

		m_spad_count = (byte) (tmp & 0x7f);
		m_spad_type_is_aperture = ((tmp >> 7) & 0x01) == 1;

		setRegister(0x81, 0x00);
		setRegister(0xFF, 0x06);
		setRegister(0x83, getRegister( 0x83  & ~0x04));
		setRegister(0xFF, 0x01);
		setRegister(0x00, 0x01);

		setRegister(0xFF, 0x00);
		setRegister(0x80, 0x00);

		return true;
	}

	// Get sequence step enables
	// based on VL53L0X_GetSequenceStepEnables()
	private SequenceStepEnables getSequenceStepEnables()
	{
		SequenceStepEnables  enables = new SequenceStepEnables();
		byte sequence_config = getRegister(VL53L0xRegister.SYSTEM_SEQUENCE_CONFIG);

		enables.tcc          = ((sequence_config >> 4) & 0x1) == 1;
		enables.dss          = ((sequence_config >> 3) & 0x1) == 1;
		enables.msrc         = ((sequence_config >> 2) & 0x1) == 1;
		enables.pre_range    = ((sequence_config >> 6) & 0x1) == 1;
		enables.final_range  = ((sequence_config >> 7) & 0x1) == 1;
		return enables;
	}

	// Get sequence step timeouts
	// based on get_sequence_step_timeout(),
	// but gets all timeouts instead of just the requested one, and also stores
	// intermediate values
	private SequenceStepTimeouts getSequenceStepTimeouts(SequenceStepEnables enables)
	{
		SequenceStepTimeouts timeouts = new SequenceStepTimeouts();
		timeouts.pre_range_vcsel_period_pclks = getVcselPulsePeriod(vcselPeriodType.VcselPeriodPreRange);

		timeouts.msrc_dss_tcc_mclks = getRegister(VL53L0xRegister.MSRC_CONFIG_TIMEOUT_MACROP) + 1;
		timeouts.msrc_dss_tcc_us =
				timeoutMclksToMicroseconds(timeouts.msrc_dss_tcc_mclks,
						timeouts.pre_range_vcsel_period_pclks);

		timeouts.pre_range_mclks =
				decodeTimeout(getRegister16bit(VL53L0xRegister.PRE_RANGE_CONFIG_TIMEOUT_MACROP_HI));
		timeouts.pre_range_us =
				timeoutMclksToMicroseconds(timeouts.pre_range_mclks,
						timeouts.pre_range_vcsel_period_pclks);

		timeouts.final_range_vcsel_period_pclks = getVcselPulsePeriod(vcselPeriodType.VcselPeriodFinalRange);

		timeouts.final_range_mclks =
				decodeTimeout(getRegister16bit(VL53L0xRegister.FINAL_RANGE_CONFIG_TIMEOUT_MACROP_HI));

		if (enables.pre_range)
		{
			timeouts.final_range_mclks -= timeouts.pre_range_mclks;
		}

		timeouts.final_range_us =
				timeoutMclksToMicroseconds(timeouts.final_range_mclks,
						timeouts.final_range_vcsel_period_pclks);
		return timeouts;
	}

	// Decode sequence step timeout in MCLKs from register value
	// based on VL53L0X_decode_timeout()
	// Note: the original function returned a uint32_t, but the return value is
	// always stored in a uint16_t.
	private int decodeTimeout(int reg_val)
	{
		// format: "(LSByte * 2^MSByte) + 1"
		return (int)((reg_val & 0x00FF) <<
				(int)((reg_val & 0xFF00) >> 8)) + 1;
	}

	// Encode sequence step timeout register value from timeout in MCLKs
	// based on VL53L0X_encode_timeout()
	// Note: the original function took a uint16_t, but the argument passed to it
	// is always a uint16_t.
	private int encodeTimeout(int timeout_mclks)
	{
		// format: "(LSByte * 2^MSByte) + 1"

		int ls_byte = 0;
		int ms_byte = 0;

		if (timeout_mclks > 0)
		{
			ls_byte = timeout_mclks - 1;

			while ((ls_byte & 0xFFFFFF00) > 0)
			{
				ls_byte >>= 1;
				ms_byte++;
			}

			return (ms_byte << 8) | (ls_byte & 0xFF);
		}
		else { return 0; }
	}

	// Convert sequence step timeout from MCLKs to microseconds with given VCSEL period in PCLKs
	// based on VL53L0X_calc_timeout_us()
	private int timeoutMclksToMicroseconds(int timeout_period_mclks, int vcsel_period_pclks)
	{
		int macro_period_ns = calcMacroPeriod(vcsel_period_pclks);

		return ((timeout_period_mclks * macro_period_ns) + (macro_period_ns / 2)) / 1000;
	}

	// Convert sequence step timeout from microseconds to MCLKs with given VCSEL period in PCLKs
	// based on VL53L0X_calc_timeout_mclks()
	private int timeoutMicrosecondsToMclks(int timeout_period_us, byte vcsel_period_pclks)
	{
		int macro_period_ns = calcMacroPeriod(vcsel_period_pclks);

		return (((timeout_period_us * 1000) + (macro_period_ns / 2)) / macro_period_ns);
	}


	// based on VL53L0X_perform_single_ref_calibration()
	private boolean performSingleRefCalibration(byte vhv_init_byte) throws VL53L0xTimeout
	{
		setRegister(VL53L0xRegister.SYSRANGE_START, 0x01 | vhv_init_byte); // VL53L0X_REG_SYSRANGE_MODE_START_STOP

		m_tof_response_timer.reset();
		m_tof_response_timer.start();
		while ((getRegister(VL53L0xRegister.RESULT_INTERRUPT_STATUS) & 0x07) == 0)
		{
			if (checkTimeoutExpired()) { return false; }
		}

		setRegister(VL53L0xRegister.SYSTEM_INTERRUPT_CLEAR, 0x01);

		setRegister(VL53L0xRegister.SYSRANGE_START, 0x00);

		return true;
	}

	private byte decodeVcselPeriod(byte reg_val)    { 
		return (byte) (((reg_val) + 1) << 1);
	}

	private byte encodeVcselPeriod(byte period_pclks) {
		return (byte) (((period_pclks) >> 1) - 1);
	}

	private int calcMacroPeriod(int vcsel_period_pclks) {
		return 	((((int)2304 * (vcsel_period_pclks) * 1655) + 500) / 1000);
	}

	private boolean checkTimeoutExpired() throws VL53L0xTimeout {
		if (m_io_timeout > 0 && ((int)(m_tof_response_timer.get()*1000000.0)) > m_io_timeout) {
			throw new VL53L0xTimeout("VL53L0x Time of Flight Sensor Timed Out.");
		}
		return false;
	}
	
	//I2C manipulations utilities
	public void setRegister(VL53L0xRegister reg, int data){
		setRegister((int)reg.value,data);
	}

	public void setRegister(int reg, int data){
		ByteBuffer temp = ByteBuffer.allocateDirect(2);
		temp.put((byte) (reg & 0xFF));
		temp.put((byte) (data & 0xFF));
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
		m_i2c.transaction(index, 1, rawData, 1);
		byte data = rawData.get();
		return data;
	}

	private void setRegister32Bit(VL53L0xRegister reg, int data) {
		setRegister32Bit((int) reg.value, data);
	}

	private void setRegister32Bit(int reg, int data) {
		ByteBuffer raw = ByteBuffer.allocateDirect(5);
		raw.put((byte) ((reg & 0xFF)));
		raw.put((byte) ((data >> 24) & 0xFF));
		raw.put((byte) ((data >> 16) & 0xFF));
		raw.put((byte) ((data >> 8) & 0xFF));
		raw.put(((byte) ((data & 0xFF))));
		m_i2c.writeBulk(raw, 5);		
	}

	private int getRegister16bit(VL53L0xRegister registerAddr){
		return getRegister16bit((int)registerAddr.value);
	}

	private int getRegister16bit(int registerAddr)
	{

		ByteBuffer rawData = ByteBuffer.allocateDirect(2);
		ByteBuffer index = ByteBuffer.allocateDirect(1);
		index.put((byte) (registerAddr & 0xFF));
		m_i2c.transaction(index, 1, rawData, 2);
		int hi = (int) rawData.get() & 0xFF;
		int lo = (int) rawData.get() & 0xFF;
		int temp = (hi << 8) + lo;
		return temp;

	}
	// Write an arbitrary number of bytes from the given array to the sensor,
	// starting at the given register
	void writeMulti(VL53L0xRegister reg, byte[] src, int count){
		writeMulti(reg.value, src, count);
	}

	// Write an arbitrary number of bytes from the given array to the sensor,
	// starting at the given register
	void writeMulti(byte reg, byte[] src, int count){
		ByteBuffer rawData = ByteBuffer.allocateDirect(count + 1);
		rawData.put((byte) (reg & 0xFF));
		for (int i = 0; i < count; i ++) {
			rawData.put(src[i]);
		}
		m_i2c.writeBulk(rawData, count + 1);
	}

	// Read an arbitrary number of bytes from the sensor, starting at the given
	// register, into the given array
	void readMulti( VL53L0xRegister reg, byte[] dst, int count)	{	
		readMulti(reg.value, dst, count);
	}

	// Read an arbitrary number of bytes from the sensor, starting at the given
	// register, into the given array
	void readMulti(byte reg, byte[] dst, int count)
	{
		ByteBuffer index = ByteBuffer.allocateDirect(1);
		ByteBuffer rawData = ByteBuffer.allocateDirect(count);

		index.put((byte) (reg & 0xFF));
		m_i2c.transaction(index, 1, rawData, count);
		for (int i = 0; i < count; i ++) {
			dst[i] = (byte) (rawData.get() & 0xFF);
		}
	}


}
