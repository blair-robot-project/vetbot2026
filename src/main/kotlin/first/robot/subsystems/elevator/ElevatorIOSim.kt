package first.robot.subsystems.elevator

import first.robot.Constants
import first.robot.Constants.ElevatorConstants
import org.wpilib.math.system.DCMotor
import org.wpilib.simulation.ElevatorSim

class ElevatorIOSim : ElevatorIOHardware() {
    private val elevatorSim: ElevatorSim =
        ElevatorSim(
            DCMotor.getKrakenX60(2),
            1 / ElevatorConstants.GEAR_RATIO,
            ElevatorConstants.CARRIAGE_MASS,
            ElevatorConstants.DRUM_RADIUS,
            ElevatorConstants.MIN_ELEVATOR_HEIGHT,
            ElevatorConstants.MAX_ELEVATOR_HEIGHT,
            true,
            ElevatorConstants.MIN_ELEVATOR_HEIGHT,
        )

    val leftMotorSim = leftMotor.simState
    val rightMotorSim = rightMotor.simState

    override fun updateInputs(input: ElevatorIO.ElevatorIOInputs) {
        super.updateInputs(input)

        leftMotorSim.setSupplyVoltage(12.0)
        rightMotorSim.setSupplyVoltage(12.0)

        elevatorSim.update(Constants.LOOP_TIME)

        val elevatorMotorPosition = elevatorSim.position * ElevatorConstants.DRUM_RADIUS * 2 * Math.PI
        leftMotorSim.setRawRotorPosition(elevatorMotorPosition)
        rightMotorSim.setRawRotorPosition(elevatorMotorPosition)

        val elevatorMotorVelocity = elevatorSim.velocity * ElevatorConstants.DRUM_RADIUS * 2 * Math.PI
        leftMotorSim.setRotorVelocity(elevatorMotorVelocity)
        rightMotorSim.setRotorVelocity(elevatorMotorVelocity)
    }
}
