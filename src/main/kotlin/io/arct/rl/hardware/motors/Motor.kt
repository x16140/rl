package io.arct.rl.hardware.motors

import io.arct.rl.navigation.Path
import io.arct.rl.units.*

interface Motor : BasicMotor {
    val busy: Boolean
    val position: Double
    var halt: HaltBehavior

    suspend fun move(speed: Double, distance: Distance): Motor

    suspend fun move(speed: Velocity, distance: Distance) =
        move(speed / velocity, distance)

    suspend fun move(speed: AngularSpeed, theta: Angle) =
        move(speed / this.speed!!, this.diameter!! / 2 * theta.rad.value)

    suspend fun move(path: Path): Motor

    enum class HaltBehavior {
        Coast,
        Brake
    }

    override fun close(): Motor
    override fun reset(): Motor
    override fun stop(): Motor = super.stop() as Motor
    override fun power(speed: Double): Motor
    override fun power(speed: Velocity): Motor = super.power(speed) as Motor
    override fun power(speed: AngularSpeed): Motor = super.power(speed) as Motor
    override fun invert(): Motor = super.invert() as Motor

    companion object
}