package frc.robot.subsystems.swerve

import edu.wpi.first.math.kinematics.ChassisSpeeds
import edu.wpi.first.wpilibj2.command.Command
import edu.wpi.first.wpilibj2.command.SubsystemBase
import org.littletonrobotics.junction.Logger

class SwerveSubsystem(
    private val io: DriveIO
) : SubsystemBase() {
    private val inputs: DriveIOInputsAutoLogged = DriveIOInputsAutoLogged()

    override fun periodic() {
        io.updateInputs(inputs)

        Logger.processInputs("Swerve", inputs)
    }

    fun setState(xVelocity: Double, yVelocity: Double, angularVelocity: Double) {
        io.setState(xVelocity, yVelocity,angularVelocity)
    }

    fun setState(speeds: ChassisSpeeds) {
        io.setState(speeds.vxMetersPerSecond,speeds.vyMetersPerSecond,speeds.omegaRadiansPerSecond)
    }

    // update to v3 command later
    fun rotate(speed: Double): Command =
        runOnce {
            io.setRotationSpeed(speed)
        }.withName("Rotate")

    fun moveUp(speed: Double): Command =
        runOnce {
            io.setVerticalVelocity(speed)
        }.withName("Move up")

    fun moveRight(speed: Double): Command =
        runOnce {
            io.setHorizontalVelocity(speed)
        }.withName("Move right")

    fun stopSwerve() {
        io.setState(0.0, 0.0, 0.0)
    }
}