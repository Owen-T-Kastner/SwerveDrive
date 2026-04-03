package frc.robot.subsystems;

import static edu.wpi.first.units.Units.RadiansPerSecond;

import org.ironmaple.simulation.drivesims.GyroSimulation;

import edu.wpi.first.math.util.Units;

public class GyroIOSim implements GyroIO{

    private final GyroSimulation gyroSim;

    public GyroIOSim(GyroSimulation gyroSim){
        this.gyroSim = gyroSim;
    }

    @Override
    public void updateInputs(GyroIOInputs inputs) {
        inputs.yawPosition = gyroSim.getGyroReading();
        inputs.yawVelocityRadPerSec = Units.degreesToRadians(gyroSim.getMeasuredAngularVelocity().in(RadiansPerSecond));
    }
    
}
