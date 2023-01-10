package agh.ics.oop;

import agh.ics.oop.auxiliary.MapDirection;
import agh.ics.oop.auxiliary.Vector2d;
import agh.ics.oop.gui.IMapRefreshObserver;
import agh.ics.oop.mapElements.Animal;
import agh.ics.oop.maps.IWorldMap;

import java.util.ArrayList;

public class Simulation implements Runnable{
    private final IWorldMap map;
    private final CONSTANT CONSTANT;
    private final ArrayList<IMapRefreshObserver> mapRefreshObservers = new ArrayList<>();
    private boolean continuation=false;
    private final int moveDelay;
    private final int numberOfStepsSimulation;
    private final int numberOfAnimalToAdd;

    public Simulation(IWorldMap map,CONSTANT constant) throws IllegalAccessException {
        this.map = map;
        this.CONSTANT=constant;
        this.moveDelay=CONSTANT.MOVEDELAY;
        this.numberOfStepsSimulation=CONSTANT.SIMULATIONLENGTH;
        this.numberOfAnimalToAdd=CONSTANT.AMOUNTOFANIMALS;
    }

    public void mapRefresh() {
        for (IMapRefreshObserver observer: mapRefreshObservers) {
            observer.refresh();
        }
    }
    public void setContinuationTrue(){
        continuation=true;
    }
    @Override
    public void run() {
        for(int i=0;i<numberOfAnimalToAdd;i++){
            if (continuation){ break; }
            Animal animal=new Animal(map,new Vector2d((int)(Math.random()*map.getEndOfMap().x()),(int)(Math.random()*map.getEndOfMap().y())), MapDirection.NORTH,CONSTANT.STARTINGENERGY,CONSTANT,0);
            System.out.println(animal.getPosition());
            map.placeAnimal(animal);
        }
        for(int i=0;i<numberOfStepsSimulation;i++){
            try {
                Thread.sleep(moveDelay);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            map.increaseSimulationDay();
            map.removeDeadAnimals();
            map.moveAnimals();
            map.upadteMap();
            map.removeDeadAnimals();
            map.countFreeSpaces();
            map.setMeanNumberOfChildren();
            map.setAverageEnergy();
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
