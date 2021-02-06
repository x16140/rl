package io.arct.ftc.hardware.input

import io.arct.rl.hardware.input.Joystick

abstract class GamepadJoystick internal constructor(private val __sdk: () -> com.qualcomm.robotcore.hardware.Gamepad) : Joystick {
    override val origin: Boolean
        get() = x == .0 && y == .0

    override var rising: Boolean = false
    override var falling: Boolean = false
}

class GamepadJoystickLeft internal constructor(private val __sdk: () -> com.qualcomm.robotcore.hardware.Gamepad) : GamepadJoystick(__sdk) {
    override val x: Double
        get() = __sdk().left_stick_x.toDouble()

    override val y: Double
        get() = __sdk().left_stick_y.toDouble()

    override val pressed: Boolean
        get() = __sdk().left_stick_button
}

class GamepadJoystickRight internal constructor(private val __sdk: () -> com.qualcomm.robotcore.hardware.Gamepad) : GamepadJoystick(__sdk) {
    override val x: Double
        get() = __sdk().right_stick_x.toDouble()

    override val y: Double
        get() = __sdk().right_stick_y.toDouble()

    override val pressed: Boolean
        get() = __sdk().right_stick_button
}