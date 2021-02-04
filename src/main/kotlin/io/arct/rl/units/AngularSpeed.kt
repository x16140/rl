package io.arct.rl.units

import kotlin.math.PI

val degps = AngularSpeed.Unit.DegreePerSecond
val radps = AngularSpeed.Unit.RadianPerSecond
val revps = AngularSpeed.Unit.RevolutionPerSecond
val degpm = AngularSpeed.Unit.DegreePerMinute
val radpm = AngularSpeed.Unit.RadianPerMinute
val revpm = AngularSpeed.Unit.RevolutionPerMinute

val Number.degps get() = AngularSpeed(this.toDouble(), AngularSpeed.Unit.DegreePerSecond)
val Number.radps get() = AngularSpeed(this.toDouble(), AngularSpeed.Unit.RadianPerSecond)
val Number.revps get() = AngularSpeed(this.toDouble(), AngularSpeed.Unit.RevolutionPerSecond)
val Number.degpm get() = AngularSpeed(this.toDouble(), AngularSpeed.Unit.DegreePerMinute)
val Number.radpm get() = AngularSpeed(this.toDouble(), AngularSpeed.Unit.RadianPerMinute)
val Number.revpm get() = AngularSpeed(this.toDouble(), AngularSpeed.Unit.RevolutionPerMinute)

data class AngularSpeed(override val value: Double, override val unit: Unit) : Quantity<AngularSpeed, AngularSpeed.Unit>() {
    override fun new(value: Double, unit: Unit) = AngularSpeed(value, unit)

    val degps get() = this to Unit.DegreePerSecond
    val radps get() = this to Unit.RadianPerSecond
    val revps get() = this to Unit.RevolutionPerSecond
    val degpm get() = this to Unit.DegreePerMinute
    val radpm get() = this to Unit.RadianPerMinute
    val revpm get() = this to Unit.RevolutionPerMinute

    val baseUnit = when (unit) {
        Unit.DegreePerSecond, Unit.DegreePerMinute -> deg
        Unit.RadianPerSecond, Unit.RadianPerMinute -> rad
        Unit.RevolutionPerSecond, Unit.RevolutionPerMinute -> rev
    }

    override fun toString() = super.toString()

    enum class Unit(override val value: Double, override val display: String) : Quantity.Unit {
        DegreePerSecond    (1.0,      "°/s"),
        RadianPerSecond    (180 / PI, "rad/s"),
        RevolutionPerSecond(360.0,    "rev/s"),
        DegreePerMinute    (1 / 60.0, "°/m"),
        RadianPerMinute    (3.0 / PI, "rad/m"),
        RevolutionPerMinute(6.0,      "rpm")
    }
}