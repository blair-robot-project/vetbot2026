package first.robot.subsystems.elevator

import org.littletonrobotics.junction.Logger
import org.wpilib.command3.Command
import org.wpilib.command3.Mechanism

class ElevatorSubsystem(
    private val io: ElevatorIO,
) : Mechanism() {
    private val inputs: ElevatorIOInputsAutoLogged = ElevatorIOInputsAutoLogged()

    var elevatorTargetPosition: Double = 0.0
    val elevatorPos: Double
        get() = inputs.elevatorPosMeters

    fun periodic() {
        io.updateInputs(inputs)
        Logger.processInputs("Elevator", inputs)

        Logger.recordOutput("Elevator/Target Position", elevatorTargetPosition)
    }

    fun moveElevator(m: Double): Command =
        run {
            elevatorTargetPosition = m
            io.setPosition(m)
        }.named("Move Elevator")
}
