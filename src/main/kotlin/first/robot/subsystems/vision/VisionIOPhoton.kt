package first.robot.subsystems.vision

import first.robot.Constants
import org.photonvision.PhotonCamera
import org.wpilib.math.geometry.Pose3d
import org.wpilib.math.geometry.Rotation2d
import org.wpilib.math.geometry.Transform3d

open class VisionIOPhoton(
    name: String,
    private val offset: Transform3d,
) : VisionIO {
    protected val photonCamera: PhotonCamera = PhotonCamera(name)

    override fun updateInputs(inputs: VisionIO.VisionIOInputs) {
        inputs.connected = photonCamera.isConnected

        val tagIds = mutableSetOf<Int>()

        val poseObservations = mutableSetOf<PoseObservation>()

        for (results in photonCamera.allUnreadResults) {
            if (results.hasTargets()) {
                inputs.latestTargetObservation =
                    TargetObservation(
                        Rotation2d.fromDegrees(results.bestTarget.yaw),
                        Rotation2d.fromDegrees(results.bestTarget.pitch),
                    )
            } else {
                inputs.latestTargetObservation =
                    TargetObservation(
                        Rotation2d.kZero,
                        Rotation2d.kZero,
                    )
            }

            if (results.multiTagResult.isPresent) {
                val multitagResult = results.multitagResult.get()

                val fieldToCamera: Transform3d = multitagResult.estimatedPose.best
                val fieldToRobot = fieldToCamera.plus(offset.inverse())
                val robotPose = Pose3d(fieldToRobot.translation, fieldToRobot.rotation)

                var totalTagDistance = 0.0
                for (target in results.targets) {
                    tagIds.add(target.fiducialId)

                    totalTagDistance += target.bestCameraToTarget.translation.norm
                }
                poseObservations.add(
                    PoseObservation(
                        robotPose,
                        results.timestampSeconds,
                        multitagResult.estimatedPose.ambiguity,
                        multitagResult.fiducialIDsUsed.size,
                        totalTagDistance / results.targets.size,
                        PoseObservationType.PhotonVision,
                    ),
                )
            } else if (results.targets.isNotEmpty()) {
                val target = results.targets[0]

                val tagPose = Constants.VisionConstants.BUNNYBOTS_FIELD_LAYOUT.getTagPose(target.fiducialId)
                if (tagPose.isPresent) {
                    val fieldToTarget = Transform3d(tagPose.get().translation, tagPose.get().rotation)
                    val cameraToTarget: Transform3d = target.bestCameraToTarget
                    val fieldToCamera = fieldToTarget.plus(cameraToTarget.inverse())
                    val fieldToRobot = fieldToCamera.plus(offset.inverse())

                    val robotPose = Pose3d(fieldToRobot.translation, fieldToRobot.rotation)

                    tagIds.add(target.fiducialId)

                    poseObservations.add(
                        PoseObservation(
                            robotPose,
                            results.timestampSeconds,
                            target.poseAmbiguity,
                            1,
                            target.bestCameraToTarget.translation.norm,
                            PoseObservationType.PhotonVision,
                        ),
                    )
                }
            }
        }
        inputs.tagIds = tagIds.toIntArray()
        inputs.poseObservations = poseObservations.toTypedArray()
    }
}
