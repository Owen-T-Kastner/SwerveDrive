
package frc.robot.subsystems;

import static edu.wpi.first.units.Units.RotationsPerSecond;

import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.math.kinematics.SwerveDriveKinematics;
import edu.wpi.first.math.kinematics.SwerveModuleState;
import edu.wpi.first.units.measure.AngularVelocity;
import edu.wpi.first.units.measure.Distance;
import edu.wpi.first.units.measure.LinearVelocity;
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
    Translation2d frPosition;
    Translation2d flPosition;
    Translation2d blPosition;
    Translation2d brPosition;
    SwerveDriveKinematics kinematics;

    public Drive(Distance wheelRadius) {
        modules[0] = new Module(leftModule, 0, wheelRadius);
        modules[1] = new Module(rightModule, 1, wheelRadius);
        modules[2] = new Module(backLeftModule, 2, wheelRadius);
        modules[3] = new Module(backRightModule, 3, wheelRadius);

        frPosition = new Translation2d(11.375, 11.375);
        flPosition = new Translation2d(11.375, -11.375);
        blPosition = new Translation2d(-11.375, -11.375);
        brPosition = new Translation2d(-11.375, 11.375);
        kinematics = new SwerveDriveKinematics(flPosition, frPosition, blPosition, brPosition);
    }
    
    public void setSwerveValues(LinearVelocity VelocityX, LinearVelocity VelocityY, AngularVelocity AngleVelocity) {
        ChassisSpeeds chassisSpeeds = new ChassisSpeeds(VelocityX, VelocityY, AngleVelocity);
        SwerveModuleState[] ModuleStates = kinematics.toSwerveModuleStates(chassisSpeeds);

        modules[0].setSpeedDrive(RotationsPerSecond.of(ModuleStates[0].speedMetersPerSecond));
        modules[1].setSpeedDrive(RotationsPerSecond.of(ModuleStates[1].speedMetersPerSecond));
        modules[2].setSpeedDrive(RotationsPerSecond.of(ModuleStates[2].speedMetersPerSecond));
        modules[3].setSpeedDrive(RotationsPerSecond.of(ModuleStates[3].speedMetersPerSecond));

        modules[0].setTurnPosition(ModuleStates[0].angle.getMeasure());
        modules[1].setTurnPosition(ModuleStates[1].angle.getMeasure());
        modules[2].setTurnPosition(ModuleStates[2].angle.getMeasure());
        modules[3].setTurnPosition(ModuleStates[3].angle.getMeasure());
    }

    @Override
    public void periodic() {

    }
}