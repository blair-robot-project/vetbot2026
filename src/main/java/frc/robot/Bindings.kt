package frc.robot

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
