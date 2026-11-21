package first.robot.subsystems.intake

import org.littletonrobotics.junction.Logger
import org.wpilib.command3.Mechanism
import org.wpilib.units.Units
import org.wpilib.units.measure.Angle

class IntakeSubsystem(
    private val io: IntakeIO,
) : Mechanism() {
    private val inputs: IntakeInputsAutoLogged = IntakeInputsAutoLogged()

    val pivotTargetRads: Angle = Units.Radians.of(0.0)
    var pivotAngleRads: Angle = Units.Radians.of(0.0)

    val rollerTargetVolts: Double = 0.0
    var rollerVolts: Double = 0.0

    fun periodic() {
        io.updateInputs(inputs)
        Logger.processInputs("Intake", inputs)
        Logger.recordOutput("Intake pivot target pos rads", pivotTargetRads)
        Logger.recordOutput("Roller target volts", rollerTargetVolts)
    }

    // Put up (set pivot motors to feedManip position)
    fun putUpPivot() {
        pivotAngleRads = IntakeConstants.PIVOT_FEED_MANIP_ANGLE_RADS
        io.setPivotAngle(pivotAngleRads)
    }

    // Put down (set pivot motors to pivot down rads)
    fun putDownPivot() {
        pivotAngleRads = IntakeConstants.PIVOT_DOWN_RADS
        io.setPivotAngle(pivotAngleRads)
    }

    // Run rollers
    fun runRollers() {
        rollerVolts = 5.0
        io.setRollerVoltage(rollerVolts)
    }

    fun runRollersBackwards() {
        rollerVolts = -5.0
    }

    // Stop rollers
    fun stopRollers() {
        rollerVolts = 0.0
        io.setRollerVoltage(rollerVolts)
    }

    // Pick up piece
    // Feed to manip
    // Outtake
}
