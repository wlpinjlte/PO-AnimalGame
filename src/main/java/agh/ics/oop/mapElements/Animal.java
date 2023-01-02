package agh.ics.oop.mapElements;

import agh.ics.oop.CONSTANT;
import agh.ics.oop.auxiliary.MapDirection;
import agh.ics.oop.auxiliary.Vector2d;
import agh.ics.oop.maps.IWorldMap;

import java.util.LinkedList;

public class Animal implements IMapElement {
    private Vector2d position;
    private MapDirection orientation;
    private IWorldMap map;
    private final Genes genome;
    private int energy;

    private int daysAlive = 0;
    protected final LinkedList<IPositionChangeObserver> positionObservers = new LinkedList<>();


    public Animal(IWorldMap map, Vector2d initialPosition, MapDirection initialOrientation, int startingEnergy) {
        setParameters(map, initialPosition, initialOrientation, startingEnergy);
        this.genome = new Genes();
    }

    public Animal(IWorldMap map, Vector2d initialPosition, MapDirection initialOrientation, int startingEnergy, Genes Genome1, Genes Genome2, int geneShare) {
        setParameters(map, initialPosition, initialOrientation, startingEnergy);
        this.genome = new Genes(Genome1, Genome2, geneShare);
    }

    private void setParameters(IWorldMap map, Vector2d pos, MapDirection ori, int energy) {
        this.map = map;
        this.position = pos;
        this.orientation = ori;
        this.energy = energy;
    }

    //wysyła zapytanie o zmiane pozycji do mapy
//genom nie działa
    public void move() {
//        int timesToTurn = genome.getGene(daysAlive%genome.getGenomeLength());
//        for (; timesToTurn > 0; timesToTurn--) {
//            orientation = orientation.next(orientation);
//        }
        positionChanged(position, position.add(orientation.toUnitVector()));
    }
    public void addToSurvivalCounter(){
        this.daysAlive++;
    }

    // dodawanie i odejmowianie energii
    public void changeEnergy(int energyChange) {
        energy += energyChange;
    }

    public Genes getGenome() {
        return genome;
    }

    public Vector2d getPosition() {
        return position;
    }

    public int getEnergy() {
        return energy;
    }


    public MapDirection getDirection() {
        return orientation;
    }

    public void setDirection(MapDirection directionToChange) {
        this.orientation = directionToChange;
    }

    public MapDirection getOrientation() {
        return orientation;
    }

    public void setPosition(Vector2d position) {
        this.position = position;
    }

    //jezeli nie maja energii zwroc null w przeciwnym przypadku usun energie rodzica i zrob dziecko, zwroc dziecko
    public Animal procreate(Animal parent1, Animal parent2) {
        if (parent1.getEnergy() >= CONSTANT.COSTTOCONCIEVECHILDREN && parent2.getEnergy() >= CONSTANT.COSTTOCONCIEVECHILDREN) {
            int geneShare = parent1.getEnergy() / (parent1.getEnergy() + parent2.getEnergy()) * 100;
            Animal child = new Animal(map, parent1.getPosition(), parent1.getOrientation(), CONSTANT.COSTTOCONCIEVECHILDREN * 2, parent1.getGenome(), parent2.getGenome(), geneShare);
            child.getGenome().mutate();
            return child;
        } else {
            return null;
        }
    }
    public void addObserver(IPositionChangeObserver observer){
        positionObservers.add(observer);
    }

    public void removeObserver(IPositionChangeObserver observer){
        positionObservers.remove(observer);
    }

    protected void positionChanged(Vector2d oldPosition, Vector2d newPosition){
        for(IPositionChangeObserver observer:positionObservers){
            observer.positionChange(this,oldPosition, newPosition);
        }
    }
}
