
package frc.robot.subsystems;

import com.ctre.phoenix6.StatusSignal;
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
import edu.wpi.first.units.measure.AngularVelocity;
import edu.wpi.first.units.measure.Current;
import edu.wpi.first.units.measure.Voltage;
import frc.robot.subsystems.Constants.DriveConstants;

public class ModuleIOTalonFX implements ModuleIO{

    private final TalonFX driveMotor;
    private final TalonFX turnMotor;
    private final CANcoder cancoder;

    private final PositionVoltage positionVoltage = new PositionVoltage(0.0);
    private final VelocityVoltage velocityVoltage = new VelocityVoltage(0.0);

    private final StatusSignal<Angle> drivePosition;
    private final StatusSignal<AngularVelocity> driveVelocity;
    private final StatusSignal<Current> driveCurrentAmps;
    private final StatusSignal<Voltage> driveVolts;
    
    private final StatusSignal<Angle> turnAbsolutePosition;
    private final StatusSignal<Angle> turnPosition;
    private final StatusSignal<AngularVelocity> turnVelocity;
    private final StatusSignal<Current> turnCurrentAmps;
    private final StatusSignal<Voltage> turnVolts; 

    public ModuleIOTalonFX() {
        driveMotor = new TalonFX(8, DriveConstants.CANbus); //front right
        turnMotor = new TalonFX(10, DriveConstants.CANbus); //front right
        cancoder = new CANcoder(9, DriveConstants.CANbus); //front right

        TalonFXConfiguration driveMotorConfig = new TalonFXConfiguration();
        driveMotorConfig.Slot0.kP = 0.25;
        driveMotorConfig.Slot0.kI = 0.0;
        driveMotorConfig.Slot0.kD = 0.0;
        driveMotorConfig.MotorOutput.NeutralMode = NeutralModeValue.Brake;

        TalonFXConfiguration turnMotorConfig = new TalonFXConfiguration();
        turnMotorConfig.Slot0.kP = 1.0;
        turnMotorConfig.Slot0.kI = 0.0;
        turnMotorConfig.Slot0.kD = 0.0;
        turnMotorConfig.MotorOutput.NeutralMode = NeutralModeValue.Brake;
        turnMotorConfig.ClosedLoopGeneral.ContinuousWrap = true;
        turnMotorConfig.Feedback.FeedbackRemoteSensorID = 9;
        turnMotorConfig.Feedback.FeedbackSensorSource = FeedbackSensorSourceValue.FusedCANcoder;

        driveMotor.getConfigurator().apply(driveMotorConfig);
        turnMotor.getConfigurator().apply(turnMotorConfig);

        drivePosition = driveMotor.getPosition();
        driveVelocity = driveMotor.getVelocity();
        driveCurrentAmps = driveMotor.getStatorCurrent();
        driveVolts = driveMotor.getMotorVoltage();

        turnAbsolutePosition = cancoder.getAbsolutePosition();
        turnPosition = turnMotor.getPosition();
        turnVelocity = turnMotor.getVelocity();
        turnCurrentAmps = turnMotor.getStatorCurrent();
        turnVolts = turnMotor.getMotorVoltage();
    }
    @Override
    public void updateInputs(ModuleIOInputs inputs) {
        inputs.drivePositionRad = Units.rotationsToRadians(drivePosition.getValueAsDouble());
        inputs.driveVelocityRadPerSec = Units.rotationsToRadians(driveVelocity.getValueAsDouble());
        inputs.driveCurrentAmps = driveCurrentAmps.getValueAsDouble();
        inputs.driveVolts = driveVolts.getValueAsDouble();

        inputs.turnAbsolutePosition = Rotation2d.fromRotations(turnAbsolutePosition.getValueAsDouble());
        inputs.turnPosition = Rotation2d.fromRotations(turnPosition.getValueAsDouble());
        inputs.turnVelocityRadPerSec = Units.rotationsToRadians(turnVelocity.getValueAsDouble());
        inputs.turnCurrentAmps = turnCurrentAmps.getValueAsDouble();
        inputs.turnVolts = turnVolts.getValueAsDouble();
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
