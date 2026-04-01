
package frc.robot;

import edu.wpi.first.units.measure.Distance;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.commands.CrabMode;
import frc.robot.commands.Move;
import frc.robot.subsystems.Drive;
import frc.robot.subsystems.GyroConstants;
import frc.robot.subsystems.GyroIO;
import frc.robot.subsystems.GyroIOPigeon2;
import frc.robot.subsystems.Constants.DriveConstantsBL;
import frc.robot.subsystems.Constants.DriveConstantsBR;
import frc.robot.subsystems.Constants.DriveConstantsFL;
import frc.robot.subsystems.Constants.DriveConstantsFR;

@SuppressWarnings("unused")
public class RobotContainer {

  Drive driver;
  GyroIOPigeon2 gyro;
  Joystick rightJoystick;
  Joystick leftJoystick;

  DriveConstantsBL blConstant;
  DriveConstantsBR brConstant;
  DriveConstantsFL flConstant;
  DriveConstantsFR frConstant;

  Distance wheelRadius;

  public RobotContainer() {
    configureBindings();
    rightJoystick = new Joystick(1);
    leftJoystick = new Joystick(0);
    driver = new Drive(wheelRadius);
    gyro = new GyroIOPigeon2(new GyroConstants());
  }

  public Command getTeleCommand(){
    //return new CrabMode(driver);
    return new Move(driver, gyro, driver.modules, rightJoystick, leftJoystick);
  }

  private void configureBindings() {}

  public Command getAutonomousCommand() {
    return null;
  }
}