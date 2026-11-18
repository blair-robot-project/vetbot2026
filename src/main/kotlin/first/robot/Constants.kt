package first.robot

import org.wpilib.framework.RobotBase
import org.wpilib.math.geometry.Pose3d
import org.wpilib.system.Filesystem
import org.wpilib.vision.apriltag.AprilTagFieldLayout

/**
 * The Constants class provides a convenient place for teams to hold robot-wide numerical or boolean
 * constants. This class should not be used for any other purpose. All constants should be declared
 * globally (i.e. public static). Do not put anything functional in this class.
 *
 *
 * It is advised to statically import this class (or one of its inner classes) wherever the
 * constants are needed, to reduce verbosity.
 */
object Constants {
    // --- OPERATIONAL MODES ---
    enum class Mode {
        REAL,
        SIM,
        REPLAY,
    }

    val CURRENT_MODE: Mode = if (RobotBase.isReal()) Mode.REAL else Mode.SIM

    object FieldConstants {
        // --- FIELD DIMENSIONS ---
        val FIELD_LENGTH_METERS = VisionConstants.BUNNYBOTS_FIELD_LAYOUT.fieldLength
        val FIELD_WIDTH_METERS = VisionConstants.BUNNYBOTS_FIELD_LAYOUT.fieldWidth
    }

    object VisionConstants {
        // --- APRIL TAG FIELD ---
        val BUNNYBOTS_FIELD_LAYOUT: AprilTagFieldLayout =
            AprilTagFieldLayout(
                Filesystem.getDeployDirectory().absolutePath + "/Bunnybots_2026_Centered.json",
            )

        // --- CAMERA NAMES ---
        const val CAMERA_ONE_NAME: String = "arducam-one"
        const val CAMERA_TWO_NAME: String = "arducam-two"

        // --- ROBOT TO CAMERA TRANSFORMATION ---
        val ROBOT_TO_CAMERA_ONE: Pose3d = Pose3d.kZero
        val ROBOT_TO_CAMERA_TWO: Pose3d = Pose3d.kZero

        // --- THRESHOLDS ---
        const val MAX_AMBIGUITY = 0.15 // check rly quick
        const val MAX_Z_ERROR_METERS = 0.5 // check

        // --- STD DEVS ---
        const val LINEAR_STD_DEV_BASELINE_METERS = 0.02
        const val ANGULAR_STD_DEV_BASELINE_RADIANS = 0.06

        val CAMERA_STD_DEV_FACTORS: DoubleArray =
            doubleArrayOf(
                1.0,
                1.0,
            )
    }
}
