package frc.team449.subsystems.manip

import first.robot.manipulator.ManipIO
import com.ctre.phoenix6.BaseStatusSignal
import com.ctre.phoenix6.CANBus
import com.ctre.phoenix6.configs.Slot0Configs
import com.ctre.phoenix6.configs.TalonFXConfiguration
import com.ctre.phoenix6.controls.VelocityVoltage
import com.ctre.phoenix6.controls.VoltageOut
import com.ctre.phoenix6.hardware.TalonFX
import frc.team449.Constants.ManipConstants
import org.wpilib.math.geometry.Rotation2d
import org.wpilib.units.Units

open class ManipIOHardware : ManipIO {

    val rollMotor = TalonFX(ManipConstants.ROLL_ID, CANBus())
    val turnMotor = TalonFX(ManipConstants.TURN_ID, CANBus())

    // separate request objects so the two motors never share mutable state
    private val rollVelocityRequest = VelocityVoltage(0.0)
    private val turnVelocityRequest = VelocityVoltage(0.0)
    private val rollVoltageRequest = VoltageOut(0.0)
    private val turnVoltageRequest = VoltageOut(0.0)

    private val rollVoltage = rollMotor.motorVoltage
    private val rollPosition = rollMotor.position
    private val rollVelocity = rollMotor.velocity

    private val turnVoltage = turnMotor.motorVoltage
    private val turnPosition = turnMotor.position
    private val turnVelocity = turnMotor.velocity

    private val manipSignals =
        arrayOf(
            rollVoltage,
            rollPosition,
            rollVelocity,
            turnVoltage,
            turnPosition,
            turnVelocity,
        )

    init {
        rollMotor.configurator.apply(rollMotorConfig)
        turnMotor.configurator.apply(turnMotorConfig)
    }

    override fun updateInputs(inputs: ManipIO.ManipIOInputs) {
        BaseStatusSignal.refreshAll(*manipSignals)

        inputs.rollMotorAppliedVoltage = rollVoltage.value.`in`(Units.Volts)
        inputs.rollMotorPosition = rollPosition.value.`in`(Units.Radians)
        inputs.rollMotorVelocity = rollVelocity.value.`in`(Units.RadiansPerSecond)

        inputs.turnMotorAngle = Rotation2d(turnPosition.value.`in`(Units.Radians))
        inputs.turnMotorVelocity = turnVelocity.value.`in`(Units.RadiansPerSecond)
        inputs.turnMotorVoltage = turnVoltage.value.`in`(Units.Volts)
    }

    override fun setVoltage(volts: Double) {
        rollMotor.setControl(rollVoltageRequest.withOutput(volts))
    }

    override fun setTurnVoltage(volts: Double) {
        turnMotor.setControl(turnVoltageRequest.withOutput(volts))
    }

    override fun setTurnVelocity(radsPerSecond: Double) {
        turnMotor.setControl(
            turnVelocityRequest.withVelocity(radsPerSecond / (2 * Math.PI))
        )
    }

    override fun setRollVelocity(radsPerSecond: Double) {
        rollMotor.setControl(
            rollVelocityRequest.withVelocity(radsPerSecond / (2 * Math.PI))
        )
    }

    companion object {
        val rollSlot0Configs =
            Slot0Configs().apply {
                kP = ManipConstants.ROLL_kP
                kI = ManipConstants.ROLL_kI
                kD = ManipConstants.ROLL_kD
                kS = ManipConstants.ROLL_kS
                kV = ManipConstants.ROLL_kV
            }
        val turnSlot0Configs =
            Slot0Configs().apply {
                kP = ManipConstants.TURN_kP
                kI = ManipConstants.TURN_kI
                kD = ManipConstants.TURN_kD
                kS = ManipConstants.TURN_kS
                kV = ManipConstants.TURN_kV
            }
        val rollMotorConfig =
            TalonFXConfiguration().apply {
                CurrentLimits.apply {
                    SupplyCurrentLimit = ManipConstants.ROLL_SUPPLY_LIMIT
                    StatorCurrentLimit = ManipConstants.ROLL_STATOR_LIMIT
                }
                MotorOutput.apply {
                    Inverted = ManipConstants.ROLL_INVERSION
                    NeutralMode = ManipConstants.NEUTRAL_MODE
                }
                Slot0 = rollSlot0Configs
            }
        val turnMotorConfig =
            TalonFXConfiguration().apply {
                CurrentLimits.apply {
                    SupplyCurrentLimit = ManipConstants.TURN_SUPPLY_LIMIT
                    StatorCurrentLimit = ManipConstants.TURN_STATOR_LIMIT
                }
                MotorOutput.apply {
                    Inverted = ManipConstants.TURN_INVERSION
                    NeutralMode = ManipConstants.NEUTRAL_MODE
                }
                Slot0 = turnSlot0Configs
            }
    }
}