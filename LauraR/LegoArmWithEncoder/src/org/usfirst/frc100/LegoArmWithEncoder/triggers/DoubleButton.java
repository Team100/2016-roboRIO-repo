package org.usfirst.frc100.LegoArmWithEncoder.triggers;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.Trigger;

/**
 * A custom button that is triggered when two buttons on a Joystick are
 * simultaneously pressed.
 */
public class DoubleButton extends Trigger {
  private final Joystick m_joy;
  private final int m_button1;
  private final int m_button2;

  /**
   * Create a new double button trigger.
   * @param joy The joystick
   * @param button1 The first button
   * @param button2 The second button
   */
  public DoubleButton(Joystick joy, int button1, int button2) {
    this.m_joy = joy;
    this.m_button1 = button1;
    this.m_button2 = button2;
  }

  @Override
  public boolean get() {
    return m_joy.getRawButton(m_button1) && m_joy.getRawButton(m_button2);
  }
}
