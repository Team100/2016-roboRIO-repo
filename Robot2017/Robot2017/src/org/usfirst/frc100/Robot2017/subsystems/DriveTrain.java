// RobotBuilder Version: 2.0
//
// This file was generated by RobotBuilder. It contains sections of
// code that are automatically generated and assigned by robotbuilder.
// These sections will be updated in the future when you export to
// Java from RobotBuilder. Do not put any code or make any change in
// the blocks indicating autogenerated code or it will be lost on an
// update. Deleting the comments indicating the section will prevent
// it from being updated in the future.


package org.usfirst.frc100.Robot2017.subsystems;

import org.usfirst.frc100.Robot2017.Robot;

import org.usfirst.frc100.Robot2017.RobotMap;
import org.usfirst.frc100.Robot2017.commands.*;


import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.AnalogGyro;
import edu.wpi.first.wpilibj.CounterBase.EncodingType;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.Ultrasonic;
import edu.wpi.first.wpilibj.VictorSP;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;


/**
 *
 */
public class DriveTrain extends Subsystem {
	
	/*
    private final SpeedController leftMotor = RobotMap.driveTrainleftMotor;
    private final SpeedController rightMotor = RobotMap.driveTrainrightMotor;
    private final RobotDrive drive = RobotMap.driveTraindrive;
    private final Encoder leftEncoder = RobotMap.driveTrainleftEncoder;
    private final Encoder rightEncoder = RobotMap.driveTrainrightEncoder;
    private final Solenoid leftShifter = RobotMap.driveTrainleftShifter;
    private final Solenoid rightShifter = RobotMap.driveTrainrightShifter;
    private final AnalogGyro digialGyroUno = RobotMap.driveTraindigialGyroUno;
    private final Ultrasonic ultraSanic = RobotMap.driveTrainultraSanic;
    */
	
	public final Solenoid driveTrainShifter = RobotMap.driveTrainShifter;
	
	public final Encoder driveTrainLeftEncoder = RobotMap.driveTrainLeftEncoder;
	public final Encoder driveTrainRightEncoder = RobotMap.driveTrainRightEncoder;

	private final RobotDrive robotDrive = RobotMap.driveTrainRobotDrive;
	
		public final ADXRS450_Gyro gyro = RobotMap.gyro;
		private static final double DEFAULT_DRIVE_TRAIN_KP = .9; //.004
		private static final double DEFAULT_DRIVE_TRAIN_KI = 0.00;
		private static final double DEFAULT_DRIVE_TRAIN_KF = .2; //.58823
	
		private static final double DEFAULT_DRIVE_TRAIN_VP = .9; //.004
		private static final double DEFAULT_DRIVE_TRAIN_VI = 0.00;
		private static final double DEFAULT_DRIVE_TRAIN_VF = .2; //.58823

		public double driveTrain_kP;
		public double driveTrain_kI;
		public double driveTrain_kF;
	
		public double driveVelP;
		public double driveVelI;
		public double driveVelD;
		public double driveVelF;
	
		public double angleP;
		public double angleI;
		public double angleD;
	
	
		public int overRiddenR = 0;
		public int overRiddenL = 0;
		public PIDControllerHenry pidPosLeft;
	    public PIDControllerHenry pidPosRight;
	    public PIDControllerHenry pidVelLeft;
	    public PIDControllerHenry pidVelRight;
	    public PIDControllerHenry pidAngle;
	    public double maxOutput;
	    public double maxSpeedReached = 0;
	    public int countL = 0;
	    public int countR = 0;
	    public int count = 0;
	    public int countTwo = 0;
	    public double velLeft; 
	    public double velRight;
	    public double angle; 
	    
	    public DriveTrain() {
	    	if (!Robot.prefs.containsKey("driveTrain_kP")) {
				Robot.prefs.putDouble("driveTrain_kP", DEFAULT_DRIVE_TRAIN_KP);
			}
			if (!Robot.prefs.containsKey("driveTrain_kI")) {
				Robot.prefs.putDouble("driveTrain_kI", DEFAULT_DRIVE_TRAIN_KI);
			}
			if (!Robot.prefs.containsKey("driveTrain_kF")) {
				Robot.prefs.putDouble("driveTrain_kF", DEFAULT_DRIVE_TRAIN_KF);
			}

			driveTrain_kP = Robot.prefs.getDouble("driveTrain_kP",
					DEFAULT_DRIVE_TRAIN_KP);
			driveTrain_kI = Robot.prefs.getDouble("driveTrain_kI",
					DEFAULT_DRIVE_TRAIN_KI);
			driveTrain_kF = Robot.prefs.getDouble("driveTrain_kF",
					DEFAULT_DRIVE_TRAIN_KF);
			//---------------------------------------------------------------------------
			if (!Robot.prefs.containsKey("driveVelP")) {
				Robot.prefs.putDouble("driveVelP", DEFAULT_DRIVE_TRAIN_VP);
			}
			if (!Robot.prefs.containsKey("driveVelI")) {
				Robot.prefs.putDouble("driveVelI", DEFAULT_DRIVE_TRAIN_VI);
			}
			if (!Robot.prefs.containsKey("driveVelF")) {
				Robot.prefs.putDouble("driveVelF", DEFAULT_DRIVE_TRAIN_VF);
			}

			driveVelP = Robot.prefs.getDouble("driveVelP",
					DEFAULT_DRIVE_TRAIN_VP);
			driveVelI = Robot.prefs.getDouble("driveVelI",
					DEFAULT_DRIVE_TRAIN_VI);
			driveVelF = Robot.prefs.getDouble("driveVelF",
					DEFAULT_DRIVE_TRAIN_VF);
			//---------------------------------------------------------------------------
			if (!Robot.prefs.containsKey("angleP")) {
				Robot.prefs.putDouble("angleP", DEFAULT_DRIVE_TRAIN_VP);
			}
			if (!Robot.prefs.containsKey("angleI")) {
				Robot.prefs.putDouble("angleI", DEFAULT_DRIVE_TRAIN_VI);
			}
			if (!Robot.prefs.containsKey("angleD")) {
				Robot.prefs.putDouble("angleD", DEFAULT_DRIVE_TRAIN_VF);
			}

			angleP = Robot.prefs.getDouble("angleP",
					.01);
			angleI = Robot.prefs.getDouble("angleI",
					0);
			angleD = Robot.prefs.getDouble("angleD",
					0);
			
			pidVelRight = new PIDControllerHenry(driveVelP, driveVelI , driveVelF, new PIDSource() { // .58823
				PIDSourceType m_sourceType = PIDSourceType.kRate;

				public double pidGet() {
					return RobotMap.driveTrainRightEncoder.getRate();
				}

				@Override
				public void setPIDSourceType(PIDSourceType pidSource) {
					m_sourceType = pidSource;
				}

				@Override
				public PIDSourceType getPIDSourceType() {
					return m_sourceType;
				}
			}, new PIDOutput() {
				public void pidWrite(double d) {
					//double output = Math.abs(d);
					velRight = d;
					
					//RobotMap.rightMaster.pidWrite(velRight);
					
				}
			});
			
			pidVelLeft = new PIDControllerHenry(driveVelP, driveVelI , driveVelF, new PIDSource() { // .58823
				PIDSourceType m_sourceType = PIDSourceType.kRate;

				public double pidGet() {
					return RobotMap.driveTrainLeftEncoder.getRate();
				}

				@Override
				public void setPIDSourceType(PIDSourceType pidSource) {
					m_sourceType = pidSource;
				}

				@Override
				public PIDSourceType getPIDSourceType() {
					return m_sourceType;
				}
			}, new PIDOutput() {
				public void pidWrite(double d) {
					//double output = Math.abs(d);
					
					velLeft  = d;	
			//		RobotMap.leftMaster.pidWrite(velLeft);
					
				}
			});
	    	
	    	pidPosLeft = new PIDControllerHenry(driveTrain_kP, driveTrain_kI, 0, driveTrain_kF, new PIDSource() { // .04 0 0 for 180
				PIDSourceType m_sourceType = PIDSourceType.kDisplacement;

				public double pidGet() {
					return (RobotMap.driveTrainLeftEncoder.getDistance() * -1);
					
					
				}

				@Override
				public void setPIDSourceType(PIDSourceType pidSource) {
					m_sourceType = pidSource;
				}

				@Override
				public PIDSourceType getPIDSourceType() {
					return m_sourceType;
				}
			}, new PIDOutput() {
				public void pidWrite(double v) {
					double o = v;
					
					if(countTwo <  FollowMotionProfile.position.size() && o > FollowMotionProfile.velocity.get(countTwo)){//&& countR == 0){//maxOutput MotionProfile.Points[count][1]) {
						o = FollowMotionProfile.velocity.get(countTwo);
						overRiddenL++;
					} 
					if(countTwo < FollowMotionProfile.position.size()){
						countTwo++;
					}
					RobotMap.leftMaster.pidWrite(o);

				}
			});
	    	pidPosRight = new PIDControllerHenry(driveTrain_kP, driveTrain_kI , 0, driveTrain_kF, new PIDSource() { // .58823
				PIDSourceType m_sourceType = PIDSourceType.kDisplacement;

				public double pidGet() {
					return RobotMap.driveTrainRightEncoder.getDistance();
				}

				@Override
				public void setPIDSourceType(PIDSourceType pidSource) {
					m_sourceType = pidSource;
				}

				@Override
				public PIDSourceType getPIDSourceType() {
					return m_sourceType;
				}
			}, new PIDOutput() {
				public void pidWrite(double d) {
					
					double output  = d;
					
					if(count < FollowMotionProfile.position.size() && output > FollowMotionProfile.velocity.get(count)){//&& countR == 0){//maxOutput MotionProfile.Points[count][1]) {
						output = FollowMotionProfile.velocity.get(count);
						overRiddenR++;
					} 
					
					if(count < FollowMotionProfile.velocity.size()){
						count++;
					}
					SmartDashboard.putNumber("overrideR", overRiddenR);
					
					if(maxSpeedReached < output){
						maxSpeedReached = output;
					}
					RobotMap.rightMaster.pidWrite(output);
					
				}
			});
	    	
	    	  pidAngle = new PIDControllerHenry(angleP,  angleI, angleD, new PIDSource() { // .58823
	    			PIDSourceType m_sourceType = PIDSourceType.kDisplacement;

	    			public double pidGet() {
	    				return RobotMap.gyro.getAngle();
	    			}

	    			@Override
	    			public void setPIDSourceType(PIDSourceType pidSource) {
	    				m_sourceType = pidSource;
	    			}

	    			@Override
	    			public PIDSourceType getPIDSourceType() {
	    				return m_sourceType;
	    			}
	    		}, new PIDOutput() {
	    			public void pidWrite(double d) {
	    				
	    				RobotMap.rightMaster.pidWrite(d);///2);
	    				RobotMap.leftMaster.pidWrite(-d);///2);
	    				
	    			}
	    		});
	    }
	    
    public void initDefaultCommand() {
        setDefaultCommand(new TankDrive()); 
    }
    
   
    public void updateDashboard() {
		SmartDashboard.putNumber("DriveTrain/Left Encoder Raw", driveTrainLeftEncoder.getRaw());
		SmartDashboard.putNumber("DriveTrain/Left Encoder Count", driveTrainLeftEncoder.get());
		SmartDashboard.putNumber("DriveTrain/Left Encoder Distance", driveTrainLeftEncoder.getDistance());
    	SmartDashboard.putNumber("DriveTrain/Left Encoder Rate", driveTrainLeftEncoder.getRate());
		
		SmartDashboard.putNumber("DriveTrain/Right Encoder Raw", driveTrainRightEncoder.getRaw());
		SmartDashboard.putNumber("DriveTrain/Right Encoder Count", driveTrainRightEncoder.get());
		SmartDashboard.putNumber("DriveTrain/Right Encoder Distance", driveTrainRightEncoder.getDistance());
		SmartDashboard.putNumber("DriveTrain/Right Encoder Rate", driveTrainRightEncoder.getRate());

    	SmartDashboard.putNumber("DriveTrain/DifferenceOfEncodersDistance:", Math.abs(driveTrainRightEncoder.getDistance() - driveTrainLeftEncoder.getDistance()));
		SmartDashboard.putNumber("DriveTrain/DifferenceOfEncodersRate:", Math.abs(driveTrainRightEncoder.getRate() - driveTrainLeftEncoder.getRate()));
		
		SmartDashboard.putNumber("DriveTrain/Gyro Angle", gyro.getAngle());
		
		SmartDashboard.putBoolean("DriveTrain/DriveTrainShifter state", driveTrainShifter.get());
	}
    
    public void driveRobot(Joystick joy, Joystick joy2){
		Robot.driveTrain.robotDrive.tankDrive(joy.getRawAxis(1), -joy2.getRawAxis(1));
    }

    public void stop(){
    	robotDrive.tankDrive(0, 0);
    }
    
    public boolean isDriveTrainShifterOpen(){
    	return driveTrainShifter.get();
    }
    
    public void setDriveTrainShifter(boolean open){
    	if(open){
    		driveTrainShifter.set(open);
    	}else{
    		driveTrainShifter.set(!open);
    	}
    }
}


