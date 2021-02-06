package io.arct.rl.hardware.input

import io.arct.rl.hardware.Device

interface Controller : Device {
    val left: Joystick
    val right: Joystick

    val dpad: DPad

    val a: Button
    val b: Button
    val x: Button
    val y: Button

    val lb: Button
    val rb: Button

    val lt: Double
    val rt: Double

    val back: Button
    val guide: Button
    val start: Button

    override fun close(): Controller
    override fun reset(): Controller

    companion object
}