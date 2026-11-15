package first.robot.subsystems.intake
import com.ctre.phoenix6.BaseStatusSignal
import com.ctre.phoenix6.hardware.TalonFX
import com.ctre.phoenix6.CANBus
import com.ctre.phoenix6.controls.PositionVoltage
import com.ctre.phoenix6.controls.VoltageOut
import com.ctre.phoenix6.hardware.ParentDevice

open class IntakeIOHardware: IntakeIO {
    val rightPivot = TalonFX(1, CANBus())
    val leftPivot = TalonFX(2, CANBus())
    val topRoller = TalonFX(3, CANBus())
    val bottomRoller = TalonFX(4, CANBus())

    private val pivotVoltageRequest = VoltageOut(0.0)
    private val pivotPositionRequest = PositionVoltage(0.0)
    private val rollerVoltageRequest = VoltageOut(0.0)

    private val rightPivotVoltage = rightPivot.motorVoltage
    private val rightPivotPosition = rightPivot.position
    private val rightPivotVelocity = rightPivot.velocity
    private val rightPivotSupply = rightPivot.supplyCurrent
    private val rightPivotStator = rightPivot.statorCurrent
    private val rightPivotTemp = rightPivot.deviceTemp

    private val leftPivotVoltage = leftPivot.motorVoltage
    private val leftPivotPosition = leftPivot.position
    private val leftPivotVelocity = leftPivot.velocity
    private val leftPivotSupply = leftPivot.supplyCurrent
    private val leftPivotStator = leftPivot.statorCurrent
    private val leftPivotTemp = leftPivot.deviceTemp

    private val topRollerVoltage = topRoller.motorVoltage
    private val topRollerVelocity = topRoller.velocity
    private val topRollerSupply = topRoller.supplyCurrent
    private val topRollerStator = topRoller.statorCurrent
    private val topRollerTemp = topRoller.deviceTemp

    private val bottomRollerVoltage = bottomRoller.motorVoltage
    private val bottomRollerVelocity = bottomRoller.velocity
    private val bottomRollerSupply = bottomRoller.supplyCurrent
    private val bottomRollerStator = bottomRoller.statorCurrent
    private val bottomRollerTemp =  bottomRoller.deviceTemp

    private val intakeSignals = arrayOf(
        rightPivotVoltage,
        rightPivotPosition,
        rightPivotVelocity,
        rightPivotSupply,
        rightPivotStator,
        rightPivotTemp,

        leftPivotVoltage,
        leftPivotPosition,
        leftPivotVelocity,
        leftPivotSupply,
        leftPivotStator,
        leftPivotTemp,

        topRollerVoltage,
        topRollerVelocity,
        topRollerSupply,
        topRollerStator,
        topRollerTemp,

        bottomRollerVoltage,
        bottomRollerVelocity,
        bottomRollerSupply,
        bottomRollerStator,
        bottomRollerTemp
    )

    private val rightPivotIsConnected: Boolean
        get() = BaseStatusSignal.isAllGood(
            rightPivotVoltage,
            rightPivotPosition,
            rightPivotVelocity,
            rightPivotSupply,
            rightPivotStator
        )

    private val leftPivotIsConnected: Boolean
        get() = BaseStatusSignal.isAllGood(
            leftPivotVoltage,
            leftPivotPosition,
            leftPivotVelocity,
            leftPivotSupply,
            leftPivotStator
        )

    private val topRollerIsConnected: Boolean
        get() = BaseStatusSignal.isAllGood(
            topRollerVoltage,
            topRollerVelocity,
            topRollerSupply,
            topRollerStator
        )

    private val bottomRollerIsConnected: Boolean
        get() = BaseStatusSignal.isAllGood(
            bottomRollerVoltage,
            bottomRollerVelocity,
            bottomRollerSupply,
            bottomRollerStator
        )

    init {
        //tryUntilOk(5) {rightPivot.configurator.apply( whatever the config is)} x4
        BaseStatusSignal.setUpdateFrequencyForAll(50.0, *intakeSignals)
        ParentDevice.optimizeBusUtilizationForAll(rightPivot, leftPivot, topRoller, bottomRoller)

    }






}