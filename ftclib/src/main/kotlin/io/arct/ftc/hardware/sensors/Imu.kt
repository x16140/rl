package io.arct.ftc.hardware.sensors

import com.qualcomm.hardware.bosch.BNO055IMU
import com.qualcomm.robotcore.hardware.I2cAddr
import io.arct.ftc.eventloop.OperationMode
import io.arct.ftc.hardware.FDevice
import io.arct.rl.hardware.Device
import org.firstinspires.ftc.robotcore.external.navigation.*

class Imu internal constructor(private val __sdk: BNO055IMU, private val __op: OperationMode) : Device {
    override val name: String = __sdk.systemStatus.name
    override val version: Int = 0

    val manufacturer: FDevice.Manufacturer = FDevice.Manufacturer.Other
    val connectionInfo: String = __sdk.systemStatus.toString()

    val acceleration
        get() = __sdk.acceleration

    val orientation: Orientation
        get() = __sdk.angularOrientation

    val angularVelocity: AngularVelocity?
        get() = __sdk.angularVelocity

    val calibrationStatus: Byte
        get() = __sdk.calibrationStatus.calibrationStatus

    val gravity: Acceleration
        get() = __sdk.gravity

    val acceleratorCalibrated: Boolean
        get() = __sdk.isAccelerometerCalibrated

    val gyroCalibrated: Boolean
        get() = __sdk.isGyroCalibrated

    val magnetometerCalibrated: Boolean
        get() = __sdk.isMagnetometerCalibrated

    val systemCalibrated: Boolean
        get() = __sdk.isSystemCalibrated

    val linearAcceleration: Acceleration
        get() = __sdk.linearAcceleration

    val magneticFlux: MagneticFlux
        get() = __sdk.magneticFieldStrength

    val overallAcceleration: Acceleration
        get() = __sdk.overallAcceleration

    val position: Position
        get() = __sdk.position

    val quaternionOrientation: Quaternion?
        get() = __sdk.quaternionOrientation

    val temperature: Temperature
        get() = __sdk.temperature

    val velocity: Velocity
        get() = __sdk.velocity

    fun init(
        accelerationBandwidth: BNO055IMU.AccelBandwidth? = null,
        accelerationPowerMode: BNO055IMU.AccelPowerMode? = null,
        accelerationRange: BNO055IMU.AccelRange? = null,
        accelerationUnit: BNO055IMU.AccelUnit? = BNO055IMU.AccelUnit.METERS_PERSEC_PERSEC,
        accelerationIntegration: BNO055IMU.AccelerationIntegrator? = null,
        angleUnit: BNO055IMU.AngleUnit? = BNO055IMU.AngleUnit.DEGREES,
        calibrationData: BNO055IMU.CalibrationData? = null,
        calibrationDataFile: String? = null,
        gyroBandwidth: BNO055IMU.GyroBandwidth? = null,
        gyroPowerMode: BNO055IMU.GyroPowerMode? = null,
        gyroRange: BNO055IMU.GyroRange? = null,
        i2cAddress: I2cAddr? = null,
        enableLogging: Boolean? = null,
        loggingTag: String? = null,
        magOpMode: BNO055IMU.MagOpMode? = null,
        magPowerMode: BNO055IMU.MagPowerMode? = null,
        magRate: BNO055IMU.MagRate? = null,
        mode: BNO055IMU.SensorMode? = null,
        pitchMode: BNO055IMU.PitchMode? = null,
        temperatureUnit: BNO055IMU.TempUnit? = null,
        useExternalCrystal: Boolean? = null
    ): Imu {
        val p = BNO055IMU.Parameters()

        accelerationBandwidth?.let { p.accelBandwidth = it }
        accelerationPowerMode?.let { p.accelPowerMode = it }
        accelerationRange?.let { p.accelRange = it }
        accelerationUnit?.let { p.accelUnit = it }
        accelerationIntegration?.let { p.accelerationIntegrationAlgorithm = it }
        angleUnit?.let { p.angleUnit = it }
        calibrationData?.let { p.calibrationData = it }
        calibrationDataFile?.let { p.calibrationDataFile = it }
        gyroBandwidth?.let { p.gyroBandwidth = it }
        gyroPowerMode?.let { p.gyroPowerMode = it }
        gyroRange?.let { p.gyroRange = it }
        i2cAddress?.let { p.i2cAddr = it }
        enableLogging?.let { p.loggingEnabled = it }
        loggingTag?.let { p.loggingTag = it }
        magOpMode?.let { p.magOpMode = it }
        magPowerMode?.let { p.magPowerMode = it }
        magRate?.let { p.magRate = it }
        mode?.let { p.mode = it }
        pitchMode?.let { p.pitchMode = it }
        temperatureUnit?.let { p.temperatureUnit = it }
        useExternalCrystal?.let { p.useExternalCrystal = it }

        __sdk.initialize(p)

        return this
    }

    override fun close(): Imu {
        __sdk.close()
        return this
    }

    override fun reset(): Imu =
        init()

    companion object
}