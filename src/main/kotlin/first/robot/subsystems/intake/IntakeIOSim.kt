package first.robot.subsystems.intake

import org.wpilib.math.system.DCMotor
import org.wpilib.simulation.FlywheelSim
import org.wpilib.simulation.SingleJointedArmSim

class IntakeIOSim : IntakeIOHardware() {
    var pivotSim = SingleJointedArmSim(
        DCMotor.getKrakenX44(2),
        //gearing
        //moi
        //

    )

    var rollerSim = FlywheelSim(

    )

    override fun updateInputs(inputs: IntakeIO.IntakeInputs) {
        super.updateInputs(inputs)

    }

}
