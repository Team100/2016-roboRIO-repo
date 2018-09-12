package org.usfirst.frc100.LegoArmWithEncoder.devices;
import edu.wpi.first.wpilibj.PWMSpeedController;
import edu.wpi.first.wpilibj.hal.FRCNetComm.tResourceType;
import edu.wpi.first.wpilibj.hal.HAL;
public class ParallaxContinuousRotationServo extends PWMSpeedController {

	public ParallaxContinuousRotationServo(final int pChannel) {
		super(pChannel);
		setBounds(1.7,1.5,1.5,1.5,1.3);
		setPeriodMultiplier(PeriodMultiplier.k4X);
		setSpeed(0.0);
		setZeroLatch();
		HAL.report(tResourceType.kResourceType_PWM, getChannel());
	    setName("ParallaxContServo", getChannel());
	}

}
