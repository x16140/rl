package io.arct.ftc.hardware

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode
import com.qualcomm.robotcore.eventloop.opmode.OpMode
import com.qualcomm.robotcore.hardware.HardwareDevice
import io.arct.ftc.eventloop.OperationMode
import io.arct.rl.hardware.Device

open class FDevice internal constructor(private val __sdk: HardwareDevice, private val __op: OperationMode): Device {
    override val name: String = __sdk.deviceName
    override val version: Int = __sdk.version

    val active: Boolean get() = if (__op is LinearOpMode) __op.opModeIsActive() else true

    val connectionInfo: String = __sdk.connectionInfo
    val manufacturer: Manufacturer = Manufacturer.__sdk(__sdk.manufacturer)

    override fun close(): Device {
        __sdk.close()
        return this
    }

    override fun reset(): Device {
        __sdk.resetDeviceConfigurationForOpMode()
        return this
    }

    enum class Manufacturer {
        Adafruit,
        AMS,
        Broadcom,
        HiTechnic,
        Lego,
        Lynx,
        Matrix,
        ModernRobotics,
        STMicroelectronics,
        Other,
        Unknown;

        companion object {
            internal fun __sdk(__sdk: HardwareDevice.Manufacturer): Manufacturer = when (__sdk) {
                HardwareDevice.Manufacturer.Adafruit -> Adafruit
                HardwareDevice.Manufacturer.AMS -> AMS
                HardwareDevice.Manufacturer.Broadcom -> Broadcom
                HardwareDevice.Manufacturer.HiTechnic -> HiTechnic
                HardwareDevice.Manufacturer.Lego -> Lego
                HardwareDevice.Manufacturer.Lynx -> Lynx
                HardwareDevice.Manufacturer.Matrix -> Matrix
                HardwareDevice.Manufacturer.ModernRobotics -> ModernRobotics
                HardwareDevice.Manufacturer.STMicroelectronics -> STMicroelectronics
                HardwareDevice.Manufacturer.Other -> Other
                HardwareDevice.Manufacturer.Unknown -> Unknown
            }
        }
    }
}