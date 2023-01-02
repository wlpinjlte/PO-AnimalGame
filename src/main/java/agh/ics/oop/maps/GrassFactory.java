package agh.ics.oop.maps;

import agh.ics.oop.auxiliary.Vector2d;

import java.lang.reflect.Array;
import java.util.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;
public class GrassFactory {
    int wariant=0;
    IWorldMap map;
    LinkedList<Vector2d> fertileFields=new LinkedList<>();
    LinkedList<Vector2d> remainingFields=new LinkedList<>();
    public GrassFactory(IWorldMap map){
        this.map=map;
    }
    public void generateGrass(){
        setFieldsStatus();
        int numberOfFields=(map.getEndOfMap().x()+1)*(map.getEndOfMap().y()+1);
        int NumberOfFiledToGrass=numberOfFields*2/10;
        System.out.println(NumberOfFiledToGrass);
        Vector2d fieldToGrowGrass;
        int i=0;
        while(i<NumberOfFiledToGrass){
            if(!fertileFields.isEmpty()&&!remainingFields.isEmpty()){
                int randomInt=(int)(Math.random()*5);
                if(randomInt==0){
                    fieldToGrowGrass=remainingFields.pop();
                }else{
                    fieldToGrowGrass=fertileFields.pop();
                }
            }else if(fertileFields.isEmpty()&&remainingFields.isEmpty()){
                break;
            }else{
                if(fertileFields.isEmpty()){
                    fieldToGrowGrass=remainingFields.pop();
                }else{
                    fieldToGrowGrass=fertileFields.pop();
                }
            }
            i++;
            map.updateGrassField(fieldToGrowGrass);
            System.out.println(fieldToGrowGrass.toString());
        }
    }

    private void setFieldsStatus(){
        if(wariant==0){
            int middleOfTheMap=map.getEndOfMap().y()/2;
            int range=map.getEndOfMap().y()/10;
            int start=middleOfTheMap-range;
            int end=middleOfTheMap+range;
            for(int i=0;i<=map.getEndOfMap().x();i++){
                for(int j=0;j<=map.getEndOfMap().y();j++){
                    if(map.grassStatus(new Vector2d(i,j))){
                        continue;
                    }
                    if(j>=start&&j<=end){
                        fertileFields.add(new Vector2d(i,j));
                    }else{
                        remainingFields.add(new Vector2d(i,j));
                    }
                }
            }
        }else if(wariant==1){
            ArrayList<Vector2d> allFields = new ArrayList<>();
            int numberOfTakingFields=((map.getEndOfMap().x()+1)*(map.getEndOfMap().y()+1)*2)/10;
            for(int i=0;i<=map.getEndOfMap().x();i++){
                for(int j=0;j<=map.getEndOfMap().y();j++){
                    allFields.add(new Vector2d(i,j));
                }
            }
            allFields.sort(new Comparator<Vector2d>(){
                @Override
                public int compare(Vector2d cordinates1, Vector2d cordinates2) {
                    if(map.getFieldStatsPosition(cordinates1).getDeathAnimals()!=map.getFieldStatsPosition(cordinates2).getDeathAnimals()){
                        return map.getFieldStatsPosition(cordinates1).getDeathAnimals()-map.getFieldStatsPosition(cordinates2).getDeathAnimals();
                    }
                    if (cordinates1.x() != cordinates2.x()) {
                        return cordinates1.x() - cordinates2.x();
                    }

                    if (cordinates1.y() != cordinates2.y()) {
                        return cordinates1.y() - cordinates2.y();
                    }
                    return 0;
                }
            });
            fertileFields= new LinkedList<>(allFields.subList(0, numberOfTakingFields));
            remainingFields=new LinkedList<>(allFields.subList(numberOfTakingFields, allFields.size()));
        }
    }
}