
package frc.robot.subsystems;

import org.littletonrobotics.junction.Logger;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.SwerveModulePosition;
import edu.wpi.first.math.kinematics.SwerveModuleState;
import static edu.wpi.first.units.Units.*;

import edu.wpi.first.units.measure.Angle;
import edu.wpi.first.units.measure.AngularVelocity;
import edu.wpi.first.units.measure.Distance;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Module extends SubsystemBase{

    private final ModuleIO io;
    private final ModuleIOInputsAutoLogged inputs = new ModuleIOInputsAutoLogged();
    private final int index;
    private Distance wheelRadius;

    public Module(ModuleIO io, int index) {
        this.io = io;
        this.index = index;
    }

    @Override
    public void periodic() {
        io.updateInputs(inputs);
        Logger.processInputs("Drive/Module" + Integer.toString(index), inputs);
    }

    public void setSpeedDrive(AngularVelocity VelocityRadPerSec) {
        io.setSpeedDrive(VelocityRadPerSec);
    }

    public void setTurnPosition(Angle angle) {
        io.setTurnPosition(angle);   
    }
    
    public void setVelocityDrive(AngularVelocity velocity) {
        io.setVelocityDrive(velocity);
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
