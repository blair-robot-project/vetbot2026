package frc.robot.subsystems.swerve

import edu.wpi.first.wpilibj2.command.SubsystemBase
import org.littletonrobotics.junction.Logger

class SwerveSubsystem(
    private val io: ModuleIO
) : SubsystemBase() {
    private val inputs: ModuleIOInputsAutoLogged = ModuleIOInputsAutoLogged()

    override fun periodic() {
        io.updateInputs(inputs)

        Logger.processInputs("Swerve", inputs)
    }
}