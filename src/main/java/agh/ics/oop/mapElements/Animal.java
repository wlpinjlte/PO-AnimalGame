package agh.ics.oop.mapElements;

import agh.ics.oop.CONSTANT;
import agh.ics.oop.auxiliary.GenomeVariants;
import agh.ics.oop.auxiliary.MapDirection;
import agh.ics.oop.auxiliary.Vector2d;
import agh.ics.oop.maps.IWorldMap;

import java.util.LinkedList;

public class Animal implements IMapElement {
    private final CONSTANT CONSTANT;
    private int numberOfChildren=0;
    private int amountOfGrassEaten=0;
    private final GenomeVariants genomeVariant;
    private Vector2d position;
    private MapDirection orientation;
    private IWorldMap map;
    private final Genes genome;
    private int energy;

    private int daysAlive = 0;
    protected final LinkedList<IPositionChangeObserver> positionObservers = new LinkedList<>();


    public Animal(IWorldMap map, Vector2d initialPosition, MapDirection initialOrientation, int startingEnergy, CONSTANT constant) {
        setParameters(map, initialPosition, initialOrientation, startingEnergy);
        this.CONSTANT=constant;
        this.genomeVariant= CONSTANT.genomeVariant;
        this.genome = new Genes(constant);
    }

    public Animal(IWorldMap map, Vector2d initialPosition, MapDirection initialOrientation, int startingEnergy, CONSTANT constant, Genes Genome1, Genes Genome2, int geneShare) {
        setParameters(map, initialPosition, initialOrientation, startingEnergy);
        this.CONSTANT=constant;
        this.genomeVariant= CONSTANT.genomeVariant;
        this.genome = new Genes(Genome1, Genome2, geneShare,constant);
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
        int timesToTurn=0;
        if(genomeVariant==GenomeVariants.GodsPlan){
            timesToTurn = genome.getGene(daysAlive%CONSTANT.GENOMELENGTH);
        }
        else if(genomeVariant==GenomeVariants.Mayhem){
            if(Math.random()>0.8){
                timesToTurn = genome.getGene((int)(Math.random()*CONSTANT.GENOMELENGTH));
            }
            else{
                timesToTurn = genome.getGene(daysAlive%CONSTANT.GENOMELENGTH);
            }
        }
        for (; timesToTurn > 0; timesToTurn--) {
            orientation = orientation.next(orientation);
        }
        addToSurvivalCounter();
        changeEnergy(-CONSTANT.PLUSENERGY/2);
        positionChanged(position, position.add(orientation.toUnitVector()));
    }
    public void addToSurvivalCounter(){
        this.daysAlive++;
    }

    // dodawanie i odejmowianie energii
    public void changeEnergy(int energyChange) {
        if (energyChange==CONSTANT.PLUSENERGY){
            amountOfGrassEaten+=1;
        }
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

    public void increaseNumberOfChildren(){
        numberOfChildren+=1;
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
    public void addToChildrenCount(Animal animal){
        animal.numberOfChildren+=1;
    }

    public String getImageResource() {
        if(energy<50){
            return "src/main/resources/animal3.png";
        }else if(energy<100){
            return "src/main/resources/animal2.png";
        }
        return "src/main/resources/animal1.png";
    }

    //jezeli nie maja energii zwroc null w przeciwnym przypadku usun energie rodzica i zrob dziecko, zwroc dziecko
    public Animal procreate(Animal parent1, Animal parent2) {
        if (parent1.getEnergy() >= CONSTANT.COSTTOCONCIEVECHILDREN && parent2.getEnergy() >= CONSTANT.COSTTOCONCIEVECHILDREN) {
            int geneShare = (int)((float)parent1.getEnergy() / ((float)parent1.getEnergy() + (float)parent2.getEnergy()) * 100);
            Animal child = new Animal(map, parent1.getPosition(), parent1.getOrientation(), CONSTANT.COSTTOCONCIEVECHILDREN * 2, CONSTANT, parent1.getGenome(), parent2.getGenome(), geneShare);
            child.getGenome().mutate();
            parent1.changeEnergy(-CONSTANT.COSTTOCONCIEVECHILDREN);
            parent2.changeEnergy(-CONSTANT.COSTTOCONCIEVECHILDREN);
            parent1.addToChildrenCount(parent1);
            parent2.addToChildrenCount(parent2);
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

    public int getAmountOfGrassEaten() {
        return amountOfGrassEaten;
    }

    public int getNumberOfChildren() {
        return numberOfChildren;
    }

    public int getDaysAlive() {
        return daysAlive;
    }
}
