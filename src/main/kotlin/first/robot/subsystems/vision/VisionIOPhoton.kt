package first.robot.subsystems.vision

import org.photonvision.PhotonCamera
import org.wpilib.math.geometry.Transform3d
import org.wpilib.system.Filesystem
import org.wpilib.vision.apriltag.AprilTagFieldLayout

open class VisionIOPhoton(
    name: String,
    offset: Transform3d,
) : VisionIO {
    protected val photonCamera: PhotonCamera = PhotonCamera(name)
    val aprilTagFieldLayout = AprilTagFieldLayout(Filesystem.getDeployDirectory().absolutePath + "/Bunnybots_2026_Centered.json")

    override fun updateInputs(inputs: VisionIO.VisionIOInputs) {
        inputs.connected = photonCamera.isConnected

        val tagIds = mutableSetOf<Int>()
        val poseObservations = mutableSetOf<PoseObservation>()
    }
}
