package first.robot

import first.robot.Constants.Mode
import first.robot.Constants.VisionConstants
import first.robot.subsystems.drive.DriveSubsystem
import first.robot.subsystems.vision.VisionIO
import first.robot.subsystems.vision.VisionIOPhoton
import first.robot.subsystems.vision.VisionIOPhotonSim
import first.robot.subsystems.vision.VisionSubsystem
import org.wpilib.math.geometry.Pose3d

/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the [Robot]
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and button mappings) should be declared here.
 */
class RobotContainer {
    // The container for the robot. Contains subsystems, OI devices, and commands.
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
