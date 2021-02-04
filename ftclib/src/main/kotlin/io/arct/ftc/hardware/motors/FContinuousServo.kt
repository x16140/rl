package io.arct.ftc.hardware.motors

import com.qualcomm.robotcore.hardware.CRServo
import io.arct.ftc.eventloop.OperationMode
import io.arct.rl.hardware.motors.ContinuousServo

class FContinuousServo internal constructor(
    __sdk: CRServo,
    __op: OperationMode
) : FBasicMotor(__sdk, __op), ContinuousServo {
    override fun close(): ContinuousServo {
        super.close()
        return this
    }

    override fun reset(): ContinuousServo {
        super.reset()
        return this
    }

    override fun power(speed: Double): ContinuousServo {
        super<FBasicMotor>.power(speed)
        return this
    }
}