package first.robot

import com.ctre.phoenix6.signals.InvertedValue
import com.ctre.phoenix6.signals.NeutralModeValue

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
    // --- SYSTEM TIMING ---
    const val LOOP_TIME = 0.02

    object ElevatorConstants {
        // --- MOTOR IDS ---
        const val LEFT_ID = 6
        const val RIGHT_ID = 7

        // --- PID VALUES ---
        const val KP: Double = 16.667
        const val KI: Double = 0.1
        const val KD: Double = 0.552
        const val KA: Double = 0.03
        const val KV: Double = 5.236
        const val KG: Double = 0.294

        // --- TALON SETTINGS ---
        const val TALON_STATOR_LIMIT: Double = 60.0
        const val TALON_SUPPLY_LIMIT: Double = 90.0

        val TALON_NEUTRAL_MODE = NeutralModeValue.Brake

        // --- TALON INVERSION ---
        val LEFT_INVERSION = InvertedValue.CounterClockwise_Positive
        val RIGHT_INVERSION = InvertedValue.Clockwise_Positive

        // --- MECHANICAL SPECS ---
        const val DRUM_RADIUS: Double = 0.028648
        const val CARRIAGE_MASS: Double = 5.0 // Waiting for CAD to be done completely
        const val GEAR_RATIO: Double = 50.0 / 12.0
        const val MIN_ELEVATOR_HEIGHT: Double = 0.0
        const val MAX_ELEVATOR_HEIGHT: Double = 2.0574
    }

    object ManipConstants {
        // --- HARDWARE IDs (placeholders: match Phoenix Tuner) ---
        const val ROLL_ID = 1
        const val TURN_ID = 2

        // --- HARDWARE CONFIGURATION ---
        val ROLL_INVERSION = InvertedValue.CounterClockwise_Positive
        val TURN_INVERSION = InvertedValue.CounterClockwise_Positive
        val NEUTRAL_MODE = NeutralModeValue.Brake

        // --- CURRENT LIMITS (amps) ---
        const val ROLL_SUPPLY_LIMIT = 30.0
        const val ROLL_STATOR_LIMIT = 60.0
        const val TURN_SUPPLY_LIMIT = 30.0
        const val TURN_STATOR_LIMIT = 60.0

        // --- VELOCITY GAINS (Slot0) ---
        const val ROLL_KP = 0.1
        const val ROLL_KI = 0.0
        const val ROLL_KD = 0.0
        const val ROLL_KS = 0.0
        const val ROLL_KV = 0.12

        const val TURN_KP = 0.1
        const val TURN_KI = 0.0
        const val TURN_KD = 0.0
        const val TURN_KS = 0.0
        const val TURN_KV = 0.12

        // --- PHYSICAL SPECS & GEARING (motor:mechanism, >1 is a reduction) ---
        const val ROLL_GEARING = 1.0
        const val ROLL_MOI_KG_MM = 0.001

        // --- TURN PIVOT (sim) ---
        const val TURN_MOI_KG_MM = 0.05
        const val ARM_LENGTH_METERS = 0.3
        const val MIN_ANGLE_RADS = 0.0
        const val MAX_ANGLE_RADS = Math.PI / 2
        const val TURN_KP_CONTROLLER = 0.0
        const val TURN_KI_CONTROLLER = 0.0
        const val TURN_KD_CONTROLLER = 0.0
        const val TURN_GEARING = 0.1
    }
}
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
