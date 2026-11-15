package frc.robot

import edu.wpi.first.wpilibj2.command.Command
import edu.wpi.first.wpilibj2.command.PrintCommand
import edu.wpi.first.wpilibj2.command.button.CommandXboxController
import frc.robot.subsystems.swerve.DriveIO
import frc.robot.subsystems.swerve.DriveIOHardware
import frc.robot.subsystems.swerve.DriveIOSim
import frc.robot.subsystems.swerve.SwerveSubsystem
import frc.robot.Constants.Mode
import frc.robot.subsystems.RobotActions

object RobotContainer {
    val driveController: CommandXboxController = CommandXboxController(0)
    val operatorController: CommandXboxController = CommandXboxController(1)
    var autonomousCommand: Command = PrintCommand("If you see this, you probably didn't run an auto.")

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