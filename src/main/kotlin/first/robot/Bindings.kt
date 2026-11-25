package first.robot

class Bindings(
    val robotContainer: RobotContainer,
) {
    val driver = robotContainer.driveController

    fun bindControls() {
        driver
            .rightBumper()
            .onTrue(
                robotContainer.intake.pickUpPiece(),
            ).onFalse(
                robotContainer.intake.runRollers(0.0),
            )
        driver
            .leftBumper()
            .onTrue(
                robotContainer.intake.feedToManip(),
            ).onFalse(
                robotContainer.intake.resetToNormalAndStop(),
            )

        driver
            .rightTrigger()
            .onTrue(
                robotContainer.intake.downAndOuttake(),
            ).onFalse(
                robotContainer.intake.resetToNormalAndStop(),
            )

        driver
            .leftTrigger()
            .onTrue(
                robotContainer.intake.outtakeInOven(),
            ).onFalse(
                robotContainer.intake.resetToNormalAndStop(),
            )
    }
}
