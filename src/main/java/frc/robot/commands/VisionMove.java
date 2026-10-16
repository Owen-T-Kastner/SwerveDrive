package frc.robot.commands;

import static edu.wpi.first.units.Units.Degrees;
import static edu.wpi.first.units.Units.DegreesPerSecond;
import static edu.wpi.first.units.Units.MetersPerSecond;
import static edu.wpi.first.units.Units.RadiansPerSecond;
import static edu.wpi.first.units.Units.RotationsPerSecond;

import java.util.ArrayList;

import org.littletonrobotics.junction.Logger;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.VecBuilder;
import edu.wpi.first.math.estimator.PoseEstimator;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Pose3d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Rotation3d;
import edu.wpi.first.math.kinematics.Kinematics;
import edu.wpi.first.units.measure.AngularVelocity;
import edu.wpi.first.units.measure.LinearVelocity;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Drive;
import frc.robot.subsystems.GyroIO;
import frc.robot.subsystems.LimelightHelpers;

public class VisionMove extends Command {

    Drive driver;
    frc.robot.subsystems.Module[] modules;
    // private double joystickIntensity;
    // private double joystickIntensity2;
    // private double joystickAngle;
    // private Angle joystickAngle2;
    private Joystick rightJoystick;
    private Joystick leftJoystick;
    private LinearVelocity VelocityX;
    private LinearVelocity VelocityZ;
    private AngularVelocity AngleVelocity;
    private Rotation2d currentAngle;
    private double deadband = 0.1;
    private double VelocityXDeadband;
    private double VelocityZDeadband;
    private double Angle;
    private double X;
    private GyroIO gyro;

    private double errorX;
    private double setpoint;
    private double kp;
    private double kpZ;
    private double anglekp;
    private double errorZ;
    private double errorTheta;
    private double theta;
    private double tagX;
    private double tagZ;

    private ArrayList<Double> rollingAverageArrayZ;
    private ArrayList<Double> rollingAverageArrayX;
    private ArrayList<Double> rollingAverageArrayTheta;
    private double rollingAverageZ;
    private double rollingAverageX;
    private double rollingAverageTheta;

    @SuppressWarnings("rawtypes")
    PoseEstimator poseEstimator;

    public VisionMove(Drive driver, GyroIO gyro, frc.robot.subsystems.Module[] modules, Joystick rightJoystick,
            Joystick leftJoystick) {
        this.driver = driver;
        this.modules = modules;
        this.rightJoystick = rightJoystick;
        this.leftJoystick = leftJoystick;
        this.gyro = gyro;
        rollingAverageArrayZ = new ArrayList<Double>();
        rollingAverageArrayX = new ArrayList<Double>();
        rollingAverageArrayTheta = new ArrayList<Double>();
    }

    @Override
    public void initialize() {
        setpoint = 1.0;
        kp = 0.5;
        anglekp = 0.005;
    }

    @Override
    public void execute() {

        Pose3d lemon = LimelightHelpers.toPose3D(LimelightHelpers.getCameraPose_TargetSpace("limelight-one"));
    
        tagX = lemon.getX();
        tagZ = lemon.getZ();
        theta = Math.atan2(tagX, tagZ);

        rollingAverageArrayZ.add(tagZ);
        if (rollingAverageArrayZ.size() > 5) {
            rollingAverageArrayZ.remove(0);
        } 

        for (int i = 0; i < rollingAverageArrayZ.size(); i++) {
            if (tagZ == rollingAverageArrayZ.get(rollingAverageArrayZ.size() - 1)) { 
                
            }
            rollingAverageZ = rollingAverageZ + rollingAverageArrayZ.get(i);
        }
        rollingAverageZ = rollingAverageZ / rollingAverageArrayZ.size();

        
        rollingAverageArrayX.add(tagX);
        if (rollingAverageArrayX.size() > 5) {
            rollingAverageArrayX.remove(0);
        } 

        for (int i = 0; i < rollingAverageArrayX.size(); i++) {
            rollingAverageX = rollingAverageX + rollingAverageArrayX.get(i);
        }
        rollingAverageX = rollingAverageX / rollingAverageArrayX.size();


        rollingAverageArrayTheta.add(theta);
        if (rollingAverageArrayTheta.size() > 5) {
            rollingAverageArrayTheta.remove(0);
        } 

        for (int i = 0; i < rollingAverageArrayTheta.size(); i++) {
            rollingAverageTheta = rollingAverageTheta + rollingAverageArrayTheta.get(i);
        }
        rollingAverageTheta = rollingAverageTheta / rollingAverageArrayTheta.size();


        errorX = -rollingAverageX;
        errorZ = setpoint + rollingAverageZ;
        errorTheta = -rollingAverageTheta;
        
        kpZ = kp * (1- (rollingAverageZ * 0.01));

        VelocityX = MetersPerSecond.of(errorX * kp); 
        VelocityZ = MetersPerSecond.of(errorZ * kpZ);        
        AngleVelocity = RadiansPerSecond.of(errorTheta * anglekp);
        currentAngle = (gyro.getYawPosition(driver.gyroInputs));

        Logger.recordOutput("limelightcamera", lemon);
        Logger.recordOutput("errorX", errorX);
        Logger.recordOutput("errorZ", errorZ);
        Logger.recordOutput("theta error", errorTheta);
        Logger.recordOutput("z value", lemon.getZ());
        Logger.recordOutput("camera angle", lemon.getRotation().getAngle());
        Logger.recordOutput("rolling average z", rollingAverageZ);
        Logger.recordOutput("rolling average theta", rollingAverageTheta);
        Logger.recordOutput("rolling average X", rollingAverageX);
        Logger.recordOutput("theta", theta);

        if (LimelightHelpers.getRawFiducials("limelight-one").length == 0) {
             driver.setSwerveValues(MetersPerSecond.of(0), MetersPerSecond.of(0), RadiansPerSecond.of(0), currentAngle);
             return;
        }

        if (Math.abs(errorX) < 0.05) {
            driver.setSwerveValues(VelocityZ, MetersPerSecond.of(0), AngleVelocity, currentAngle);
            return;
        }

        if (Math.abs(errorZ) < 0.05) {
            driver.setSwerveValues(MetersPerSecond.of(0), VelocityX, AngleVelocity, currentAngle);
            return;
        }

        driver.setSwerveValues(VelocityZ, VelocityX, AngleVelocity, currentAngle);


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
