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

private fun unit(u: DistanceUnit) = when (u) {
    DistanceUnit.CM -> Distance.Unit.Centimeter
    DistanceUnit.INCH -> Distance.Unit.Inch
    DistanceUnit.METER -> Distance.Unit.Meter
    DistanceUnit.MM -> TODO()
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
                Distance(__sdk.acceleration.xAccel, unit(__sdk.acceleration.unit)) per second per second,
                Distance(__sdk.acceleration.yAccel, unit(__sdk.acceleration.unit)) per second per second,
                Distance(__sdk.acceleration.zAccel, unit(__sdk.acceleration.unit)) per second per second
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
                Distance(__sdk.gravity.xAccel, unit(__sdk.gravity.unit)) per second per second,
                Distance(__sdk.gravity.yAccel, unit(__sdk.gravity.unit)) per second per second,
                Distance(__sdk.gravity.zAccel, unit(__sdk.gravity.unit)) per second per second
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
                Distance(__sdk.linearAcceleration.xAccel, unit(__sdk.linearAcceleration.unit)) per second per second,
                Distance(__sdk.linearAcceleration.yAccel, unit(__sdk.linearAcceleration.unit)) per second per second,
                Distance(__sdk.linearAcceleration.zAccel, unit(__sdk.linearAcceleration.unit)) per second per second
        )

    val magneticFlux: MagneticFlux
        get() = __sdk.magneticFieldStrength

    override val overallAcceleration
        get() = Space(
                Distance(__sdk.overallAcceleration.xAccel, unit(__sdk.overallAcceleration.unit)) per second per second,
                Distance(__sdk.overallAcceleration.yAccel, unit(__sdk.overallAcceleration.unit)) per second per second,
                Distance(__sdk.overallAcceleration.zAccel, unit(__sdk.overallAcceleration.unit)) per second per second
        )

    override val position: Space<Distance>
        get() = Space(
                Distance(__sdk.position.x, unit(__sdk.position.unit)),
                Distance(__sdk.position.y, unit(__sdk.position.unit)),
                Distance(__sdk.position.z, unit(__sdk.position.unit)),
        )

    val quaternionOrientation: Quaternion?
        get() = __sdk.quaternionOrientation

    override val temperature: Double
        get() = __sdk.temperature.toUnit(TempUnit.CELSIUS).temperature

    override val velocity
        get() = Space(
                Distance(__sdk.velocity.xVeloc, unit(__sdk.velocity.unit)) per second,
                Distance(__sdk.velocity.yVeloc, unit(__sdk.velocity.unit)) per second,
                Distance(__sdk.velocity.zVeloc, unit(__sdk.velocity.unit)) per second,
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