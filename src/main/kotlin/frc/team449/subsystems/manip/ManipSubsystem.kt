package frc.team449.subsystems.manip

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

class ManipSubsystem(
    private val io: ManipIO
) : SubsystemBase() {
    private val inputs: ManipIOInputsAutoLogged = ManipIOInputsAutoLogged()

    override fun periodic() {
        io.updateInputs(inputs)
        Logger.processInputs("Manip", inputs)
    }

    fun setMotorVoltage(voltage: Double): Command =
        runOnce { io.setVoltage(voltage) }
}