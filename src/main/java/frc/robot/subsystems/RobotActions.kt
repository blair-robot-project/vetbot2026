package frc.robot.subsystems

import frc.robot.RobotContainer
import frc.robot.subsystems.swerve.SwerveSubsystem

class RobotActions(
    robotContainer: RobotContainer,
) {
    private val swerve: SwerveSubsystem = robotContainer.swerve
}
