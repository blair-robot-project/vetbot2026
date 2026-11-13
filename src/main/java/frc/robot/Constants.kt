package frc.robot

import edu.wpi.first.wpilibj.RobotBase

/*
 * The Constants file provides a convenient place for teams to hold robot-wide
 * numerical or boolean constants. This file should not be used for any other purpose.
 * All String, Boolean, and numeric (Int, Long, Float, Double) constants should use
 * `const` definitions. Other constant types should use `val` definitions.
 */

object Constants
{
    // --- OPERATIONAL MODES ---
    enum class Mode {
        REAL,
        SIM,
        REPLAY
    }

    val CURRENT_MODE: Mode = if (RobotBase.isReal()) Mode.REAL else Mode.SIM

    object SwerveConstants {
        val TOP_LEFT_TURNING_MOTOR_ID = 0
        val TOP_RIGHT_TURNING_MOTOR_ID = 0
        val BOTTOM_LEFT_TURNING_MOTOR_ID = 0
        val BOTTOM_RIGHT_TURNING_MOTOR_ID = 0
        val TOP_LEFT_DRIVING_MOTOR_ID = 0
        val TOP_RIGHT_DRIVING_MOTOR_ID = 0
        val BOTTOM_LEFT_DRIVING_MOTOR_ID = 0
        val BOTTOM_RIGHT_DRIVING_MOTOR_ID = 0
    }
}



