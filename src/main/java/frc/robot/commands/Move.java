package frc.robot.commands;

import static edu.wpi.first.units.Units.Degrees;
import static edu.wpi.first.units.Units.MetersPerSecond;
import static edu.wpi.first.units.Units.RotationsPerSecond;

import edu.wpi.first.units.measure.AngularVelocity;
import edu.wpi.first.units.measure.LinearVelocity;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Drive;

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

    public Move(Drive driver, frc.robot.subsystems.Module[] modules, Joystick rightJoystick, Joystick leftJoystick) {
        this.driver = driver;
        this.modules = modules;
        this.rightJoystick = rightJoystick;
        this.leftJoystick = leftJoystick;
    }

    @Override
    public void initialize() {
    }

    @Override
    public void execute() {

        VelocityY = MetersPerSecond.of(leftJoystick.getY() * 2);
        VelocityX = MetersPerSecond.of(leftJoystick.getX() * 2);
        AngleVelocity = RotationsPerSecond.of(rightJoystick.getX() * 0.1);

        driver.setSwerveValues(VelocityX, VelocityY, AngleVelocity);

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
