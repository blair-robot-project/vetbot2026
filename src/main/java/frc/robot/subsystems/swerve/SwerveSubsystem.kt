package frc.robot.subsystems.swerve

import org.littletonrobotics.junction.Logger
import org.wpilib.command3.Command
import org.wpilib.command3.Mechanism
import org.wpilib.math.kinematics.ChassisVelocities

class SwerveSubsystem(
    private val io: DriveIO,
) : Mechanism {
    private val inputs: DriveIOInputsAutoLogged = DriveIOInputsAutoLogged()

    fun periodic() {
        io.updateInputs(inputs)

        Logger.processInputs("Swerve", inputs)
    }

    fun setState(
        xVelocity: Double,
        yVelocity: Double,
        angularVelocity: Double,
    ) {
        io.setState(xVelocity, yVelocity, angularVelocity)
    }

    fun setState(speeds: ChassisVelocities) {
        io.setState(speeds.vx, speeds.vy, speeds.omega)
    }

    fun rotate(speed: Double): Command =
        run {
            io.setRotationSpeed(speed)
        }.named("Rotate")

    fun moveUp(speed: Double): Command =
        run {
            io.setVerticalVelocity(speed)
        }.named("Move up")

    fun moveRight(speed: Double): Command =
        run {
            io.setHorizontalVelocity(speed)
        }.named("Move right")

    fun stopSwerve() {
        io.setState(0.0, 0.0, 0.0)
    }
}
