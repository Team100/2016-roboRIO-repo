package org.usfirst.frc0.MyRobot;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Instrum {

	private static int _loops = 0;
	
	public static void Process(WPI_TalonSRX tal, StringBuilder sb)
	{
		/* smart dash plots */
    	SmartDashboard.putNumber("RPM", tal.getSelectedSensorVelocity(0));
    	SmartDashboard.putNumber("Pos",  tal.getSelectedSensorPosition(0));
    	SmartDashboard.putNumber("AppliedThrottle", (tal.getMotorOutputVoltage()/tal.getBusVoltage())*1023);
    	SmartDashboard.putNumber("ClosedLoopError", tal.getClosedLoopError(0));
    	if (tal.getControlMode() == ControlMode.MotionMagic) {
			//These API calls will be added in our next release.
    		//SmartDashboard.putNumber("ActTrajVelocity", tal.getMotionMagicActTrajVelocity());
    		//SmartDashboard.putNumber("ActTrajPosition", tal.getMotionMagicActTrajPosition());
    	}
    	/* periodically print to console */
        if(++_loops >= 10) {
        	_loops = 0;
        	System.out.println(sb.toString());
        }
        /* clear line cache */
        sb.setLength(0);
	}
}
