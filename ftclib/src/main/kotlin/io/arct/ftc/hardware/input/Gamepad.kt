package io.arct.ftc.hardware.input

import io.arct.rl.hardware.input.Controller
import io.arct.rl.hardware.input.DPad
import io.arct.rl.hardware.input.Joystick

class Gamepad internal constructor(private val __sdk: () -> com.qualcomm.robotcore.hardware.Gamepad) : Controller {
    private val gamepad
        get() = __sdk()

    override val left: Joystick = object : Joystick {
        override val origin: Boolean get() = gamepad.left_stick_y == 0f && gamepad.left_stick_x == 0f
        override val x: Double get() = gamepad.left_stick_x.toDouble()
        override val y: Double get() = gamepad.left_stick_y.toDouble()
        override val pressed: Boolean get() = gamepad.left_stick_button
    }

    override val right: Joystick = object : Joystick {
        override val origin: Boolean get() = gamepad.right_stick_y == 0f && gamepad.right_stick_x == 0f
        override val x: Double get() = gamepad.right_stick_x.toDouble()
        override val y: Double get() = gamepad.right_stick_y.toDouble()
        override val pressed: Boolean get() = gamepad.right_stick_button
    }

    override val dpad: DPad = object : DPad {
        override val up: Boolean get() = gamepad.dpad_up
        override val down: Boolean get() = gamepad.dpad_down
        override val left: Boolean get() = gamepad.dpad_left
        override val right: Boolean get() = gamepad.dpad_right
    }

    override val a: Boolean get() = gamepad.a
    override val b: Boolean get() = gamepad.b
    override val x: Boolean get() = gamepad.x
    override val y: Boolean get() = gamepad.y

    override val lb: Boolean get() = gamepad.left_bumper
    override val rb: Boolean get() = gamepad.right_bumper

    override val lt: Double get() = gamepad.left_trigger.toDouble()
    override val rt: Double get() = gamepad.right_trigger.toDouble()

    override val back: Boolean get() = gamepad.back
    override val guide: Boolean get() = gamepad.guide
    override val start: Boolean get() = gamepad.start

    override fun close(): Gamepad =
        this

    override fun reset(): Gamepad =
        this

    override val name: String =
        "Gamepad"

    override val version: Int =
        0
}