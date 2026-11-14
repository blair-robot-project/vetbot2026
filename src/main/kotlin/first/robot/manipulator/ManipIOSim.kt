package frc.team449.subsystems.manip

import frc.team449.Constants
import frc.team449.Constants.ManipConstants
import org.wpilib.math.system.DCMotor
import org.wpilib.math.system.Models.flywheelFromPhysicalConstants
import org.wpilib.math.util.Units
import org.wpilib.simulation.FlywheelSim
import org.wpilib.simulation.SingleJointedArmSim
import first.robot.manipulator.ManipIO

class ManipIOSim : ManipIOHardware() {
    private val rollSim =
        FlywheelSim(
            flywheelFromPhysicalConstants(
                DCMotor.getKrakenX60(1),
                ManipConstants.ROLL_MOI_KG_MM,
                ManipConstants.ROLL_GEARING,
            ),
            DCMotor.getKrakenX60(1),
        )

    private val turnSim =
        SingleJointedArmSim(
            DCMotor.getKrakenX60(1),
            ManipConstants.TURN_GEARING,
            ManipConstants.TURN_MOI_KG_MM,
            ManipConstants.ARM_LENGTH_METERS,
            ManipConstants.MIN_ANGLE_RADS,
            ManipConstants.MAX_ANGLE_RADS,
            true,
            ManipConstants.MIN_ANGLE_RADS,
        )

    private val rollMotorSim = rollMotor.simState
    private val turnMotorSim = turnMotor.simState

    override fun updateInputs(inputs: ManipIO.ManipIOInputs) {
        super.updateInputs(inputs)

        // roller
        rollMotorSim.setSupplyVoltage(12.0)
        rollSim.setInput(rollMotorSim.motorVoltage)
        rollSim.update(Constants.LOOP_TIME)
        rollMotorSim.setRotorVelocity(
            Units.radiansToRotations(rollSim.angularVelocity) * ManipConstants.ROLL_GEARING
        )

        // turn pivot
        turnMotorSim.setSupplyVoltage(12.0)
        turnSim.setInput(turnMotorSim.motorVoltage)
        turnSim.update(Constants.LOOP_TIME)
        turnMotorSim.setRawRotorPosition(
            Units.radiansToRotations(turnSim.angle) * ManipConstants.TURN_GEARING
        )
        turnMotorSim.setRotorVelocity(
            Units.radiansToRotations(turnSim.velocity) * ManipConstants.TURN_GEARING
        )
    }
}