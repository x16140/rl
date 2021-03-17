package io.arct.rl.robot.position

import io.arct.rl.hardware.sensors.Imu
import io.arct.rl.units.*

class ImuPositioning(
        private val imu: Imu,

        private val x: (Space<Distance>) -> Distance,
        private val y: (Space<Distance>) -> Distance,

        private val angle: (Space<Angle>) -> Angle
) : Positioning() {
    private var cx = 0.cm
    private var cy = 0.cm
    private var ca = 0.deg

    override val position: Coordinates
        get() = Coordinates(x(imu.position) - cx, y(imu.position) - cy)

    override val rotation: Angle
        get() = angle(imu.orientation) - ca

    init {
        zero()
    }

    override fun zero(): ImuPositioning {
        cx = x(imu.position)
        cy = y(imu.position)
        ca = angle(imu.orientation)

        return this
    }
}