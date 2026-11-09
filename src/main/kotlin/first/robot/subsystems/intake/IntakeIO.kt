package first.robot.subsystems.intake

import org.littletonrobotics.junction.AutoLog
import org.wpilib.units.measure.Angle

interface IntakeIO {
    @AutoLog
    open class IntakeInputs {
        @JvmField var rightPivotConnected: Boolean = false

        @JvmField var rightPivotAppliedVolts: Double = 0.0

        @JvmField var rightPivotVelocityRadsPerSec: Double = 0.0

        @JvmField var rightPivotSupplyCurrentAmps: Double = 0.0

        @JvmField var rightPivotStatorCurrentAmps: Double = 0.0

        @JvmField var rightPivotTempCelsius: Double = 0.0

        @JvmField var leftPivotConnected: Boolean = false

        @JvmField var leftPivotAppliedVolts: Double = 0.0

        @JvmField var leftPivotVelocityRadsPerSec: Double = 0.0

        @JvmField var leftPivotSupplyCurrentAmps: Double = 0.0

        @JvmField var leftPivotStatorCurrentAmps: Double = 0.0

        @JvmField var leftPivotTempCelsius: Double = 0.0

        @JvmField var topRollerConnected: Boolean = false

        @JvmField var topRollerAppliedVolts: Double = 0.0

        @JvmField var topRollerVelocityRadsPerSec: Double = 0.0

        @JvmField var topRollerSupplyCurrentAmps: Double = 0.0

        @JvmField var topRollerStatorCurrentAmps: Double = 0.0

        @JvmField var topRollerTempCelsius: Double = 0.0

        @JvmField var bottomRollerConnected: Boolean = false

        @JvmField var bottomRollerAppliedVolts: Double = 0.0

        @JvmField var bottomRollerVelocityRadsPerSec: Double = 0.0

        @JvmField var bottomRollerSupplyCurrentAmps: Double = 0.0

        @JvmField var bottomRollerStatorCurrentAmps: Double = 0.0

        @JvmField var bottomRollerTempCelsius: Double = 0.0
    }

    fun updateInputs(inputs: IntakeInputs) {}

    fun setPivotVoltage(volts: Double) {}

    fun setPivotAngle(pivotAngle: Angle) {}

    fun setRollerVoltage(volts: Double) {}
}