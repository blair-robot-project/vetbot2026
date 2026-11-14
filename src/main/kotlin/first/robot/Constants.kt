package frc.team449

import com.ctre.phoenix6.signals.InvertedValue
import com.ctre.phoenix6.signals.NeutralModeValue

object Constants {
    const val LOOP_TIME = 0.02

    // ...your other constants

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
        const val ROLL_kP = 0.1
        const val ROLL_kI = 0.0
        const val ROLL_kD = 0.0
        const val ROLL_kS = 0.0
        const val ROLL_kV = 0.12

        const val TURN_kP = 0.1
        const val TURN_kI = 0.0
        const val TURN_kD = 0.0
        const val TURN_kS = 0.0
        const val TURN_kV = 0.12

        // --- PHYSICAL SPECS & GEARING (motor:mechanism, >1 is a reduction) ---
        const val ROLL_GEARING = 1.0
        const val ROLL_MOI_KG_MM = 0.001

        // --- TURN PIVOT (sim) ---
        const val TURN_MOI_KG_MM = 0.05
        const val ARM_LENGTH_METERS = 0.3
        const val MIN_ANGLE_RADS = 0.0
        const val MAX_ANGLE_RADS = Math.PI / 2
        const val TURN_kP_controller = 0.0
        const val TURN_kI_controller = 0.0
        const val TURN_kD_controller = 0.0
        const val TURN_GEARING = 0.1
    }
}