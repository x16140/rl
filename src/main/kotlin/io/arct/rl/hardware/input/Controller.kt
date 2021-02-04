package io.arct.rl.hardware.input

import io.arct.rl.hardware.Device

interface Controller : Device {
    val left: Joystick
    val right: Joystick

    val dpad: DPad

    val a: Boolean
    val b: Boolean
    val x: Boolean
    val y: Boolean

    val lb: Boolean
    val rb: Boolean

    val lt: Double
    val rt: Double

    val back: Boolean
    val guide: Boolean
    val start: Boolean

    override fun close(): Controller
    override fun reset(): Controller

    companion object
}