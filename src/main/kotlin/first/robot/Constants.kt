package first.robot

import com.ctre.phoenix6.signals.InvertedValue
import com.ctre.phoenix6.signals.NeutralModeValue

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
    const val LOOP_TIME = 0.02

    object IntakeConstants {
        // IDS (they need to be checked)
        const val RIGHT_PIVOT_ID = 1
        const val LEFT_PIVOT_ID = 2
        const val TOP_ROLLER_ID = 3
        const val BOTTOM_ROLLER_ID = 4

        // hardware config info
        const val PIVOT_SUPPLY_LIMIT = 10.0
        const val PIVOT_STATOR_LIMIT = 40.0
        const val ROLLER_SUPPLY_LIMIT = 20.0
        const val ROLLER_STATOR_LIMIT = 60.0

        val RIGHT_PIVOT_NEUTRAL_MODE = NeutralModeValue.Brake
        val RIGHT_PIVOT_INVERSION = InvertedValue.Clockwise_Positive // needs to be checked
        val LEFT_PIVOT_NEUTRAL_MODE = NeutralModeValue.Brake
        val LEFT_PIVOT_INVERSION = InvertedValue.Clockwise_Positive // needs to be checked
        val TOP_ROLLER_NEUTRAL_MODE = NeutralModeValue.Brake
        val TOP_ROLLER_INVERSION = InvertedValue.Clockwise_Positive // needs to be checked
        val BOTTOM_ROLLER_NEUTRAL_MODE = NeutralModeValue.Brake
        val BOTTOM_ROLLER_INVERSION = InvertedValue.Clockwise_Positive // needs to be checked

        const val PIVOT_SENSOR_MECH_RATIO = 52.0 // needs to be checked
        const val ROLLER_SENSOR_MECH_RATIO = 4.0 / 3 // needs to be checked

        // SIM STUFF
        const val PIVOT_GEARING = 2.0 // needs to be checked
        const val PIVOT_MOI_KG_MM = 0.005 // needs to be  checked
        const val PIVOT_ARM_LENGTH = 2.0 // needs to be checked
        const val PIVOT_MIN_RADS = 0.0 // needs to be checked
        const val PIVOT_MAX_RADS = 1.0 // needs to be checked

        const val ROLLER_MOI_KG_MM = 0.005 // needs to be checked
        const val ROLLER_GEARING = 2.0 // needs to be checked

        // SUBSYSTEM STUFF
        const val PIVOT_FEED_MANIP_ANGLE_RADS: Double = 2.394 // needs to be checked
        const val PIVOT_DOWN_RADS: Double = 0.0 // needs to be checked
        const val PIVOT_DEPOT_ANGLE_RADS: Double = 1.0 // needs to be checked
        const val PIVOT_OVEN_ANGLE_RADS: Double = 0.5 // needs to be checked
    }
}