package io.arct.rl.hardware

import kotlin.reflect.KClass

abstract class HardwareMap {
    abstract fun <T : Device> get(type: KClass<T>, identifier: String, vararg arguments: Any?): T
}