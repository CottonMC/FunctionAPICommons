package io.github.cottonmc.functionapi.util

import io.github.cottonmc.functionapi.api.content.enums.Direction
import io.github.cottonmc.functionapi.api.content.enums.PlacementPosition

class PlacementMapping(val stateName: String, val stateValue: String, var direction: Direction, val placementPosition: PlacementPosition, val facingDirection: Direction) {
    fun setDirection(direction: Direction): PlacementMapping {
        this.direction = direction
        return this
    }
}