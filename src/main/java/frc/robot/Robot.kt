package frc.robot

import com.ctre.phoenix6.SignalLogger
import org.littletonrobotics.junction.LogFileUtil
import org.littletonrobotics.junction.LoggedRobot
import org.littletonrobotics.junction.Logger
import org.littletonrobotics.junction.networktables.NT4Publisher
import org.littletonrobotics.junction.wpilog.WPILOGReader
import org.littletonrobotics.junction.wpilog.WPILOGWriter

class Robot : LoggedRobot() {
    private val robotContainer = RobotContainer

    init {
        println("Initializing Robot!")

        when (Constants.CURRENT_MODE) {
            Constants.Mode.REAL -> {
                Logger.addDataReceiver(WPILOGWriter())
                Logger.addDataReceiver(NT4Publisher())
            }

            Constants.Mode.SIM -> {
                Logger.addDataReceiver(NT4Publisher())
            }

            Constants.Mode.REPLAY -> {
                this.setUseTiming(false) // run as fast as possible
                val logPath: String = LogFileUtil.findReplayLog()
                Logger.setReplaySource(WPILOGReader(logPath))
                Logger.addDataReceiver(WPILOGWriter(LogFileUtil.addPathSuffix(logPath, "_sim")))
            }
        }

        SignalLogger.enableAutoLogging(false)
        Logger.start()
    }

    fun robotInit() {
        robotContainer.bindings.setDefaultCommands()
        robotContainer.bindings.bindControls()
    }

    override fun robotPeriodic() {
        Logger.recordOutput("Robot/Mode", Constants.CURRENT_MODE.name)

        logComponentPoses()
    }

    override fun autonomousPeriodic() {}

    private fun logComponentPoses() {
    }
}
