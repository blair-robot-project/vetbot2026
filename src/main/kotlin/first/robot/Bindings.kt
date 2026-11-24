package first.robot
import frc.team449.Constants.ManipConstants

class Bindings(
    val robotContainer: RobotContainer,
){
    val driver = robotContainer.controller

    fun bindControls(){
        val manip = robotContainer.manipulator

        driver.a().whileTrue(manip.grabPiece(ManipConstants.ROLL_SPEED))
        driver.a().onFalse((manip.stopRoller()))

        driver.b().whileTrue(manip.ejectPiece(ManipConstants.ROLL_SPEED))
        driver.b().onFalse((manip.stopRoller()))

        driver.x().onTrue(manip.setAngle(ManipConstants.MAX_ANGLE_RADS))

    }

}
