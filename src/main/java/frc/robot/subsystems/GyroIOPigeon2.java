package frc.robot.subsystems;

import com.ctre.phoenix6.StatusSignal;
import com.ctre.phoenix6.hardware.Pigeon2;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.units.measure.Angle;
import edu.wpi.first.units.measure.AngularVelocity;
import frc.robot.subsystems.Constants.DriveConstants;

public class GyroIOPigeon2 implements GyroIO{

    private final Pigeon2 pigeon;
    private final StatusSignal<Angle> yaw;
    private final StatusSignal<AngularVelocity> yawVelocity; 

    public GyroIOPigeon2(DriveConstants constants) {
    pigeon = new Pigeon2(22, constants.CANbus);
    yaw = pigeon.getYaw();
    yawVelocity = pigeon.getAngularVelocityZWorld(); 
    }

    @Override
    public void updateInputs(GyroIOInputs inputs) {
        inputs.yawPosition = Rotation2d.fromDegrees(yaw.getValueAsDouble());
        inputs.yawVelocityRadPerSec = Units.degreesToRadians(yawVelocity.getValueAsDouble());

    }
}
