
package frc.robot;

import edu.wpi.first.units.measure.Distance;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.commands.CrabMode;
import frc.robot.commands.Move;
import frc.robot.subsystems.Drive;
import frc.robot.subsystems.ModuleIOTalonFX;
import frc.robot.subsystems.Constants.DriveConstantsBL;
import frc.robot.subsystems.Constants.DriveConstantsBR;
import frc.robot.subsystems.Constants.DriveConstantsFL;
import frc.robot.subsystems.Constants.DriveConstantsFR;

public class RobotContainer {

  Drive driver;
  Joystick joystick;

  ModuleIOTalonFX leftModule;
  ModuleIOTalonFX rightModule;
  ModuleIOTalonFX backLeftModule;
  ModuleIOTalonFX backRightModule;

  DriveConstantsBL blConstant;
  DriveConstantsBR brConstant;
  DriveConstantsFL flConstant;
  DriveConstantsFR frConstant;

  Distance wheelRadius;

  public RobotContainer() {
    configureBindings();
    joystick = new Joystick(0);
    driver = new Drive(rightModule, leftModule, backRightModule, backLeftModule, wheelRadius);
  }

  public Command getTeleCommand(){
    return new CrabMode(driver);
    //return new Move(driver.modules, joystick);
  }

  private void configureBindings() {}

  public Command getAutonomousCommand() {
    return null;
  }
}