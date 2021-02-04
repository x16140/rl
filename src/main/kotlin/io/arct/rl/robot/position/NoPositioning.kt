package io.arct.rl.robot.position

import io.arct.rl.units.Angle
import io.arct.rl.units.Coordinates
import io.arct.rl.units.cm
import io.arct.rl.units.rad

class NoPositioning(val errorOnAccess: Boolean = false) : Positioning() {
    override val position: Coordinates get() = if (errorOnAccess)
        throw Exception("Cannot access property position of NoPosition")
    else
        Coordinates(0.cm, 0.cm)

    override val rotation: Angle get() = if (errorOnAccess)
        throw Exception("Cannot access property rotation of NoPosition")
    else
        0.rad

    override fun zero() = if (errorOnAccess)
        throw Exception("Cannot access function zero of NoPosition")
    else
        this
}