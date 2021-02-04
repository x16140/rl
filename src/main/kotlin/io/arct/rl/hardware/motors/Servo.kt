package io.arct.rl.hardware.motors

import io.arct.rl.hardware.Device

interface Servo : Device {
    var position: Double

    fun move(target: Double, time: Long, stepsPerSecond: Int = 500): Servo

    override fun close(): Servo
    override fun reset(): Servo

    companion object
}