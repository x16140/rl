package io.arct.rl.hardware.input

interface Joystick : Button {
    val origin: Boolean
    val x: Double
    val y: Double
}