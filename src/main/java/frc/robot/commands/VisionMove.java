package frc.robot.commands;

import static edu.wpi.first.units.Units.Degrees;
import static edu.wpi.first.units.Units.DegreesPerSecond;
import static edu.wpi.first.units.Units.MetersPerSecond;
import static edu.wpi.first.units.Units.RotationsPerSecond;

import org.littletonrobotics.junction.Logger;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.VecBuilder;
import edu.wpi.first.math.estimator.PoseEstimator;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Pose3d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.Kinematics;
import edu.wpi.first.units.measure.AngularVelocity;
import edu.wpi.first.units.measure.LinearVelocity;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Drive;
import frc.robot.subsystems.GyroIO;
import frc.robot.subsystems.LimelightHelpers;

public class VisionMove extends Command {

    Drive driver;
    frc.robot.subsystems.Module[] modules;
    // private double joystickIntensity;
    // private double joystickIntensity2;
    // private double joystickAngle;
    // private Angle joystickAngle2;
    private Joystick rightJoystick;
    private Joystick leftJoystick;
    private LinearVelocity VelocityX;
    private LinearVelocity VelocityZ;
    private AngularVelocity AngleVelocity;
    private Rotation2d currentAngle;
    private double deadband = 0.1;
    private double VelocityXDeadband;
    private double VelocityYDeadband;
    private double Angle;
    private double X;
    private GyroIO gyro;

    private double errorX;
    private double setpoint;
    private double kp;
    private double errorZ;

    @SuppressWarnings("rawtypes")
    PoseEstimator poseEstimator;

    public VisionMove(Drive driver, GyroIO gyro, frc.robot.subsystems.Module[] modules, Joystick rightJoystick,
            Joystick leftJoystick) {
        this.driver = driver;
        this.modules = modules;
        this.rightJoystick = rightJoystick;
        this.leftJoystick = leftJoystick;
        this.gyro = gyro;
    }

    @Override
    public void initialize() {
        setpoint = 1.0;
        kp = 0.5;
    }

    @Override
    public void execute() {

        Pose3d lemon = LimelightHelpers.toPose3D(LimelightHelpers.getCameraPose_TargetSpace("limelight-one"));

        errorX = setpoint - lemon.getX();
        errorZ = setpoint + lemon.getZ();
        
        VelocityX = MetersPerSecond.of(errorX * kp); 
        VelocityZ = MetersPerSecond.of(errorZ * kp);        
        AngleVelocity = DegreesPerSecond.of(0);
        currentAngle = (gyro.getYawPosition(driver.gyroInputs));

        driver.setSwerveValues(VelocityZ, VelocityX, AngleVelocity, currentAngle);

        Logger.recordOutput("limelightcamera", lemon);
        Logger.recordOutput("errorX", errorX);
        Logger.recordOutput("errorZ", errorZ);
        Logger.recordOutput("z value", lemon.getZ());
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
