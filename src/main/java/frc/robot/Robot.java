
package frc.robot;

import org.littletonrobotics.junction.LoggedRobot;
import org.littletonrobotics.junction.Logger;
import org.littletonrobotics.junction.networktables.NT4Publisher;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;


public class Robot extends LoggedRobot {
  
  private Command autoCommand;

  private final RobotContainer robotContainer;


  public Robot() {
    Logger.recordMetadata("ProjectName", "TankDrive"); 
    Logger.addDataReceiver(new NT4Publisher());
    Logger.start();
    robotContainer = new RobotContainer();
  }

  @Override
  public void robotPeriodic() {

    CommandScheduler.getInstance().run();
  }

  @Override
  public void disabledInit() {}

  @Override
  public void disabledPeriodic() {}

  @Override
  public void autonomousInit() {
    autoCommand = robotContainer.getAutonomousCommand();

    if (autoCommand != null) {
      CommandScheduler.getInstance().schedule(autoCommand);
    }
  }

  @Override
  public void autonomousPeriodic() {}

  @Override
  public void teleopInit() {

    if (autoCommand != null) {
      autoCommand.cancel();
    }
    robotContainer.getTeleCommand().schedule();
  }


  @Override
  public void teleopPeriodic() {}

  @Override
  public void testInit() {
    CommandScheduler.getInstance().cancelAll();
  }

  @Override
  public void testPeriodic() {}

  @Override
  public void simulationInit() {}

  @Override
  public void simulationPeriodic() {}
}
