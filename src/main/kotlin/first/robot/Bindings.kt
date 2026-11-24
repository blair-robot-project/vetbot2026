package first.robot

class Bindings(
    val robotContainer: RobotContainer
) {
    val driver = robotContainer.driveController

    fun bindControls() {
        driver.x().onTrue(
            robotContainer.intake.printCommand("potato")
        )
        driver.b().onTrue(
            robotContainer.intake.PrintCommand("cheese")
        )
    }
}