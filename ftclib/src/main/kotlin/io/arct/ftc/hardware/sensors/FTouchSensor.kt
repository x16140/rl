package io.arct.ftc.hardware.sensors

import com.qualcomm.robotcore.eventloop.opmode.OpMode
import io.arct.ftc.eventloop.OperationMode
import io.arct.ftc.hardware.FDevice
import io.arct.rl.hardware.sensors.TouchSensor

class FTouchSensor internal constructor(private val __sdk: com.qualcomm.robotcore.hardware.TouchSensor, private val __op: OperationMode) : FDevice(__sdk, __op), TouchSensor {
    override val value: Double
        get() = __sdk.value

    override val pressed: Boolean
        get() = __sdk.isPressed

    override fun close(): FTouchSensor {
        super.close()
        return this
    }

    override fun reset(): FTouchSensor {
        super.reset()
        return this
    }
}