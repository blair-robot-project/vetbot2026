package first.robot.subsystems.vision

import first.robot.Constants
import first.robot.Constants.VisionConstants
import org.littletonrobotics.junction.Logger
import org.wpilib.command3.Mechanism
import org.wpilib.driverstation.Alert
import org.wpilib.math.geometry.Pose2d
import org.wpilib.math.geometry.Pose3d
import org.wpilib.math.linalg.Matrix
import org.wpilib.math.linalg.VecBuilder
import org.wpilib.math.numbers.N1
import org.wpilib.math.numbers.N3
import java.util.function.Supplier
import kotlin.math.abs
import kotlin.math.pow
import kotlin.text.compareTo

class VisionSubsystem(
    private val consumeVisionMeasurement: (
        visionRobotPoseMeters: Pose2d,
        timeStampSeconds: Double,
        visionMeasurementStdDevs: Matrix<N3, N1>,
    ) -> Unit,
    private val questNavActive: Supplier<Boolean>,
    private vararg val io: VisionIO,
) : Mechanism() {
    private val inputs: Array<VisionIOInputsAutoLogged> = Array(io.size) { VisionIOInputsAutoLogged() }

    private val cameraDisconnectAlert: Array<Alert> =
        Array(io.size) { i ->
            Alert("Camera #$i", Alert.Level.LOW)
        }

    var latestPoseForQuest: Pose3d = Pose3d()

    fun periodic() {
        val allAcceptedRobotPose = mutableListOf<Pose3d>()
        val allRejectedRobotPoses = mutableListOf<Pose3d>()

        for (cameraIndex in io.indices) {
            io[cameraIndex].updateInputs(inputs[cameraIndex])
            Logger.processInputs("Vision/Camera$cameraIndex", inputs[cameraIndex])
            cameraDisconnectAlert[cameraIndex].set(!inputs[cameraIndex].connected)

            val acceptedRobotPose = mutableListOf<Pose3d>()
            val rejectedRobotPose = mutableListOf<Pose3d>()

            val linearStdDevs = mutableListOf<Double>()
            val angularStdDevs = mutableListOf<Double>()

            for (observation in inputs[cameraIndex].poseObservations) {
                val rejectPose =
                    observation.targetCount == 0 ||
                        observation.ambiguity > VisionConstants.MAX_AMBIGUITY ||
                        abs(observation.estimatedPose.z) > VisionConstants.MAX_Z_ERROR_METERS ||
                        observation.estimatedPose.x < 0.0 ||
                        observation.estimatedPose.x > Constants.FieldConstants.FIELD_LENGTH_METERS ||
                        observation.estimatedPose.y < 0.0 ||
                        observation.estimatedPose.y > Constants.FieldConstants.FIELD_WIDTH_METERS
                if (rejectPose) {
                    rejectedRobotPose.add(observation.estimatedPose)
                    continue
                }

                acceptedRobotPose.add(observation.estimatedPose)

                val stdDevFactor = observation.averageTagDistance.pow(2.0)
                var linearStdDev = VisionConstants.LINEAR_STD_DEV_BASELINE_METERS * stdDevFactor
                var angularStdDev = VisionConstants.ANGULAR_STD_DEV_BASELINE_RADIANS * stdDevFactor

                if (cameraIndex < VisionConstants.CAMERA_STD_DEV_FACTORS.size) {
                    linearStdDev *= VisionConstants.CAMERA_STD_DEV_FACTORS[cameraIndex]
                    angularStdDev *= VisionConstants.CAMERA_STD_DEV_FACTORS[cameraIndex]
                }

                if (!questNavActive.get()) {
                    consumeVisionMeasurement(
                        observation.estimatedPose.toPose2d(),
                        observation.timeStamp,
                        VecBuilder.fill(linearStdDev, linearStdDev, angularStdDev),
                    )
                }
                val confident =
                    observation.averageTagDistance < 4.0 &&
                        linearStdDev > 0.05
                if (confident) {
                    latestPoseForQuest = observation.estimatedPose
                }
                linearStdDevs.add(linearStdDev)
                angularStdDevs.add(angularStdDev)
                Logger.recordOutput("Vision/Camera$cameraIndex/LinearStdDevs", linearStdDevs.toDoubleArray())
                Logger.recordOutput("Vision/Camera$cameraIndex/AngularStdDevs", angularStdDevs.toDoubleArray())

                Logger.recordOutput("Vision/Camera$cameraIndex/Yaw", inputs[cameraIndex].latestTargetObservation.tx)
                Logger.recordOutput("Vision/Camera$cameraIndex/RobotPosesAccepted", *acceptedRobotPose.toTypedArray())
                Logger.recordOutput("Vision/Camera$cameraIndex/RobotPosesRejected", *rejectedRobotPose.toTypedArray())

                allAcceptedRobotPose.addAll(acceptedRobotPose)
                allRejectedRobotPoses.addAll(rejectedRobotPose)
            }

            Logger.recordOutput("Vision/Summary/RobotPosesAccepted", *allAcceptedRobotPose.toTypedArray())
            Logger.recordOutput("Vision/Summary/RobotPosesRejected", *allRejectedRobotPoses.toTypedArray())
        }
    }
}
