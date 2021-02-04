package io.arct.ftc.eventloop

import com.qualcomm.robotcore.eventloop.opmode.OpMode
import io.arct.rl.eventloop.ProgramLoop

abstract class OperationMode : FHardwareMap(current!!), ProgramLoop {
    private val __sdk: OpMode = current!!

    init {
        op = this
    }

    val time: Double
        get() = __sdk.time

    val log = Telemetry(__sdk.telemetry)

    override suspend fun exit(): Unit =
        __sdk.requestOpModeStop()

    open suspend fun initLoop() {}
    open suspend fun start() {}

    annotation class Register(val type: Type, val name: String = "", val group: String = "")

    enum class Type {
        Autonomous,
        Operated,
        Disabled
    }

    internal companion object {
        var current: OpMode? = null
    }
}