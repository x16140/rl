package io.arct.rl.control

import io.arct.rl.hardware.input.Controller
import io.arct.rl.hardware.input.Joystick
import io.arct.rl.robot.drive.IDrive
import io.arct.rl.robot.drive.MecanumDrive
import io.arct.rl.units.Angle
import kotlin.math.min
import kotlin.math.pow
import kotlin.math.sqrt

class MecanumControl(
    drive: IDrive,
    val joystick: Controller.() -> Joystick,
    val maxSpeed: Double = 1.0,
    val invertY: Boolean = false,
    val invertX: Boolean = false
) : Control(drive) {
    override fun apply(controller: Controller) {
        val joystick = this.joystick(controller)
        val velocity = drive.velocity * maxSpeed

        drive.move(
            Angle.fromCoordinates(
                joystick.x * if (invertX) -1.0 else 1.0,
                -joystick.y * if (invertY) -1.0 else 1.0
            ) ?: return,

            velocity * min(sqrt(joystick.x.pow(2) + joystick.y.pow(2)), 1.0)
        )
    }
}