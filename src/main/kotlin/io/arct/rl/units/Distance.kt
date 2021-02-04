package io.arct.rl.units

val cm   = Distance.Unit.Centimeter
val m    = Distance.Unit.Meter
val km   = Distance.Unit.Kilometer
val inch = Distance.Unit.Inch
val ft   = Distance.Unit.Foot
val yd   = Distance.Unit.Yard
val mi   = Distance.Unit.Mile

val Number.cm   get() = Distance(this.toDouble(), Distance.Unit.Centimeter)
val Number.m    get() = Distance(this.toDouble(), Distance.Unit.Meter)
val Number.km   get() = Distance(this.toDouble(), Distance.Unit.Kilometer)
val Number.inch get() = Distance(this.toDouble(), Distance.Unit.Inch)
val Number.ft   get() = Distance(this.toDouble(), Distance.Unit.Foot)
val Number.yd   get() = Distance(this.toDouble(), Distance.Unit.Yard)
val Number.mi   get() = Distance(this.toDouble(), Distance.Unit.Mile)

data class Distance(override val value: Double, override val unit: Unit) : Quantity<Distance, Distance.Unit>() {
    override fun new(value: Double, unit: Unit) = Distance(value, unit)

    val cm   get() = this to Unit.Centimeter
    val m    get() = this to Unit.Meter
    val km   get() = this to Unit.Kilometer
    val inch get() = this to Unit.Inch
    val ft   get() = this to Unit.Foot
    val yd   get() = this to Unit.Yard
    val mi   get() = this to Unit.Mile

    val derivedUnit = when (unit) {
        Unit.Centimeter -> cmps
        Unit.Meter -> mps
        Unit.Kilometer -> kmps
        Unit.Inch -> inps
        Unit.Foot -> ftps
        Unit.Yard -> ydps
        Unit.Mile -> mps
    }

    override fun toString() = super.toString()

    infix fun per(time: Time) = Velocity(if (time == second) value else value * 60, derivedUnit)

    enum class Unit(override val value: Double, override val display: String) : Quantity.Unit {
        Centimeter(0.01,     "cm"),
        Meter     (1.0,      "m"),
        Kilometer (1000.0,   "km"),
        Inch      (.0254,    "in"),
        Foot      (.3048,    "ft"),
        Yard      (.9144,    "yd"),
        Mile      (1609.344, "mi"),
    }
}