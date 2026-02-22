
package frc.robot.subsystems;

import org.littletonrobotics.junction.Logger;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.SwerveModulePosition;
import edu.wpi.first.math.kinematics.SwerveModuleState;
import static edu.wpi.first.units.Units.*;

import edu.wpi.first.units.measure.Angle;
import edu.wpi.first.units.measure.Distance;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Module extends SubsystemBase{

    private final ModuleIO io;
    private final ModuleIOInputsAutoLogged inputs = new ModuleIOInputsAutoLogged();
    private final int index;
    private final Distance wheelRadius;

    public Module(ModuleIO io, int index, Distance wheelRadius) {
        this.io = io;
        this.index = index;
        this.wheelRadius = wheelRadius;
    }

    @Override
    public void periodic() {
        io.updateInputs(inputs);
        Logger.processInputs("Drive/Module" + Integer.toString(index), inputs);
    }

    public void runSetpoint(SwerveModuleState state) {
        state.optimize(getAngle());
        state.cosineScale(inputs.turnPosition);

        io.setSpeedDrive(state.speedMetersPerSecond / wheelRadius.in(Meters));
        io.setTurnPosition(null);
    }

    public void setSpeedDrive(double VelocityRadPerSec) {
        io.setSpeedDrive(VelocityRadPerSec * 10);
    }

    public void setTurnPosition(Angle angle) {
        io.setTurnPosition(angle);   
    }
    
    public Rotation2d getAngle() {
        return inputs.turnPosition;
    }

    public double getPositionMeters() {
        return inputs.drivePositionRad * wheelRadius.in(Meters);
    }

    public double getVelocityMetersPerSec() {
        return inputs.driveVelocityRadPerSec * wheelRadius.in(Meters);
    }

    public SwerveModulePosition getPosition() {
        return new SwerveModulePosition(getPositionMeters(), getAngle());
    }

    public SwerveModuleState getState() {
        return new SwerveModuleState(getVelocityMetersPerSec(), getAngle());
    }
}
