
package frc.robot.subsystems;

import com.ctre.phoenix6.configs.CANcoderConfiguration;
import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.controls.PositionVoltage;
import com.ctre.phoenix6.controls.VelocityVoltage;
import com.ctre.phoenix6.hardware.CANcoder;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.FeedbackSensorSourceValue;
import com.ctre.phoenix6.signals.NeutralModeValue;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.units.measure.Angle;
import frc.robot.subsystems.Constants.DriveConstants;

public class ModuleIOTalonFX implements ModuleIO{

    private final TalonFX driveMotor;
    private final TalonFX turnMotor;
    private final CANcoder cancoder;

    private final PositionVoltage positionVoltage = new PositionVoltage(0.0);
    private final VelocityVoltage velocityVoltage = new VelocityVoltage(0.0);

    @SuppressWarnings("static-access")
    public ModuleIOTalonFX(DriveConstants constants) {
        driveMotor = new TalonFX(constants.driveMotorId, DriveConstants.CANbus);
        turnMotor = new TalonFX(constants.turnMotorId, DriveConstants.CANbus);
        cancoder = new CANcoder(constants.cancoder, DriveConstants.CANbus);

        TalonFXConfiguration driveMotorConfig = new TalonFXConfiguration();
        driveMotorConfig.Slot0.kP = 5.0;
        driveMotorConfig.Slot0.kI = 0.0;
        driveMotorConfig.Slot0.kD = 0.0;
        driveMotorConfig.MotorOutput.NeutralMode = NeutralModeValue.Brake;

        TalonFXConfiguration turnMotorConfig = new TalonFXConfiguration();
        turnMotorConfig.Slot0.kP = 100.0;
        turnMotorConfig.Slot0.kI = 0.0;
        turnMotorConfig.Slot0.kD = 0.0;
        turnMotorConfig.MotorOutput.NeutralMode = NeutralModeValue.Brake;
        turnMotorConfig.ClosedLoopGeneral.ContinuousWrap = true;
        turnMotorConfig.Feedback.FeedbackRemoteSensorID = 9;
        turnMotorConfig.Feedback.FeedbackSensorSource = FeedbackSensorSourceValue.FusedCANcoder;

        CANcoderConfiguration CANcoderConfig = new CANcoderConfiguration();
        CANcoderConfig.MagnetSensor.MagnetOffset = DriveConstants.magnetOffset;
        //CANcoderConfig.MagnetSensor.SensorDirection = 0;

        driveMotor.getConfigurator().apply(driveMotorConfig);
        turnMotor.getConfigurator().apply(turnMotorConfig);
        cancoder.getConfigurator().apply(CANcoderConfig);
    }
    @Override
    public void updateInputs(ModuleIOInputs inputs) {
        inputs.drivePositionRad = Units.rotationsToRadians(driveMotor.getPosition().getValueAsDouble());
        inputs.driveVelocityRadPerSec = Units.rotationsToRadians(driveMotor.getVelocity().getValueAsDouble());
        inputs.driveCurrentAmps = driveMotor.getStatorCurrent().getValueAsDouble();
        inputs.driveVolts = driveMotor.getMotorVoltage().getValueAsDouble();

        inputs.turnAbsolutePosition = Rotation2d.fromRotations(cancoder.getAbsolutePosition().getValueAsDouble());
        inputs.turnPosition = Rotation2d.fromRotations(turnMotor.getPosition().getValueAsDouble());
        inputs.turnVelocityRadPerSec = Units.rotationsToRadians(turnMotor.getVelocity().getValueAsDouble());
        inputs.turnCurrentAmps = turnMotor.getStatorCurrent().getValueAsDouble();
        inputs.turnVolts = turnMotor.getMotorVoltage().getValueAsDouble();
    }

    @Override
    public void setSpeedDrive(double VelocityRadPerSec) {
        driveMotor.setControl(velocityVoltage.withVelocity(VelocityRadPerSec));
        }

    @Override
    public void setTurnPosition(Angle angle) {
        turnMotor.setControl(positionVoltage.withPosition(angle));
    }
}
