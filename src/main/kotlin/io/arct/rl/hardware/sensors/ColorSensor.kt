package io.arct.rl.hardware.sensors

import io.arct.rl.hardware.Device

interface ColorSensor : Device {
    val alpha: Int
    val argb: Int
    val red: Int
    val green: Int
    val blue: Int

    var led: Boolean

    override fun close(): ColorSensor
    override fun reset(): ColorSensor

    companion object
}