package frc.team449.subsystems.manip

import edu.wpi.first.math.controller.PIDController
import edu.wpi.first.math.filter.Debouncer
import edu.wpi.first.units.Units.Radians
import edu.wpi.first.units.Units.Seconds
import edu.wpi.first.units.Units.Volts
import edu.wpi.first.units.measure.Angle
import edu.wpi.first.units.measure.Voltage
import edu.wpi.first.wpilibj.Alert
import edu.wpi.first.wpilibj.sysid.SysIdRoutineLog
import edu.wpi.first.wpilibj2.command.Command
import edu.wpi.first.wpilibj2.command.SubsystemBase
import edu.wpi.first.wpilibj2.command.sysid.SysIdRoutine
import edu.wpi.first.wpilibj2.command.sysid.SysIdRoutine.Mechanism
import frc.team449.Constants.IntakeConstants
import org.littletonrobotics.junction.AutoLogOutput
import org.littletonrobotics.junction.Logger
import kotlin.math.abs
import edu.wpi.first.math.geometry.Rotation2d

class ManipSubsystem(
    private val io: ManipIO
) : SubsystemBase() {
    private val inputs: ManipIOInputsAutoLogged = ManipIOInputsAutoLogged()

    private val turnController = PIDController(0.0, 0.0, 0.0) //WTF NUMBER

    override fun periodic() {
        io.updateInputs(inputs)
        io.setTurnVoltage(turnController.calculate(inputs.turnMotorAngle.radians))

        Logger.processInputs("Manipulator", inputs)
    }

    fun setMotorVoltage(voltage: Double): Command =
        runOnce { io.setVoltage(voltage) }

    fun grabPiece(radsPerSecond: Double): Command =
        run {
            io.setRollVelocity(radsPerSecond)
        }

    fun ejectPiece(radsPerSecond: Double): Command =
        run {
            io.setRollVelocity(-radsPerSecond)
        }

    fun setAngle(setpoint:Double): Command=
        runOnce{
            turnController.setpoint=setpoint
        }

    fun stop(): Command =
        runOnce {
            io.setVoltage(0.0)
            turnController.setpoint = inputs.turnMotorAngle.radians
        }
}