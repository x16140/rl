package io.arct.ftc.hardware.motors

import com.qualcomm.robotcore.hardware.DcMotor
import io.arct.ftc.eventloop.LinearOperationMode
import io.arct.ftc.eventloop.OperationMode
import io.arct.rl.hardware.motors.Motor
import io.arct.rl.navigation.Path
import io.arct.rl.units.*
import java.lang.Exception

class FMotor internal constructor(
        internal val __sdk: DcMotor,
        private val __op: OperationMode,

        speed: AngularVelocity? = null,
        diameter: Distance? = null,
        encoderTicksPerDegree: Double = 1.0
) : FBasicMotor(__sdk, __op, speed, diameter), Motor {
    var useEncoder: Boolean
        get() = __sdk.mode == DcMotor.RunMode.RUN_USING_ENCODER
        set(value) {
            if (value != useEncoder)
                __sdk.mode = if (value)
                    DcMotor.RunMode.RUN_USING_ENCODER
                else
                    DcMotor.RunMode.RUN_WITHOUT_ENCODER
        }

    val encoder = FMotorEncoder(this, encoderTicksPerDegree)

    override val busy: Boolean
        get() = __sdk.isBusy

    @Deprecated("Deprecated, use FMotor#encoder instead", ReplaceWith("encoder.angleRaw"))
    override val position: Double
        get() = __sdk.currentPosition.toDouble()

    override var halt: Motor.HaltBehavior
        get() = when (__sdk.zeroPowerBehavior) {
            DcMotor.ZeroPowerBehavior.BRAKE -> Motor.HaltBehavior.Brake
            DcMotor.ZeroPowerBehavior.FLOAT -> Motor.HaltBehavior.Coast
            else -> throw Exception("Unknown Motor HaltBehavior")
        }
        set(v) {
            __sdk.zeroPowerBehavior = when (v) {
                Motor.HaltBehavior.Brake -> DcMotor.ZeroPowerBehavior.BRAKE
                Motor.HaltBehavior.Coast -> DcMotor.ZeroPowerBehavior.FLOAT
            }
        }

    var ratio: Double = FMotor.ratio

    init {
        useEncoder = false
    }

    override suspend fun move(speed: Double, distance: Distance): Motor {
        __sdk.mode = DcMotor.RunMode.RUN_TO_POSITION
        __sdk.targetPosition = (encoder.angleRaw + distance.cm.value * ratio).toInt()

        power(speed)

        while (busy && (__op !is LinearOperationMode || __op.active))
            if (__op is LinearOperationMode)
                __op.sleep(10)

        return stop()
    }

    override suspend fun move(path: Path): Motor {
        useEncoder = true
        val initial = encoder.angleRaw

        do {
            val pos = encoder.angleRaw - initial
            val speed = path((pos / ratio).cm)

            power(speed)

            if (__op is LinearOperationMode)
                __op.sleep(10)
        } while (speed != 0.0.mps && (__op !is LinearOperationMode || __op.active))

        return stop()
    }

    override fun close(): Motor = super.close() as Motor
    override fun reset(): Motor = super.reset() as Motor
    override fun stop(): Motor = super<FBasicMotor>.stop() as Motor
    override fun power(speed: Double): Motor = super<FBasicMotor>.power(speed) as Motor
    override fun power(speed: Velocity): Motor = super<FBasicMotor>.power(speed) as Motor
    override fun power(speed: AngularVelocity): Motor = super<FBasicMotor>.power(speed) as Motor

    companion object {
        var ratio: Double = 4.0
    }
}