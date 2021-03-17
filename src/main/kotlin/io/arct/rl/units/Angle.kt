package io.arct.rl.units

import kotlin.math.PI
import kotlin.math.atan2

val deg = Angle.Unit.Degree
val rad = Angle.Unit.Radian
val rev = Angle.Unit.Revolution

val Number.deg get() = Angle(this.toDouble(), Angle.Unit.Degree)
val Number.rad get() = Angle(this.toDouble(), Angle.Unit.Radian)
val Number.rev get() = Angle(this.toDouble(), Angle.Unit.Revolution)

data class Angle(override val value: Double, override val unit: Unit) : Quantity<Angle, Angle.Unit>() {
    override fun new(value: Double, unit: Unit) = Angle(value, unit)

    val normal: Double get() {
        val r = deg.value % 360

        return (when {
            r.isNaN() -> r
            r > 180 -> r - 360
            r <= -180 -> r + 360
            else -> r
        }.deg to unit).value
    }

    val general: Double get() {
        val r = Angle(normal, unit).deg.value

        return (when {
            r.isNaN() -> r
            r < 0 -> 360 + r
            else -> r
        }.deg to unit).value
    }

    val deg get() = this to Unit.Degree
    val rad get() = this to Unit.Radian
    val rev get() = this to Unit.Revolution

    fun derivedUnit(time: Time) = when (unit) {
        Unit.Degree -> if (time == second) degps else degpm
        Unit.Radian -> if (time == second) radps else radpm
        Unit.Revolution -> if (time == second) revps else revpm
    }

    override fun toString() = super.toString()

    infix fun per(time: Time) = AngularVelocity(value, derivedUnit(time))

    enum class Unit(override val value: Double, override val display: String) : Quantity.Unit {
        Degree    (1.0,      "°"),
        Radian    (180 / PI, "rad"),
        Revolution(360.0,    "rev")
    }

    companion object {
        val Forward = 0.deg
        val Right = 90.deg
        val Backward = 180.deg
        val Left = (-90).deg

        fun fromCoordinates(x: Double, y: Double): Angle? =
                 if (x > 1 || y > 1)       null
            else if (x != 0.0 || y != 0.0) (atan2(y, -x) * (180 / Math.PI) - 90).deg
            else                           null
    }
}