package frc.robot.subsystems.swerve

import edu.wpi.first.units.measure.Angle
import org.littletonrobotics.junction.AutoLog

interface ModuleIO {
    @AutoLog
    open class ModuleIOInputs {
        // swy if this is not good :(
        @JvmField val data = arrayOf(
            ModuleData(0.0, 0.0),
            ModuleData(0.0, 0.0),
            ModuleData(0.0, 0.0),
            ModuleData(0.0, 0.0),
        )
    }

    data class ModuleData(
        var turningVoltage: Double,
        var drivingVoltage: Double
    )

    fun updateInputs(inputs: ModuleIOInputs) {}

    fun setRotationSpeed(velocity: Double) {}

    fun setHorizontalVelocity(velocity: Double) {}

    fun setVerticalVelocity(velocity: Double) {}

    fun setState(horizVelocity: Double, vertVelocity: Double, rotVelocity: Double) {}

    fun setVoltages(voltage: Double)
}