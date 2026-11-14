package first.robot.subsystems.vision

import org.littletonrobotics.junction.AutoLog
import org.wpilib.math.geometry.Pose3d
import org.wpilib.math.geometry.Rotation2d
import org.wpilib.util.struct.Struct
import org.wpilib.util.struct.StructSerializable
import java.nio.ByteBuffer

interface VisionIO {
    @AutoLog
    open class VisionIOInputs {
        @JvmField var connected: Boolean = true

        @JvmField var tagIds: IntArray = IntArray(0)

        @JvmField var latestTargetObservation: TargetObservation = TargetObservation(Rotation2d.kZero, Rotation2d.kZero)

        @JvmField var estimatedPose: Array<PoseObservation> = emptyArray()
    }

    fun updateInputs(inputs: VisionIOInputs) {}
}

data class TargetObservation(
    val tx: Rotation2d,
    val ty: Rotation2d,
) : StructSerializable {
    companion object {
        @JvmField
        val struct =
            object : Struct<TargetObservation> {
                override fun getTypeClass(): Class<TargetObservation> = TargetObservation::class.java

                override fun getTypeName(): String = "TargetObservation"

                override fun getSize(): Int = Rotation2d.struct.size * 2

                override fun getSchema(): String = "Rotation2d tx; Rotation2d ty"

                override fun pack(
                    byte: ByteBuffer,
                    target: TargetObservation,
                ) {
                    Rotation2d.struct.pack(byte, target.tx)
                    Rotation2d.struct.pack(byte, target.ty)
                }

                override fun unpack(byte: ByteBuffer): TargetObservation {
                    val newTx = Rotation2d.struct.unpack(byte)
                    val newTy = Rotation2d.struct.unpack(byte)
                    return TargetObservation(
                        newTx,
                        newTy,
                    )
                }
            }
    }
}

data class PoseObservation(
    val estimatedPose: Pose3d,
    val timeStamp: Double,
    val ambiguity: Double,
    val targetCount: Int,
    val averageTagDistance: Double,
    val poseObservation: PoseObservationType,
) : StructSerializable {
    companion object {
        @JvmField
        val struct =
            object : Struct<PoseObservation> {
                override fun getTypeClass(): Class<PoseObservation> = PoseObservation::class.java

                override fun getTypeName(): String = "PoseObservation"

                override fun getSize(): Int = Double.SIZE_BITS + Pose3d.struct.size

                override fun getSchema(): String =
                    "Pose3d estimatedPose;double timeStamp; double ambiguity;int targetCount;double averageTagDistance;PoseObservationType poseObservationType"

                override fun pack(
                    byte: ByteBuffer,
                    pose: PoseObservation,
                ) {
                    Pose3d.struct.pack(byte, pose.estimatedPose)
                    byte.putDouble(pose.timeStamp)
                    byte.putDouble(pose.ambiguity)
                    byte.putInt(pose.targetCount)
                    byte.putDouble(pose.averageTagDistance)
                    byte.putInt(pose.poseObservation.ordinal)
                }

                override fun unpack(byte: ByteBuffer): PoseObservation {
                    val newEstimatedPose = Pose3d.struct.unpack(byte)
                    val newTimeStamp = byte.double
                    val newAmbiguity = byte.double
                    val newTargetCount = byte.int
                    val newAverageTagDistance = byte.double
                    val newPoseObservation = PoseObservationType.entries[byte.int]
                    return PoseObservation(
                        newEstimatedPose,
                        newTimeStamp,
                        newAmbiguity,
                        newTargetCount,
                        newAverageTagDistance,
                        newPoseObservation,
                    )
                }
            }
    }
}

enum class PoseObservationType {
    MEGATAG_1,
    MEGATAG_2,
    PhotonVision,
}
