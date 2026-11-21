package first.robot.subsystems.intake

import org.littletonrobotics.junction.Logger
import org.wpilib.command3.Mechanism

class IntakeSubsystem(
    private val io: IntakeIO,
) : Mechanism() {
    private val inputs: IntakeIOInputsAutoLogged = IntakeIOInputsAutoLogged()

    val rightPivotTargetRads: Double = 0.0
    val rightPivotAngleRads: Double get() = inputs.rightPivotPositionRads

    val leftPivotTargetRads: Double = 0.0
    val leftPivotAngleRads: Double get() = inputs.leftPivotPositionRads

    fun periodic() {
        io.updateInputs(inputs)
        Logger.processInputs("Intake", inputs)
        Logger.recordOutput("R Intake pivot target pos rads", rightPivotTargetRads)
        Logger.recordOutput("L Intake pivot target pos rads", leftPivotTargetRads)
    }
    // Put up (set pivot motors to feedManip position)
    //  fun feedManip(): {

    // }

    // Put down (set pivot motors to pivot down rads)
    // Run rollers
    // Stop rollers

    // Pick up piece
    // Feed to manip
    // Outtake
}
