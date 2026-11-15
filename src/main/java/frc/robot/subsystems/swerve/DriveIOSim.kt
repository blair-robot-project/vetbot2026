package frc.robot.subsystems.swerve

import org.wpilib.math.system.DCMotor
import org.wpilib.simulation.DCMotorSim

class DriveIOSim : DriveIOHardware() {
    val motorSims: List<DCMotorSim> =
        (0..7).map { i ->
            DCMotorSim(
                null,
                DCMotor.getKrakenX60(1),
            )
        }

    // val swerveSim: SwerveDriveSimulation

    override fun updateInputs(inputs: DriveIO.DriveIOInputs) {
        super.updateInputs(inputs)

        turningMotors.map { i -> i.simState.setSupplyVoltage(12.0) }
        drivingMotors.map { i -> i.simState.setSupplyVoltage(12.0) }
    }

    override fun setVoltages(voltage: Double) {
        for (i in 0..7) {
            motorSims[i].inputVoltage = voltage
        }
    }
}
