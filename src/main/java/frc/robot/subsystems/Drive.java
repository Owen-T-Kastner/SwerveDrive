
package frc.robot.subsystems;

import static edu.wpi.first.units.Units.RotationsPerSecond;

import org.littletonrobotics.junction.Logger;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.math.kinematics.SwerveDriveKinematics;
import edu.wpi.first.math.kinematics.SwerveModuleState;
import edu.wpi.first.units.measure.AngularVelocity;
import edu.wpi.first.units.measure.LinearVelocity;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.subsystems.Constants.DriveConstantsBL;
import frc.robot.subsystems.Constants.DriveConstantsBR;
import frc.robot.subsystems.Constants.DriveConstantsFL;
import frc.robot.subsystems.Constants.DriveConstantsFR;

public class Drive extends SubsystemBase{

    public Module[] modules = new Module[4];
    GyroIOPigeon2 gyro = new GyroIOPigeon2(new GyroConstants());
    public final GyroIOInputsAutoLogged gyroInputs = new GyroIOInputsAutoLogged();
    ModuleIO rightModule = new ModuleIOTalonFX(new DriveConstantsFR());
    ModuleIO leftModule = new ModuleIOTalonFX(new DriveConstantsFL());
    ModuleIO backRightModule = new ModuleIOTalonFX(new DriveConstantsBR());
    ModuleIO backLeftModule = new ModuleIOTalonFX(new DriveConstantsBL());
    Joystick joystick;
    Translation2d frPosition;
    Translation2d flPosition;
    Translation2d blPosition;
    Translation2d brPosition;
    SwerveDriveKinematics kinematics;

    public Drive(ModuleIO rightModule, ModuleIO leftModule, ModuleIO backRightModule, ModuleIO backLeftModule) {

        this.rightModule = rightModule;
        this.leftModule = leftModule;
        this.backRightModule = backRightModule;
        this.backLeftModule = backLeftModule;

        modules[0] = new Module(leftModule, 0);
        modules[1] = new Module(rightModule, 1);
        modules[2] = new Module(backLeftModule, 2);
        modules[3] = new Module(backRightModule, 3);

        frPosition = new Translation2d(11.375, -11.375);
        flPosition = new Translation2d(11.375, 11.375);
        blPosition = new Translation2d(-11.375, 11.375);
        brPosition = new Translation2d(-11.375, -11.375);
        kinematics = new SwerveDriveKinematics(flPosition, frPosition, blPosition, brPosition);
    }
    
    public void setSwerveValues(LinearVelocity VelocityX, LinearVelocity VelocityY, AngularVelocity AngleVelocity, Rotation2d currentAngle) {
        ChassisSpeeds chassis = ChassisSpeeds.fromFieldRelativeSpeeds(VelocityX, VelocityY, AngleVelocity, currentAngle);
        SwerveModuleState[] ModuleStates = kinematics.toSwerveModuleStates(chassis);

        ModuleStates[0].optimize(modules[0].getAngle());
        ModuleStates[1].optimize(modules[1].getAngle());
        ModuleStates[2].optimize(modules[2].getAngle());
        ModuleStates[3].optimize(modules[3].getAngle());

        modules[0].setVelocityDrive(RotationsPerSecond.of(ModuleStates[0].speedMetersPerSecond));
        modules[1].setVelocityDrive(RotationsPerSecond.of(ModuleStates[1].speedMetersPerSecond));
        modules[2].setVelocityDrive(RotationsPerSecond.of(ModuleStates[2].speedMetersPerSecond));
        modules[3].setVelocityDrive(RotationsPerSecond.of(ModuleStates[3].speedMetersPerSecond));

        modules[0].setTurnPosition(ModuleStates[0].angle.getMeasure());
        modules[1].setTurnPosition(ModuleStates[1].angle.getMeasure());
        modules[2].setTurnPosition(ModuleStates[2].angle.getMeasure());
        modules[3].setTurnPosition(ModuleStates[3].angle.getMeasure());
    }

    @Override
    public void periodic() {
        gyro.updateInputs(gyroInputs);
        Logger.processInputs("Drive/Gyro", gyroInputs);
    }
}