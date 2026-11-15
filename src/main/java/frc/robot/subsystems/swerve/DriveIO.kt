package frc.robot.subsystems.swerve

import org.littletonrobotics.junction.AutoLog

interface DriveIO {
    @AutoLog
    open class DriveIOInputs {
        @JvmField val data = arrayOf(
            ModuleData.fromArray(Array(6) {0.0}),
            ModuleData.fromArray(Array(6) {0.0}),
            ModuleData.fromArray(Array(6) {0.0}),
            ModuleData.fromArray(Array(6) {0.0}),
        )
    }

    data class ModuleData(
        var turningVoltage: Double,
        var turningVelocity: Double,
        var turningStatorCurrent: Double,
        var drivingVoltage: Double,
        var drivingVelocity: Double,
        var drivingStatorCurrent: Double
    ) {

        fun toArray(): Array<Double> {
            return arrayOf(turningVoltage, turningVelocity, turningStatorCurrent,
                drivingVoltage, drivingVelocity, drivingStatorCurrent)
        }

        companion object {
            fun fromArray(data: Array<Double>): ModuleData {
                return ModuleData(data[0], data[1], data[2],
                    data[3], data[4], data[5])
            }
        }
    }

    fun updateInputs(inputs: DriveIOInputs) {}

    fun setRotationSpeed(velocity: Double) {}

    fun setHorizontalVelocity(velocity: Double) {}

    fun setVerticalVelocity(velocity: Double) {}

    fun setState(horizVelocity: Double, vertVelocity: Double, rotVelocity: Double) {}

    fun setVoltages(voltage: Double) {}
}