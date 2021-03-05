package io.arct.ftc.eventloop

import com.qualcomm.robotcore.eventloop.opmode.OpMode
import io.arct.ftc.hardware.FDevice
import io.arct.ftc.hardware.devices.FDigitalDevice
import io.arct.ftc.hardware.input.Gamepad
import io.arct.ftc.hardware.motors.FBasicMotor
import io.arct.ftc.hardware.motors.FMotor
import io.arct.ftc.hardware.motors.FMotorEncoder
import io.arct.ftc.hardware.motors.FServo
import io.arct.ftc.hardware.sensors.FColorSensor
import io.arct.ftc.hardware.sensors.FGyroSensor
import io.arct.ftc.hardware.sensors.FTouchSensor
import io.arct.ftc.hardware.sensors.FImu
import io.arct.rl.hardware.Device
import io.arct.rl.hardware.input.Controller
import io.arct.rl.hardware.devices.DigitalDevice
import io.arct.rl.hardware.motors.BasicMotor
import io.arct.rl.hardware.motors.ContinuousServo
import io.arct.rl.hardware.motors.Motor
import io.arct.rl.hardware.motors.Servo
import io.arct.rl.hardware.sensors.ColorSensor
import io.arct.rl.hardware.sensors.Encoder
import io.arct.rl.hardware.sensors.GyroSensor
import io.arct.rl.hardware.sensors.TouchSensor
import io.arct.rl.units.AngularSpeed
import io.arct.rl.units.Distance
import java.lang.Exception
import kotlin.reflect.KProperty

abstract class FHardwareMap internal constructor(private val __sdk: OpMode) {
    protected lateinit var op: OperationMode

    // Digital Device
    fun DigitalDevice.Companion.get(identifier: String): FDigitalDevice =
        FDigitalDevice(getSdk(identifier), op)

    operator fun DigitalDevice.Companion.getValue(thisRef: Any?, property: KProperty<*>): FDigitalDevice =
        DigitalDevice.get(property.name)

    // Gamepad
    fun Controller.Companion.get(identifier: String): Gamepad = when (identifier) {
        "gamepad0" -> Gamepad(__sdk::gamepad1)
        "gamepad1" -> Gamepad(__sdk::gamepad2)
        else -> throw Exception()
    }

    operator fun Controller.Companion.getValue(thisRef: Any?, property: KProperty<*>): Gamepad =
        Controller.get(property.name)

    // Basic Motor
    fun BasicMotor.Companion.get(identifier: String): FBasicMotor =
        FBasicMotor(getSdk(identifier), op)

    operator fun BasicMotor.Companion.getValue(thisRef: Any?, property: KProperty<*>): FBasicMotor =
        BasicMotor.get(property.name)

    // Continuous Servo
    fun ContinuousServo.Companion.get(identifier: String): FBasicMotor =
        FBasicMotor(getSdk(identifier), op)

    operator fun ContinuousServo.Companion.getValue(thisRef: Any?, property: KProperty<*>): FBasicMotor =
        ContinuousServo.get(property.name)

    // Motor
    fun Motor.Companion.get(
        identifier: String,
        speed: AngularSpeed? = null,
        diameter: Distance? = null,
        ticksPerDeg: Double = 1.0
    ): FMotor =
        FMotor(getSdk(identifier), op, speed, diameter, ticksPerDeg)

    operator fun Motor.Companion.getValue(thisRef: Any?, property: KProperty<*>): FMotor =
        Motor.get(property.name)

    // Encoder
    fun Encoder.Companion.get(identifier: String, ticksPerDeg: Double = 1.0): FMotorEncoder =
        Motor.get(identifier, null, null, ticksPerDeg).encoder

    operator fun Encoder.Companion.getValue(thisRef: Any?, property: KProperty<*>): FMotorEncoder =
        Encoder.get(property.name)

    // Servo
    fun Servo.Companion.get(identifier: String): FServo =
        FServo(getSdk(identifier), op)

    operator fun Servo.Companion.getValue(thisRef: Any?, property: KProperty<*>): FServo =
        Servo.get(property.name)

    // Color Sensor
    fun ColorSensor.Companion.get(identifier: String): FColorSensor =
        FColorSensor(getSdk(identifier), op)

    operator fun ColorSensor.Companion.getValue(thisRef: Any?, property: KProperty<*>): FColorSensor =
        ColorSensor.get(property.name)

    // Gyro Sensor
    fun GyroSensor.Companion.get(identifier: String): FGyroSensor =
        FGyroSensor(getSdk(identifier), op)

    operator fun GyroSensor.Companion.getValue(thisRef: Any?, property: KProperty<*>): FGyroSensor =
        GyroSensor.get(property.name)

    // Touch Sensor
    fun TouchSensor.Companion.get(identifier: String): FTouchSensor =
        FTouchSensor(getSdk(identifier), op)

    operator fun TouchSensor.Companion.getValue(thisRef: Any?, property: KProperty<*>): FTouchSensor =
        TouchSensor.get(property.name)

    // Imu
    fun FImu.Companion.get(identifier: String): FImu =
        FImu(getSdk(identifier), op)

    operator fun FImu.Companion.getValue(thisRef: Any?, property: KProperty<*>): FImu =
        FImu.get(property.name)

    // Basic Motor
    fun Device.Companion.get(identifier: String): FDevice =
        FDevice(getSdk(identifier), op)

    operator fun Device.Companion.getValue(thisRef: Any?, property: KProperty<*>): FDevice =
        Device.get(property.name)

    // Util
    private inline fun <reified T> getSdk(identifier: String): T =
        __sdk.hardwareMap.get(T::class.java, identifier) as T
}