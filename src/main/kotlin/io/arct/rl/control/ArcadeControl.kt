package io.arct.rl.control

import io.arct.rl.hardware.input.Controller
import io.arct.rl.hardware.input.Joystick
import io.arct.rl.robot.drive.IDrive

class ArcadeControl(
    drive: IDrive,
    val joystick: Controller.() -> Joystick,
    val maxSpeed: Double = 1.0,
    val invertY: Boolean = false,
    val invertX: Boolean = false
) : Control(drive) {
    override fun apply(controller: Controller) {
        val joystick = this.joystick(controller)
        val velocity = drive.velocity * maxSpeed

        drive.turn(
            velocity * joystick.y * if (invertY) 1.0 else -1.0,
            velocity * joystick.x * if (invertX) -1.0 else 1.0
        )
    }
}