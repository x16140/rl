package io.arct.rl.navigation

import io.arct.rl.units.*
import kotlin.math.round

fun path(fn: (distance: Distance) -> Velocity): Path =
    Path(fn)

class Path internal constructor(
    private val function: (distance: Distance) -> Velocity
) {
    private val cache: MutableMap<Distance, List<Velocity>> = mutableMapOf()

    fun cache(step: Distance): List<Velocity> {
        if (cache.containsKey(step))
            return cache[step] ?: listOf()

        val list = mutableListOf<Velocity>()
        var inc = 0

        do {
            val v = this(step * inc++)

            list.add(v)
        } while (v > 0.mps)

        cache[step] = list
        return list
    }

    fun cached(step: Distance): Path {
        val cache = cache(step)

        return path {
            val n = it.to(step.unit).value
            val i = round(n / step.value).toInt()

            if (cache.size > i)
                cache[i]
            else
                0.0.mps
        }
    }

    operator fun rangeTo(step: Distance): List<Velocity> =
        cache(step)

    operator fun invoke(distance: Distance): Velocity =
        function(distance)

    companion object {
        fun constant(distance: Distance, speed: Velocity) = path {
            if (it < distance) speed else 0.mps
        }

//        fun smooth(
//            distance: Distance,
//            maxPower: Double,
//            accel: Acceleration,
//        ) = path {
//
//        }
    }
}