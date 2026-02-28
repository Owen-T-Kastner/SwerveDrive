
package frc.robot.subsystems;

import edu.wpi.first.units.measure.Distance;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.subsystems.Constants.DriveConstantsBL;
import frc.robot.subsystems.Constants.DriveConstantsBR;
import frc.robot.subsystems.Constants.DriveConstantsFL;
import frc.robot.subsystems.Constants.DriveConstantsFR;

public class Drive extends SubsystemBase{

    public Module[] modules = new Module[4];
    GyroIO gyro;
    GyroIOInputsAutoLogged gyroInputs = new GyroIOInputsAutoLogged();
    ModuleIOTalonFX rightModule = new ModuleIOTalonFX(new DriveConstantsFR());
    ModuleIOTalonFX leftModule = new ModuleIOTalonFX(new DriveConstantsFL());
    ModuleIOTalonFX backRightModule = new ModuleIOTalonFX(new DriveConstantsBR());
    ModuleIOTalonFX backLeftModule = new ModuleIOTalonFX(new DriveConstantsBL());
    Joystick joystick;

    public Drive(Distance wheelRadius) {

        modules[0] = new Module(leftModule, 0, wheelRadius);
        modules[1] = new Module(rightModule, 1, wheelRadius);
        modules[2] = new Module(backLeftModule, 2, wheelRadius);
        modules[3] = new Module(backRightModule, 3, wheelRadius);
    }

    @Override
    public void periodic() {

    }
}