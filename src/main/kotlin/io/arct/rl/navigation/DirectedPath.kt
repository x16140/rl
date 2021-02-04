package io.arct.rl.navigation

import io.arct.rl.units.Angle
import io.arct.rl.units.Distance
import io.arct.rl.units.Velocity

fun path(direction: Angle, fn: (distance: Distance) -> Velocity): DirectedPath =
    DirectedPath(direction, Path(fn))

data class DirectedPath(val direction: Angle, val path: Path)