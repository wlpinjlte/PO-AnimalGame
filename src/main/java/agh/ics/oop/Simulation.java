package agh.ics.oop;

import agh.ics.oop.auxiliary.MapDirection;
import agh.ics.oop.auxiliary.Vector2d;
import agh.ics.oop.gui.IMapRefreshObserver;
import agh.ics.oop.mapElements.Animal;
import agh.ics.oop.maps.AbstractWorldMap;
import agh.ics.oop.maps.IWorldMap;

import java.util.ArrayList;

public class Simulation implements Runnable{
    private final IWorldMap map;
    private final ArrayList<IMapRefreshObserver> mapRefreshObservers = new ArrayList<>();

    private final int moveDelay=1000;
    private final int numberOfStepsSimulation=100;
    private final int numberOfAnimalToAdd=25;

    public Simulation(IWorldMap map) throws IllegalAccessException {
        this.map = map;
    }

    public void mapRefresh() {
        for (IMapRefreshObserver observer: mapRefreshObservers) {
            observer.refresh();
        }
    }
    @Override
    public void run() {
        for(int i=0;i<numberOfAnimalToAdd;i++){
            Animal animal=new Animal(map,new Vector2d((int)(Math.random()*map.getEndOfMap().x()),(int)(Math.random()*map.getEndOfMap().y())), MapDirection.NORTH,CONSTANT.PLUSENERGY*5);
            System.out.println(animal.getPosition());
            map.placeAnimal(animal);
        }
        for(int i=0;i<numberOfStepsSimulation;i++){
            try {
                Thread.sleep(moveDelay);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
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
