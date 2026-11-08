package frc.team449.subsystems.manip

import edu.wpi.first.math.geometry.Rotation2d
import edu.wpi.first.math.system.plant.DCMotor
import edu.wpi.first.math.system.plant.LinearSystemId
import edu.wpi.first.wpilibj.simulation.DCMotorSim

class ManipIOSim : ManipIOHardware() {
    private val turnMotorSim: DCMotorSim =
        DCMotorSim(
            LinearSystemId.createDCMotorSystem(
                DCMotor.getKrakenX60(1),
                1.2, //moI, idk
                1.0, //gearing... idk
            ),
            DCMotor.getKrakenX60(1)
        )

    private val rollMotorSim: DCMotorSim =
        DCMotorSim(
            LinearSystemId.createDCMotorSystem(
                DCMotor.getKrakenX60(1),
                1.2,
                1.0,
            ),
            DCMotor.getKrakenX60(1)

        )

    override fun setVoltage(volts: Double) {
        turnMotorSim.inputVoltage=(volts)
    }
    override fun setTurnVoltage(volts: Double) {
        rollMotorSim.inputVoltage=(volts)
    }

    override fun setTurnVelocity(radsPerSecond: Double) {
        turnMotorSim.setAngularVelocity(radsPerSecond)
    }
    override fun setRollVelocity(radsPerSecond: Double) {
        rollMotorSim.setAngularVelocity(radsPerSecond)
    }
    override fun updateInputs(inputs: ManipIO.ManipIOInputs) {
        inputs.turnMotorVoltage=turnMotorSim.inputVoltage
        inputs.rollMotorAppliedVoltage=rollMotorSim.inputVoltage
        inputs.turnMotorVelocity=turnMotorSim.angularVelocityRadPerSec
        inputs.rollMotorVelocity=rollMotorSim.angularVelocityRadPerSec
        inputs.turnMotorAngle = Rotation2d(turnMotorSim.angularPositionRad)
        turnMotorSim.update(0.02)
        rollMotorSim.update(0.02)
    }


}