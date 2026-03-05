package frc.robot.subsystems.Constants;

public class DriveConstantsFL extends DriveConstants{

    public DriveConstantsFL() {
        driveMotorId = 20;
        turnMotorId = 19;
        cancoder = 21;
        magnetOffset = -0.11865234375; // -0.286865234375
        CANbus = "drivebase_2026";
        SwervleCANbus = "Drivebase 2025";
    }
}
