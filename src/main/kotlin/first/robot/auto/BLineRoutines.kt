package first.robot.auto
import edu.wpi.first.math.controller.PIDController

class BLineRoutines (
    // pass in drive and robot actions
) {
    private val translationController = PIDController(0.0,0.0,0.0)
    private val rotationController = PIDController(0.0,0.0,0.0)
    private val crossTrackController = PIDController(0.0,0.0,0.0)

    private val applySpeedsRequest = SwerveRequest.ApplyRobotSpeeds()

    private val baseBuilder =
        FollowPath.Builder(
            //pass in drive subsystem
            //pass in pose supplier
            //pass in relative speeds supplier
            {speeds: ChassisSpeeds ->
                drive.setControl(applySpeedsRequest.withSpeeds(speeds))
            },
            translationController,
            rotationController,
            crossTrackController
        ).withDefaultShouldFlip()

    fun logFollowPath() {
        FollowPath.setPoseLoggingConsumer {pair ->
            Logger.recordOutput(pair.first, pair.second)
        }

        FollowPath.setTranslationListLoggingConsumer { pair ->
            Logger.recordOutput(pair.first, *pair.second)
        }

        FollowPath.setDoubleLoggingConsumer {
            pair ->
            Logger.recordOutput(pair.first, pair.second)
        }
        FollowPath.setBooleanLoggingConsumer {
            pair -> Logger.recordOutput(pair.first, pair.second)
        }

    }

    init {
        logFollowPath()
    }

}