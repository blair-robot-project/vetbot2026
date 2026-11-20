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
}
