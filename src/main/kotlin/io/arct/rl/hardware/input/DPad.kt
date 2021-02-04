package io.arct.rl.hardware.input

interface DPad {
    val up: Boolean
    val down: Boolean
    val left: Boolean
    val right: Boolean
}