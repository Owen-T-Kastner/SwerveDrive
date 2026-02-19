
package frc.robot;

import edu.wpi.first.wpilibj.RobotBase;

public final class Constants {
  public static final Mode simMode = Mode.SIM;
  public static final Mode currentMode = RobotBase.isReal() ? Mode.REAL : simMode;
  public static final ControlScheme controlScheme = ControlScheme.OI;
  public static final RobotHardware robotHardware = RobotHardware.Swervles;

  public static enum Mode {
    REAL,
    SIM
  }

  public static enum ControlScheme {
    OI,
    XBOX
  }

  public static enum RobotHardware {
    Swervles,
    Rebuilt
  }
}
