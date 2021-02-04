package io.arct.ftc.eventloop.bindings

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode
import io.arct.ftc.eventloop.FHardwareMap
import io.arct.ftc.eventloop.LinearOperationMode
import io.arct.ftc.eventloop.OperationMode
import kotlinx.coroutines.runBlocking

abstract class LinearOpModeBinding : LinearOpMode() {
    private lateinit var mode: LinearOperationMode

    protected abstract fun getOperationMode(): LinearOperationMode

    override fun runOpMode() {
        OperationMode.current = this
        mode = getOperationMode()

        runBlocking {
            try {
                mode.init()

                waitForStart()

                mode.start()
                mode.run()
                mode.stop()
            } catch (e: Exception) {
                mode.log
                    .add("ERROR:")
                    .add(e.message ?: "Unknown Error")
                    .add(e.stackTrace.joinToString("\n") { "\t" + it.toString() })
                    .update()

                while (true)
                    sleep(10)
            }
        }
    }
}