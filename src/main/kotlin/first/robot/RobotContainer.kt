package first.robot

import first.robot.subsystems.elevator.ElevatorIOSim
import first.robot.subsystems.elevator.ElevatorSubsystem

/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the [Robot]
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and button mappings) should be declared here.
 */
class RobotContainer {
    // The container for the robot. Contains subsystems, OI devices, and commands.
    val elevator: ElevatorSubsystem = ElevatorSubsystem(ElevatorIOSim())
}
