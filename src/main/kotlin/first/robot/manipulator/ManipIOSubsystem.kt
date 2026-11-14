package frc.team449.subsystems.manip

import frc.team449.Constants.ManipConstants
import org.littletonrobotics.junction.Logger
import org.wpilib.command3.Command
import org.wpilib.math.controller.PIDController
import first.robot.manipulator.ManipIO
import org.wpilib.command3.Mechanism

class ManipSubsystem(
    private val io: ManipIO
) : Mechanism() {
    private val inputs: ManipIOInputsAutoLogged = ManipIOInputsAutoLogged()

    private val turnController =
        PIDController(
            ManipConstants.TURN_kP_controller,
            ManipConstants.TURN_kI_controller,
            ManipConstants.TURN_kD_controller,
        )

    fun periodic() {
        io.updateInputs(inputs)
        io.setTurnVoltage(turnController.calculate(inputs.turnMotorAngle.radians))

        Logger.processInputs("Manipulator", inputs)
        Logger.recordOutput("Manipulator/TurnSetpoint", turnController.setpoint)
    }

    fun setMotorVoltage(voltage: Double): Command =
        run {
            io.setVoltage(voltage)
        } as Command

    fun grabPiece(radsPerSecond: Double): Command =
        run {
            io.setRollVelocity(radsPerSecond)
        } as Command

    fun ejectPiece(radsPerSecond: Double): Command =
        run {
            io.setRollVelocity(-radsPerSecond)
        } as Command

    fun setAngle(setpoint: Double): Command =
        run {
            turnController.setpoint = setpoint
        } as Command

    fun stop(): Command =
        run {
            io.setVoltage(0.0)
            turnController.setpoint = inputs.turnMotorAngle.radians
        } as Command
}