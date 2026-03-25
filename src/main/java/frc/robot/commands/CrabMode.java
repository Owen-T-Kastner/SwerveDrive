package frc.robot.commands;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Drive;
import frc.robot.subsystems.Module;

import static edu.wpi.first.units.Units.Degrees;
import static edu.wpi.first.units.Units.RotationsPerSecond;

import edu.wpi.first.units.measure.Angle;

public class CrabMode extends Command{

    Joystick joystick;
    double joystickX;
    double joystickY;
    double joystickIntensity;
    double joystickIntensity2;
    double joystickAngle;
    Angle joystickAngle2;
    Drive driver;
    Module[] modules;

    public CrabMode(Drive driver) {
        this.modules = driver.modules;
    }

    @Override
    public void initialize() {
        joystick = new Joystick(0);
    }

    @Override
    public void execute() {

        joystickAngle = joystick.getDirectionDegrees();
        joystickIntensity = joystick.getMagnitude();

        joystickIntensity2 = (Math.signum(joystickIntensity) * Math.pow(joystickIntensity, 2)) * 60;
        joystickAngle2 = Degrees.of(joystickAngle);

        modules[0].setSpeedDrive(RotationsPerSecond.of(joystickIntensity2));
        modules[1].setSpeedDrive(RotationsPerSecond.of(joystickIntensity2));
        modules[2].setSpeedDrive(RotationsPerSecond.of(joystickIntensity2));
        modules[3].setSpeedDrive(RotationsPerSecond.of(joystickIntensity2));

        modules[0].setTurnPosition(joystickAngle2);
        modules[1].setTurnPosition(joystickAngle2);
        modules[2].setTurnPosition(joystickAngle2);
        modules[3].setTurnPosition(joystickAngle2);
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
    public boolean isFinished(){
        return false;
    }
}