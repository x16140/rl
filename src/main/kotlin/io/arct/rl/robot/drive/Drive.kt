package io.arct.rl.robot.drive

import io.arct.rl.navigation.DirectedPath
import io.arct.rl.robot.Robot
import io.arct.rl.units.*

abstract class Drive : IDrive {
    lateinit var robot: Robot
        private set

    override fun stop(): IDrive =
        move(0.deg, 0.0.mps)

    internal fun init(robot: Robot) {
        this.robot = robot
    }
}

interface IDrive {
    val velocity: Velocity

    fun move(direction: Angle, speed: Velocity): IDrive
    fun turn(speed: Velocity, rotationSpeed: Velocity): IDrive
    fun rotate(speed: Velocity): IDrive

    suspend fun move(direction: Angle, distance: Distance, speed: Velocity): IDrive
    suspend fun move(path: DirectedPath): IDrive
    suspend fun rotate(angle: Angle, speed: Velocity): IDrive

    fun stop(): IDrive
}