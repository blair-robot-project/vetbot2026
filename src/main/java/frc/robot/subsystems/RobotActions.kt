package frc.robot.subsystems

import edu.wpi.first.units.Units
import edu.wpi.first.wpilibj2.command.Command
import edu.wpi.first.wpilibj2.command.Commands
import edu.wpi.first.wpilibj2.command.WaitCommand
import frc.robot.RobotContainer
import frc.robot.subsystems.swerve.SwerveSubsystem

class RobotActions(
    robotContainer: RobotContainer
) {
    private val swerve: SwerveSubsystem = robotContainer.swerve
}
