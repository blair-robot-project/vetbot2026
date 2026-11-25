package first.robot.mechanism.vision

import first.robot.Constants
import org.photonvision.simulation.PhotonCameraSim
import org.photonvision.simulation.SimCameraProperties
import org.photonvision.simulation.VisionSystemSim
import org.wpilib.math.geometry.Pose2d
import org.wpilib.math.geometry.Pose3d
import org.wpilib.math.geometry.Rotation2d
import java.util.function.Supplier

class VisionIOPhotonSim(
    name: String,
    cameraPosition: Pose3d,
    private val poseSupplier: Supplier<Pose2d>,
) : VisionIOPhoton(
        name,
        cameraPosition.minus(Pose3d.kZero),
    ) {
    private val cameraSim: PhotonCameraSim
    private val visionSim: VisionSystemSim = VisionSystemSim("main")

    init {
        visionSim.addAprilTags(Constants.VisionConstants.BUNNYBOTS_FIELD_LAYOUT)

        val cameraProperties = SimCameraProperties()
        cameraProperties.setCalibration(1600, 1200, Rotation2d(84.28))
        cameraProperties.setCalibError(0.0, 0.0)
        cameraProperties.setFPS(50.0)
        cameraProperties.setAvgLatencyMs(15.0)
        cameraProperties.setLatencyStdDevMs(5.0)

        cameraSim = PhotonCameraSim(photonCamera, cameraProperties, Constants.VisionConstants.BUNNYBOTS_FIELD_LAYOUT)
        visionSim.addCamera(cameraSim, cameraPosition.minus(Pose3d.kZero))
    }

    override fun updateInputs(inputs: VisionIO.VisionIOInputs) {
        visionSim.update(poseSupplier.get())
        super.updateInputs(inputs)
    }
}
