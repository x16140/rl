package io.arct.rl.units

import kotlin.math.PI

val degps = AngularVelocity.Unit.DegreePerSecond
val radps = AngularVelocity.Unit.RadianPerSecond
val revps = AngularVelocity.Unit.RevolutionPerSecond
val degpm = AngularVelocity.Unit.DegreePerMinute
val radpm = AngularVelocity.Unit.RadianPerMinute
val revpm = AngularVelocity.Unit.RevolutionPerMinute

val Number.degps get() = AngularVelocity(this.toDouble(), AngularVelocity.Unit.DegreePerSecond)
val Number.radps get() = AngularVelocity(this.toDouble(), AngularVelocity.Unit.RadianPerSecond)
val Number.revps get() = AngularVelocity(this.toDouble(), AngularVelocity.Unit.RevolutionPerSecond)
val Number.degpm get() = AngularVelocity(this.toDouble(), AngularVelocity.Unit.DegreePerMinute)
val Number.radpm get() = AngularVelocity(this.toDouble(), AngularVelocity.Unit.RadianPerMinute)
val Number.revpm get() = AngularVelocity(this.toDouble(), AngularVelocity.Unit.RevolutionPerMinute)

data class AngularVelocity(override val value: Double, override val unit: Unit) : Quantity<AngularVelocity, AngularVelocity.Unit>() {
    override fun new(value: Double, unit: Unit) = AngularVelocity(value, unit)

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