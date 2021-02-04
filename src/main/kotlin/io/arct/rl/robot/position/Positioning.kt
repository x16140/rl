package io.arct.rl.robot.position

import io.arct.rl.robot.Robot
import io.arct.rl.units.Angle
import io.arct.rl.units.Coordinates

abstract class Positioning : IPositioning {
    lateinit var robot: Robot
        private set

    internal fun init(robot: Robot) {
        this.robot = robot
    }
}

interface IPositioning {
    val position: Coordinates
    val rotation: Angle

    fun zero(): IPositioning
}