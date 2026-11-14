package first.robot.manipulator

import frc.team449.Constants.ManipConstants
import org.littletonrobotics.junction.Logger
import org.wpilib.command3.Command
import org.wpilib.command3.Mechanism
import org.wpilib.math.controller.PIDController

class ManipIOSubsystem(
    private val io: ManipIO,
) : Mechanism() {
    private val inputs: ManipIOInputsAutoLogged = ManipIOInputsAutoLogged()

    private val turnController =
        PIDController(
            ManipConstants.TURN_KP_CONTROLLER,
            ManipConstants.TURN_KI_CONTROLLER,
            ManipConstants.TURN_KD_CONTROLLER,
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
