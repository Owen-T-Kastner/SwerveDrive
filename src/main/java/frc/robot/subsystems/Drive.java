
package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Drive extends SubsystemBase{

    Module[] modules = new Module[4];
        ModuleIO rightModule;
        ModuleIO leftModule;
        ModuleIO backRightModule;
        ModuleIO backLeftModule;

    public Drive(ModuleIO rightModule, ModuleIO leftModule, ModuleIO backRightModule,ModuleIO backLeftModule) {
/*
        modules[0] = new Module(leftModule, 0, tunerConstants.getFrontLeft());
        modules[1] = new Module(rightModule, 1, tunerConstants.getFrontRight());
        modules[2] = new Module(backLeftModule, 2, tunerConstants.getBackLeft());
        modules[3] = new Module(backRightModule, 3, tunerConstants.getBackRight()); */
    }
}