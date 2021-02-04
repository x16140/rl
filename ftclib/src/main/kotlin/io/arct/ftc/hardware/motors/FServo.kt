package io.arct.ftc.hardware.motors

import com.qualcomm.robotcore.eventloop.opmode.OpMode
import io.arct.ftc.eventloop.LinearOperationMode
import io.arct.ftc.eventloop.OperationMode
import io.arct.ftc.hardware.FDevice
import io.arct.rl.hardware.motors.Servo

class FServo internal constructor(private val __sdk: com.qualcomm.robotcore.hardware.Servo, private val __op: OperationMode) : FDevice(__sdk, __op), Servo {
    override var position: Double
        get() = __sdk.position
        set(v) {
            __sdk.position = v
        }

    override fun move(target: Double, time: Long, stepsPerSecond: Int): FServo {
        val steps = (stepsPerSecond * target / 1000).toInt()
        val stepDistance = (position - target) / steps
        val delay = 1000L / stepsPerSecond

        val originalPosition = position

        for (i in 0..steps) {
            position = originalPosition + i * stepDistance

            if (__op is LinearOperationMode)
                __op.sleep(delay)
            else
                Thread.sleep(delay)
        }

        return this
    }

    override fun close(): FServo {
        super.close()
        return this
    }

    override fun reset(): FServo {
        super.close()
        return this
    }
}