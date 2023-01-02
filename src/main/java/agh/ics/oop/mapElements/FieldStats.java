package agh.ics.oop.mapElements;

import agh.ics.oop.auxiliary.Vector2d;

public class FieldStats {
    private int deathAnimals;
    private final Vector2d fieldPosition;
    public FieldStats(Vector2d fieldPosition){
        this.fieldPosition=fieldPosition;
        deathAnimals=0;
    }

    public Vector2d getFieldPosition() {
        return fieldPosition;
    }

    public int getDeathAnimals() {
        return deathAnimals;
    }
    public void increasedDeathAnimals(){
        deathAnimals+=1;
    }
}
