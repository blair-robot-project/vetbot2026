package first.robot.mechanism.intake
import com.ctre.phoenix6.BaseStatusSignal
import com.ctre.phoenix6.CANBus
import com.ctre.phoenix6.controls.PositionVoltage
import com.ctre.phoenix6.controls.VoltageOut
import com.ctre.phoenix6.hardware.ParentDevice
import com.ctre.phoenix6.hardware.TalonFX
import org.wpilib.units.Units
import org.wpilib.units.measure.Angle

open class IntakeIOHardware : IntakeIO {
    val rightPivot = TalonFX(1, CANBus())
    val leftPivot = TalonFX(2, CANBus())
    val topRoller = TalonFX(3, CANBus())
    val bottomRoller = TalonFX(4, CANBus())

    private val pivotVoltageRequest = VoltageOut(0.0)
    private val pivotPositionRequest = PositionVoltage(0.0)
    private val rollerVoltageRequest = VoltageOut(0.0)

    private val rightPivotVoltage = rightPivot.motorVoltage
    private val rightPivotPosition = rightPivot.position
    private val rightPivotVelocity = rightPivot.velocity
    private val rightPivotSupply = rightPivot.supplyCurrent
    private val rightPivotStator = rightPivot.statorCurrent
    private val rightPivotTemp = rightPivot.deviceTemp

    private val leftPivotVoltage = leftPivot.motorVoltage
    private val leftPivotPosition = leftPivot.position
    private val leftPivotVelocity = leftPivot.velocity
    private val leftPivotSupply = leftPivot.supplyCurrent
    private val leftPivotStator = leftPivot.statorCurrent
    private val leftPivotTemp = leftPivot.deviceTemp

    private val topRollerVoltage = topRoller.motorVoltage
    private val topRollerVelocity = topRoller.velocity
    private val topRollerSupply = topRoller.supplyCurrent
    private val topRollerStator = topRoller.statorCurrent
    private val topRollerTemp = topRoller.deviceTemp

    private val bottomRollerVoltage = bottomRoller.motorVoltage
    private val bottomRollerVelocity = bottomRoller.velocity
    private val bottomRollerSupply = bottomRoller.supplyCurrent
    private val bottomRollerStator = bottomRoller.statorCurrent
    private val bottomRollerTemp = bottomRoller.deviceTemp

    private val intakeSignals =
        arrayOf(
            rightPivotVoltage,
            rightPivotPosition,
            rightPivotVelocity,
            rightPivotSupply,
            rightPivotStator,
            rightPivotTemp,
            leftPivotVoltage,
            leftPivotPosition,
            leftPivotVelocity,
            leftPivotSupply,
            leftPivotStator,
            leftPivotTemp,
            topRollerVoltage,
            topRollerVelocity,
            topRollerSupply,
            topRollerStator,
            topRollerTemp,
            bottomRollerVoltage,
            bottomRollerVelocity,
            bottomRollerSupply,
            bottomRollerStator,
            bottomRollerTemp,
        )

    private val rightPivotIsConnected: Boolean
        get() =
            BaseStatusSignal.isAllGood(
                rightPivotVoltage,
                rightPivotPosition,
                rightPivotVelocity,
                rightPivotSupply,
                rightPivotStator,
            )

    private val leftPivotIsConnected: Boolean
        get() =
            BaseStatusSignal.isAllGood(
                leftPivotVoltage,
                leftPivotPosition,
                leftPivotVelocity,
                leftPivotSupply,
                leftPivotStator,
            )

    private val topRollerIsConnected: Boolean
        get() =
            BaseStatusSignal.isAllGood(
                topRollerVoltage,
                topRollerVelocity,
                topRollerSupply,
                topRollerStator,
            )

    private val bottomRollerIsConnected: Boolean
        get() =
            BaseStatusSignal.isAllGood(
                bottomRollerVoltage,
                bottomRollerVelocity,
                bottomRollerSupply,
                bottomRollerStator,
            )

    init {
        // tryUntilOk(5) {rightPivot.configurator.apply( whatever the config is)} x4
        BaseStatusSignal.setUpdateFrequencyForAll(50.0, *intakeSignals)
        ParentDevice.optimizeBusUtilizationForAll(rightPivot, leftPivot, topRoller, bottomRoller)
    }

    override fun updateInputs(inputs: IntakeIO.IntakeInputs) {
        BaseStatusSignal.refreshAll(*intakeSignals)

        inputs.leftPivotConnected = leftPivotIsConnected
        inputs.leftPivotAppliedVolts = leftPivotVoltage.value.`in`(Units.Volts)
        inputs.leftPivotPositionRads = leftPivotPosition.value.`in`(Units.Radians)
        inputs.leftPivotVelocityRadsPerSec = leftPivotVelocity.value.`in`(Units.RadiansPerSecond)
        inputs.leftPivotSupplyCurrentAmps = leftPivotSupply.value.`in`(Units.Amps)
        inputs.leftPivotStatorCurrentAmps = leftPivotStator.value.`in`(Units.Amps)
        inputs.leftPivotTempCelsius = leftPivotTemp.value.`in`(Units.Celsius)

        inputs.rightPivotConnected = rightPivotIsConnected
        inputs.rightPivotAppliedVolts = rightPivotVoltage.value.`in`(Units.Volts)
        inputs.rightPivotPositionRads = rightPivotPosition.value.`in`(Units.Radians)
        inputs.rightPivotVelocityRadsPerSec = rightPivotVelocity.value.`in`(Units.RadiansPerSecond)
        inputs.rightPivotSupplyCurrentAmps = rightPivotSupply.value.`in`(Units.Amps)
        inputs.rightPivotStatorCurrentAmps = rightPivotStator.value.`in`(Units.Amps)
        inputs.rightPivotTempCelsius = rightPivotTemp.value.`in`(Units.Celsius)

        inputs.topRollerConnected = topRollerIsConnected
        inputs.topRollerAppliedVolts = topRollerVoltage.value.`in`(Units.Volts)
        inputs.topRollerVelocityRadsPerSec = topRollerVelocity.value.`in`(Units.RadiansPerSecond)
        inputs.topRollerSupplyCurrentAmps = topRollerSupply.value.`in`(Units.Amps)
        inputs.topRollerStatorCurrentAmps = topRollerStator.value.`in`(Units.Amps)
        inputs.topRollerTempCelsius = topRollerTemp.value.`in`(Units.Celsius)

        inputs.bottomRollerConnected = bottomRollerIsConnected
        inputs.bottomRollerAppliedVolts = bottomRollerVoltage.value.`in`(Units.Volts)
        inputs.bottomRollerVelocityRadsPerSec = bottomRollerVelocity.value.`in`(Units.RadiansPerSecond)
        inputs.bottomRollerSupplyCurrentAmps = bottomRollerSupply.value.`in`(Units.Amps)
        inputs.bottomRollerStatorCurrentAmps = bottomRollerStator.value.`in`(Units.Amps)
        inputs.bottomRollerTempCelsius = bottomRollerTemp.value.`in`(Units.Celsius)
    }

    override fun setPivotVoltage(volts: Double) {
        rightPivot.setControl(pivotVoltageRequest.withOutput(volts))
        leftPivot.setControl(pivotVoltageRequest.withOutput(volts))
    }

    override fun setPivotAngle(angle: Angle) {
        rightPivot.setControl(pivotPositionRequest.withPosition(angle))
    }

    override fun setRollerVoltage(volts: Double) {
        topRoller.setControl(rollerVoltageRequest.withOutput(volts))
        bottomRoller.setControl(rollerVoltageRequest.withOutput(volts))
    }
}
