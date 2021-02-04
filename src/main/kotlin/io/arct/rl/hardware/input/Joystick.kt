package io.arct.rl.hardware.input

interface Joystick {
    val origin: Boolean
    val x: Double
    val y: Double
    val pressed: Boolean
}