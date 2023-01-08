package agh.ics.oop.maps;

import agh.ics.oop.auxiliary.Vector2d;
import agh.ics.oop.mapElements.Animal;
import agh.ics.oop.mapElements.FieldStats;
import agh.ics.oop.mapElements.Grass;
import agh.ics.oop.mapElements.IMapElement;

import java.util.LinkedList;

public interface IWorldMap {
    void placeAnimal(Animal animal);
    void removeDeadAnimals();
    void growingGrass();
    void eatingGrass(Vector2d positionToUpdate);
    void multiplication(Vector2d positionToUpdate);
    Vector2d getEndOfMap();
    void upadteMap();
    FieldStats getFieldStatsPosition(Vector2d position);
    void updateGrassField(Vector2d grassToPlace);
    void moveAnimals();
    void wys();
    boolean grassStatus(Vector2d positionToCheck);
    LinkedList<Animal> animalsAt(Vector2d positionToCheck);
    void countFreeSpaces();
    void setMeanNumberOfChildren();
    void setAverageEnergy();
}
