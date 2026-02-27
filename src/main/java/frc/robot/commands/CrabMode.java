package frc.robot.commands;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Drive;
import frc.robot.subsystems.Module;

import static edu.wpi.first.units.Units.Degrees;

public class CrabMode extends Command{

    Joystick leftJoystick;
    Joystick rightJoystick;
    double joystickX;
    double joystickY;
    Drive driver;
    Module[] modules;

    public CrabMode(Drive driver) {
        this.modules = driver.modules;
    }

    @Override
    public void initialize() {
        leftJoystick = new Joystick(0);
        rightJoystick = new Joystick(1);
    }

    @Override
    public void execute() {

        joystickY = leftJoystick.getY();
        modules[0].setSpeedDrive((Math.signum(joystickY) * Math.pow(joystickY, 2)) * 60);
        modules[1].setSpeedDrive((Math.signum(joystickY) * Math.pow(joystickY, 2)) * 60);
        modules[2].setSpeedDrive((Math.signum(joystickY) * Math.pow(joystickY, 2)) * 60);
        modules[3].setSpeedDrive((Math.signum(joystickY) * Math.pow(joystickY, 2)) * 60);

        joystickX = rightJoystick.getX() * 180;
        modules[0].setTurnPosition(Degrees.of(joystickX));
        modules[1].setTurnPosition(Degrees.of(joystickX));
        modules[2].setTurnPosition(Degrees.of(joystickX));
        modules[4].setTurnPosition(Degrees.of(joystickX));
    }

    @Override
    public void end(boolean interrupted) {

        modules[0].setSpeedDrive(0.0);
        modules[0].setTurnPosition(Degrees.of(0));

        modules[1].setSpeedDrive(0.0);
        modules[1].setTurnPosition(Degrees.of(0));

        modules[2].setSpeedDrive(0.0);
        modules[2].setTurnPosition(Degrees.of(0));

        modules[3].setSpeedDrive(0.0);
        modules[3].setTurnPosition(Degrees.of(0));
    }

    @Override
    public boolean isFinished(){
        return false;
    }
}