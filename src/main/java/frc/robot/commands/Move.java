package frc.robot.commands;

import static edu.wpi.first.units.Units.Degrees;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.Command;

public class Move extends Command {

    frc.robot.subsystems.Module[] modules;
    double joystickY;
    double joystickX;
    Joystick joystick;

    public Move(frc.robot.subsystems.Module[] modules , Joystick joystick) {
        this.modules = modules;
        this.joystick = joystick;
        System.out.println("move being called");
    }

    @Override
    public void initialize() {
    }

    @Override
    public void execute() {
        joystickY = joystick.getY();
        modules[0].setSpeedDrive((Math.signum(joystickY) * Math.pow(joystickY, 2)) * 60);
        modules[1].setSpeedDrive((Math.signum(joystickY) * Math.pow(joystickY, 2)) * 60);
        modules[2].setSpeedDrive((Math.signum(joystickY) * Math.pow(joystickY, 2)) * 60);
        modules[3].setSpeedDrive((Math.signum(joystickY) * Math.pow(joystickY, 2)) * 60);

        joystickX = joystick.getX() * 180;
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
    public boolean isFinished() {
        return false;
    }
}
