package io.arct.rl.hardware.sensors

import io.arct.rl.hardware.Device

interface GyroSensor : Device {
    val calibrating: Boolean

    val x: Int
    val y: Int
    val z: Int

    fun calibrate(): GyroSensor

    override fun close(): GyroSensor
    override fun reset(): GyroSensor

    companion object
}