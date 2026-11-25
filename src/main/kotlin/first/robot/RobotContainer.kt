package first.robot

import first.robot.Constants.Mode
import first.robot.Constants.VisionConstants
import first.robot.mechanism.drive.DriveSubsystem
import first.robot.mechanism.elevator.ElevatorIOSim
import first.robot.mechanism.elevator.ElevatorSubsystem
import first.robot.mechanism.vision.VisionIO
import first.robot.mechanism.vision.VisionIOPhoton
import first.robot.mechanism.vision.VisionIOPhotonSim
import first.robot.mechanism.vision.VisionSubsystem
import org.wpilib.command3.button.CommandNiDsXboxController
import org.wpilib.math.geometry.Pose3d

/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the [Robot]
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and button mappings) should be declared here.
 */
class RobotContainer {
    val driveController: CommandNiDsXboxController = CommandNiDsXboxController(0)

    // The container for the robot. Contains subsystems, OI devices, and commands.
    val elevator: ElevatorSubsystem = ElevatorSubsystem(ElevatorIOSim())

    val bindings = Bindings(this)
    val drive: DriveSubsystem =
        DriveSubsystem()
    val vision: VisionSubsystem =
        when (Constants.CURRENT_MODE) {
            Mode.REAL -> {
                VisionSubsystem(
                    drive::addVisionMeasurement,
                    { false }, // replace with questNav.isActive when it is avliable,
                    VisionIOPhoton(
                        VisionConstants.CAMERA_ONE_NAME,
                        VisionConstants.ROBOT_TO_CAMERA_ONE.minus(Pose3d.kZero),
                    ),
                    VisionIOPhoton(
                        VisionConstants.CAMERA_TWO_NAME,
                        VisionConstants.ROBOT_TO_CAMERA_TWO.minus(Pose3d.kZero),
                    ),
                )
            }

            Mode.SIM -> {
                VisionSubsystem(
                    drive::addVisionMeasurement,
                    { false },
                    VisionIOPhotonSim(
                        "camera1",
                        VisionConstants.ROBOT_TO_CAMERA_ONE,
                        { drive.pose },
                    ),
                    VisionIOPhotonSim(
                        "camera2",
                        VisionConstants.ROBOT_TO_CAMERA_TWO,
                        { drive.pose },
                    ),
                )
            }

            Mode.REPLAY -> {
                VisionSubsystem(
                    drive::addVisionMeasurement,
                    { false },
                    object : VisionIO {},
                    object : VisionIO {},
                )
            }
        }

    init {
    }
}
