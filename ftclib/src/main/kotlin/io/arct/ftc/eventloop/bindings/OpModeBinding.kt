package io.arct.ftc.eventloop.bindings

import com.qualcomm.robotcore.eventloop.opmode.OpMode
import io.arct.ftc.eventloop.FHardwareMap
import io.arct.ftc.eventloop.OperationMode
import kotlinx.coroutines.runBlocking

abstract class OpModeBinding : OpMode() {
    private lateinit var mode: OperationMode

    protected abstract fun getOperationMode(): OperationMode

    override fun init() {
        OperationMode.current = this
        mode = getOperationMode()

        runBlocking {
            mode.init()
        }
    }

    override fun init_loop() = runBlocking { mode.initLoop() }
    override fun start() = runBlocking { mode.start() }
    override fun loop() = runBlocking { mode.loop() }

    override fun stop() = runBlocking { mode.stop() }
}