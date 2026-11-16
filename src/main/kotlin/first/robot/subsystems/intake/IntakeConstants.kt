package first.robot.subsystems.intake

import com.ctre.phoenix6.signals.InvertedValue
import com.ctre.phoenix6.signals.NeutralModeValue

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
}
