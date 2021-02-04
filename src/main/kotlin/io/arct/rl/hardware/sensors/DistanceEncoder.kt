package io.arct.rl.hardware.sensors

import io.arct.rl.units.Distance

interface DistanceEncoder : Encoder {
    val diameter: Distance

    val position: Distance get() =
        Distance(diameter.value * angle.rad.value / 2, diameter.unit)

    override fun zero(): DistanceEncoder
    override fun close(): DistanceEncoder
    override fun reset(): DistanceEncoder

    companion object
}