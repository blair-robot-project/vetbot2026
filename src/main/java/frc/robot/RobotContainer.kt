package frc.robot

import frc.robot.subsystems.swerve.DriveIO
import frc.robot.subsystems.swerve.DriveIOHardware
import frc.robot.subsystems.swerve.DriveIOSim
import frc.robot.subsystems.swerve.SwerveSubsystem
import frc.robot.Constants.Mode
import frc.robot.subsystems.RobotActions
import org.wpilib.command3.Command
import org.wpilib.command3.button.CommandXboxController

object RobotContainer {
    val driveController: CommandXboxController = CommandXboxController(0)
    val operatorController: CommandXboxController = CommandXboxController(1)

    val swerve: SwerveSubsystem =
        SwerveSubsystem(
            when (Constants.CURRENT_MODE) {
                Mode.REAL -> DriveIOHardware()
                Mode.SIM -> DriveIOSim()
                Mode.REPLAY -> object : DriveIO {}
            },
        )

    val actions = RobotActions(this)
    val bindings = Bindings(this)
}