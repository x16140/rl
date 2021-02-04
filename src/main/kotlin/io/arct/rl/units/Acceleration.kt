package io.arct.rl.units

val cmps2 = Acceleration.Unit.CentimeterPerSecond2
val mps2  = Acceleration.Unit.MeterPerSecond2
val kmps2 = Acceleration.Unit.KilometerPerSecond2
val inps2 = Acceleration.Unit.InchPerSecond2
val ftps2 = Acceleration.Unit.FootPerSecond2
val ydps2 = Acceleration.Unit.YardPerSecond2
val mips2 = Acceleration.Unit.MilePerSecond2

val Number.cmps2   get() = Acceleration(this.toDouble(), Acceleration.Unit.CentimeterPerSecond2)
val Number.mps2    get() = Acceleration(this.toDouble(), Acceleration.Unit.MeterPerSecond2)
val Number.kmps2   get() = Acceleration(this.toDouble(), Acceleration.Unit.KilometerPerSecond2)
val Number.inchps2 get() = Acceleration(this.toDouble(), Acceleration.Unit.InchPerSecond2)
val Number.ftps2   get() = Acceleration(this.toDouble(), Acceleration.Unit.FootPerSecond2)
val Number.ydps2   get() = Acceleration(this.toDouble(), Acceleration.Unit.YardPerSecond2)
val Number.mips2   get() = Acceleration(this.toDouble(), Acceleration.Unit.MilePerSecond2)

data class Acceleration(override val value: Double, override val unit: Unit) : Quantity<Acceleration, Acceleration.Unit>() {
    override fun new(value: Double, unit: Unit) = Acceleration(value, unit)

    val cmps2 get() = this to Unit.CentimeterPerSecond2
    val mps2  get() = this to Unit.MeterPerSecond2
    val kmps2 get() = this to Unit.KilometerPerSecond2
    val inps2 get() = this to Unit.InchPerSecond2
    val ftps2 get() = this to Unit.FootPerSecond2
    val ydps2 get() = this to Unit.YardPerSecond2
    val mips2 get() = this to Unit.MilePerSecond2

    val baseUnit = when (unit) {
        Unit.CentimeterPerSecond2 -> cmps
        Unit.MeterPerSecond2 -> mps
        Unit.KilometerPerSecond2 -> kmps
        Unit.InchPerSecond2 -> inps
        Unit.FootPerSecond2 -> ftps
        Unit.YardPerSecond2 -> ydps
        Unit.MilePerSecond2 -> mps
    }

    override fun toString() = super.toString()

    enum class Unit(override val value: Double, override val display: String) : Quantity.Unit {
        CentimeterPerSecond2(0.01,     "cm/s²"),
        MeterPerSecond2     (1.0,      "m/s²"),
        KilometerPerSecond2 (1000.0,   "km/s²"),
        InchPerSecond2      (.0254,    "in/s²"),
        FootPerSecond2      (.3048,    "ft/s²"),
        YardPerSecond2      (.9144,    "yd/s²"),
        MilePerSecond2      (1609.344, "mi/s²"),
    }
}