package first.robot.subsystems.intake
import com.ctre.phoenix6.BaseStatusSignal
import com.ctre.phoenix6.CANBus
import com.ctre.phoenix6.configs.Slot0Configs
import com.ctre.phoenix6.configs.TalonFXConfiguration
import com.ctre.phoenix6.controls.PositionVoltage
import com.ctre.phoenix6.controls.VoltageOut
import com.ctre.phoenix6.hardware.ParentDevice
import com.ctre.phoenix6.hardware.TalonFX
import first.robot.Constants.IntakeConstants
import first.robot.util.PhoenixUtil.tryUntilOk
import org.wpilib.units.Units
import org.wpilib.units.measure.Angle

open class IntakeIOHardware : IntakeIO {
    val rightPivot = TalonFX(IntakeConstants.RIGHT_PIVOT_ID, CANBus())
    val leftPivot = TalonFX(IntakeConstants.LEFT_PIVOT_ID, CANBus())
    val topRoller = TalonFX(IntakeConstants.TOP_ROLLER_ID, CANBus())
    val bottomRoller = TalonFX(IntakeConstants.BOTTOM_ROLLER_ID, CANBus())

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
        tryUntilOk(5) { rightPivot.configurator.apply(rightPivotConfig) }
        tryUntilOk(5) { leftPivot.configurator.apply(leftPivotConfig) }
        tryUntilOk(5) { topRoller.configurator.apply(topRollerConfig) }
        tryUntilOk(5) { bottomRoller.configurator.apply(bottomRollerConfig) }
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
        leftPivot.setControl(pivotPositionRequest.withPosition(angle))
    }

    override fun setRollerVoltage(volts: Double) {
        topRoller.setControl(rollerVoltageRequest.withOutput(volts))
        bottomRoller.setControl(rollerVoltageRequest.withOutput(volts))
    }

    companion object {
        val rightPivotConfig =
            TalonFXConfiguration().apply {
                CurrentLimits.apply {
                    SupplyCurrentLimit = IntakeConstants.PIVOT_SUPPLY_LIMIT
                    StatorCurrentLimit = IntakeConstants.PIVOT_STATOR_LIMIT
                }

                MotorOutput.apply {
                    NeutralMode = IntakeConstants.RIGHT_PIVOT_NEUTRAL_MODE
                    Inverted = IntakeConstants.RIGHT_PIVOT_INVERSION
                }

                Feedback.SensorToMechanismRatio = IntakeConstants.PIVOT_SENSOR_MECH_RATIO

                Slot0 =
                    Slot0Configs().apply {
                        // these values are from last yr, we need to update!!
                        kP = 100.0
                        kI = 0.0
                        kD = 0.1
                        kS = 0.2
                    }
            }

        val leftPivotConfig =
            TalonFXConfiguration().apply {
                CurrentLimits.apply {
                    SupplyCurrentLimit = IntakeConstants.PIVOT_SUPPLY_LIMIT
                    StatorCurrentLimit = IntakeConstants.PIVOT_STATOR_LIMIT
                }

                MotorOutput.apply {
                    NeutralMode = IntakeConstants.LEFT_PIVOT_NEUTRAL_MODE
                    Inverted = IntakeConstants.LEFT_PIVOT_INVERSION
                }

                Feedback.SensorToMechanismRatio = IntakeConstants.PIVOT_SENSOR_MECH_RATIO

                Slot0 =
                    Slot0Configs().apply {
                        // also need to be updated
                        kP = 100.0
                        kI = 0.0
                        kD = 0.1
                        kS = 0.2
                    }
            }

        val topRollerConfig =
            TalonFXConfiguration().apply {
                CurrentLimits.apply {
                    SupplyCurrentLimit = IntakeConstants.ROLLER_SUPPLY_LIMIT
                    StatorCurrentLimit = IntakeConstants.ROLLER_SUPPLY_LIMIT
                }

                MotorOutput.apply {
                    NeutralMode = IntakeConstants.TOP_ROLLER_NEUTRAL_MODE
                    Inverted = IntakeConstants.TOP_ROLLER_INVERSION
                }

                Feedback.SensorToMechanismRatio = IntakeConstants.ROLLER_SENSOR_MECH_RATIO
            }

        val bottomRollerConfig =
            TalonFXConfiguration().apply {
                CurrentLimits.apply {
                    SupplyCurrentLimit = IntakeConstants.ROLLER_SUPPLY_LIMIT
                    StatorCurrentLimit = IntakeConstants.ROLLER_STATOR_LIMIT
                }

                MotorOutput.apply {
                    NeutralMode = IntakeConstants.BOTTOM_ROLLER_NEUTRAL_MODE
                    Inverted = IntakeConstants.BOTTOM_ROLLER_INVERSION
                }

                Feedback.SensorToMechanismRatio = IntakeConstants.ROLLER_SENSOR_MECH_RATIO
            }
    }
}
