package io.arct.rl.units

abstract class Quantity<Self: Quantity<Self, T>, T: Quantity.Unit> {
    abstract val value: Double
    abstract val unit: T

    infix fun to(unit: T): Self =
        new(value * this.unit.value / unit.value, unit)

    operator fun unaryPlus(): Self =
        new(value, unit)

    operator fun unaryMinus(): Self =
        new(-value, unit)

    operator fun plus(rhs: Self): Self =
        new(value + (rhs to unit).value, unit)

    operator fun minus(rhs: Self): Self =
        new(value - (rhs to unit).value, unit)

    operator fun times(rhs: Self): Double =
        value * (rhs to unit).value

    operator fun times(rhs: Number): Self =
        new(value * rhs.toDouble(), unit)

    operator fun div(rhs: Self): Double =
        value / (rhs to unit).value

    operator fun div(rhs: Number): Self =
        new(value / rhs.toDouble(), unit)

    operator fun rem(rhs: Number): Self =
        new(value % rhs.toDouble(), unit)

    operator fun compareTo(rhs: Self): Int =
        (value - (rhs to unit).value).toInt()

    override fun toString(): String =
        "$value${unit.display}"

    protected abstract fun new(value: Double, unit: T): Self

    interface Unit {
        val value: Double
        val display: String
    }
}