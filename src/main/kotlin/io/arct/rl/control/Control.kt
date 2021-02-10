package io.arct.rl.control

import io.arct.rl.robot.drive.Drive
import io.arct.rl.hardware.input.Controller
import io.arct.rl.robot.drive.IDrive

abstract class Control(protected val drive: IDrive) {
    abstract fun apply(controller: Controller)
}