package frc.robot

import edu.wpi.first.units.Units
import edu.wpi.first.wpilibj2.command.Command
import edu.wpi.first.wpilibj2.command.Commands
import edu.wpi.first.wpilibj2.command.button.Trigger
import kotlin.math.abs

class Bindings(
    val robotContainer: RobotContainer
) {
    val driver = robotContainer.driveController
    val actions = robotContainer.actions

    fun setDefaultCommands() {
    }

    fun bindControls() {
        driver
            .a()
            .whileTrue(
                robotContainer.swerve
                    .moveRight(1.0),
            )

        driver
            .x()
            .whileTrue(
                robotContainer.swerve
                    .moveUp(1.0),
            )

        driver
            .y()
            .whileTrue(
                robotContainer.swerve
                    .moveRight(-1.0),
            )

        driver
            .b()
            .whileTrue(
                robotContainer.swerve
                    .rotate(1.0),
            )
    }
}
