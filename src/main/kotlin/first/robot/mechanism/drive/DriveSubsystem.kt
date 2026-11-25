package first.robot.mechanism.drive

import org.wpilib.command3.Mechanism
import org.wpilib.math.geometry.Pose2d
import org.wpilib.math.linalg.Matrix
import org.wpilib.math.numbers.N1
import org.wpilib.math.numbers.N3

class DriveSubsystem : Mechanism() {
    val pose: Pose2d = Pose2d.kZero

    fun addVisionMeasurement(
        visionRobotPoseMeters: Pose2d,
        timestampSeconds: Double,
        visionMeasurementStdDevs: Matrix<N3, N1>,
    ) {}
}
