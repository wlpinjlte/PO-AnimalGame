package agh.ics.oop.mapElements;

import agh.ics.oop.auxiliary.MapDirection;
import agh.ics.oop.auxiliary.Vector2d;
import agh.ics.oop.maps.AbstractWorldMap;

public class Animal implements IMapElement {
    int costToConcieveChildren;
    private Vector2d position;
    private MapDirection orientation;
    private AbstractWorldMap map;
    private final Genes genome;
    private int energy;

    private int daysAlive = 0;


    public Animal(AbstractWorldMap map, Vector2d initialPosition, MapDirection initialOrientation, int startingEnergy) {
        setParameters(map, initialPosition, initialOrientation, startingEnergy);
        this.genome = new Genes();
    }

    public Animal(AbstractWorldMap map, Vector2d initialPosition, MapDirection initialOrientation, int startingEnergy, Genes Genome1, Genes Genome2, int geneShare) {
        setParameters(map, initialPosition, initialOrientation, startingEnergy);
        this.genome = new Genes(Genome1, Genome2, geneShare);
    }

    private void setParameters(AbstractWorldMap map, Vector2d pos, MapDirection ori, int energy) {
        this.map = map;
        this.position = pos;
        this.orientation = ori;
        this.energy = energy;
    }

    //wysyła zapytanie o zmiane pozycji do mapy

    public void move() {
        int timesToTurn = genome.getGene(daysAlive%genome.getGenomeLength());
        for (; timesToTurn > 0; timesToTurn--) {
            orientation = orientation.next(orientation);
        }
        map.positionChange(this, position, position.add(orientation.toUnitVector()));
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
        if (parent1.getEnergy() >= costToConcieveChildren && parent2.getEnergy() >= costToConcieveChildren) {
            int geneShare = parent1.getEnergy() / (parent1.getEnergy() + parent2.getEnergy()) * 100;
            Animal child = new Animal(map, parent1.getPosition(), parent1.getOrientation(), costToConcieveChildren * 2, parent1.getGenome(), parent2.getGenome(), geneShare);
            child.getGenome().mutate();
            return child;
        } else {
            return null;
        }
    }


}
