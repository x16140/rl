package io.arct.rl.eventloop

import io.arct.rl.hardware.HardwareMap

interface ProgramLoop {
    suspend fun exit()

    suspend fun init() {}
    suspend fun loop()
    suspend fun stop() {}
}