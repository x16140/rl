package io.arct.rl.hardware.sensors

import io.arct.rl.hardware.Device

interface TouchSensor : Device {
    val value: Double
    val pressed: Boolean

    override fun close(): TouchSensor
    override fun reset(): TouchSensor

    companion object
}