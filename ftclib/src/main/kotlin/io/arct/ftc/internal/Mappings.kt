package io.arct.ftc.internal

import com.qualcomm.hardware.bosch.BNO055IMU
import com.qualcomm.robotcore.hardware.*
import io.arct.ftc.hardware.FDevice
import io.arct.rl.hardware.*
import io.arct.rl.hardware.devices.*
import io.arct.rl.hardware.motors.*
import io.arct.ftc.hardware.devices.*
import io.arct.ftc.hardware.motors.*
import io.arct.ftc.hardware.sensors.*
import io.arct.rl.hardware.motors.Servo
import io.arct.rl.hardware.sensors.ColorSensor
import io.arct.rl.hardware.sensors.GyroSensor
import io.arct.rl.hardware.sensors.TouchSensor
import kotlin.reflect.KClass

internal val LibMap: Map<KClass<out Device>, KClass<out Device>> = mapOf(
    DigitalDevice::class to   FDigitalDevice::class,
    BasicMotor::class to      FBasicMotor::class,
    ContinuousServo::class to FContinuousServo::class,
    Motor::class to           FMotor::class,
    Servo::class to           FServo::class,
    ColorSensor::class to     FColorSensor::class,
    GyroSensor::class to      FGyroSensor::class,
    TouchSensor::class to     FTouchSensor::class,
    Device::class to          FDevice::class
)

internal val SdkMap: Map<KClass<out Device>, Class<*>> = mapOf(
    FDigitalDevice::class to   DigitalChannel::class.java,
    FBasicMotor::class to      DcMotorSimple::class.java,
    FContinuousServo::class to CRServo::class.java,
    FMotor::class to           DcMotor::class.java,
    FServo::class to           com.qualcomm.robotcore.hardware.Servo::class.java,
    FColorSensor::class to     com.qualcomm.robotcore.hardware.ColorSensor::class.java,
    FGyroSensor::class to      com.qualcomm.robotcore.hardware.GyroSensor::class.java,
    Imu::class to             BNO055IMU::class.java,
    FTouchSensor::class to     com.qualcomm.robotcore.hardware.TouchSensor::class.java,
    FDevice::class to          HardwareDevice::class.java
)