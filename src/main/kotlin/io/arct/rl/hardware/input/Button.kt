package io.arct.rl.hardware.input

interface Button {
    val pressed: Boolean

    operator fun unaryPlus() = pressed
    operator fun unaryMinus() = !pressed
    operator fun not() = !pressed
}