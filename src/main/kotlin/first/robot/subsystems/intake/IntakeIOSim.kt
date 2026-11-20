package first.robot.subsystems.intake

import org.wpilib.math.system.DCMotor
import org.wpilib.math.system.Models.flywheelFromPhysicalConstants
import org.wpilib.simulation.FlywheelSim
import org.wpilib.simulation.SingleJointedArmSim

class IntakeIOSim : IntakeIOHardware() {
    var pivotSim = SingleJointedArmSim(
        DCMotor.getKrakenX44(2),
        IntakeConstants.PIVOT_GEARING,
        IntakeConstants.PIVOT_MOI_KG_MM,
        IntakeConstants.PIVOT_ARM_LENGTH,
        IntakeConstants.PIVOT_MIN_RADS,
        IntakeConstants.PIVOT_MAX_RADS,
        true,
        IntakeConstants.PIVOT_MIN_RADS
    )

    var rollerSim = FlywheelSim(
        flywheelFromPhysicalConstants(
            DCMotor.getKrakenX60(2),
            IntakeConstants.ROLLER_MOI_KG_MM,
            IntakeConstants.ROLLER_GEARING
        ),
        DCMotor.getKrakenX60(2)
    )

    private val rightPivotSim = rightPivot.simState
    private val leftPivotSim = leftPivot.simState
    private val topRollerSim = topRoller.simState
    private val bottomRollerSim = bottomRoller.simState

    override fun updateInputs(inputs: IntakeIO.IntakeInputs) {
        super.updateInputs(inputs)

    }

}
