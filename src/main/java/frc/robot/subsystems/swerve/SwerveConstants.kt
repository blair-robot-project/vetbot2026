package frc.robot.subsystems.swerve

object SwerveConstants {
    // --- Robot Constants ---
    const val ROBOT_WIDTH = 29.5 * 0.0254 // inches to meters
    const val ROBOT_LENGTH = 29.5 * 0.0254
    const val ROBOT_MASS = 0.0

    // --- Drive Constants ---
    const val DRIVE_GEARING = 0.0 // meter of distance to radian of motor
    const val TURN_GEARING = 0.0 // change in module angle to radian of motor

    // --- Motor Constants ---
    val TURNING_MOTOR_IDS: Array<Int> = arrayOf(0, 0, 0, 0)
    val DRIVING_MOTOR_IDS: Array<Int> = arrayOf(0, 0, 0, 0)
    const val MOTOR_MOI = 0.0
    const val MOTOR_GEARING = 0.0

    // --- PID Constants ---
    const val SWERVE_KP = 0.0
    const val SWERVE_KI = 0.0
    const val SWERVE_KD = 0.0
}
