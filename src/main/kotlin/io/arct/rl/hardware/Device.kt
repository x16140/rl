package io.arct.rl.hardware

interface Device {
    val name: String
    val version: Int

    fun close(): Device
    fun reset(): Device

    companion object
}