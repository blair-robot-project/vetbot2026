package first.robot

class Bindings(
    val robotContainer: RobotContainer,
) {
    val driver = robotContainer.driveController

    fun bindControls() {
        driver
            .x()
            .onTrue(
                robotContainer.intake.pickUpPiece(),
            ).onFalse(
                robotContainer.intake.runRollers(0.0),
            )
        driver
            .y()
            .onTrue(
                robotContainer.intake.feedToManip(),
            ).onFalse(
                robotContainer.intake.resetToNormalAndStop(),
            )

        driver
            .a()
            .onTrue(
                robotContainer.intake.downAndOuttake(),
            ).onFalse(
                robotContainer.intake.resetToNormalAndStop(),
            )

        driver
            .b()
            .onTrue(
                robotContainer.intake.outtakeInOven(),
            ).onFalse(
                robotContainer.intake.resetToNormalAndStop(),
            )
    }
}
