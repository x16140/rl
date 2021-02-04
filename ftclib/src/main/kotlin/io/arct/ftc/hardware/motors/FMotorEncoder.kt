package io.arct.ftc.hardware.motors

import io.arct.rl.hardware.sensors.Encoder
import io.arct.rl.units.Angle
import io.arct.rl.units.deg

class FMotorEncoder(val motor: FMotor, private val ticksPerDegree: Double = 1.0) : Encoder {
    var offset = 0.0

    override val angleRaw: Double
        get() = (motor.__sdk.currentPosition.toDouble() - offset) * if (inverted) -1 else 1

    override val angle: Angle
        get() = (angleRaw / ticksPerDegree).deg

    override val name: String
        get() = motor.name

    override val version: Int
        get() = motor.version

    override var inverted: Boolean = false

    override fun zero(): Encoder {
        offset = motor.__sdk.currentPosition.toDouble()
        return this
    }

    fun resetOffset(): Encoder {
        offset = 0.0
        return this
    }

    override fun close(): Encoder {
        motor.close()
        return this
    }

    override fun reset(): Encoder {
        motor.reset()
        return this
    }
}