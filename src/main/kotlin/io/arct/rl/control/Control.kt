package io.arct.rl.control

import io.arct.rl.robot.drive.Drive
import io.arct.rl.hardware.input.Controller

abstract class Control<T: Drive>(protected val drive: T) {
    abstract fun apply(controller: Controller)
}