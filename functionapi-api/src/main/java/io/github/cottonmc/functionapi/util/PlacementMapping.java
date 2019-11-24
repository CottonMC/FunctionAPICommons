package io.github.cottonmc.functionapi.util;

import io.github.cottonmc.functionapi.api.content.enums.Direction;
import io.github.cottonmc.functionapi.api.content.enums.PlacementPosition;



public class PlacementMapping {

    private String stateName;
    private String stateValue;
    private Direction direction;
    private PlacementPosition placementPosition;
    private Direction facingDirection;


    public PlacementMapping(String stateName, String stateValue, Direction direction, PlacementPosition placementPosition, Direction facingDirection) {
        this.stateName = stateName;
        this.stateValue = stateValue;
        this.direction = direction;
        this.placementPosition = placementPosition;
        this.facingDirection = facingDirection;
    }

    public String getStateValue() {
        return stateValue;
    }

    public String getStateName() {
        return stateName;
    }

    public Direction getDirection() {
        return direction;
    }

    public PlacementMapping setDirection(Direction direction) {
        this.direction = direction;
        return this;
    }

    public PlacementPosition getPlacementPosition() {
        return placementPosition;
    }


    public Direction getFacingDirection() {
        return facingDirection;
    }
}
