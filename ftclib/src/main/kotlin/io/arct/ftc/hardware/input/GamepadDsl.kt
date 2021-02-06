package io.arct.ftc.hardware.input

import io.arct.rl.hardware.input.Button
import io.arct.rl.hardware.input.Controller
import io.arct.rl.hardware.input.DPad
import io.arct.rl.hardware.input.Joystick

class GamepadDsl internal constructor() {
    internal val click: MutableList<Triple<(Controller) -> Button, Boolean, () -> Unit>> = mutableListOf()
    internal val active: MutableList<Pair<(Controller) -> Boolean, () -> Unit>> = mutableListOf()

    fun click(condition: (Controller) -> Button, rising: Boolean = true, action: () -> Unit) =
        click.add(Triple(condition, rising, action))

    @JvmName("clickJoystick")
    fun click(joystick: (Controller) -> Joystick, rising: Boolean = true, action: () -> Unit) =
        click(fun(gamepad: Controller): Button = joystick(gamepad), rising, action)

    @JvmName("clickDPad")
    fun click(button: (DPad) -> Button, rising: Boolean = true, action: () -> Unit) =
        click(fun(gamepad: Controller): Button = button(gamepad.dpad), rising, action)

    fun active(condition: (Controller) -> Boolean, action: () -> Unit) =
        active.add(condition to action)

    fun active(trigger: (Controller) -> Double, threshold: Double = 0.5, action: () -> Unit) =
        active(fun(gamepad: Controller): Boolean = if (threshold >= 0) trigger(gamepad) > threshold else trigger(gamepad) < -threshold, action)

    fun active(action: () -> Unit) =
        active({ true }, action)
}