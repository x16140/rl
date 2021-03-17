package io.arct.ftc.hardware.motors

import com.qualcomm.robotcore.hardware.DcMotorSimple
import io.arct.ftc.eventloop.OperationMode
import io.arct.ftc.hardware.FDevice
import io.arct.rl.hardware.motors.BasicMotor
import io.arct.rl.units.AngularVelocity
import io.arct.rl.units.Distance
import java.lang.Exception

open class FBasicMotor internal constructor(
        private val __sdk: DcMotorSimple,
        private val __op: OperationMode,

        override val speed: AngularVelocity? = null,
        override val diameter: Distance? = null
) : FDevice(__sdk, __op), BasicMotor {

    override var direction: BasicMotor.Direction
        get() = when (__sdk.direction) {
            DcMotorSimple.Direction.FORWARD -> BasicMotor.Direction.Forward
            DcMotorSimple.Direction.REVERSE -> BasicMotor.Direction.Reverse
            null -> throw Exception()
        }
        set(v) {
            __sdk.direction = when(v) {
                BasicMotor.Direction.Forward -> DcMotorSimple.Direction.FORWARD
                BasicMotor.Direction.Reverse -> DcMotorSimple.Direction.REVERSE
            }
        }

    override fun power(speed: Double): BasicMotor {
        __sdk.power = speed
        return this
    }

    override fun close(): BasicMotor = super.close() as BasicMotor
    override fun reset(): BasicMotor = super.reset() as BasicMotor
}