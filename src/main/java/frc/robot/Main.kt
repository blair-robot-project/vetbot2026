package frc.robot

import org.wpilib.framework.RobotBase

object Main {
    @JvmStatic
    fun main(args: Array<String>) {
        RobotBase.startRobot { Robot() }
    }
}

