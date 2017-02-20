package org.usfirst.frc100.RobotAndrew.commands;
import com.ctre.CANTalon;
import edu.wpi.first.wpilibj.Notifier;
import com.ctre.CANTalon.SetValueMotionProfile;
import com.ctre.CANTalon.TalonControlMode;

	public class LoadMotionProfile {
		private CANTalon.MotionProfileStatus _status = new CANTalon.MotionProfileStatus();
		private CANTalon talon1;
		private int state = 0;
		private int loopTimeO = -1;
		private boolean start = false;
		private CANTalon.SetValueMotionProfile setVal = CANTalon.SetValueMotionProfile.Disable;
		private int  minPoints= 5;
		private int timeout = 10;
		
		class PeriodicRunnable implements java.lang.Runnable {
		    public void run() {  talon1.processMotionProfileBuffer();    }
		}
		Notifier _notifer = new Notifier(new PeriodicRunnable());
		public LoadMotionProfile(CANTalon talon) {
			talon1 = talon;
			talon1.changeMotionControlFramePeriod(5);
			_notifer.startPeriodic(0.005);
		}
		public void reset() {
			talon1.clearMotionProfileTrajectories();
			setVal = CANTalon.SetValueMotionProfile.Disable;
			state = 0;
			loopTimeO = -1;
			start = false;
		}
		public void control() {
			talon1.getMotionProfileStatus(_status);
			if (loopTimeO < 0) {
			} else {
				if (loopTimeO == 0) {
					instrumentation.OnNoProgress();
				} else {
					--loopTimeO;
				}
			}
			
			if (talon1.getControlMode() != TalonControlMode.MotionProfile) {
				state = 0;
				loopTimeO = -1;
			} else {
					if(state == 0){
						if (start) {
							start = false;
							setVal = CANTalon.SetValueMotionProfile.Disable;
							startFilling();
							state = 1;
							loopTimeO = timeout;
						}
					}
					 
					if(state == 1) {
						if (_status.btmBufferCnt > minPoints) {
							setVal = CANTalon.SetValueMotionProfile.Enable;
							state = 2;
							loopTimeO = timeout;
						}
					}
					if(state == 2) {

						if (_status.isUnderrun == false) {
							loopTimeO = timeout;
						}
						
						if (_status.activePointValid && _status.activePoint.isLastPoint) {
							
							setVal = CANTalon.SetValueMotionProfile.Hold;
							state = 0;
							loopTimeO = -1;
						}
					}
				}
			instrumentation.process(_status);
		}		
		private void startFilling() {
			startFilling(MotionProfile.Points, MotionProfile.kNumPoints);
		}

		private void startFilling(double[][] profile, int totalCnt) {

			CANTalon.TrajectoryPoint point = new CANTalon.TrajectoryPoint();

			if (_status.hasUnderrun) {
				instrumentation.OnUnderrun();
				talon1.clearMotionProfileHasUnderrun();
			}
			talon1.clearMotionProfileTrajectories();
			for (int i = 0; i < totalCnt; ++i) {
				point.position = profile[i][0];
				point.velocity = profile[i][1];
				point.timeDurMs = (int) profile[i][2];
				point.profileSlotSelect = 0; 
				point.velocityOnly = false; 
				point.zeroPos = false;
				if (i == 0)
					point.zeroPos = true; 
				
				point.isLastPoint = false;
				if ((i + 1) == totalCnt)
					point.isLastPoint = true; 

				talon1.pushMotionProfileTrajectory(point);
			}
		}

		void startMotionProfile() {
			start = true;
		}
		CANTalon.SetValueMotionProfile getSetValue() {
			return setVal;
		}
	}

