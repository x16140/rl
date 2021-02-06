package io.arct.ftc.hardware.input

import io.arct.rl.hardware.input.Button

class GamepadButton internal constructor(private val state: () -> Boolean) : Button {
    override val pressed: Boolean
        get() = state()

    var previous: Boolean = false
}