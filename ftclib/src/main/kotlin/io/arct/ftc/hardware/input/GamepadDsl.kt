package io.arct.ftc.hardware.input

import io.arct.rl.hardware.input.Button
import io.arct.rl.hardware.input.DPad
import io.arct.rl.hardware.input.Joystick

class GamepadDsl internal constructor() {
    internal val click: MutableList<Triple<Gamepad.() -> Button, Boolean, () -> Unit>> = mutableListOf()
    internal val active: MutableList<Pair<Gamepad.() -> Boolean, () -> Unit>> = mutableListOf()

    fun click(condition: Gamepad.() -> Button, rising: Boolean = true, action: () -> Unit) =
        click.add(Triple(condition, rising, action))

    @JvmName("clickJoystick")
    fun click(joystick: Gamepad.() -> Joystick, rising: Boolean = true, action: () -> Unit) =
        click(fun(gamepad: Gamepad): Button = joystick(gamepad), rising, action)

    @JvmName("clickDPad")
    fun click(button: (DPad) -> Button, rising: Boolean = true, action: () -> Unit) =
        click(fun(gamepad: Gamepad): Button = button(gamepad.dpad), rising, action)

    fun active(condition: Gamepad.() -> Boolean, action: () -> Unit) =
        active.add(condition to action)

    fun active(trigger: Gamepad.() -> Double, threshold: Double = 0.5, action: () -> Unit) =
        active(fun(gamepad: Gamepad): Boolean = if (threshold >= 0) trigger(gamepad) > 0.5 else trigger(gamepad) < -threshold, action)

    fun active(action: () -> Unit) =
        active({ true }, action)
}