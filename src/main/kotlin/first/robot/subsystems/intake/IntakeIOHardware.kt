package first.robot.subsystems.intake

open class IntakeIOHardware: IntakeIO {
    val rightPivot = TalonFX(1)
    val leftPivot = TalonFX(2)
    val topRoller = TalonFX(3)
    val bottomRoller = TalonFX(4)


}