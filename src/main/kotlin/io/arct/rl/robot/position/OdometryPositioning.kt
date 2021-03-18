package io.arct.rl.robot.position

import io.arct.rl.hardware.sensors.DistanceEncoder
import io.arct.rl.hardware.sensors.Imu
import io.arct.rl.units.*
import kotlin.concurrent.thread
import kotlin.math.atan
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.sin

class OdometryPositioning(
        val y1: DistanceEncoder,
        val y2: DistanceEncoder,
        val x: DistanceEncoder,

        private val imu: Imu,
        private val angle: (Space<Angle>) -> Angle,
) : Positioning() {
    override var position: Coordinates = Coordinates(0.cm, 0.cm)
        private set

    override val rotation: Angle
        get() = angle(imu.orientation) - ca

    private var thread: Thread? = null

    private var py1 = y1.position
    private var py2 = y2.position
    private var px = x.position
    private var ca = 0.deg
    private var pa: Angle = rotation

    init {
        zero()
    }

    fun spawn(): OdometryPositioning {
        stop()

        thread = thread(start = true) {
            while (true) {
                var dyb = ((y1.position - py1 + y2.position - py2) / 2).cm.value
                var dxb = x.position.cm.value

                if (dyb == .0)
                    dyb = Double.MIN_VALUE

                if (dxb == .0)
                    dxb = Double.MIN_VALUE

                val a = 90.deg - (rotation + (rotation - pa) / 2)

                val db = atan(dyb / dxb).cm
                val dyf = db * sin(a.rad.value)
                val dxf = db * cos(a.rad.value)

                val (cx, cy) = position
                position = Coordinates(cx + dxf, cy + dyf)

                pa = rotation
                py1 = y1.position
                py2 = y2.position
                px = x.position
            }
        }

        return this
    }

    fun stop(): OdometryPositioning {
        thread?.interrupt()
        return this
    }

    override fun zero(): OdometryPositioning {
        y1.zero()
        y2.zero()
        x.zero()

        pa = rotation
        py1 = y1.position
        py2 = y2.position
        px = x.position
        ca = angle(imu.orientation)
        position = Coordinates(0.cm, 0.cm)

        return this
    }
}