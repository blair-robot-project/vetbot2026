package frc.robot.subsystems.swerve

import edu.wpi.first.math.system.plant.DCMotor
import edu.wpi.first.math.system.plant.LinearSystemId
import edu.wpi.first.wpilibj.simulation.DCMotorSim

class DriveIOSim : DriveIOHardware() {
    val motorSims: List<DCMotorSim> = (0..7).map {i ->
        DCMotorSim(
            LinearSystemId.createDCMotorSystem(
                DCMotor.getKrakenX60(1),
                SwerveConstants.MOTOR_MOI,
                SwerveConstants.MOTOR_GEARING,
            ),
            DCMotor.getKrakenX60(1),
        )
    }

    override fun updateInputs(inputs: DriveIO.DriveIOInputs) {
        super.updateInputs(inputs)

        for (i in 0..7) {
            motors[i].simState.setSupplyVoltage(12.0)
        }
    }

    override fun setVoltages(voltage: Double) {
        for (i in 0..7) {
            motorSims[i].inputVoltage = voltage
        }
    }
}