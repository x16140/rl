package io.arct.ftc.hardware.sensors

import com.qualcomm.robotcore.eventloop.opmode.OpMode
import io.arct.ftc.eventloop.OperationMode
import io.arct.ftc.hardware.FDevice
import io.arct.rl.hardware.sensors.GyroSensor

class FGyroSensor internal constructor(private val __sdk: com.qualcomm.robotcore.hardware.GyroSensor, private val __op: OperationMode) : FDevice(__sdk, __op), GyroSensor {
    override val calibrating: Boolean
        get() = __sdk.isCalibrating

    override val x: Int
        get() = __sdk.rawX()

    override val y: Int
        get() = __sdk.rawY()

    override val z: Int
        get() = __sdk.rawZ()

    override fun calibrate(): FGyroSensor {
        __sdk.calibrate()
        return this
    }

    override fun close(): FGyroSensor {
        super.close()
        return this
    }

    override fun reset(): FGyroSensor {
        __sdk.resetZAxisIntegrator()
        return this
    }
}