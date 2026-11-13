package frc.robot.commands;

import static edu.wpi.first.units.Units.Degrees;
import static edu.wpi.first.units.Units.MetersPerSecond;
import static edu.wpi.first.units.Units.RadiansPerSecond;
import static edu.wpi.first.units.Units.RotationsPerSecond;

import org.littletonrobotics.junction.Logger;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.units.measure.AngularVelocity;
import edu.wpi.first.units.measure.LinearVelocity;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Drive;
import frc.robot.subsystems.GyroIO;
import frc.robot.subsystems.LimelightHelpers;
import frc.robot.subsystems.LimelightHelpers.RawDetection;

/*Get this command to find balls, get the txnc, and use a PID to set the txnc to ~ 0. Thats all for now. */

public class BallDetector extends Command {

    Drive driver;
    frc.robot.subsystems.Module[] modules;
    private LinearVelocity VelocityX;
    private LinearVelocity VelocityY;
    private AngularVelocity AngleVelocity;
    private Rotation2d currentAngle;
    private double deadband = 0.1;
    private double VelocityXDeadband;
    private double VelocityYDeadband;
    private GyroIO gyro;

    private double setpoint;
    private double kp;
    private double errorX;
    private double errorY;
    private double errorTheta;
    private double anglekp;

    public BallDetector(Drive driver, GyroIO gyro, frc.robot.subsystems.Module[] modules) {
        this.driver = driver;
        this.modules = modules;
        this.gyro = gyro;
    }

    @Override
    public void initialize() {
        setpoint = 1280 / 2;
        kp = 0.5;
        anglekp = 0.005;

    }

    @Override
    public void execute() {

        RawDetection[] lime = LimelightHelpers.getRawDetections("limelight-two");
        LimelightHelpers.setPipelineIndex("limelight-two", 1);

        errorX = setpoint - LimelightHelpers.getTX("limelight-two");
        errorY = 0;
        errorTheta = 0;

        VelocityYDeadband = MathUtil.applyDeadband(errorY, deadband);
        VelocityXDeadband = MathUtil.applyDeadband(errorX, deadband);

        AngleVelocity = RadiansPerSecond.of(errorTheta * anglekp);
        VelocityX = MetersPerSecond.of(VelocityXDeadband * kp);
        VelocityY = MetersPerSecond.of(VelocityYDeadband * kp);
        currentAngle = (gyro.getYawPosition(driver.gyroInputs));

        Logger.recordOutput("errorX", errorX);
        Logger.recordOutput("errorY", errorY);
        Logger.recordOutput("errorTheta", errorTheta);

        driver.setSwerveValues(VelocityY, VelocityX, AngleVelocity, currentAngle);
    }

    @Override
    public void end(boolean interrupted) {
        modules[0].setSpeedDrive(RotationsPerSecond.of(0));
        modules[0].setTurnPosition(Degrees.of(0));

        modules[1].setSpeedDrive(RotationsPerSecond.of(0));
        modules[1].setTurnPosition(Degrees.of(0));

        modules[2].setSpeedDrive(RotationsPerSecond.of(0));
        modules[2].setTurnPosition(Degrees.of(0));

        modules[3].setSpeedDrive(RotationsPerSecond.of(0));
        modules[3].setTurnPosition(Degrees.of(0));
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}
