package io.arct.rl.hardware.sensors

import io.arct.rl.hardware.Device
import io.arct.rl.units.*

interface Imu : Device {
    val acceleration: Space<Acceleration>
    val orientation: Space<Angle>
    val angularVelocity: Space<AngularVelocity>
    val gravity: Space<Acceleration>
    val linearAcceleration: Space<Acceleration>
    val overallAcceleration: Space<Acceleration>
    val position: Space<Distance>
    val temperature: Double
    val velocity: Space<Velocity>
}