package io.arct.rl.hardware.motors

import io.arct.rl.units.AngularSpeed
import io.arct.rl.units.Velocity

interface ContinuousServo : BasicMotor {
    override fun close(): ContinuousServo
    override fun reset(): ContinuousServo
    override fun stop(): ContinuousServo = super.stop() as ContinuousServo
    override fun power(speed: Double): ContinuousServo
    override fun power(speed: Velocity): ContinuousServo = super.power(speed) as ContinuousServo
    override fun power(speed: AngularSpeed): ContinuousServo = super.power(speed) as ContinuousServo
    override fun invert(): ContinuousServo = super.invert() as ContinuousServo

    companion object
}