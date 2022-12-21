package agh.ics.oop.mapElements;

import agh.ics.oop.auxiliary.Vector2d;

public interface IPositionChangeObserver {
    void positionChange(IMapElement elementToAdd, Vector2d oldPosition, Vector2d newPosition);
}
