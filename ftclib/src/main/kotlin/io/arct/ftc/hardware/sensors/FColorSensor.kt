package io.arct.ftc.hardware.sensors

import com.qualcomm.robotcore.eventloop.opmode.OpMode
import io.arct.ftc.eventloop.OperationMode
import io.arct.ftc.hardware.FDevice
import io.arct.rl.hardware.sensors.ColorSensor

class FColorSensor internal constructor(private val __sdk: com.qualcomm.robotcore.hardware.ColorSensor, private val __op: OperationMode) : FDevice(__sdk, __op), ColorSensor {
    init {
        __sdk.enableLed(true)
    }

    override val alpha: Int
        get() = __sdk.alpha()

    override val argb: Int
        get() = __sdk.argb()

    override val red: Int
        get() = __sdk.red()

    override val green: Int
        get() = __sdk.green()

    override val blue: Int
        get() = __sdk.blue()

    override var led: Boolean = true
        set(v) {
            __sdk.enableLed(v)
            field = v
        }

    override fun close(): FColorSensor {
        super.close()
        return this
    }

    override fun reset(): FColorSensor {
        super.reset()
        return this
    }
}