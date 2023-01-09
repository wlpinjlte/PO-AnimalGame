package agh.ics.oop.mapElements;

import agh.ics.oop.CONSTANT;

public class MapStats {
    private final CONSTANT CONSTANT;
    private int amountOfAnimals;
    private int amountOfGrass;
    private int amountOfDeadAnimals;
    private int freeSpaces;
    private double averageEnergy;
    private double averageLifespan;
    private double averageNumberOfChildren;

    public MapStats(CONSTANT constant, int freeSpaces) {
        CONSTANT = constant;
        amountOfAnimals = 0;
        averageEnergy = constant.PLUSENERGY*5;
        averageNumberOfChildren = 0;
        amountOfDeadAnimals = 0;
        averageLifespan = 0;
        amountOfGrass = 0;
        this.freeSpaces = freeSpaces;
    }

    public void updateFreeSpaces(int x) {
        freeSpaces = x;
    }

    public void increaseAnimalCount() {
        amountOfAnimals += 1;
    }

    public void decreaseAnimalCount() {
        amountOfAnimals -= 1;
    }

    public void increaseGrassCount() {
        amountOfGrass += 1;
    }

    public void decreaseGrassCount() {
        amountOfGrass -= 1;
    }

    public void increaseDeadAnimalCount() {
        amountOfDeadAnimals += 1;
    }

    public void updateAverageLifespan(Animal animal) {
        averageLifespan = (averageLifespan * ( amountOfDeadAnimals) + animal.getDaysAlive()) / (1 + amountOfDeadAnimals);
    }

    public void setMeanNumberOfChildren(double x) {
        averageNumberOfChildren=x;
    }

    public void setAverageEnergy(double x){
        averageEnergy = x;
    }

    public int getAmountOfAnimals() {
        return amountOfAnimals;
    }

    public int getAmountOfGrass() {
        return amountOfGrass;
    }

    public int getFreeSpaces() {
        return freeSpaces;
    }

    public double getAverageLifespan() {
        return Math.round(averageLifespan*100.0)/100.0;
    }

    public double getAverageNumberOfChildren() {
        return Math.round(averageNumberOfChildren*100.0)/100.0;
    }

    public double getAverageEnergy() {
        return Math.round(averageEnergy*100.0)/100.0;
    }

    public int getAmountOfDeadAnimals() {
        return amountOfDeadAnimals;
    }
}
