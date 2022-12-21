package agh.ics.oop.mapElements;

import agh.ics.oop.auxiliary.MapDirection;
import agh.ics.oop.auxiliary.Vector2d;

public interface IMapElement {
    Vector2d getPosition();
    MapDirection getDirection();
    void setDirection(MapDirection directionToChange);
}
