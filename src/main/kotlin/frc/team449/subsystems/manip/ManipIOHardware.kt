package frc.team449.subsystems.manip

import com.ctre.phoenix6.BaseStatusSignal
import com.ctre.phoenix6.configs.Slot0Configs
import com.ctre.phoenix6.configs.TalonFXConfiguration
import com.ctre.phoenix6.controls.Follower
import com.ctre.phoenix6.controls.PositionVoltage
import com.ctre.phoenix6.controls.VoltageOut
import com.ctre.phoenix6.hardware.ParentDevice
import com.ctre.phoenix6.hardware.TalonFX
import edu.wpi.first.units.Units
import edu.wpi.first.units.measure.Angle
import frc.team449.Constants.IntakeConstants
import frc.team449.util.PhoenixUtil.tryUntilOk

open class ManipIOHardware : ManipIO {

    val motor1 = TalonFX(0)

    private val voltage1 = motor1.motorVoltage
    private val position1 = motor1.position
    private val velocity1 = motor1.velocity

    override fun updateInputs(inputs: ManipIO.ManipIOInputs) {
        inputs.motor1AppliedVoltage = voltage1.value.`in`(Units.Volts)
        inputs.motor1Position = position1.value.`in`(Units.Radians)
        inputs.motor1Velocity = velocity1.value.`in`(Units.RadiansPerSecond)
    }

    override fun setVoltage(voltage: Double) {
        motor1.setVoltage(voltage)
    }
}