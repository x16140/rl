package io.arct.rl.units

val cmps = Velocity.Unit.CentimeterPerSecond
val mps  = Velocity.Unit.MeterPerSecond
val kmps = Velocity.Unit.KilometerPerSecond
val inps = Velocity.Unit.InchPerSecond
val ftps = Velocity.Unit.FootPerSecond
val ydps = Velocity.Unit.YardPerSecond
val mips = Velocity.Unit.MilePerSecond

val Number.cmps   get() = Velocity(this.toDouble(), Velocity.Unit.CentimeterPerSecond)
val Number.mps    get() = Velocity(this.toDouble(), Velocity.Unit.MeterPerSecond)
val Number.kmps   get() = Velocity(this.toDouble(), Velocity.Unit.KilometerPerSecond)
val Number.inchps get() = Velocity(this.toDouble(), Velocity.Unit.InchPerSecond)
val Number.ftps   get() = Velocity(this.toDouble(), Velocity.Unit.FootPerSecond)
val Number.ydps   get() = Velocity(this.toDouble(), Velocity.Unit.YardPerSecond)
val Number.mips   get() = Velocity(this.toDouble(), Velocity.Unit.MilePerSecond)

data class Velocity(override val value: Double, override val unit: Unit) : Quantity<Velocity, Velocity.Unit>() {
    override fun new(value: Double, unit: Unit) = Velocity(value, unit)

    val cmps get() = this to Unit.CentimeterPerSecond
    val mps  get() = this to Unit.MeterPerSecond
    val kmps get() = this to Unit.KilometerPerSecond
    val inps get() = this to Unit.InchPerSecond
    val ftps get() = this to Unit.FootPerSecond
    val ydps get() = this to Unit.YardPerSecond
    val mips get() = this to Unit.MilePerSecond

    val baseUnit = when (unit) {
        Unit.CentimeterPerSecond -> cm
        Unit.MeterPerSecond -> m
        Unit.KilometerPerSecond -> km
        Unit.InchPerSecond -> inch
        Unit.FootPerSecond -> ft
        Unit.YardPerSecond -> yd
        Unit.MilePerSecond -> m
    }

    val derivedUnit = when (unit) {
        Unit.CentimeterPerSecond -> cmps2
        Unit.MeterPerSecond -> mps2
        Unit.KilometerPerSecond -> kmps2
        Unit.InchPerSecond -> inps2
        Unit.FootPerSecond -> ftps2
        Unit.YardPerSecond -> ydps2
        Unit.MilePerSecond -> mps2
    }

    override fun toString() = super.toString()

    infix fun per(time: Time) = Acceleration(if (time == second) value else value * 60, derivedUnit)

    enum class Unit(override val value: Double, override val display: String) : Quantity.Unit {
        CentimeterPerSecond(0.01,     "cm/s"),
        MeterPerSecond     (1.0,      "m/s"),
        KilometerPerSecond (1000.0,   "km/s"),
        InchPerSecond      (.0254,    "in/s"),
        FootPerSecond      (.3048,    "ft/s"),
        YardPerSecond      (.9144,    "yd/s"),
        MilePerSecond      (1609.344, "mi/s"),
    }
}