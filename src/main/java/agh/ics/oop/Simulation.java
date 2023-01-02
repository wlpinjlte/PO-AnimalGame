package agh.ics.oop;

import agh.ics.oop.auxiliary.MapDirection;
import agh.ics.oop.auxiliary.Vector2d;
import agh.ics.oop.gui.IMapRefreshObserver;
import agh.ics.oop.mapElements.Animal;
import agh.ics.oop.maps.AbstractWorldMap;
import agh.ics.oop.maps.IWorldMap;

import java.util.ArrayList;

public class Simulation {
    private final IWorldMap map;
    private final ArrayList<IMapRefreshObserver> mapRefreshObservers = new ArrayList<>();

    private final int moveDelay=100;
    private final int numberOfStepsSimulation=100;
    private final int numberOfAnimalToAdd=10;

    public Simulation(IWorldMap map) throws IllegalAccessException {
        this.map = map;
    }

    public void mapRefresh() {
        for (IMapRefreshObserver observer: mapRefreshObservers) {
            observer.refresh();
        }
    }
    public void run() throws InterruptedException {
        for(int i=0;i<numberOfAnimalToAdd;i++){
            Animal animal=new Animal(map,new Vector2d((int)(Math.random()*map.getEndOfMap().x()),(int)(Math.random()*map.getEndOfMap().y())), MapDirection.NORTH,CONSTANT.PLUSENERGY);
            System.out.println(animal.getPosition());
            map.placeAnimal(animal);
        }
        Animal animal=new Animal(map,new Vector2d((int)(Math.random()*map.getEndOfMap().x()),(int)(Math.random()*map.getEndOfMap().y())), MapDirection.NORTH,0);
        map.placeAnimal(animal);
        for(int i=0;i<numberOfStepsSimulation;i++){
            Thread.sleep(moveDelay);
            map.removeDeadAnimals();
            map.moveAnimals();
            map.upadteMap();
            mapRefresh();
        }
    }
    public void addObserver(IMapRefreshObserver observer) {
        this.mapRefreshObservers.add(observer);
    }

    public void removeObserver(IMapRefreshObserver observer) {
        this.mapRefreshObservers.remove(observer);
    }
}
