package io.arct.ftc.hardware.sensors

import com.qualcomm.hardware.bosch.BNO055IMU
import com.qualcomm.robotcore.hardware.I2cAddr
import io.arct.ftc.eventloop.OperationMode
import io.arct.ftc.hardware.FDevice
import io.arct.rl.hardware.Device
import io.arct.rl.hardware.sensors.Imu
import io.arct.rl.units.*
import io.arct.rl.units.AngularVelocity
import org.firstinspires.ftc.robotcore.external.navigation.*
import org.firstinspires.ftc.robotcore.external.navigation.Velocity

private fun d(t: Number, u: DistanceUnit) = when (u) {
    DistanceUnit.CM -> Distance(t.toDouble(), Distance.Unit.Centimeter)
    DistanceUnit.INCH ->Distance(t.toDouble(),  Distance.Unit.Inch)
    DistanceUnit.METER -> Distance(t.toDouble(), Distance.Unit.Meter)
    DistanceUnit.MM -> Distance(t.toDouble() / 10.0, Distance.Unit.Centimeter)
}

private fun unit(u: AngleUnit) = when (u) {
    AngleUnit.DEGREES -> Angle.Unit.Degree
    AngleUnit.RADIANS -> Angle.Unit.Radian
}

class FImu internal constructor(private val __sdk: BNO055IMU, private val __op: OperationMode) : Imu {
    override val name: String = __sdk.systemStatus.name
    override val version: Int = 0

    val manufacturer: FDevice.Manufacturer = FDevice.Manufacturer.Other
    val connectionInfo: String = __sdk.systemStatus.toString()

    override val acceleration
        get() = Space(
                d(__sdk.acceleration.xAccel, __sdk.acceleration.unit) per second per second,
                d(__sdk.acceleration.yAccel, __sdk.acceleration.unit) per second per second,
                d(__sdk.acceleration.zAccel, __sdk.acceleration.unit) per second per second
        )

    override val orientation
        get() = Space(
                Angle(__sdk.angularOrientation.firstAngle.toDouble(), unit(__sdk.angularOrientation.angleUnit)),
                Angle(__sdk.angularOrientation.secondAngle.toDouble(), unit(__sdk.angularOrientation.angleUnit)),
                Angle(__sdk.angularOrientation.thirdAngle.toDouble(), unit(__sdk.angularOrientation.angleUnit))
        )

    override val angularVelocity
        get() = Space(
                Angle(__sdk.angularVelocity.xRotationRate.toDouble(), unit(__sdk.angularVelocity.unit)) per second,
                Angle(__sdk.angularVelocity.yRotationRate.toDouble(), unit(__sdk.angularVelocity.unit)) per second,
                Angle(__sdk.angularVelocity.zRotationRate.toDouble(), unit(__sdk.angularVelocity.unit)) per second
        )

    val calibrationStatus: Byte
        get() = __sdk.calibrationStatus.calibrationStatus

    override val gravity
        get() = Space(
                d(__sdk.gravity.xAccel, __sdk.gravity.unit) per second per second,
                d(__sdk.gravity.yAccel, __sdk.gravity.unit) per second per second,
                d(__sdk.gravity.zAccel, __sdk.gravity.unit) per second per second
        )

    val acceleratorCalibrated: Boolean
        get() = __sdk.isAccelerometerCalibrated

    val gyroCalibrated: Boolean
        get() = __sdk.isGyroCalibrated

    val magnetometerCalibrated: Boolean
        get() = __sdk.isMagnetometerCalibrated

    val systemCalibrated: Boolean
        get() = __sdk.isSystemCalibrated

    override val linearAcceleration
        get() = Space(
                d(__sdk.linearAcceleration.xAccel, __sdk.linearAcceleration.unit) per second per second,
                d(__sdk.linearAcceleration.yAccel, __sdk.linearAcceleration.unit) per second per second,
                d(__sdk.linearAcceleration.zAccel, __sdk.linearAcceleration.unit) per second per second
        )

    val magneticFlux: MagneticFlux
        get() = __sdk.magneticFieldStrength

    override val overallAcceleration
        get() = Space(
                d(__sdk.overallAcceleration.xAccel, __sdk.overallAcceleration.unit) per second per second,
                d(__sdk.overallAcceleration.yAccel, __sdk.overallAcceleration.unit) per second per second,
                d(__sdk.overallAcceleration.zAccel, __sdk.overallAcceleration.unit) per second per second
        )

    override val position: Space<Distance>
        get() = Space(
                d(__sdk.position.y, __sdk.position.unit),
                d(__sdk.position.x, __sdk.position.unit),
                d(__sdk.position.z, __sdk.position.unit),
        )

    val quaternionOrientation: Quaternion?
        get() = __sdk.quaternionOrientation

    override val temperature: Double
        get() = __sdk.temperature.toUnit(TempUnit.CELSIUS).temperature

    override val velocity
        get() = Space(
                d(__sdk.velocity.xVeloc, __sdk.velocity.unit) per second,
                d(__sdk.velocity.yVeloc, __sdk.velocity.unit) per second,
                d(__sdk.velocity.zVeloc, __sdk.velocity.unit) per second,
        )

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
    ): FImu {
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

    fun startIntegration() {
        __sdk.startAccelerationIntegration(
                Position(DistanceUnit.CM, .0, .0, .0, 0L),
                Velocity(DistanceUnit.CM, .0, .0, .0, 0L),
                1
        )
    }

    fun stopIntegration() =
            __sdk.stopAccelerationIntegration()

    override fun close(): FImu {
        __sdk.close()
        return this
    }

    override fun reset(): FImu =
        init()

    companion object
}