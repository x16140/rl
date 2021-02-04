package io.arct.rl.units

import kotlin.math.pow
import kotlin.math.sqrt

data class Coordinates(val x: Distance, val y: Distance) {
    infix fun distance(other: Coordinates): Distance = sqrt(
        (other.x - x).cm.value.pow(2.0) +
        (other.y - y).cm.value.pow(2.0)
    ).cm to x.unit

    override fun toString() = "($x, $y)"
}