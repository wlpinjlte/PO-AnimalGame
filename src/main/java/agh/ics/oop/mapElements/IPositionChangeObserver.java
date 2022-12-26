package agh.ics.oop.mapElements;

import agh.ics.oop.auxiliary.Vector2d;

public interface IPositionChangeObserver {
    //zmienilem elemet na animal bo trawa i tak sie nie rusza a tak musialbym rzutowac albo zalozyc ze trawa ma orientacje
    void positionChange(Animal elementToAdd, Vector2d oldPosition, Vector2d newPosition);
}
