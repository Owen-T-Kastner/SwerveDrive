package frc.robot.commands;

import static edu.wpi.first.units.Units.Degrees;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.Command;

public class Move extends Command{
    
    frc.robot.subsystems.Module module;
    double joystickY;
    double joystickX;
    Joystick joystick;

    public Move(frc.robot.subsystems.Module module, Joystick joystick) {
        this.module = module;
        this.joystick = joystick;
        System.out.println("move being called");
    }

    @Override
    public void initialize() {
    }

    @Override
    public void execute() {
        joystickY = joystick.getY();
        module.setSpeedDrive((Math.signum(joystickY) * Math.pow(joystickY, 2)) * 10);
        //System.out.println("Y: " + joystickY);
        //System.out.println("speedDrive: " + (Math.signum(joystickY) * Math.pow(joystickY, 2)));

        joystickX = joystick.getX() * 180;
        module.setTurnPosition(Degrees.of(joystickX));
        System.out.println("X: " + joystickX); 
    }

    @Override
    public void end(boolean interrupted) {
        module.setSpeedDrive(0.0);
        module.setTurnPosition(Degrees.of(0));
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}

