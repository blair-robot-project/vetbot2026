package frc.robot.subsystems.swerve

import com.ctre.phoenix6.BaseStatusSignal
import com.ctre.phoenix6.CANBus
import com.ctre.phoenix6.controls.PositionVoltage
import com.ctre.phoenix6.controls.VelocityVoltage
import com.ctre.phoenix6.hardware.TalonFX
import frc.robot.subsystems.swerve.ModuleData.Companion.fromArray
import org.wpilib.math.geometry.Translation2d
import org.wpilib.math.kinematics.ChassisVelocities
import org.wpilib.math.kinematics.SwerveDriveKinematics
import org.wpilib.units.Units

open class DriveIOHardware : DriveIO {
    val turningMotors: List<TalonFX> = SwerveConstants.TURNING_MOTOR_IDS.map { i -> TalonFX(i, CANBus()) }
    val drivingMotors: List<TalonFX> = SwerveConstants.DRIVING_MOTOR_IDS.map { i -> TalonFX(i, CANBus()) }

    val turningMotorsConnected =
        turningMotors.map { i ->
            BaseStatusSignal.isAllGood(
                i.motorVoltage,
                i.velocity,
                i.statorCurrent,
            )
        }

    val drivingMotorsConnected =
        drivingMotors.map { i ->
            BaseStatusSignal.isAllGood(
                i.motorVoltage,
                i.velocity,
                i.statorCurrent,
            )
        }

    val length = SwerveConstants.ROBOT_LENGTH
    val width = SwerveConstants.ROBOT_WIDTH

    val frontLeftLocation = Translation2d(width / 2, length / 2)
    val frontRightLocation = Translation2d(-width / 2, length / 2)
    val backLeftLocation = Translation2d(width / 2, -length / 2)
    val backRightLocation = Translation2d(-width / 2, -length / 2)

    val kinematics =
        SwerveDriveKinematics(
            frontLeftLocation,
            frontRightLocation,
            backLeftLocation,
            backRightLocation,
        )

    val speeds = ChassisVelocities(0.0, 0.0, 0.0)
    val moduleStates = kinematics.toSwerveModuleVelocities(speeds)

    val velocityRequest = VelocityVoltage(0.0)
    val positionRequest = PositionVoltage(0.0)

    override fun updateInputs(inputs: DriveIO.DriveIOInputs) {
        inputs.topLeftData = fromArray(getModuleData(0))
        inputs.topRightData = fromArray(getModuleData(1))
        inputs.bottomLeftData = fromArray(getModuleData(2))
        inputs.bottomRightData = fromArray(getModuleData(3))
    }

    override fun setVoltages(voltage: Double) {
        turningMotors.map { i -> i.setVoltage(voltage) }
        drivingMotors.map { i -> i.setVoltage(voltage) }
    }

    override fun setRotationSpeed(velocity: Double) {
        speeds.omega = velocity
        calculate()
    }

    override fun setHorizontalVelocity(velocity: Double) {
        speeds.vx = velocity
        calculate()
    }

    override fun setVerticalVelocity(velocity: Double) {
        speeds.vy = velocity
        calculate()
    }

    override fun setState(
        horizVelocity: Double,
        vertVelocity: Double,
        rotVelocity: Double,
    ) {
        speeds.vx = horizVelocity
        speeds.vy = vertVelocity
        speeds.omega = rotVelocity
        calculate()
    }

    fun calculate() {
        for (i in 0..3) {
            turningMotors[i].setControl(positionRequest.withPosition(moduleStates[i].angle.radians * SwerveConstants.TURN_GEARING))
            drivingMotors[i].setControl(velocityRequest.withVelocity(moduleStates[i].velocity * SwerveConstants.DRIVE_GEARING))
        }
    }

    fun getModuleData(index: Int): Array<Double> {
        val turningMotor: TalonFX = turningMotors[index]
        val driveMotor: TalonFX = drivingMotors[index]

        return arrayOf(
            turningMotor.motorVoltage.value.`in`(Units.Volts),
            turningMotor.velocity.value.`in`(Units.RadiansPerSecond),
            turningMotor.statorCurrent.value.`in`(Units.Amps),
            driveMotor.motorVoltage.value.`in`(Units.Volts),
            driveMotor.velocity.value.`in`(Units.RadiansPerSecond),
            driveMotor.statorCurrent.value.`in`(Units.Amps),
        )
    }
}
