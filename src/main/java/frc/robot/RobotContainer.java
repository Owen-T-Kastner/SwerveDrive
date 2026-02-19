
package frc.robot;

import edu.wpi.first.units.measure.Distance;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.commands.Move;
import frc.robot.subsystems.Drive;
import frc.robot.subsystems.Module;
import frc.robot.subsystems.ModuleIOTalonFX;

public class RobotContainer {

  Joystick joystick;

  Distance wheelRadius;
  ModuleIOTalonFX talonFX;
  Module Module;
  Drive driver;

  public RobotContainer() {
    configureBindings();
    talonFX = new ModuleIOTalonFX();
    joystick = new Joystick(0);
    Module = new Module(talonFX, 0, wheelRadius);
    // driver.setDefaultCommand(mover);
    
  }

  public Command getTeleCommand(){
    return new Move(Module, joystick);
  }

  private void configureBindings() {}

  public Command getAutonomousCommand() {
    return null;
  }
}