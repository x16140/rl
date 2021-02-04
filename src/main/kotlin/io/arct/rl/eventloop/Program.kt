package io.arct.rl.eventloop

interface Program {
    val active: Boolean

    suspend fun run()
}