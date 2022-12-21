package agh.ics.oop.maps;

import agh.ics.oop.auxiliary.Vector2d;
import agh.ics.oop.mapElements.IMapElement;

import java.util.LinkedList;

public interface IWorldMap {
    void place(IMapElement animal);
    void removeDeadAnimals(Vector2d positionToUpdate);
    LinkedList<IMapElement> objectsAt(Vector2d position);
    void growingGrass(Vector2d positionToUpdate);
    void eatingGrass(Vector2d positionToUpdate);
    void multiplication(Vector2d positionToUpdate);
    void upadteMap();
}
