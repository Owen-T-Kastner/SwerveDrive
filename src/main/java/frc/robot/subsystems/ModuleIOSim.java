package frc.robot.subsystems;

import static edu.wpi.first.units.Units.Amps;
import static edu.wpi.first.units.Units.RadiansPerSecond;

import org.ironmaple.simulation.drivesims.SwerveModuleSimulation;
import org.ironmaple.simulation.motorsims.SimulatedMotorController;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.units.measure.Angle;
import edu.wpi.first.units.measure.AngularVelocity;

public class ModuleIOSim implements ModuleIO{

    private static final double Drive_KP = 0.05;
    private static final double Drive_KD = 0.0;
    private static final double Drive_KS = 0.0;

    private static final double KVR = 0.91035;
    private static final double KV = 1/Units.rotationsToRadians(1/KVR);

    private static final double Turn_KP = 8.0;
    private static final double Turn_KD = 0.0;

    private final SwerveModuleSimulation moduleSim;
    private final SimulatedMotorController.GenericMotorController driveMotor;
    private final SimulatedMotorController.GenericMotorController turnMotor;

    private final PIDController driveController;
    private final PIDController turnController;

    private double driveVolts = 0.0;
    private double turnVolts = 0.0;
    private double driveFFVolts = 0.0;

    private boolean driveloop;
    private boolean turnloop;

    public ModuleIOSim(SwerveModuleSimulation moduleSim) {
        this.moduleSim = moduleSim;
        this.driveMotor = moduleSim
        .useGenericMotorControllerForDrive()
        .withCurrentLimit(Amps.of(0.0));
        this.turnMotor = moduleSim.useGenericControllerForSteer().withCurrentLimit(Amps.of(20));

        this.driveController = new PIDController(0.05, 0.0, 0.0);
        this.turnController = new PIDController(8.0, 0.0, 0.0);

        turnController.enableContinuousInput(-Math.PI, Math.PI);
    }

    @Override
    public void updateInputs(ModuleIOInputs inputs) {

        inputs.driveVelocityRadPerSec = moduleSim.getDriveWheelFinalSpeed().in(RadiansPerSecond);
        inputs.driveVolts = driveVolts;
        inputs.driveCurrentAmps = Math.abs(moduleSim.getDriveMotorStatorCurrent().in(Amps));

        inputs.turnAbsolutePosition = moduleSim.getSteerAbsoluteFacing();
        inputs.turnPosition = moduleSim.getSteerAbsoluteFacing();
        inputs.turnVelocityRadPerSec = moduleSim.getSteerAbsoluteEncoderSpeed().in(RadiansPerSecond);
        inputs.turnVolts = turnVolts;
        inputs.turnCurrentAmps = Math.abs(moduleSim.getSteerMotorStatorCurrent().in(Amps));
    }
    
    @Override
    public void setSpeedDrive(AngularVelocity velocityRadPerSec){
        driveloop = true;
        driveFFVolts = Drive_KS * Math.signum(velocityRadPerSec.baseUnitMagnitude()) + KV * velocityRadPerSec.baseUnitMagnitude();
        driveController.setSetpoint(velocityRadPerSec.baseUnitMagnitude());
    }

    @Override
    public void setTurnPosition(Angle angle){
        turnloop = true;
        turnController.setSetpoint(angle.baseUnitMagnitude());
    }
}
