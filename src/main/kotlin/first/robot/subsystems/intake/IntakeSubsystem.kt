package first.robot.subsystems.intake

import org.littletonrobotics.junction.Logger
import org.wpilib.command3.Command
import org.wpilib.command3.Mechanism
import org.wpilib.units.Units
import org.wpilib.units.measure.Angle

class IntakeSubsystem(
    private val io: IntakeIO,
) : Mechanism() {
    private val inputs: IntakeInputsAutoLogged = IntakeInputsAutoLogged()

    var pivotTargetRads: Angle = Units.Radians.of(0.0)
    var pivotAngleRads: Angle = Units.Radians.of(0.0)

    var rollerTargetVolts: Double = 0.0
    var rollerVolts: Double = 0.0

    fun periodic() {
        io.updateInputs(inputs)
        Logger.processInputs("Intake", inputs)
        Logger.recordOutput("Intake pivot target pos rads", pivotTargetRads)
        Logger.recordOutput("Roller target volts", rollerTargetVolts)
    }

    // Put down (set pivot motors to pivot down rads)
    fun setPivot(pos: Double): Command =
        Command
            .requiring(
                this,
            ).executing { coroutine ->
                pivotTargetRads = Units.Radians.of(pos)
                while (true) {
                    io.setPivotAngle(Units.Radians.of(pos))
                    coroutine.yield()
                }
            }.named("Set pivot angle")

    fun putUpPivot(): Command =
        Command
            .requiring(
                this,
            ).executing { coroutine ->
                while (true) {
                    setPivot(IntakeConstants.PIVOT_FEED_MANIP_ANGLE_RADS)
                    coroutine.yield()
                }
            }.named("put up pivot")

    // Run rollers
    fun runRollers(volts: Double): Command =
        Command
            .requiring(
                this,
            ).executing { coroutine ->
                rollerTargetVolts = volts
                while (true) {
                    io.setRollerVoltage(volts)
                    coroutine.yield()
                }
            }.named("run rollers")

    // Stop rollers
    fun stopRollers(): Command =
        Command
            .requiring(
                this,
            ).executing { coroutine ->
                rollerTargetVolts = 0.0
                while (true) {
                    runRollers(0.0)
                    coroutine.yield()
                }
            }.named("stop rollers")

    // Pick up piece: put the pivot down, runRollers
    fun pickUpPiece(): Command =
        Command
            .requiring(
                this,
            ).executing { coroutine ->
                while (true) {
                    setPivot(IntakeConstants.PIVOT_DOWN_RADS)
                    coroutine.await(runRollers(5.0))
                    coroutine.yield()
                }
            }.named("pick up piece")

    // Feed to manip
    // put the pivot up
    // run rollers backwards
    fun feedToManip(): Command =
        Command
            .requiring(
                this,
            ).executing { coroutine ->
                while (true) {
                    putUpPivot()
                    coroutine.await(runRollers(-5.0))
                    coroutine.yield()
                }
            }.named("feed to manip")

    fun downAndOuttake(): Command =
        Command
            .requiring(
                this,
            ).executing { coroutine ->
                while (true) {
                    setPivot(IntakeConstants.PIVOT_DOWN_RADS)
                    coroutine.await(runRollers(-5.0))
                    coroutine.yield()
                }
            }.named("down and outtake")

    fun getDepotDirectly(): Command =
        Command
            .requiring(
                this,
            ).executing { coroutine ->
                while (true) {
                    setPivot(IntakeConstants.PIVOT_DEPOT_ANGLE_RADS)
                    coroutine.await(runRollers(5.0))
                    coroutine.yield()
                }
            }.named("intake depot directly")

    fun outtakeInOven(): Command =
        Command
            .requiring(
                this,
            ).executing { coroutine ->
                while (true) {
                    setPivot(IntakeConstants.PIVOT_OVEN_ANGLE_RADS)
                    coroutine.await(runRollers(-5.0))
                    coroutine.yield()
                }
            }.named("outtake in oven")
}
