package io.arct.rl.robot.position

import io.arct.rl.hardware.sensors.DistanceEncoder
import io.arct.rl.hardware.sensors.Imu
import io.arct.rl.units.*
import kotlin.concurrent.thread

class OdometryPositioning(
        val y1: DistanceEncoder,
        val y2: DistanceEncoder,
        val x: DistanceEncoder,

        private val imu: Imu,
        private val angle: (Space<Angle>) -> Angle,

        private val diameter: Distance
) : Positioning() {
    override var position: Coordinates = Coordinates(0.cm, 0.cm)
        private set

    override val rotation: Angle
        get() = angle(imu.orientation) - ca

    private var thread: Thread? = null
    private var pa: Angle = rotation
    private var ca = 0.deg

    init {
        zero()
    }

    fun spawn(): OdometryPositioning {
        stop()

        thread = thread(start = true) {
            val dy = (y1.position + y2.position) / 2
            val da = rotation - pa
            val aa = rotation + da / 2

            // need to do angles math + distance
        }

        return this
    }

    fun stop(): OdometryPositioning {
        thread?.interrupt()
        return this
    }

    override fun zero(): OdometryPositioning {
        pa = rotation
        ca = angle(imu.orientation)
        position = Coordinates(0.cm, 0.cm)

        return this
    }
}