package io.arct.rl.eventloop

interface ProgramLoop {
    suspend fun exit()

    suspend fun init() {}
    suspend fun loop()
    suspend fun stop() {}
}