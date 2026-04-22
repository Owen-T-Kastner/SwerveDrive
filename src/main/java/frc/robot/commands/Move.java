package frc.robot.commands;

import static edu.wpi.first.units.Units.Degrees;
import static edu.wpi.first.units.Units.MetersPerSecond;
import static edu.wpi.first.units.Units.RotationsPerSecond;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.units.measure.AngularVelocity;
import edu.wpi.first.units.measure.LinearVelocity;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Drive;
import frc.robot.subsystems.GyroIO;

public class Move extends Command {

    Drive driver;
    frc.robot.subsystems.Module[] modules;
    // private double joystickIntensity;
    // private double joystickIntensity2;
    // private double joystickAngle;
    // private Angle joystickAngle2;
    private Joystick rightJoystick;
    private Joystick leftJoystick;
    private LinearVelocity VelocityX;
    private LinearVelocity VelocityY;
    private AngularVelocity AngleVelocity;
    private Rotation2d currentAngle;
    private double deadband = 0.1;
    private double VelocityXDeadband;
    private double VelocityYDeadband;
    private double Angle;
    private double X;
    private double Y;
    private GyroIO gyro;

    public Move(Drive driver, GyroIO gyro, frc.robot.subsystems.Module[] modules, Joystick rightJoystick, Joystick leftJoystick) {
        this.driver = driver;
        this.modules = modules;
        this.rightJoystick = rightJoystick;
        this.leftJoystick = leftJoystick;
        this.gyro = gyro;
    }

    @Override
    public void initialize() {}

    @Override
    public void execute() {

        Y = (-(Math.signum(leftJoystick.getX()) * Math.pow(leftJoystick.getX(), 2)));
        X = (-(Math.signum(leftJoystick.getY()) * Math.pow(leftJoystick.getY(), 2)));
        Angle = (rightJoystick.getX() * 0.01);
        currentAngle = (gyro.getYawPosition(driver.gyroInputs));

        VelocityYDeadband = MathUtil.applyDeadband(Y, deadband);
        VelocityXDeadband = MathUtil.applyDeadband(X, deadband);

        AngleVelocity = RotationsPerSecond.of(Angle);
        VelocityX = MetersPerSecond.of(VelocityXDeadband);
        VelocityY = MetersPerSecond.of(VelocityYDeadband);

        driver.setSwerveValues(VelocityX, VelocityY, AngleVelocity, currentAngle);

        // joystickIntensity = rightJoystick.getMagnitude();
        // joystickIntensity2 = (Math.signum(joystickIntensity) * Math.pow(joystickIntensity, 2)) * 60;

        // joystickAngle = rightJoystick.getDirectionDegrees();
        // joystickAngle2 = Degrees.of(joystickAngle);
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
