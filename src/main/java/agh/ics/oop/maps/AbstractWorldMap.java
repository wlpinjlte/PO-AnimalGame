package agh.ics.oop.maps;

import agh.ics.oop.CONSTANT;
import agh.ics.oop.auxiliary.MapDirection;
import agh.ics.oop.auxiliary.Vector2d;
import agh.ics.oop.mapElements.*;

import java.util.*;

public class AbstractWorldMap implements IWorldMap, IPositionChangeObserver {
    protected MapStats mapStats;
    protected int simulationDay=0;
    protected final CONSTANT CONSTANT;
    protected final HashMap<Vector2d,LinkedList<Animal>> animalMap =new HashMap<>();
    protected final boolean[][]grassMap;
    protected final FieldStats [][]fieldStats;
    protected final Vector2d endOfMap;
    protected final Vector2d startOfMap=new Vector2d(0,0);
    GrassFactory grassFactory;
    public AbstractWorldMap(int height, int width,CONSTANT constant,MapStats mapStats){
        this.CONSTANT=constant;
        this.mapStats=mapStats;
        grassMap=new boolean[width][height];
        fieldStats= new FieldStats[width][height];
        endOfMap=new Vector2d(width-1,height-1);
        grassFactory=new GrassFactory(this,CONSTANT,mapStats);
        for(int i=0;i<width;i++){
            for(int j=0;j<height;j++){
                LinkedList<Animal> listAnimalToPut=new LinkedList<>();
                animalMap.put(new Vector2d(i,j),listAnimalToPut);
                grassMap[i][j]=false;
                fieldStats[i][j]=new FieldStats(new Vector2d(i,j));
            }
        }
    }
    public void increaseSimulationDay(){
        simulationDay+=1;
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
        mapStats.increaseAnimalCount();
    }

    @Override
    public void removeDeadAnimals() {
        for(int i=0;i< endOfMap.x();i++){
            for(int j=0;j< endOfMap.y();j++){
                Vector2d fieldToUpdate=new Vector2d(i,j);
                LinkedList<Animal>copyMapValue=(LinkedList<Animal>) animalMap.get(fieldToUpdate).clone();
                for(Animal animal:copyMapValue){
                    if(animal.getEnergy()<=0) {
                        mapStats.updateAverageLifespan(animal);
                        mapStats.increaseDeadAnimalCount();
                        animal.kill();
                        mapStats.decreaseAnimalCount();
                        animalMap.get(fieldToUpdate).remove(animal);
                        fieldStats[i][j].increasedDeathAnimals();
                        animal.removeObserver(this);
                    }
                }
            }
        }
    }
    public void countFreeSpaces(){
        int cnt=0;
        for(int i=0;i< endOfMap.x();i++){
            for(int j=0;j< endOfMap.y();j++) {
                Vector2d fieldToCount = new Vector2d(i, j);
                if(animalMap.get(fieldToCount).isEmpty()&&!grassMap[fieldToCount.x()][fieldToCount.y()]){
                    cnt+=1;
                }
            }
        }
        mapStats.updateFreeSpaces(cnt);
    }
    public void setAverageEnergy(){
        double cnt=0;
        double val=0;
        for(int i=0;i< endOfMap.x();i++){
            for(int j=0;j< endOfMap.y();j++){
                Vector2d fieldToUpdate=new Vector2d(i,j);
                LinkedList<Animal>copyMapValue=(LinkedList<Animal>) animalMap.get(fieldToUpdate).clone();
                for(Animal animal:copyMapValue) {
                    val += animal.getEnergy();
                    cnt+=1;
                }
            }
        }
        mapStats.setAverageEnergy(val/cnt);
    }

    @Override
    public void growingGrass() {
        grassFactory.generateGrass();
    }

    @Override
    public void eatingGrass(Vector2d positionToUpdate) {
        if(animalMap.get(positionToUpdate).size()>0&& grassMap[positionToUpdate.x()][positionToUpdate.y()]){
            Animal maxAnimal=new Animal(this,new Vector2d(0,0), MapDirection.NORTH,0,CONSTANT,simulationDay);
            for(Animal animal:animalMap.get(positionToUpdate)){
                if(maxAnimal.getEnergy()<animal.getEnergy()){
                    maxAnimal=animal;
                }
            }
            maxAnimal.changeEnergy(CONSTANT.PLUSENERGY);
            grassMap[positionToUpdate.x()][positionToUpdate.y()]=false;
            mapStats.decreaseGrassCount();
        }
    }

    @Override
    public void multiplication(Vector2d positionToUpdate) {
        if(animalMap.get(positionToUpdate).size()>1){
            Animal maxAnimal1=new Animal(this,new Vector2d(0,0), MapDirection.NORTH,0,CONSTANT,simulationDay);
            Animal maxAnimal2=new Animal(this,new Vector2d(0,0), MapDirection.NORTH,0,CONSTANT,simulationDay);
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
            Animal animalChild=maxAnimal1.procreate(maxAnimal1,maxAnimal2,simulationDay);
            if(animalChild!=null) {
                placeAnimal(animalChild);
            }
        }
    }
    public void setMeanNumberOfChildren(){
        double cnt=0;
        double val=0;
        for(int i=0;i< endOfMap.x();i++){
            for(int j=0;j< endOfMap.y();j++){
                Vector2d fieldToUpdate=new Vector2d(i,j);
                LinkedList<Animal>copyMapValue=(LinkedList<Animal>) animalMap.get(fieldToUpdate).clone();
                for(Animal animal:copyMapValue) {
                    val += animal.getNumberOfChildren();
                    cnt+=1;
                }
            }
        }
        mapStats.setMeanNumberOfChildren(val/cnt);
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
        if(!grassMap[grassToPlace.x()][grassToPlace.y()]){
            mapStats.increaseGrassCount();
        }
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
