package io.arct.ftc.hardware.devices

import com.qualcomm.robotcore.eventloop.opmode.OpMode
import com.qualcomm.robotcore.hardware.DigitalChannel
import io.arct.ftc.eventloop.OperationMode
import io.arct.ftc.hardware.FDevice
import io.arct.rl.hardware.devices.DigitalDevice
import io.arct.rl.hardware.devices.DigitalDevice.Mode

class FDigitalDevice internal constructor(private val __sdk: DigitalChannel, private val __op: OperationMode): FDevice(__sdk, __op), DigitalDevice {
    private var modeBoth = true

    override var value: Boolean
        get() = when (mode) {
            Mode.Both -> {
                __sdk.mode = DigitalChannel.Mode.OUTPUT
                __sdk.state
            }

            Mode.Output -> __sdk.state

            else -> throw Exception("Cannot Get Value of Digital Device on Input Mode!")
        }

        set(v) = when (mode) {
            Mode.Both -> {
                __sdk.mode = DigitalChannel.Mode.INPUT
                __sdk.state = v
            }

            Mode.Input -> __sdk.state = v

            else -> throw Exception("Cannot Set Value of Digital Device on Output Mode!")
        }

    override var mode: Mode
        get() = if (modeBoth) Mode.Both else when (__sdk.mode) {
            DigitalChannel.Mode.INPUT -> Mode.Input
            DigitalChannel.Mode.OUTPUT -> Mode.Output
            else -> Mode.Both
        }

        set(v) = when (v) {
            Mode.Input -> {
                modeBoth = false
                __sdk.mode = DigitalChannel.Mode.INPUT
            }

            Mode.Output -> {
                modeBoth = false
                __sdk.mode = DigitalChannel.Mode.OUTPUT
            }

            Mode.Both -> modeBoth = true
        }

    override fun close(): FDigitalDevice {
        super.close()
        return this
    }

    override fun reset(): FDigitalDevice {
        super.reset()
        return this
    }
}