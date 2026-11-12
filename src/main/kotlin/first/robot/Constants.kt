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
    // --- SYSTEM TIMING ---
    const val LOOP_TIME = 0.02

    object ElevatorConstants {
        // --- MOTOR IDS ---
        const val LEFT_ID = 6
        const val RIGHT_ID = 7

        // --- PID VALUES ---
        const val kP: Double = 0.0
        const val kI: Double = 0.0
        const val kD: Double = 0.0
        const val kG: Double = 0.0

        // --- TALON SETTINGS ---
        const val TALON_STATOR_LIMIT: Double = 60.0
        const val TALON_SUPPLY_LIMIT: Double = 70.0

        val TALON_NEUTRAL_MODE = NeutralModeValue.Coast

        // --- TALON INVERSION ---
        val LEFT_INVERSION = InvertedValue.Clockwise_Positive
        val RIGHT_INVERSION = InvertedValue.CounterClockwise_Positive

        // --- MECHANICAL SPECS ---
        const val DRUM_RADIUS: Double = 0.35
        const val CARRIAGE_MASS: Double = 0.5
        const val GEAR_RATIO: Double = 2.0 / 3.0
        const val MIN_ELEVATOR_HEIGHT: Double = 0.0
        const val MAX_ELEVATOR_HEIGHT: Double = 1.0
    }
}
