package agh.ics.oop.maps;

import agh.ics.oop.CONSTANT;
import agh.ics.oop.auxiliary.MapDirection;
import agh.ics.oop.auxiliary.Vector2d;
import agh.ics.oop.mapElements.*;

import java.util.*;
import java.util.stream.Stream;

public class AbstractWorldMap implements IWorldMap, IPositionChangeObserver {
    protected final HashMap<Vector2d,LinkedList<Animal>> animalMap =new HashMap<>();
    protected final boolean[][]grassMap;
    protected final FieldStats [][]fieldStats;
    protected final Vector2d endOfMap;
    protected final Vector2d startOfMap=new Vector2d(0,0);
    GrassFactory grassFactory;
    public AbstractWorldMap(int height, int width){
        grassMap=new boolean[width][height];
        fieldStats= new FieldStats[width][height];
        endOfMap=new Vector2d(width-1,height-1);
        grassFactory=new GrassFactory(this);
        for(int i=0;i<width;i++){
            for(int j=0;j<height;j++){
                LinkedList<Animal> listAnimalToPut=new LinkedList<>();
                animalMap.put(new Vector2d(i,j),listAnimalToPut);
                grassMap[i][j]=false;
                fieldStats[i][j]=new FieldStats(new Vector2d(i,j));
            }
        }
    }
    //dodalem setter pozycji zwierzecia
    @Override
    public void positionChange(Animal elementToAdd, Vector2d oldPosition, Vector2d newPosition) {
        animalMap.get(oldPosition).remove(elementToAdd);
        animalMap.get(newPosition).add(elementToAdd);
        elementToAdd.setPosition(newPosition);
    }

    @Override
    public void placeAnimal(Animal animal) {
        animalMap.get(animal.getPosition()).add(animal);
        animal.addObserver(this);
    }

    @Override
    public void removeDeadAnimals() {
        for(int i=0;i< endOfMap.x();i++){
            for(int j=0;j< endOfMap.y();j++){
                Vector2d fieldToUpdate=new Vector2d(i,j);
                for(Animal animal:animalMap.get(fieldToUpdate)){
                    if(animal.getEnergy()==0) {
                        animalMap.get(fieldToUpdate).remove(animal);
                        fieldStats[i][j].increasedDeathAnimals();
                    }
                }
            }
        }
    }

    @Override
    public void growingGrass() {
        grassFactory.generateGrass();
    }

    @Override
    public void eatingGrass(Vector2d positionToUpdate) {
        if(animalMap.get(positionToUpdate).size()>0&& grassMap[positionToUpdate.x()][positionToUpdate.y()]){
            Animal maxAnimal=new Animal(this,new Vector2d(0,0), MapDirection.NORTH,0);
            for(Animal animal:animalMap.get(positionToUpdate)){
                if(maxAnimal.getEnergy()<animal.getEnergy()){
                    maxAnimal=animal;
                }
            }
            maxAnimal.changeEnergy(CONSTANT.PLUSENERGY);
            grassMap[positionToUpdate.x()][positionToUpdate.y()]=false;
        }
    }

    @Override
    public void multiplication(Vector2d positionToUpdate) {
        if(animalMap.get(positionToUpdate).size()>1){
            Animal maxAnimal1=new Animal(this,new Vector2d(0,0), MapDirection.NORTH,0);
            Animal maxAnimal2=new Animal(this,new Vector2d(0,0), MapDirection.NORTH,0);
            for(Animal animal:animalMap.get(positionToUpdate)){
                if(maxAnimal1.getEnergy()<animal.getEnergy()){
                    maxAnimal1=animal;
                }
            }
            for(Animal animal:animalMap.get(positionToUpdate)){
                if(maxAnimal2.getEnergy()<animal.getEnergy()&&maxAnimal1.getEnergy()!=animal.getEnergy()){
                    maxAnimal2=animal;
                }
            }
            Animal animalChild=maxAnimal1.procreate(maxAnimal1,maxAnimal2);
            if(animalChild!=null) {
                placeAnimal(animalChild);
            }
        }
    }

    @Override
    public void upadteMap() {
        for(int i=0;i< endOfMap.x();i++){
            for(int j=0;j< endOfMap.y();j++){
                Vector2d fieldToUpdate=new Vector2d(i,j);
                eatingGrass(fieldToUpdate);
                multiplication(fieldToUpdate);
            }
        }
        growingGrass();
        wys();
    }
    public void updateGrassField(Vector2d grassToPlace){
        grassMap[grassToPlace.x()][grassToPlace.y()]=true;
    }
    public boolean grassStatus(Vector2d positionToCheck){
        return grassMap[positionToCheck.x()][positionToCheck.y()];
    }
    public Vector2d getEndOfMap() {
        return endOfMap;
    }
    public FieldStats getFieldStatsPosition(Vector2d position){
        return fieldStats[position.x()][position.y()];
    }
    public LinkedList<Animal> animalsAt(Vector2d positionToCheck){
        return animalMap.get(positionToCheck);
    }

    public void moveAnimals(){
        LinkedList<LinkedList<Animal>> copyMapValue=new LinkedList<>();
        for(LinkedList<Animal> list: animalMap.values()){
            copyMapValue.add((LinkedList<Animal>) list.clone());
        }
        for (LinkedList<Animal> list : copyMapValue) {
            if(list.size()>0){
                for(Animal animal:list){
                    animal.move();
                }
            }
        }
    }
    //temp
    public void wys(){
        for(int i=0;i<=getEndOfMap().x();i++){
            for(int j=0;j<=getEndOfMap().y();j++){
                System.out.println(i+" "+j);
                System.out.println();
                System.out.println("grass"+grassMap[i][j]);
                for(Animal animal:animalMap.get(new Vector2d(i,j))){
                    System.out.println(animal);
                    System.out.println(animal.getEnergy());
                }
            }
        }
        System.out.println();
        System.out.println("koniec");
        System.out.println();
    }
}
