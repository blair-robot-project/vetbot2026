package frc.robot

import edu.wpi.first.wpilibj.RobotBase

object Constants
{
    // --- OPERATIONAL MODES ---
    enum class Mode {
        REAL,
        SIM,
        REPLAY
    }

    val CURRENT_MODE: Mode = if (RobotBase.isReal()) Mode.REAL else Mode.SIM
}



