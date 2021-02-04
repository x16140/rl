package io.arct.rl.robot.position

import io.arct.rl.units.Angle
import io.arct.rl.units.Coordinates
import io.arct.rl.units.cm
import io.arct.rl.units.deg

abstract class DynamicPositioning: Positioning() {
    override var position: Coordinates = Coordinates(0.cm, 0.cm)
        protected set

    override var rotation: Angle = 0.deg
        protected set

    abstract fun updateLinear()
    abstract fun updateAngular()

    companion object {
        fun updateLinear(positioning: IPositioning) {
            if (positioning is DynamicPositioning)
                positioning.updateLinear()
        }

        fun updateAngular(positioning: IPositioning) {
            if (positioning is DynamicPositioning)
                positioning.updateAngular()
        }
    }
}