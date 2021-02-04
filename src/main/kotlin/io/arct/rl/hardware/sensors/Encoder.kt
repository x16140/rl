package io.arct.rl.hardware.sensors

import io.arct.rl.hardware.Device
import io.arct.rl.units.Angle
import io.arct.rl.units.Distance

interface Encoder : Device {
    var inverted: Boolean

    val angleRaw: Double
    val angle: Angle

    fun asDistanceEncoder(diameter: Distance): DistanceEncoder = object : DistanceEncoder, Encoder by this {
        override val diameter: Distance = diameter

        override fun zero(): DistanceEncoder {
            this@Encoder.zero()
            return this
        }

        override fun close(): DistanceEncoder {
            this@Encoder.close()
            return this
        }

        override fun reset(): DistanceEncoder {
            this@Encoder.reset()
            return this
        }
    }

    fun zero(): Encoder

    override fun close(): Encoder
    override fun reset(): Encoder

    fun invert(): Encoder {
        inverted = !inverted
        return this
    }

    companion object
}