package frc.robot.subsystems;

import org.littletonrobotics.junction.AutoLog;

import edu.wpi.first.math.geometry.Rotation2d;

public interface GyroIO {

@AutoLog
public static class GyroIOInputs{
    public Rotation2d yawPosition = Rotation2d.kZero;
    public double yawVelocityRadPerSec = 0.0;
}
    
public default void updateInputs(GyroIOInputs inputs) {}
}
