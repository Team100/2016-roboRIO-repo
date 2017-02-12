// RobotBuilder Version: 2.0
//
// This file was generated by RobotBuilder. It contains sections of
// code that are automatically generated and assigned by robotbuilder.
// These sections will be updated in the future when you export to
// Java from RobotBuilder. Do not put any code or make any change in
// the blocks indicating autogenerated code or it will be lost on an
// update. Deleting the comments indicating the section will prevent
// it from being updated in the future.


package org.usfirst.frc100.RobotAndrew.subsystems;


import java.util.Timer;


import org.usfirst.frc100.RobotAndrew.Robot;
import org.usfirst.frc100.RobotAndrew.RobotMap;
import org.usfirst.frc100.RobotAndrew.commands.*;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.Talon;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;



/**
 *
 */
public class DriveTrain extends Subsystem {
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
	public int overRiddenR = 0;
    public int overRiddenL = 0;

    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTANTS

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTANTS
	//private FollowMotionProfile FMP = new FollowMotionProfile();
    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS
    private final SpeedController right = RobotMap.driveTrainright;
    private final SpeedController left = RobotMap.driveTrainleft;
    private final RobotDrive robotDrive = RobotMap.driveTrainRobotDrive;
    public PIDController pidPosLeft;
    public PIDController pidPosRight;
    public PIDController pidVelLeft;
    public PIDController pidVelRight;
    public PIDController pidAngle;
    public double maxOutput;
    public double maxSpeedReached = 0;
    public int countL = 0;
    public int countR = 0;
    public int count = 0;
    public int countTwo = 0;
    public double velLeft; 
    public double velRight;
    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS

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
		
		pidVelRight = new PIDController(driveVelP, driveVelI , driveVelF, new PIDSource() { // .58823
			PIDSourceType m_sourceType = PIDSourceType.kRate;

			public double pidGet() {
				return RobotMap.encoderRight.getRate();
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
		
		pidVelLeft = new PIDController(driveVelP, driveVelI , driveVelF, new PIDSource() { // .58823
			PIDSourceType m_sourceType = PIDSourceType.kRate;

			public double pidGet() {
				return RobotMap.encoderLeft.getRate();
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
    	
    	pidPosLeft = new PIDController(driveTrain_kP, driveTrain_kI, 0, driveTrain_kF, new PIDSource() { // .04 0 0 for 180
			PIDSourceType m_sourceType = PIDSourceType.kDisplacement;

			public double pidGet() {
				return (RobotMap.encoderLeft.getDistance() * -1);
				
				
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
				SmartDashboard.putNumber("overrideL", overRiddenL);
			}
		});
//pid.setPID(p, i, d);
    	pidPosRight = new PIDController(driveTrain_kP, driveTrain_kI , 0, driveTrain_kF, new PIDSource() { // .58823
			PIDSourceType m_sourceType = PIDSourceType.kDisplacement;

			public double pidGet() {
				return RobotMap.encoderRight.getDistance();
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
				SmartDashboard.putNumber("counter", count);
				SmartDashboard.putNumber("output to motor", output);
				
				
			}
		});
    	
    	  pidAngle = new PIDController(0,  0, 0, new PIDSource() { // .58823
    			PIDSourceType m_sourceType = PIDSourceType.kDisplacement;

    			public double pidGet() {
    				return RobotMap.internalGyro.getAngle();
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
    				
    				RobotMap.rightMaster.pidWrite(d);
    				RobotMap.leftMaster.pidWrite(-d);
    				
    			}
    		});
    }
    
  

    // Put methods for controlling this subsystem
    // here. Call these from Commands.

    public void initDefaultCommand() {
        // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DEFAULT_COMMAND

        setDefaultCommand(new ArcadeDrive());

        // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DEFAULT_COMMAND

        // Set the default command for a subsystem here.
        // setDefaultCommand(new MySpecialCommand());
    }
    
   // public void driveRobot(Joystick joy){
    	
    //}

	public void driveRobot(Joystick joy) {
		robotDrive.tankDrive(-joy.getRawAxis(1), joy.getRawAxis(5));
		//RobotMap.leftMaster.set(joy.getRawAxis(1));
		SmartDashboard.putNumber("joystick output",joy.getRawAxis(1));//, -joystick1.getRawAxis(2));
		// TODO Auto-generated method stub
		
	}

	public void stop() {
		robotDrive.arcadeDrive(0, 0);
		countTwo = 0;
		count = 0;
		// TODO Auto-generated method stub
		
	}
}

