
package frc.robot;

import static edu.wpi.first.units.Units.Inches;

import org.ironmaple.simulation.drivesims.COTS;
import org.ironmaple.simulation.drivesims.GyroSimulation;
import org.ironmaple.simulation.drivesims.SwerveDriveSimulation;
import org.ironmaple.simulation.drivesims.SwerveModuleSimulation;
import org.ironmaple.simulation.drivesims.configs.DriveTrainSimulationConfig;
import org.ironmaple.simulation.drivesims.configs.SwerveModuleSimulationConfig;

import com.ctre.phoenix6.swerve.SwerveModuleConstants;
import com.fasterxml.jackson.databind.Module;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.system.plant.DCMotor;
import edu.wpi.first.units.measure.Distance;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.commands.CrabMode;
import frc.robot.commands.Move;
import frc.robot.subsystems.Drive;
import frc.robot.subsystems.GyroConstants;
import frc.robot.subsystems.GyroIO;
import frc.robot.subsystems.GyroIOPigeon2;
import frc.robot.subsystems.GyroIOSim;
import frc.robot.subsystems.ModuleIOSim;
import frc.robot.subsystems.ModuleIOTalonFX;
import frc.robot.subsystems.Constants.DriveConstantsBL;
import frc.robot.subsystems.Constants.DriveConstantsBR;
import frc.robot.subsystems.Constants.DriveConstantsFL;
import frc.robot.subsystems.Constants.DriveConstantsFR;

@SuppressWarnings("unused")
public class RobotContainer {

  Drive driver;
  Drive driveSim;
  GyroIOPigeon2 gyro;
  GyroIO gyroSim;
  Joystick rightJoystick;
  Joystick leftJoystick;
  GyroSimulation gyroS;
  Distance wheelRadius;
  SwerveDriveSimulation swerveSim;
  DriveTrainSimulationConfig config = null;
  DriveConstantsBL blConstant = new DriveConstantsBL();
  DriveConstantsBR brConstant = new DriveConstantsBR();
  DriveConstantsFL flConstant = new DriveConstantsFL();
  DriveConstantsFR frConstant = new DriveConstantsFR();

  public RobotContainer() {
    configureBindings();
    rightJoystick = new Joystick(1);
    leftJoystick = new Joystick(0);

    config = DriveTrainSimulationConfig.Default()
      .withGyro(COTS.ofPigeon2())
      .withSwerveModule(COTS.ofMark4(DCMotor.getKrakenX60(1), DCMotor.getFalcon500(1), COTS.WHEELS.COLSONS.cof, 3))
      .withTrackLengthTrackWidth(Inches.of(24), Inches.of(24))
      .withBumperSize(Inches.of(30), Inches.of(30));

    swerveSim = new SwerveDriveSimulation(config, new Pose2d(3, 3, new Rotation2d()));
    driver = 
      new Drive(new ModuleIOTalonFX(frConstant), new ModuleIOTalonFX(flConstant), new ModuleIOTalonFX(brConstant), new ModuleIOTalonFX(blConstant));
    driveSim = 
      new Drive(new ModuleIOSim(swerveSim.getModules()[0]), new ModuleIOSim(swerveSim.getModules()[1]), new ModuleIOSim(swerveSim.getModules()[2]), new ModuleIOSim(swerveSim.getModules()[3]));
    gyro = new GyroIOPigeon2(new GyroConstants());
    gyroSim = new GyroIOSim(gyroS);
  }

  public Command getTeleCommand(){
    //return new CrabMode(driver); //Crabmode Drive
    //return new Move(driveSim, gyroSim, driveSim.modules, rightJoystick, leftJoystick); //Sim Drive
    return new Move(driver, gyro, driver.modules, rightJoystick, leftJoystick); //Regular Drive
  }

  private void configureBindings() {}

  public Command getAutonomousCommand() {
    return null; 
  }
}