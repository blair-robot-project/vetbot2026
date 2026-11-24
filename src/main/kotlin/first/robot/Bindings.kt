package first.robot

class Bindings(
    val robotContainer: RobotContainer,
) {
    val driver = robotContainer.driveController

    fun bindControls() {
        driver.x().onTrue(
            robotContainer.elevator.moveElevator(0.25),
        )
        driver.b().onTrue(
            robotContainer.elevator.moveElevator(1.0),
        )
    }
}
