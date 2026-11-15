package frc.robot.subsystems.swerve

import com.ctre.phoenix6.BaseStatusSignal
import com.ctre.phoenix6.controls.PositionVoltage
import com.ctre.phoenix6.controls.VelocityVoltage
import com.ctre.phoenix6.hardware.TalonFX
import edu.wpi.first.math.geometry.Translation2d
import edu.wpi.first.math.kinematics.ChassisSpeeds
import edu.wpi.first.math.kinematics.SwerveDriveKinematics
import edu.wpi.first.units.Units

open class DriveIOHardware() : DriveIO {
    val motors: List<TalonFX> = SwerveConstants.MOTOR_IDS.map { i -> TalonFX(i) }

    val motorsConnected = (0..7).map { i ->
        BaseStatusSignal.isAllGood(
            motors[i].motorVoltage,
            motors[i].velocity,
            motors[i].statorCurrent
        )
    }

    val length = SwerveConstants.ROBOT_LENGTH
    val width = SwerveConstants.ROBOT_WIDTH

    val frontLeftLocation = Translation2d(width / 2, length / 2)
    val frontRightLocation = Translation2d(-width / 2, length / 2)
    val backLeftLocation = Translation2d(width / 2, -length / 2)
    val backRightLocation = Translation2d(-width / 2, -length / 2)

    val kinematics = SwerveDriveKinematics(
        frontLeftLocation, frontRightLocation, backLeftLocation, backRightLocation
    )

    val speeds = ChassisSpeeds(0.0, 0.0, 0.0)
    val moduleStates = kinematics.toSwerveModuleStates(speeds)

    val velocityRequest = VelocityVoltage(0.0)
    val positionRequest = PositionVoltage(0.0)

    override fun updateInputs(inputs: DriveIO.DriveIOInputs) {
        for (i in 0..3) {
            inputs.data[i].turningVoltage = motors[i * 2].motorVoltage.value.`in`(Units.Volts);
            inputs.data[i].drivingVoltage = motors[i * 2 + 1].motorVoltage.value.`in`(Units.Volts);
        }
    }

    override fun setVoltages(voltage: Double) {
        for (i in 0..7) {
            motors[i].setVoltage(voltage)
        }
    }

    override fun setRotationSpeed(velocity: Double) {
        speeds.omegaRadiansPerSecond = velocity
        calculate()
    }

    override fun setHorizontalVelocity(velocity: Double) {
        speeds.vxMetersPerSecond = velocity
        calculate()
    }

    override fun setVerticalVelocity(velocity: Double) {
        speeds.vyMetersPerSecond = velocity
        calculate()
    }

    override fun setState(horizVelocity: Double, vertVelocity: Double, rotVelocity: Double) {
        speeds.vxMetersPerSecond = horizVelocity
        speeds.vyMetersPerSecond = vertVelocity
        speeds.omegaRadiansPerSecond = rotVelocity
        calculate()
    }

    fun calculate() {
        for (i in 0..3) {
            motors[i * 2 + 1].setControl(velocityRequest.withVelocity(moduleStates[i].speedMetersPerSecond * SwerveConstants.DRIVE_GEARING))
            motors[i * 2].setControl(positionRequest.withPosition(moduleStates[i].angle.radians * SwerveConstants.TURN_GEARING))
        }
    }
}