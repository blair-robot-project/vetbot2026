package first.robot.auto
import com.ctre.phoenix6.swerve.SwerveRequest
import org.wpilib.math.controller.PIDController
import bline.commands.FollowPath
import bline.path.Path
import org.littletonrobotics.junction.Logger
import org.wpilib.math.kinematics.ChassisVelocities


class BLineRoutines (
    // pass in drive and robot actions
) {
    private val translationController = PIDController(0.0,0.0,0.0)
    private val rotationController = PIDController(0.0,0.0,0.0)
    private val crossTrackController = PIDController(0.0,0.0,0.0)

    private val applySpeedsRequest = SwerveRequest.ApplyRobotVelocity()

    private val baseBuilder =
        FollowPath.Builder(
            //pass in drive subsystem
            //pass in pose supplier
            //pass in relative speeds supplier
            {speeds: ChassisVelocities ->
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

    val path: Path = Path("new path")

}