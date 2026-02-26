
package frc.robot;

import edu.wpi.first.units.measure.Distance;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.commands.Move;
import frc.robot.subsystems.Drive;
import frc.robot.subsystems.Module;
import frc.robot.subsystems.ModuleIOTalonFX;
import frc.robot.subsystems.Constants.DriveConstantsBL;
import frc.robot.subsystems.Constants.DriveConstantsBR;
import frc.robot.subsystems.Constants.DriveConstantsFL;
import frc.robot.subsystems.Constants.DriveConstantsFR;

public class RobotContainer {

  Joystick joystick;

  Distance wheelRadius;
  ModuleIOTalonFX leftModule;
  ModuleIOTalonFX rightModule;
  ModuleIOTalonFX backLeftModule;
  ModuleIOTalonFX backRightModule;
  
  Drive driver;
  Module[] modules;

  DriveConstantsBL blConstant;
  DriveConstantsBR brConstant;
  DriveConstantsFL flConstant;
  DriveConstantsFR frConstant;


  public RobotContainer() {
    configureBindings();
    joystick = new Joystick(0);
    driver = new Drive(rightModule, leftModule, backRightModule, backLeftModule, wheelRadius);
  }

  public Command getTeleCommand(){
    return new Move(driver.modules, joystick);
  }

  private void configureBindings() {}

  public Command getAutonomousCommand() {
    return null;
  }
}