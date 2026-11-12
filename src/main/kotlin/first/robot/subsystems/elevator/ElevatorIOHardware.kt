package first.robot.subsystems.elevator

import ElevatorIO
import com.ctre.phoenix6.BaseStatusSignal
import com.ctre.phoenix6.CANBus
import com.ctre.phoenix6.configs.Slot0Configs
import com.ctre.phoenix6.configs.TalonFXConfiguration
import com.ctre.phoenix6.controls.PositionVoltage
import com.ctre.phoenix6.hardware.TalonFX
import first.robot.Constants.ElevatorConstants
import org.wpilib.units.Units

open class ElevatorIOHardware : ElevatorIO {
    val leftMotor = TalonFX(ElevatorConstants.LEFT_ID, CANBus())
    val rightMotor = TalonFX(ElevatorConstants.RIGHT_ID, CANBus())

    val positionRequest = PositionVoltage(0.0)

    private val leftVoltage = leftMotor.motorVoltage
    private val leftVelocity = leftMotor.velocity
    private val leftSupplyCurrent = leftMotor.supplyCurrent
    private val leftStatorCurrent = leftMotor.statorCurrent
    private val leftTemp = leftMotor.deviceTemp

    private val rightVoltage = rightMotor.motorVoltage
    private val rightVelocity = rightMotor.velocity
    private val rightSupplyCurrent = rightMotor.supplyCurrent
    private val rightStatorCurrent = rightMotor.statorCurrent
    private val rightTemp = rightMotor.deviceTemp

    private var elevatorPos = 0.0

    private val isLeftConnected: Boolean =
        BaseStatusSignal.isAllGood(
            leftVoltage,
            leftVelocity,
            leftStatorCurrent,
        )
    private val isRightConnected: Boolean =
        BaseStatusSignal.isAllGood(
            rightVoltage,
            rightVelocity,
            rightStatorCurrent,
        )

    private val elevatorSignals =
        arrayOf(
            leftVoltage,
            leftVelocity,
            leftSupplyCurrent,
            leftStatorCurrent,
            leftTemp,
            rightVoltage,
            rightVelocity,
            rightSupplyCurrent,
            rightStatorCurrent,
            rightTemp,
        )

    init {
        leftMotor.configurator.apply(leftMotorConfig)
        rightMotor.configurator.apply(rightMotorConfig)
    }

    override fun updateInputs(input: ElevatorIO.ElevatorIOInputs) {
        BaseStatusSignal.refreshAll(*elevatorSignals)
        input.leftConnected = isLeftConnected
        input.leftAppliedVoltage = leftVoltage.value.`in`(Units.Volts)
        input.leftVelocityRadPerSec = leftVelocity.value.`in`(Units.RadiansPerSecond)
        input.leftSupplyCurrentAmps = leftSupplyCurrent.value.`in`(Units.Amps)
        input.leftStatorCurrentAmps = leftStatorCurrent.value.`in`(Units.Amps)
        input.leftTempCelsius = leftTemp.value.`in`(Units.Celsius)

        input.rightConnected = isRightConnected
        input.rightAppliedVoltage = rightVoltage.value.`in`(Units.Volts)
        input.rightVelocityRadPerSec = rightVelocity.value.`in`(Units.RadiansPerSecond)
        input.rightSupplyCurrentAmps = rightSupplyCurrent.value.`in`(Units.Amps)
        input.rightStatorCurrentAmps = rightStatorCurrent.value.`in`(Units.Amps)
        input.rightTempCelsius = rightTemp.value.`in`(Units.Celsius)

        input.elevatorPosMeters = elevatorPos
    }

    override fun setPosition(posMeters: Double) {
        elevatorPos = posMeters
        val motorRadians: Double = posMeters * ElevatorConstants.DRUM_RADIUS * 2 * Math.PI
        positionRequest.withPosition(motorRadians)
        leftMotor.setControl(positionRequest)
        rightMotor.setControl(positionRequest)
    }

    companion object {
        val talonSlot0Configs =
            Slot0Configs().apply {
                kP = ElevatorConstants.kP
                kI = ElevatorConstants.kI
                kD = ElevatorConstants.kD
                kG = ElevatorConstants.kG
            }
        val leftMotorConfig =
            TalonFXConfiguration().apply {
                CurrentLimits.apply {
                    SupplyCurrentLimit = ElevatorConstants.TALON_SUPPLY_LIMIT
                    StatorCurrentLimit = ElevatorConstants.TALON_STATOR_LIMIT
                }
                MotorOutput.apply {
                    Inverted = ElevatorConstants.LEFT_INVERSION
                    NeutralMode = ElevatorConstants.TALON_NEUTRAL_MODE
                }
                Slot0 = talonSlot0Configs
            }
        val rightMotorConfig =
            TalonFXConfiguration().apply {
                CurrentLimits.apply {
                    SupplyCurrentLimit = ElevatorConstants.TALON_SUPPLY_LIMIT
                    StatorCurrentLimit = ElevatorConstants.TALON_STATOR_LIMIT
                }
                MotorOutput.apply {
                    Inverted = ElevatorConstants.RIGHT_INVERSION
                    NeutralMode = ElevatorConstants.TALON_NEUTRAL_MODE
                }
                Slot0 = talonSlot0Configs
            }
    }
}
