package first.robot.subsystems.elevator

import com.ctre.phoenix6.sim.TalonFXSimState
import first.robot.Constants
import first.robot.Constants.ElevatorConstants
import org.wpilib.math.system.DCMotor
import org.wpilib.simulation.ElevatorSim

class ElevatorIOSim : ElevatorIOHardware() {
    private val elevatorSim: ElevatorSim =
        ElevatorSim(
            DCMotor.getKrakenX60(2),
            ElevatorConstants.GEAR_RATIO,
            ElevatorConstants.CARRIAGE_MASS,
            ElevatorConstants.DRUM_RADIUS,
            ElevatorConstants.MIN_ELEVATOR_HEIGHT,
            ElevatorConstants.MAX_ELEVATOR_HEIGHT,
            true,
            ElevatorConstants.MIN_ELEVATOR_HEIGHT,
        )

    val leftMotorSim: TalonFXSimState = leftMotor.simState
    val rightMotorSim: TalonFXSimState = rightMotor.simState

    override fun updateInputs(input: ElevatorIO.ElevatorIOInputs) {
        super.updateInputs(input)

        leftMotorSim.setSupplyVoltage(12.0)
        rightMotorSim.setSupplyVoltage(12.0)

        elevatorSim.setInputVoltage(leftMotorSim.motorVoltage)
        elevatorSim.update(Constants.LOOP_TIME)

        val elevatorMotorPosition =
            (elevatorSim.position / (2.0 * Math.PI * ElevatorConstants.DRUM_RADIUS)) * ElevatorConstants.GEAR_RATIO
        leftMotorSim.setRawRotorPosition(elevatorMotorPosition)
        rightMotorSim.setRawRotorPosition(elevatorMotorPosition)

        val elevatorMotorVelocity =
            (elevatorSim.velocity / (2.0 * Math.PI * ElevatorConstants.DRUM_RADIUS)) * ElevatorConstants.GEAR_RATIO
        leftMotorSim.setRotorVelocity(elevatorMotorVelocity)
        rightMotorSim.setRotorVelocity(elevatorMotorVelocity)
    }
}
