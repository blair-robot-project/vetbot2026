package frc.team449.subsystems.manip

import com.ctre.phoenix6.BaseStatusSignal
import com.ctre.phoenix6.configs.Slot0Configs
import com.ctre.phoenix6.configs.TalonFXConfiguration
import com.ctre.phoenix6.controls.Follower
import com.ctre.phoenix6.controls.PositionVoltage
import com.ctre.phoenix6.controls.VelocityVoltage
import com.ctre.phoenix6.controls.VoltageOut
import com.ctre.phoenix6.hardware.ParentDevice
import com.ctre.phoenix6.hardware.TalonFX
import edu.wpi.first.math.geometry.Rotation2d

import edu.wpi.first.units.Units
import edu.wpi.first.units.measure.Angle
import edu.wpi.first.units.measure.Velocity
import frc.team449.Constants.IntakeConstants
import frc.team449.util.PhoenixUtil.tryUntilOk

open class ManipIOHardware : ManipIO {

    val motor1 = TalonFX(0)
    val turnMotor = TalonFX(1)
    val SpeedControl = VelocityVoltage(0.0)


    var appliedVolts = 0.0;

    override fun updateInputs(inputs: ManipIO.ManipIOInputs) {
        inputs.rollMotorAppliedVoltage = motor1.motorVoltage.value.`in`(Units.Volts)
        inputs.rollMotorPosition = motor1.position.value.`in`(Units.Radians)//??? gulp
        inputs.rollMotorVelocity = motor1.velocity.value.`in`(Units.RadiansPerSecond)
        inputs.turnMotorAngle = Rotation2d(turnMotor.position.value.`in`(Units.Radians))
        inputs.turnMotorVelocity = turnMotor.velocity.value.`in`(Units.RadiansPerSecond)
        inputs.turnMotorVoltage = turnMotor.motorVoltage.value.`in`(Units.Volts)
    }

    override fun setVoltage(volts: Double){
        appliedVolts = volts
        motor1.setVoltage(appliedVolts)
    }

    override fun setTurnVoltage(volts: Double) {
        turnMotor.setVoltage((volts))
    }

    override fun setTurnVelocity(radsPerSecond: Double){
        turnMotor.setControl(SpeedControl.withVelocity(radsPerSecond/(Math.PI*2)))
    }
    override fun setRollVelocity(radsPerSecond: Double){
        motor1.setControl(SpeedControl.withVelocity(radsPerSecond/(Math.PI*2)))
    }
}