
package frc.robot.subsystems;

import org.littletonrobotics.junction.AutoLog;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.units.measure.Angle;

public interface ModuleIO {
    
    @AutoLog
    public static class ModuleIOInputs {
        public double drivePositionRad = 0.0;
        public double driveVelocityRadPerSec = 0.0;
        public double driveVolts = 0.0;
        public double driveCurrentAmps = 0.0;

        public Rotation2d turnAbsolutePosition = Rotation2d.kZero;
        public Rotation2d turnPosition = Rotation2d.kZero;
        public double turnVelocityRadPerSec = 0.0;
        public double turnVolts = 0.0;
        public double turnCurrentAmps = 0.0;
    }

    public default void updateInputs(ModuleIOInputs inputs) {}

    public default void setSpeedDrive(double VelocityRadPerSec) {}

    public default void setTurnPosition(Angle angle) {}
}
