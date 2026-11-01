package frc.team449.subsystems.manip

import org.littletonrobotics.junction.AutoLog

interface ManipIO {
    @AutoLog
    open class ManipIOInputs() {
        @JvmField var motor1AppliedVoltage : Double = 0.0

        @JvmField var motor1Position : Double = 0.0

        @JvmField var motor1Velocity : Double = 0.0

    }

    fun updateInputs(inputs: ManipIOInputs) {}

    fun setVoltage(voltage: Double) {}
}