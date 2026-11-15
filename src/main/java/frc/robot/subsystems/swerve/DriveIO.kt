package frc.robot.subsystems.swerve

import org.littletonrobotics.junction.AutoLog

interface DriveIO {
    @AutoLog
    open class DriveIOInputs {
        @JvmField var topLeftData = ModuleData()

        @JvmField var topRightData = ModuleData()

        @JvmField var bottomLeftData = ModuleData()

        @JvmField var bottomRightData = ModuleData()
    }

    /*data class ModuleData(
        var turningVoltage: Double,
        var turningVelocity: Double,
        var turningStatorCurrent: Double,
        var drivingVoltage: Double,
        var drivingVelocity: Double,
        var drivingStatorCurrent: Double,
    ) {
        fun toArray(): Array<Double> =
            arrayOf(
                turningVoltage,
                turningVelocity,
                turningStatorCurrent,
                drivingVoltage,
                drivingVelocity,
                drivingStatorCurrent,
            )

        /*fun updateFromArray(data: Array<Double>) {
            turningVoltage = data[0]
            turningVelocity = data[1]
            turningStatorCurrent = data[2]
            drivingVoltage = data[3]
            drivingVelocity = data[4]
            drivingStatorCurrent = data[5]
        }*/

        companion object {
            fun fromArray(data: Array<Double>): ModuleData =
                ModuleData(
                    data[0],
                    data[1],
                    data[2],
                    data[3],
                    data[4],
                    data[5],
                )
        }
    }*/

    fun updateInputs(inputs: DriveIOInputs) {}

    fun setRotationSpeed(velocity: Double) {}

    fun setHorizontalVelocity(velocity: Double) {}

    fun setVerticalVelocity(velocity: Double) {}

    fun setState(
        horizVelocity: Double,
        vertVelocity: Double,
        rotVelocity: Double,
    ) {}

    fun setVoltages(voltage: Double) {}
}
