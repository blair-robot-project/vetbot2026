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
    private var turnSetpointInitilized= false
    fun periodic() {
        io.updateInputs(inputs)
        if (!turnSetpointInitilized){
            turnController.setpoint=inputs.turnMotorAngle.radians
            turnSetpointInitilized=true
        }
        io.setTurnVoltage(turnController.calculate(inputs.turnMotorAngle.radians))

        Logger.processInputs("Manipulator", inputs)
        Logger.recordOutput("Manipulator/TurnSetpoint", turnController.setpoint)
    }

    fun setMotorVoltage(voltage: Double): Command =
        Command
            .requiring(
                this,
            ).executing { coroutine ->
                while(true){
                    io.setRollVelocity(voltage)
                    coroutine.yield()
                }
            }.named("set roll voltage")

    fun grabPiece(radsPerSecond: Double): Command =
        Command
            .requiring(
                this,
            ).executing { coroutine ->
                val rollTargetVelocity = radsPerSecond
                while(true){
                    io.setRollVelocity(radsPerSecond)
                    coroutine.yield()
                }
            }.named("grab piece")

    fun ejectPiece(radsPerSecond: Double): Command =
        Command
            .requiring(
                this,
            ).executing { coroutine ->
                val rollTargetVelocity = -radsPerSecond
                while(true){
                    io.setRollVelocity(-radsPerSecond)
                    coroutine.yield()
                }
            }.named("eject piece")

    fun setAngle(setpoint: Double): Command =
        Command
            .requiring(
                this,
            ).executing { coroutine ->
                turnController.setpoint=
                    setpoint.coerceIn(ManipConstants.MIN_ANGLE_RADS, ManipConstants.MAX_ANGLE_RADS)
                coroutine.yield()
            }.named("grab piece")

    fun stop(): Command =
        Command

            .requiring(
                this,
            ).executing { coroutine ->
                val rollTargetVelocity= 0.0
                io.setVoltage(0.0)
                turnController.setpoint=inputs.turnMotorAngle.radians
                coroutine.yield()
            }.named("stop manip")

}
