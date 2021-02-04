package io.arct.rl.hardware.motors

import io.arct.rl.hardware.Device
import io.arct.rl.units.AngularSpeed
import io.arct.rl.units.Distance
import io.arct.rl.units.Velocity

interface BasicMotor : Device {
    var direction: Direction

    val speed: AngularSpeed?
    val diameter: Distance?

    val velocity: Velocity get() {
        val r = diameter!! / 2
        val w = speed!!.radps.value

        return Velocity(r.value * w, r.derivedUnit)
    }

    fun power(speed: Double): BasicMotor

    fun power(speed: Velocity): BasicMotor =
        power(speed / velocity)

    fun power(speed: AngularSpeed): BasicMotor =
        power(speed / this.speed!!)

    fun stop(): BasicMotor {
        power(0.0)
        return this
    }

    fun invert(): BasicMotor {
        direction = !direction
        return this
    }

    enum class Direction {
        Forward,
        Reverse;

        val inverse: Direction get() = when (this) {
            Forward -> Reverse
            Reverse -> Forward
        }

        operator fun not() = this.inverse
    }

    override fun close(): BasicMotor
    override fun reset(): BasicMotor

    companion object
}