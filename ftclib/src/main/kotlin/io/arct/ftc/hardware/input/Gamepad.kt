package io.arct.ftc.hardware.input

import io.arct.rl.hardware.input.Button
import io.arct.rl.hardware.input.Controller
import io.arct.rl.hardware.input.DPad
import io.arct.rl.hardware.input.Joystick

class Gamepad internal constructor(private val __sdk: () -> com.qualcomm.robotcore.hardware.Gamepad) : Controller {
    private val gamepad
        get() = __sdk()

    override val left: Joystick = GamepadJoystickLeft(::gamepad)
    override val right: Joystick = GamepadJoystickRight(::gamepad)

    override val dpad: DPad = object : DPad {
        override val up: Button = GamepadButton(gamepad::dpad_up)
        override val down: Button = GamepadButton(gamepad::dpad_down)
        override val left: Button = GamepadButton(gamepad::dpad_left)
        override val right: Button = GamepadButton(gamepad::dpad_right)
    }

    override val a: Button = GamepadButton(gamepad::a)
    override val b: Button = GamepadButton(gamepad::b)
    override val x: Button = GamepadButton(gamepad::x)
    override val y: Button = GamepadButton(gamepad::y)

    override val lb: Button = GamepadButton(gamepad::left_bumper)
    override val rb: Button = GamepadButton(gamepad::right_bumper)

    override val lt: Double get() = gamepad.left_trigger.toDouble()
    override val rt: Double get() = gamepad.right_trigger.toDouble()

    override val back: Button = GamepadButton(gamepad::back)
    override val guide: Button = GamepadButton(gamepad::guide)
    override val start: Button = GamepadButton(gamepad::start)

    override fun close(): Gamepad =
        this

    override fun reset(): Gamepad =
        this

    override val name: String =
        "Gamepad"

    override val version: Int =
        0

    operator fun invoke(fn: GamepadDsl.() -> Unit) {
        val dsl = GamepadDsl()
        fn(dsl)

        for ((condition, action) in dsl.active)
            if (condition(this))
                action()

        for ((buttonFn, rising, action) in dsl.click) {
            val button = buttonFn(this) as? GamepadButton ?: continue

            if (button.pressed) {
                if (!button.previous && rising)
                    action()

                button.previous = true
            } else {
                if (button.previous && !rising)
                    action()

                button.previous = false
            }
        }
    }
}