package io.arct.rl.hardware.devices

import io.arct.rl.hardware.Device

interface DigitalDevice : Device {
    var value: Boolean
    var mode: Mode

    enum class Mode {
        Input,
        Output,
        Both
    }

    override fun close(): DigitalDevice
    override fun reset(): DigitalDevice

    companion object
}