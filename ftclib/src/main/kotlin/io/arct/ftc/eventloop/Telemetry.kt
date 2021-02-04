package io.arct.ftc.eventloop

class Telemetry internal constructor(private val __sdk: org.firstinspires.ftc.robotcore.external.Telemetry) {
    var autoClear: Boolean
        get() = __sdk.isAutoClear
        set(v) { __sdk.isAutoClear = v }

    fun add(line: String): Telemetry {
        __sdk.addLine(line)
        return this
    }

    fun add(data: List<String>): Telemetry {
        for (line in data)
            add(line)

        return this
    }

    fun update(): Telemetry {
        __sdk.update()
        return this
    }

    fun clear(): Telemetry {
        __sdk.clear()
        return this
    }

    fun clearAll(): Telemetry {
        __sdk.clearAll()
        return this
    }
}