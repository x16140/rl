package io.arct.rl.robot

import io.arct.rl.robot.drive.Drive
import io.arct.rl.robot.drive.IDrive
import io.arct.rl.robot.position.IPositioning
import io.arct.rl.robot.position.Positioning
import java.lang.Exception

class Robot internal constructor(
    internal val drive: IDrive,
    internal val positioning: IPositioning
) : IDrive by drive, IPositioning by positioning

fun robot(fn: RobotBuilder.() -> Unit): Robot {
    val builder = RobotBuilder()
    fn(builder)

    val drive = builder.drive
    val positioning = builder.positioning

    if (drive == null || positioning == null)
        throw Exception("Error while creating robot: Drive and Positioning strategy must be set!")

    val robot = Robot(drive, positioning)

    drive.init(robot)
    positioning.init(robot)

    return robot
}

class RobotBuilder internal constructor() {
    internal var drive: Drive? = null
    internal var positioning: Positioning? = null

    val using = Using(this)

    class Using internal constructor(private val builder: RobotBuilder) {
        infix fun drive(drive: Drive) {
            builder.drive = drive
        }

        infix fun positioning(positioning: Positioning) {
            builder.positioning = positioning
        }
    }
}