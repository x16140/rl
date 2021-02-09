package io.arct.ftc.eventloop

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode
import io.arct.rl.eventloop.Program

abstract class LinearOperationMode : OperationMode(), Program {
    override val __sdk: LinearOpMode = (current as? LinearOpMode?)!!

    val started: Boolean
        get() = __sdk.isStarted

    val stopRequested: Boolean
        get() = __sdk.isStopRequested

    override val active: Boolean
        get() = __sdk.opModeIsActive()

    fun idle(): LinearOperationMode {
        __sdk.idle()
        return this
    }

    fun sleep(ms: Long): LinearOperationMode {
        __sdk.sleep(ms)
        return this
    }

    override suspend fun init() {}

    final override suspend fun loop() {}
    final override suspend fun initLoop() {}
}